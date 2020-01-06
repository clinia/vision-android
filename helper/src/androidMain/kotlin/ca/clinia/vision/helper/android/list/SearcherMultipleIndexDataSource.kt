package ca.clinia.vision.helper.android.list

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import ca.clinia.search.model.multipleindex.IndexQuery
import ca.clinia.search.model.response.ResponseSearch
import ca.clinia.vision.helper.searcher.SearcherMultipleIndex
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

public class SearcherMultipleIndexDataSource<T>(
    private val searcher: SearcherMultipleIndex,
    private val indexQuery: IndexQuery,
    private val transformer: (ResponseSearch.Record) -> T
) : PageKeyedDataSource<Int, T>() {

    public class Factory<T>(
        private val searcher: SearcherMultipleIndex,
        private val indexQuery: IndexQuery,
        private val transformer: (ResponseSearch.Record) -> T
    ) : DataSource.Factory<Int, T>() {

        override fun create(): DataSource<Int, T> {
            return SearcherMultipleIndexDataSource(searcher, indexQuery, transformer)
        }
    }

    private val index = searcher.queries.indexOf(indexQuery)
    private var initialLoadSize: Int = 30

    init {
        require(index != -1) { "The IndexQuery is not present in SearcherMultipleIndex" }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        initialLoadSize = params.requestedLoadSize
        indexQuery.query.hitsPerPage = initialLoadSize
        indexQuery.query.page = 0
        searcher.isLoading.value = true
        runBlocking {
            try {
                val response = searcher.search()
                val result = response.results[index]
                val nextKey = if (result.meta.total > initialLoadSize) 1 else null

                withContext(searcher.coroutineScope.coroutineContext) {
                    searcher.response.value = response
                    searcher.isLoading.value = false
                }
                callback.onResult(result.records.map(transformer), 0, result.meta.total, null, nextKey)
            } catch (throwable: Throwable) {
                withContext(searcher.coroutineScope.coroutineContext) {
                    searcher.error.value = throwable
                    searcher.isLoading.value = false
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val initialOffset = (initialLoadSize / params.requestedLoadSize) - 1
        val page = params.key + initialOffset

        indexQuery.query.page = page
        indexQuery.query.hitsPerPage = params.requestedLoadSize
        searcher.isLoading.value = true
        runBlocking {
            try {
                val response = searcher.search()
                val result = response.results[index]
                val nextKey = if (page + 1 < result.meta.total) params.key + 1 else null

                withContext(searcher.coroutineScope.coroutineContext) {
                    searcher.response.value = response
                    searcher.isLoading.value = false
                }
                callback.onResult(result.records.map(transformer), nextKey)
            } catch (throwable: Throwable) {
                withContext(searcher.coroutineScope.coroutineContext) {
                    searcher.error.value = throwable
                    searcher.isLoading.value = false
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) = Unit
}
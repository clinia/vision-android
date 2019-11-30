package searcher

import ca.clinia.search.model.IndexName
import ca.clinia.search.model.multipleindex.IndexQuery
import ca.clinia.search.model.response.ResponseSearches
import ca.clinia.vision.helper.searcher.SearcherMultipleIndex
import mockClient
import respondJson
import shouldFailWith
import kotlin.test.Test

class TestSearchMultipleIndex {

    private val client = mockClient(respondJson(ResponseSearches(listOf()), ResponseSearches.serializer()))
    private val indexA = IndexName("indexA")
    private val indexB = IndexName("indexB")
    private val indexC = IndexName("indexC")
    private val  queries = listOf(
        IndexQuery(indexA),
        IndexQuery(indexB)
    )
    private val searcher = SearcherMultipleIndex(client, queries)

//    @Test
//    fun connectWithWrongIndexShouldThrowException() {
//        IllegalArgumentException::class.shouldFailWith {
//
//        }
//    }

}
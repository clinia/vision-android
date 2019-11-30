package searcher

import ca.clinia.search.client.Index
import ca.clinia.search.model.Attribute
import ca.clinia.vision.helper.searcher.SearcherScope
import ca.clinia.vision.helper.searcher.SearcherSingleIndex
import kotlinx.coroutines.Dispatchers

val TestCoroutineScope = SearcherScope(Dispatchers.Default)

fun TestSearcherSingle(index: Index) = SearcherSingleIndex(
    index = index,
    coroutineScope = TestCoroutineScope
)


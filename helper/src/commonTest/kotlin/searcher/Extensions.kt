package searcher

import ca.clinia.search.client.ClientPlaces
import ca.clinia.search.client.ClientSearch
import ca.clinia.search.client.Index
import ca.clinia.vision.helper.searcher.*
import kotlinx.coroutines.Dispatchers

val TestCoroutineScope = SearcherScope(Dispatchers.Default)

fun TestSearcherSingle(index: Index) = SearcherSingleIndex(
    index = index,
    coroutineScope = TestCoroutineScope
)

val TestCoroutinePlaceScope = SearcherPlacesScope(Dispatchers.Default)

fun TestSearcherPlaces(clientPlaces: ClientPlaces) = SearcherPlaces(
    clientPlaces = clientPlaces,
    coroutineScope = TestCoroutinePlaceScope
)

val TestCoroutineQuerySuggestionsScope = SearcherQuerySuggestionsScope(Dispatchers.Default)

fun TestSearcherQuerySuggestions(clientSearch: ClientSearch) = SearcherQuerySuggestions(
    clientSearch = clientSearch,
    coroutineScope = TestCoroutineQuerySuggestionsScope
)
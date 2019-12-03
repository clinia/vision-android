package ca.clinia.vision.helper.searcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

public class SearcherQuerySuggestionsScope(val dispatcher: CoroutineDispatcher = defaultDispatcher) :
    CoroutineScope {

    override val coroutineContext = SupervisorJob() + dispatcher
}
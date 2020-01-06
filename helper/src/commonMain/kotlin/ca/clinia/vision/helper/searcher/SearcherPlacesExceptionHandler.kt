package ca.clinia.vision.helper.searcher

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

internal class SearcherPlacesExceptionHandler(
    private val searcherPlaces: SearcherPlaces
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        searcherPlaces.error.value = exception
        searcherPlaces.isLoading.value = false
    }
}
package ca.clinia.vision.helper.searcher

import ca.clinia.vision.core.searcher.Searcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

internal class SearcherExceptionHandler<R>(
    private val searcher: Searcher<R>
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        searcher.error.value = exception
        searcher.isLoading.value = false
    }
}
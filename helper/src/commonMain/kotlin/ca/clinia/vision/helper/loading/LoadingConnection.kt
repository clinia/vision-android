package ca.clinia.vision.helper.loading

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.loading.LoadingView
import ca.clinia.vision.core.loading.LoadingViewModel
import ca.clinia.vision.core.loading.connectView
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceLoadingInMillis

public fun <R> LoadingViewModel.connectSearcher(
    searcher: Searcher<R>,
    debouncer: Debouncer = Debouncer(debounceLoadingInMillis)
): Connection {
    return LoadingConnectionSearcher(this, searcher, debouncer)
}

public fun <R> LoadingConnector<R>.connectView(
    view: LoadingView
): Connection {
    return viewModel.connectView(view)
}
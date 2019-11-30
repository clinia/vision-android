package ca.clinia.vision.core.loading

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl

internal data class LoadingConnectionView(
    private val viewModel: LoadingViewModel,
    private val view: LoadingView
) : ConnectionImpl() {

    private val updateIsLoading: Callback<Boolean> = { isLoading ->
        view.setIsLoading(isLoading)
    }

    override fun connect() {
        super.connect()
        viewModel.isLoading.subscribePast(updateIsLoading)
        view.onReload = (viewModel.eventReload::send)
    }

    override fun disconnect() {
        super.disconnect()
        viewModel.isLoading.unsubscribe(updateIsLoading)
        view.onReload = null
    }
}
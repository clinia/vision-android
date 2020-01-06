package ca.clinia.vision.core.location

import ca.clinia.vision.core.connection.ConnectionImpl

internal data class LocationBoxConnectionView(
    private val viewModel: LocationBoxViewModel,
    private val view: LocationBoxView
) : ConnectionImpl() {

    override fun connect() {
        super.connect()

        view.setText(viewModel.query.value)
        view.onQueryChanged = (viewModel.query::value::set)
        view.onLocationSubmitted = {
            viewModel.query.value = it
            viewModel.eventSubmit.send(it)
        }
    }

    override fun disconnect() {
        super.disconnect()
        view.onQueryChanged = null
        view.onLocationSubmitted = null
    }
}
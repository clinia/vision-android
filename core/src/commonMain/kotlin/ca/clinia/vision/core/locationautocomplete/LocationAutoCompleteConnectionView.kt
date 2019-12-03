package ca.clinia.vision.core.locationautocomplete

import ca.clinia.vision.core.connection.ConnectionImpl

internal data class LocationAutoCompleteConnectionView(
    private val viewModel: LocationAutoCompleteViewModel,
    private val view: LocationAutoCompleteView
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
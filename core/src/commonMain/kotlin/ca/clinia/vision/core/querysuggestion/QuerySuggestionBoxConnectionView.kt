package ca.clinia.vision.core.querysuggestion

import ca.clinia.vision.core.connection.ConnectionImpl

internal data class QuerySuggestionBoxConnectionView(
    private val viewModel: QuerySuggestionViewModel,
    private val view: QuerySuggestionBoxView
) : ConnectionImpl() {

    override fun connect() {
        super.connect()
        view.setText(viewModel.query.value)
        view.onQueryChanged = (viewModel.query::value::set)
        view.onQuerySubmitted = {
            viewModel.query.value = it
            viewModel.eventSubmit.send(it)
        }
    }

    override fun disconnect() {
        super.disconnect()
        view.onQueryChanged = null
        view.onQuerySubmitted = null
    }
}
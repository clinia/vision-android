package ca.clinia.vision.core.searchbox

import ca.clinia.vision.core.connection.Connection

public fun SearchBoxViewModel.connectView(
    view: SearchBoxView
) : Connection {
    return SearchBoxConnectionView(this, view)
}
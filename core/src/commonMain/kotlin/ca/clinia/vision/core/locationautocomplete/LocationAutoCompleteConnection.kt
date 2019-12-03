package ca.clinia.vision.core.locationautocomplete

import ca.clinia.vision.core.connection.Connection

public fun LocationAutoCompleteViewModel.connectView(
    view: LocationAutoCompleteView
): Connection {
    return LocationAutoCompleteConnectionView(
        this,
        view
    )
}
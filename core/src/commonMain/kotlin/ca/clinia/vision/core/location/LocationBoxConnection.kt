package ca.clinia.vision.core.location

import ca.clinia.vision.core.connection.Connection

public fun LocationBoxViewModel.connectView(
    view: LocationBoxView
): Connection {
    return LocationBoxConnectionView(
        this,
        view
    )
}
package ca.clinia.vision.core.loading

import ca.clinia.vision.core.connection.Connection

public fun LoadingViewModel.connectView(view: LoadingView): Connection {
    return LoadingConnectionView(this, view)
}
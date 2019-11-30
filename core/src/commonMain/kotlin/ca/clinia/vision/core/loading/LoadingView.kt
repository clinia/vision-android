package ca.clinia.vision.core.loading

import ca.clinia.vision.core.Callback

public interface LoadingView {

    var onReload: Callback<Unit>?

    public fun setIsLoading(isLoading: Boolean)
}
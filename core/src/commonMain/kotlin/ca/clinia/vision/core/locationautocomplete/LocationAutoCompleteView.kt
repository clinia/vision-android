package ca.clinia.vision.core.locationautocomplete

import ca.clinia.vision.core.Callback

public interface LocationAutoCompleteView {

    public var onQueryChanged: Callback<String?>?
    public var onLocationSubmitted: Callback<String?>?

    public fun setText(text: String?, submitLocation: Boolean = false)
}
package ca.clinia.vision.core.location

import ca.clinia.vision.core.Callback

public interface LocationBoxView {

    public var onQueryChanged: Callback<String?>?
    public var onLocationSubmitted: Callback<String?>?

    public fun setText(text: String?, submitLocation: Boolean = false)
}
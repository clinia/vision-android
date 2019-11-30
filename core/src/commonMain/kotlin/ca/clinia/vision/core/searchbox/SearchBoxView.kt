package ca.clinia.vision.core.searchbox

import ca.clinia.vision.core.Callback

public interface SearchBoxView {

    public var onQueryChanged: Callback<String?>?
    public var onQuerySubmitted: Callback<String?>?

    public fun setText(text: String?, submitQuery: Boolean = false)
}
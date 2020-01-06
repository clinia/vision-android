package ca.clinia.vision.core.querysuggestion

import ca.clinia.vision.core.Callback

public interface QuerySuggestionBoxView {

    public var onQueryChanged: Callback<String?>?
    public var onQuerySubmitted: Callback<String?>?

    public fun setText(text: String?, submitQuery: Boolean = false)
}
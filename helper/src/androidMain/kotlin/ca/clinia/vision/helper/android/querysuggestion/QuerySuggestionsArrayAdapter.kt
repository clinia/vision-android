package ca.clinia.vision.helper.android.querysuggestion

import android.widget.ArrayAdapter
import ca.clinia.vision.core.querysuggestion.QuerySuggestionsView

public class QuerySuggestionsArrayAdapter<T>(
    public val adapter: ArrayAdapter<T>
) : QuerySuggestionsView<T> {

    override fun setSuggestions(suggestions: List<T>) {
        adapter.clear()
        adapter.addAll(suggestions)
    }
}
package ca.clinia.vision.helper.android.querysuggestion

import android.os.Build
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions

@RequiresApi(Build.VERSION_CODES.Q)
public fun <R, T> SearcherQuerySuggestions<R>.connectQuerySuggestionsArrayAdapter(
    adapter: QuerySuggestionsArrayAdapter<T>,
    view: AutoCompleteTextView,
    presenter: Presenter<R, List<T>>
): Connection {
    return QuerySuggestionsArrayAdapterConnection(this, adapter, view, presenter)
}
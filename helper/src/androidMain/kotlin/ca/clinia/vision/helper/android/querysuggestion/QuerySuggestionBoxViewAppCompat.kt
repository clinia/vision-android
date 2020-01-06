package ca.clinia.vision.helper.android.querysuggestion

import androidx.appcompat.widget.SearchView
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.querysuggestion.QuerySuggestionBoxView

public class QuerySuggestionBoxViewAppCompat(
    public val searchView: SearchView
) : QuerySuggestionBoxView {

    override var onQueryChanged: Callback<String?>? = null
    override var onQuerySubmitted: Callback<String?>? = null

    init {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                onQuerySubmitted?.invoke(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                onQueryChanged?.invoke(query)
                return false
            }
        })
    }

    override fun setText(text: String?, submitQuery: Boolean) {
        searchView.setQuery(text, submitQuery)
    }
}
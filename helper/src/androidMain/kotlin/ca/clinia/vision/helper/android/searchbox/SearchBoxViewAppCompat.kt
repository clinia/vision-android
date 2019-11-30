package ca.clinia.vision.helper.android.searchbox

import android.widget.SearchView
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.searchbox.SearchBoxView

public class SearchBoxViewAppCompat(
    public val searchView: SearchView
) : SearchBoxView {

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
package ca.clinia.vision.helper.android.locationautocomplete

import androidx.appcompat.widget.SearchView
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteView

public class LocationAutoCompleteViewAppCompat(
    public val searchView: SearchView
) : LocationAutoCompleteView {

    override var onQueryChanged: Callback<String?>? = null
    override var onLocationSubmitted: Callback<String?>? = null

    init {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                onLocationSubmitted?.invoke(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                onQueryChanged?.invoke(query)
                return false
            }
        })
    }

    override fun setText(text: String?, submitLocation: Boolean) {
        searchView.setQuery(text, submitLocation)
    }
}
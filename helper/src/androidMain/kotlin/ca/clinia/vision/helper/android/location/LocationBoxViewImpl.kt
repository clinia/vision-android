package ca.clinia.vision.helper.android.location

import android.widget.SearchView
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.location.LocationBoxView

public class LocationBoxViewImpl(
    public val searchView: SearchView
) : LocationBoxView {

    override var onQueryChanged: Callback<String?>? = null
    override var onLocationSubmitted: Callback<String?>? = null

    init {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(location: String?): Boolean {
                onLocationSubmitted?.invoke(location)
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
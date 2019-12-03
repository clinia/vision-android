package ca.clinia.vision.helper.android.locationautocomplete

import android.widget.ArrayAdapter
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteSuggestionsView

public class PlacesArrayAdapter<T>(
    public val adapter: ArrayAdapter<T>
) : LocationAutoCompleteSuggestionsView<T> {

    override fun setPlaces(places: List<T>) {
        adapter.clear()
        adapter.addAll(places)
    }
}
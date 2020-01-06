package ca.clinia.vision.helper.android.location

import android.widget.ArrayAdapter
import ca.clinia.vision.core.location.PlacesView

public class PlacesArrayAdapter<T>(
    public val adapter: ArrayAdapter<T>
) : PlacesView<T> {

    override fun setPlaces(places: List<T>) {
        adapter.clear()
        adapter.addAll(places)
    }
}
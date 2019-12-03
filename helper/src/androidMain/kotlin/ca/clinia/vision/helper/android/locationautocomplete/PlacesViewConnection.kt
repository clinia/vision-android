package ca.clinia.vision.helper.android.locationautocomplete

import android.os.Build
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searcher.SearcherPlaces

@RequiresApi(Build.VERSION_CODES.Q)
public fun <R, T> SearcherPlaces<R>.connectPlacesArrayAdapter(
    adapter: PlacesArrayAdapter<T>,
    view: AutoCompleteTextView,
    presenter: Presenter<R, List<T>>
): Connection {
    return PlacesArrayAdapterConnection<R, T>(this, adapter, view, presenter)
}
package ca.clinia.vision.helper.android.hits

import android.os.Build
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searcher.Searcher

@RequiresApi(Build.VERSION_CODES.Q)
public fun <R, T> Searcher<R>.connectHitsArrayAdapter(
    adapter: HitsArrayAdapter<T>,
    view: AutoCompleteTextView,
    presenter: Presenter<R, List<T>>
): Connection {
    return HitsArrayAdapterConnection<R, T>(this, adapter, view, presenter)
}
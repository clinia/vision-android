package ca.clinia.vision.helper.android.locationautocomplete

import android.os.Build
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searcher.SearcherPlaces

@RequiresApi(Build.VERSION_CODES.Q)
internal data class PlacesArrayAdapterConnection<R, T>(
    private val searcher: SearcherPlaces<R>,
    private val adapter: PlacesArrayAdapter<T>,
    private val view: AutoCompleteTextView,
    private val presenter: Presenter<R, List<T>>
) : ConnectionImpl() {

    init {
        view.setAdapter(adapter.adapter)
    }

    private val callback: Callback<R?> = { response ->
        if (response != null) {
            adapter.adapter.apply {
                setNotifyOnChange(false)
                clear()
                addAll(presenter(response))
                notifyDataSetChanged()
            }
            view.refreshAutoCompleteResults()
        }
    }

    override fun connect() {
        super.connect()
        searcher.response.subscribe(callback)
    }

    override fun disconnect() {
        super.disconnect()
        searcher.response.unsubscribe(callback)
    }
}
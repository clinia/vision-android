package ca.clinia.vision.helper.android.hits

import android.widget.ArrayAdapter
import ca.clinia.vision.core.hits.HitsView

public class HitsArrayAdapter<T>(
    public val adapter: ArrayAdapter<T>
) : HitsView<T> {

    override fun setRecords(records: List<T>) {
        adapter.clear()
        adapter.addAll(records)
    }
}
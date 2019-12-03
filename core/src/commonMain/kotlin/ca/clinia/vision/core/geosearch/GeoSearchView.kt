package ca.clinia.vision.core.geosearch

import ca.clinia.vision.core.Callback

public interface GeoSearchView<T> {

    public var onInsideBoundingBoxChanged: Callback<String?>?
    public var onInsideBoundingBoxSubmitted: Callback<String?>?

    public fun setBoundingBox(text: String?, submitQuery: Boolean = false)
    public fun setRecords(records: List<T>)
}
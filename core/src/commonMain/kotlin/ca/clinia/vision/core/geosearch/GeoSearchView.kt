package ca.clinia.vision.core.geosearch

import ca.clinia.search.model.search.BoundingBox
import ca.clinia.vision.core.Callback

public interface GeoSearchView<T> {

    public var onInsideBoundingBoxChanged: Callback<BoundingBox?>?
    public var onInsideBoundingBoxSubmitted: Callback<BoundingBox?>?

    public fun setBoundingBox(boundingBox: BoundingBox?, submitQuery: Boolean = false)
    public fun setRecords(records: List<T>)
}
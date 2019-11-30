package index

import ca.clinia.search.model.IndexName
import ca.clinia.vision.helper.index.IndexPresenterImpl
import mockClient
import shouldEqual
import kotlin.test.Test

class TestIndexPresenter {

    private val indexName = IndexName("name")
    private val client = mockClient()
    private val index = client.initIndex(indexName)

    @Test
    fun impl() {
        IndexPresenterImpl(index) shouldEqual indexName.raw
    }
}
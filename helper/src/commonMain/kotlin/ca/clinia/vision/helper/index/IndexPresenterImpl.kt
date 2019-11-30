package ca.clinia.vision.helper.index

import ca.clinia.search.client.Index

public object IndexPresenterImpl : IndexPresenter {

    override fun invoke(index: Index): String {
        return index.indexName.raw
    }
}
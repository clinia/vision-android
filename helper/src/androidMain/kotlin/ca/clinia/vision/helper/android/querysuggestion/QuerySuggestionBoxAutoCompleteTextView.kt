package ca.clinia.vision.helper.android.querysuggestion

import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.querysuggestion.QuerySuggestionBoxView
import ca.clinia.vision.core.searchbox.SearchBoxView

public class QuerySuggestionBoxAutoCompleteTextView(
    public val autoCompleteTextView: AutoCompleteTextView
) : QuerySuggestionBoxView {

    override var onQueryChanged: Callback<String?>? = null
    override var onQuerySubmitted: Callback<String?>? = null

    init {
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onQueryChanged?.invoke(s?.toString())
            }
        })
    }

    override fun setText(text: String?, submitQuery: Boolean) {
        autoCompleteTextView.setText(text)
        if (submitQuery) onQuerySubmitted?.invoke(text)
    }
}
package ca.clinia.vision.helper.android.searchbox

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.searchbox.SearchBoxView

public class SearchBoxViewEditText(
    public val editText: EditText
) : SearchBoxView {

    override var onQueryChanged: Callback<String?>? = null
    override var onQuerySubmitted: Callback<String?>? = null

    init {
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onQueryChanged?.invoke(s?.toString())
            }
        })
    }

    override fun setText(text: String?, submitQuery: Boolean) {
        editText.setText(text)
        if (submitQuery) onQuerySubmitted?.invoke(text)
    }
}
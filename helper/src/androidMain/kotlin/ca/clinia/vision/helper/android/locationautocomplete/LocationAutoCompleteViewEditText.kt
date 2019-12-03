package ca.clinia.vision.helper.android.locationautocomplete

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteView

public class LocationAutoCompleteViewEditText(
    public val editText: EditText
) : LocationAutoCompleteView {

    override var onQueryChanged: Callback<String?>? = null
    override var onLocationSubmitted: Callback<String?>? = null

    init {
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onQueryChanged?.invoke(s?.toString())
            }
        })
    }

    override fun setText(text: String?, submitLocation: Boolean) {
        editText.setText(text)
        if (submitLocation) onLocationSubmitted?.invoke(text)
    }
}
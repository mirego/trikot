package com.mirego.trikot.metaviews

import android.text.Editable
import android.text.InputType.TYPE_CLASS_DATETIME
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_PHONE
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_DATETIME_VARIATION_DATE
import android.text.InputType.TYPE_DATETIME_VARIATION_NORMAL
import android.text.InputType.TYPE_DATETIME_VARIATION_TIME
import android.text.InputType.TYPE_NULL
import android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
import android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import com.mirego.trikot.metaviews.android.ktx.R
import com.mirego.trikot.metaviews.mutable.MutableMetaInputText
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaInputType
import com.mirego.trikot.streams.android.ktx.observe
import com.mirego.trikot.streams.reactive.just

object MetaInputTextBinder {

    val NoMetaInputText = MutableMetaInputText().apply { hidden = true.just() } as MetaInputText

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(
        editText: EditText,
        metaInputText: MetaInputText?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        editText.bindMetaView(metaInputText, lifecycleOwnerWrapper)
        (metaInputText ?: NoMetaInputText).let {
            it.let {
                addTextWatcher(it, editText)
                it.placeholderText.observe(lifecycleOwnerWrapper.lifecycleOwner) { hint ->
                    editText.hint = hint
                }

                it.userInput.observe(lifecycleOwnerWrapper.lifecycleOwner) { textValue ->
                    if (textValue != editText.text.toString()) editText.setText(textValue)
                }

                it.textColor.observe(lifecycleOwnerWrapper.lifecycleOwner) { textColor ->
                    if (textColor != Color.None) {
                        editText.setTextColor(textColor.toIntColor())
                    }
                }

                it.inputType.observe(lifecycleOwnerWrapper.lifecycleOwner) { inputType ->
                    editText.inputType =
                        when (inputType) {
                            MetaInputType.DATE ->
                                TYPE_DATETIME_VARIATION_DATE or
                                    TYPE_CLASS_DATETIME
                            MetaInputType.DATETIME ->
                                TYPE_DATETIME_VARIATION_NORMAL or
                                    TYPE_CLASS_DATETIME
                            MetaInputType.EMAIL ->
                                TYPE_TEXT_VARIATION_EMAIL_ADDRESS or
                                    TYPE_CLASS_TEXT
                            MetaInputType.NUMBER ->
                                TYPE_CLASS_NUMBER
                            MetaInputType.TEXT ->
                                TYPE_TEXT_FLAG_CAP_SENTENCES or
                                    TYPE_CLASS_TEXT or
                                    TYPE_TEXT_FLAG_NO_SUGGESTIONS
                            MetaInputType.PASSWORD ->
                                TYPE_TEXT_VARIATION_PASSWORD or
                                    TYPE_CLASS_TEXT
                            MetaInputType.PHONE_NUMBER ->
                                TYPE_CLASS_PHONE
                            MetaInputType.TIME ->
                                TYPE_DATETIME_VARIATION_TIME or
                                    TYPE_CLASS_DATETIME
                            MetaInputType.MULTILINE ->
                                TYPE_TEXT_FLAG_CAP_SENTENCES or
                                        TYPE_CLASS_TEXT or
                                        TYPE_TEXT_FLAG_NO_SUGGESTIONS or
                                        TYPE_TEXT_FLAG_MULTI_LINE
                            else -> TYPE_NULL
                        }
                }

                editText.bindMetaView(it, lifecycleOwnerWrapper)
            }
        }
    }

    private fun addTextWatcher(
        metaInputText: MetaInputText,
        editText: EditText
    ) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                metaInputText.setUserInput(editText.text.toString())
            }
        }

        ListenerUtil.trackListener<TextWatcher>(editText, textWatcher, R.id.textWatcher)
            ?.let { editText.removeTextChangedListener(it) }

        editText.addTextChangedListener(textWatcher)
    }
}

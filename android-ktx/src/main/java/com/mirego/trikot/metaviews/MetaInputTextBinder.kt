package com.mirego.trikot.metaviews

import android.text.Editable
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_NULL
import android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
import android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import com.mirego.trikot.metaviews.android.ktx.R
import com.mirego.trikot.metaviews.mutable.MutableMetaInputText
import com.mirego.trikot.metaviews.properties.MetaInputType
import com.mirego.trikot.streams.android.ktx.observe

object MetaInputTextBinder {

    val NoMetaInputText = MutableMetaInputText().apply { hidden.value = true } as MetaInputText

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(
        editText: EditText,
        metaInputText: MetaInputText?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (metaInputText ?: NoMetaInputText).let {
            it.let {
                addTextWatcher(it, editText)
                it.placeholderText.observe(lifecycleOwnerWrapper.lifecycleOwner) { hint ->
                    editText.hint = hint
                }

                it.userInput.observe(lifecycleOwnerWrapper.lifecycleOwner) { textValue ->
                    if (textValue != editText.text.toString()) editText.setText(textValue)
                }

                it.inputType.observe(lifecycleOwnerWrapper.lifecycleOwner) { inputType ->
                    editText.inputType =
                        when (inputType) {
                            MetaInputType.EMAIL ->
                                TYPE_TEXT_VARIATION_EMAIL_ADDRESS or
                                    TYPE_CLASS_TEXT
                            MetaInputType.TEXT ->
                                TYPE_TEXT_FLAG_CAP_SENTENCES or
                                    TYPE_CLASS_TEXT or
                                    TYPE_TEXT_FLAG_NO_SUGGESTIONS
                            MetaInputType.PASSWORD ->
                                TYPE_TEXT_VARIATION_PASSWORD or
                                    TYPE_CLASS_TEXT
                            else -> TYPE_NULL
                        }
                }

                MetaLabelBinder.bindWithoutTextPublishers(
                    editText, metaInputText, lifecycleOwnerWrapper
                )
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

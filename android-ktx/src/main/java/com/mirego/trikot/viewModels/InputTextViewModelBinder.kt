package com.mirego.trikot.viewmodels

import android.text.Editable
import android.text.InputType.TYPE_CLASS_DATETIME
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_PHONE
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_DATETIME_VARIATION_DATE
import android.text.InputType.TYPE_DATETIME_VARIATION_NORMAL
import android.text.InputType.TYPE_DATETIME_VARIATION_TIME
import android.text.InputType.TYPE_NULL
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
import android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
import android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import com.mirego.trikot.streams.android.ktx.asLiveData
import com.mirego.trikot.viewmodels.android.ktx.R
import com.mirego.trikot.viewmodels.mutable.MutableInputTextViewModel
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.InputTextType
import com.mirego.trikot.streams.android.ktx.observe
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.properties.ViewModelAction

object InputTextViewModelBinder {

    val NoInputTextViewModel = MutableInputTextViewModel().apply { hidden = true.just() } as InputTextViewModel

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        editText: EditText,
        InputTextViewModel: InputTextViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        editText.bindViewModel(InputTextViewModel, lifecycleOwnerWrapper)
        (InputTextViewModel ?: NoInputTextViewModel).let {
            it.let {
                addTextWatcher(it, editText)
                it.placeholderText.observe(lifecycleOwnerWrapper.lifecycleOwner) { hint ->
                    editText.hint = hint
                }

                it.userInput.observe(lifecycleOwnerWrapper.lifecycleOwner) { textValue ->
                    editText.noTextWatcher {
                        if (textValue != editText.text.toString()) editText.setText(textValue)
                    }
                }

                it.textColor.observe(lifecycleOwnerWrapper.lifecycleOwner) { textColor ->
                    if (textColor != Color.None) {
                        editText.setTextColor(textColor.toIntColor())
                    }
                }

                it.enabled
                    .asLiveData()
                    .observe(lifecycleOwnerWrapper.lifecycleOwner) { editText.isEnabled = it }

                it.inputType.observe(lifecycleOwnerWrapper.lifecycleOwner) { inputType ->
                    editText.noTextWatcher {
                        editText.inputType =
                            when (inputType) {
                                InputTextType.DATE ->
                                    TYPE_DATETIME_VARIATION_DATE or
                                            TYPE_CLASS_DATETIME
                                InputTextType.DATETIME ->
                                    TYPE_DATETIME_VARIATION_NORMAL or
                                            TYPE_CLASS_DATETIME
                                InputTextType.EMAIL ->
                                    TYPE_TEXT_VARIATION_EMAIL_ADDRESS or
                                            TYPE_CLASS_TEXT
                                InputTextType.NUMBER ->
                                    TYPE_CLASS_NUMBER
                                InputTextType.NUMBER_DECIMAL ->
                                    TYPE_CLASS_NUMBER or
                                            TYPE_NUMBER_FLAG_DECIMAL
                                InputTextType.TEXT ->
                                    TYPE_TEXT_FLAG_CAP_SENTENCES or
                                            TYPE_CLASS_TEXT or
                                            TYPE_TEXT_FLAG_NO_SUGGESTIONS
                                InputTextType.PASSWORD ->
                                    TYPE_TEXT_VARIATION_PASSWORD or
                                            TYPE_CLASS_TEXT
                                InputTextType.PHONE_NUMBER ->
                                    TYPE_CLASS_PHONE
                                InputTextType.TIME ->
                                    TYPE_DATETIME_VARIATION_TIME or
                                            TYPE_CLASS_DATETIME
                                InputTextType.MULTILINE ->
                                    TYPE_TEXT_FLAG_CAP_SENTENCES or
                                            TYPE_CLASS_TEXT or
                                            TYPE_TEXT_FLAG_NO_SUGGESTIONS or
                                            TYPE_TEXT_FLAG_MULTI_LINE
                                else -> TYPE_NULL
                            }
                    }
                }

                it.editorAction.observe(lifecycleOwnerWrapper.lifecycleOwner) { action ->
                    when (action) {
                        ViewModelAction.None -> {
                            editText.setOnEditorActionListener(null)
                        }
                        else -> editText.setOnEditorActionListener { v, actionId, event ->
                            action.execute(v)
                        }
                    }
                }

                editText.bindViewModel(it, lifecycleOwnerWrapper)
            }
        }
    }

    private fun EditText.noTextWatcher(block: () -> Unit) {
        val textWatcher = ListenerUtil.getListener<TextWatcher>(this, R.id.textWatcher)
            ?.also { removeTextChangedListener(it) }
        block()
        textWatcher?.let {
            ListenerUtil.trackListener<TextWatcher>(this, it, R.id.textWatcher)
            addTextChangedListener(it)
        }
    }

    private fun addTextWatcher(
        InputTextViewModel: InputTextViewModel,
        editText: EditText
    ) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                InputTextViewModel.setUserInput(editText.text.toString())
            }
        }

        ListenerUtil.trackListener<TextWatcher>(editText, textWatcher, R.id.textWatcher)
            ?.let { editText.removeTextChangedListener(it) }

        editText.addTextChangedListener(textWatcher)
    }
}

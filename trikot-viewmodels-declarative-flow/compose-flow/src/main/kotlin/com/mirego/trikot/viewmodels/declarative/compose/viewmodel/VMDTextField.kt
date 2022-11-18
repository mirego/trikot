package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.mirego.trikot.viewmodels.declarative.components.VMDTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.composeValue
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal.FormattedVisualTransformation

@Composable
fun VMDTextField(
    modifier: Modifier = Modifier,
    viewModel: VMDTextFieldViewModel,
    textStyle: TextStyle = LocalTextStyle.current,
    placeHolderStyle: TextStyle = LocalTextStyle.current,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    isError: Boolean = false,
    textFieldColors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
    val textFieldViewModel: VMDTextFieldViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())
    val keyboardActionsDelegate = buildKeyboardActions(viewModel, keyboardActions)

    TextField(
        modifier = Modifier
            .hidden(textFieldViewModel.isHidden)
            .then(modifier),
        value = textFieldViewModel.text,
        onValueChange = { value ->
            viewModel.onValueChange(value)
        },
        enabled = textFieldViewModel.isEnabled,
        placeholder = {
            Text(
                text = textFieldViewModel.placeholder,
                style = placeHolderStyle
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = textFieldColors,
        textStyle = textStyle,
        keyboardActions = keyboardActionsDelegate,
        keyboardOptions = KeyboardOptions(
            keyboardType = textFieldViewModel.keyboardType.composeValue,
            capitalization = textFieldViewModel.autoCapitalization.composeValue,
            imeAction = textFieldViewModel.keyboardReturnKeyType.composeValue
        ),
        singleLine = singleLine,
        isError = isError,
        visualTransformation = FormattedVisualTransformation(viewModel.formatText)
    )
}

fun buildKeyboardActions(viewModel: VMDTextFieldViewModel, keyboardActions: KeyboardActions) =
    KeyboardActions(
        onDone = {
            viewModel.onReturnKeyTap.invoke()
            keyboardActions.onDone?.invoke(this)
        },
        onNext = {
            viewModel.onReturnKeyTap.invoke()
            keyboardActions.onNext?.invoke(this)
        },
        onGo = {
            viewModel.onReturnKeyTap.invoke()
            keyboardActions.onGo?.invoke(this)
        },
        onPrevious = {
            viewModel.onReturnKeyTap.invoke()
            keyboardActions.onPrevious?.invoke(this)
        },
        onSearch = {
            viewModel.onReturnKeyTap.invoke()
            keyboardActions.onSearch?.invoke(this)
        },
        onSend = {
            viewModel.onReturnKeyTap.invoke()
            keyboardActions.onSend?.invoke(this)
        },
    )

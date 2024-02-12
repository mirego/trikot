package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.mirego.trikot.viewmodels.declarative.components.VMDTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.composeValue
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal.FormattedVisualTransformation

@Composable
fun VMDTextField(
    viewModel: VMDTextFieldViewModel,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    placeHolderStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable ((String) -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions(),
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
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
        label = label,
        placeholder = {
            placeholder?.invoke(textFieldViewModel.placeholder) ?: Text(
                text = textFieldViewModel.placeholder,
                style = placeHolderStyle
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle = textStyle,
        isError = isError,
        visualTransformation = FormattedVisualTransformation(viewModel.formatText),
        keyboardActions = keyboardActionsDelegate,
        keyboardOptions = KeyboardOptions(
            keyboardType = textFieldViewModel.keyboardType.composeValue,
            capitalization = textFieldViewModel.autoCapitalization.composeValue,
            imeAction = textFieldViewModel.keyboardReturnKeyType.composeValue
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = textFieldColors
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
        }
    )

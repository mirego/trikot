package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import com.mirego.trikot.viewmodels.declarative.components.VMDTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.composeValue
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal.FormattedVisualTransformation

@ExperimentalMaterialApi
@Composable
fun VMDBasicTextField(
    viewModel: VMDTextFieldViewModel,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    placeHolderStyle: TextStyle = LocalTextStyle.current,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    isError: Boolean = false,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    cursorBrush: Brush = SolidColor(colors.cursorColor(isError).value),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = TextFieldDefaults.textFieldWithoutLabelPadding()
) {
    val textFieldViewModel: VMDTextFieldViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())
    val keyboardActionsDelegate = buildKeyboardActions(viewModel, keyboardActions)

    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(viewModel.isEnabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    val visualTransformation = FormattedVisualTransformation(viewModel.formatText)

    BasicTextField(
        value = viewModel.text,
        onValueChange = { value: String ->
            viewModel.onValueChange(value)
        },
        modifier = modifier.vmdModifier(viewModel),
        enabled = textFieldViewModel.isEnabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        keyboardOptions = KeyboardOptions(
            keyboardType = textFieldViewModel.keyboardType.composeValue,
            capitalization = textFieldViewModel.autoCapitalization.composeValue,
            imeAction = textFieldViewModel.keyboardReturnKeyType.composeValue
        ),
        keyboardActions = keyboardActionsDelegate,
        maxLines = maxLines,
        minLines = minLines,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = viewModel.text,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = textFieldViewModel.placeholder,
                        style = placeHolderStyle
                    )
                },
                singleLine = singleLine,
                enabled = viewModel.isEnabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    )
}

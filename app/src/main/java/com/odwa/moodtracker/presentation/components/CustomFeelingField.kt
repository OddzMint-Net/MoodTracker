package com.odwa.moodtracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.R

@Composable
fun CustomFeelingField(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val focusManager = LocalFocusManager.current
    val canSend = text.isNotBlank()
    val shape = RoundedCornerShape(16.dp)

    fun send() {
        if (!canSend) return
        onSend()
        focusManager.clearFocus()
    }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.onSurface),
        cursorBrush = SolidColor(colors.primary),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(onSend = { send() }),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .clip(shape)
                    .background(colors.surface)
                    .border(1.dp, colors.outlineVariant, shape)
                    .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.custom_feeling_hint),
                            style = MaterialTheme.typography.bodyLarge,
                            color = colors.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                    innerTextField()
                }
                FilledIconButton(
                    onClick = ::send,
                    enabled = canSend,
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary,
                        disabledContainerColor = colors.onSurface.copy(alpha = 0.12f),
                        disabledContentColor = colors.onSurface.copy(alpha = 0.38f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.custom_feeling_send),
                        modifier = Modifier.size(20.dp).rotate(-90f)
                    )
                }
            }
        }
    )
}

@Preview(name = "Long text", showBackground = true)
@Composable
private fun CustomFeelingFieldLongTextPreview() {
    MaterialTheme {
        CustomFeelingField(
            text = "Anxious about tomorrow",
            onTextChange = {},
            onSend = {},
            modifier = Modifier.padding(16.dp)
        )
    }

}
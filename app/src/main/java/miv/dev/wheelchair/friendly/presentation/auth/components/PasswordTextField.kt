package miv.dev.wheelchair.friendly.presentation.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.Dialpad
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import miv.dev.wheelchair.friendly.R

@Composable
fun PasswordTextField(value: String, onValueChange: (String) -> Unit, imeAction: ImeAction) {
	var showPassword by remember {
		mutableStateOf(false)
	}
	OutlinedTextField(
		label = {
			Text(text = stringResource(id = R.string.label_password))
		},
		leadingIcon = {
			Icon(Icons.Rounded.Dialpad, contentDescription = stringResource(id = R.string.label_email))
		},
		maxLines = 1,
		value = value,
		onValueChange = onValueChange,
		modifier = Modifier.fillMaxWidth(),
		singleLine = true,
		keyboardOptions = KeyboardOptions(
			imeAction = imeAction,
			keyboardType = KeyboardType.Password
		),
		visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
		trailingIcon = {
			IconButton(onClick = { showPassword = !showPassword }) {
				Icon(
					imageVector =
					if (showPassword)
						Icons.Outlined.VisibilityOff
					else Icons.Outlined.Visibility, contentDescription = "Show password"
				)
			}
		}
	)
}

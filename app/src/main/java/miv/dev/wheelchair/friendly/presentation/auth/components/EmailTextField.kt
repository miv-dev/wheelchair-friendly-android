package miv.dev.wheelchair.friendly.presentation.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import miv.dev.wheelchair.friendly.R


@Composable
fun EmailTextField(
	value: String, onValueChange: (String) -> Unit, imeAction: ImeAction
) {
	OutlinedTextField(
		label = {
			Text(text = stringResource(id = R.string.label_email))
		},
		leadingIcon = {
			Icon(Icons.Rounded.AlternateEmail, contentDescription = stringResource(id = R.string.label_email))
		},
		singleLine = true,
		value = value, onValueChange = onValueChange, modifier = Modifier.fillMaxWidth(),
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Email,
			imeAction = imeAction
		),
		
		)
}

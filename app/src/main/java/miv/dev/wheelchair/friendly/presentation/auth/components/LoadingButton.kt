package miv.dev.wheelchair.friendly.presentation.auth.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
	onClick: () -> Unit,
	enabled: Boolean,
	showError: Boolean,
	isLoading: Boolean,
	label: String
) {
	val theme = MaterialTheme.colorScheme
	
	
	FilledTonalButton(
		modifier = Modifier.fillMaxWidth(),
		onClick = onClick,
		enabled = enabled,
		colors = if (showError) ButtonDefaults.filledTonalButtonColors(
			containerColor = theme.errorContainer,
			contentColor = theme.onErrorContainer
		) else ButtonDefaults.filledTonalButtonColors()
	) {
		AnimatedVisibility(visible = isLoading) {
			CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
		}
		AnimatedVisibility(visible = showError) {
			Icon(imageVector = Icons.Outlined.Error, contentDescription = "Error")
		}
		Text(
			text = label ,
			modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
		)
	}
	
}

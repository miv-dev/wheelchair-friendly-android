package miv.dev.wheelchair.friendly.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import miv.dev.wheelchair.friendly.R
import miv.dev.wheelchair.friendly.getApplicationComponent

@Composable
fun ProfileScreen() {
	val component = getApplicationComponent()
	val vm: ProfileScreenViewModel = viewModel(factory = component.getViewModelFactory())
	val screenState = vm.screenState.collectAsState(initial = ProfileScreenState.Initial)
	ProfileScreenContent(viewModel = vm, screenState = screenState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
	viewModel: ProfileScreenViewModel,
	screenState: State<ProfileScreenState>
) {
	
	LaunchedEffect(Unit){
		viewModel.fetchUser()
	}
	val theme = MaterialTheme.colorScheme
	
	Column(
		Modifier
			.padding(horizontal = 16.dp)
			.fillMaxSize(),
		verticalArrangement = Arrangement.spacedBy(10.dp)
	) {
		
		when (val state = screenState.value) {
			is ProfileScreenState.Error ->
				Box(Modifier.fillMaxSize()) {
					Column {
						Icon(imageVector = Icons.Rounded.BugReport, contentDescription = "Error")
						Text(text = state.msg)
					}
				}
			
			ProfileScreenState.Initial,
			ProfileScreenState.Loading -> CircularProgressIndicator()
			
			is ProfileScreenState.Profile -> {
				Text(text = state.user.username)
			}
		}
		
		ElevatedCard(
			colors = CardDefaults.elevatedCardColors(containerColor = theme.errorContainer),
			onClick = viewModel::logout
		) {
			ListItem(
				headlineContent = {
					Text(text = stringResource(id = R.string.btn_logout))
				},
				trailingContent = {
					Icon(
						imageVector = Icons.Outlined.Logout,
						contentDescription = stringResource(id = R.string.btn_logout)
					)
				},
				colors = ListItemDefaults.colors(
					containerColor = Color.Transparent,
					headlineColor = theme.onErrorContainer,
					trailingIconColor = theme.onErrorContainer
				
				)
			)
		}
	}
}

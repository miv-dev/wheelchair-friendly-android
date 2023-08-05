package miv.dev.wheelchair.friendly.presentation.screens.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
	
	LaunchedEffect(Unit) {
		viewModel.fetchUser()
	}
	val theme = MaterialTheme.colorScheme
	
	AnimatedContent(targetState = screenState.value, label = "screen") {
		when (val state = it) {
			is ProfileScreenState.Error ->
				Column(
					Modifier.fillMaxSize(),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					ElevatedCard(
						Modifier
							.fillMaxWidth(0.5f)
							.aspectRatio(3f / 4f),
						colors = CardDefaults.elevatedCardColors(theme.errorContainer, theme.onErrorContainer)
					) {
						Column(
							horizontalAlignment = Alignment.CenterHorizontally,
							modifier = Modifier
								.fillMaxSize()
								.padding(20.dp),
						) {
							Icon(
								imageVector = Icons.Rounded.BugReport,
								contentDescription = "Error",
								modifier = Modifier
									.size(100.dp)
									.weight(1f)
							)
							Text(text = state.msg)
							ElevatedButton(
								onClick = viewModel::fetchUser,
								colors = ButtonDefaults.elevatedButtonColors(containerColor = theme.error, contentColor = theme.onError)
							) {
								Text(text = "Reload")
							}
						}
					}
				}
			
			
			ProfileScreenState.Initial,
			ProfileScreenState.Loading ->
				Box(Modifier.fillMaxSize()) {
					CircularProgressIndicator(Modifier.align(Alignment.Center))
				}
			
			is ProfileScreenState.Profile -> {
				Column(
					Modifier
						.padding(horizontal = 16.dp, vertical = 16.dp)
						.fillMaxSize(),
					verticalArrangement = Arrangement.spacedBy(10.dp)
				) {
					ListItem(
						leadingContent = {
							Icon(
								imageVector = Icons.Outlined.Circle,
								contentDescription = stringResource(R.string.profile_avatar),
								modifier = Modifier.size(40.dp)
							)
						},
						headlineContent = {
							Text(text = state.user.username)
						},
						supportingContent = {
							Text(text = state.user.email)
						}
					)
					
					
					
					
					ElevatedCard(
					) {
						ListItem(
							headlineContent = {
								Text(text = stringResource(id = R.string.btn_settings))
							},
							leadingContent = {
								Icon(
									imageVector = Icons.Outlined.Settings,
									contentDescription = stringResource(id = R.string.btn_settings)
								)
							},
							trailingContent = {
								Icon(
									imageVector = Icons.Outlined.ArrowForward,
									contentDescription = stringResource(id = R.string.btn_logout)
								)
							},
						)
					}
					Card(
						colors = CardDefaults.cardColors(
							containerColor = theme.errorContainer,
							contentColor = theme.onErrorContainer
						),
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
		}
		
	}
	
}

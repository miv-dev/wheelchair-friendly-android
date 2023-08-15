package miv.dev.wheelchair.friendly.presentation.screens.profile

import android.content.res.Resources.Theme
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.RateReview
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
					
					LazyVerticalGrid(
						columns = GridCells.Fixed(2),
						horizontalArrangement = Arrangement.spacedBy(8.dp),
						modifier = Modifier.fillMaxWidth()
					) {
						item {
							Card(Modifier.aspectRatio(4f / 5f)) {
								Column(
									Modifier.fillMaxSize(),
									horizontalAlignment = Alignment.CenterHorizontally,
									verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
								) {
									Icon(
										imageVector = Icons.Rounded.Place,
										contentDescription = "Places",
										Modifier.size(40.dp)
									)
									Text(text = "Places")
								}
							}
						}
						item {
							Card(Modifier.aspectRatio(4f / 5f)) {
								Column(
									Modifier.fillMaxSize(),
									horizontalAlignment = Alignment.CenterHorizontally,
									verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
								) {
									Icon(
										imageVector = Icons.Rounded.RateReview,
										contentDescription = "Reviews",
										Modifier.size(40.dp)
									)
									Text(text = "Reviews")
								}
							}
						}
					}
//					ElevatedCard(
//					) {
//						ListItem(
//							colors = ListItemDefaults.colors(
//								theme.tertiaryContainer,
//								theme.onTertiaryContainer
//							),
//							headlineContent = {
//								Text(text = "By me a coffee")
//							},
//							leadingContent = {
//								Icon(
//									imageVector = Icons.Outlined.Coffee,
//									contentDescription = "Coffee"
//								)
//							},
//							trailingContent = {
//								Icon(
//									imageVector = Icons.Outlined.Outbound,
//									contentDescription = null
//								)
//							},
//						)
//					}
					
					
					Spacer(Modifier.height(16.dp))
					ListItem(
						modifier = Modifier
							.clip(MaterialTheme.shapes.medium)
							.clickable() {
							
							},
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
					
					
					ListItem(
						modifier = Modifier
							.clip(MaterialTheme.shapes.medium)
							.clickable() {
							
							},
						headlineContent = {
							Text(text = stringResource(R.string.btn_support))
						},
						leadingContent = {
							Icon(
								imageVector = Icons.Outlined.QuestionMark,
								contentDescription = stringResource(id = R.string.btn_support)
							)
						},
						trailingContent = {
							
							Icon(
								imageVector = Icons.Outlined.ArrowForward,
								contentDescription = stringResource(id = R.string.btn_support)
							)
							
						},
					)
					
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItem(
	trailingIcon: @Composable () -> Unit,
	onClick: () -> Unit,
	
	) {
	Card(
		onClick = onClick
	) {
		ListItem(
			headlineContent = {
				Text(text = stringResource(R.string.btn_support))
			},
			leadingContent = {
				Icon(
					imageVector = Icons.Outlined.QuestionMark,
					contentDescription = stringResource(id = R.string.btn_support)
				)
			},
			trailingContent = {
				Icon(
					imageVector = Icons.Outlined.ArrowForward,
					contentDescription = stringResource(id = R.string.btn_support)
				)
			},
		)
	}
}

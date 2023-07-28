package miv.dev.wheelchair.friendly.presentation.auth

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Dialpad
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.layout.DisplayFeature
import kotlinx.coroutines.delay
import miv.dev.wheelchair.friendly.R
import miv.dev.wheelchair.friendly.getApplicationComponent
import miv.dev.wheelchair.friendly.utils.AppContentType
import miv.dev.wheelchair.friendly.utils.AppNavigationType

@Composable
fun LoginScreen(
	contentType: AppContentType,
	navigationType: AppNavigationType,
	displayFeatures: List<DisplayFeature>,
) {
	val component = getApplicationComponent()
	val vm: LoginScreenViewModel = viewModel(factory = component.getViewModelFactory())
	val screenUiState = vm.screenState.collectAsState()
	
	LoginScreenContent(screenUiState = screenUiState.value, onRegisterPressed = {}, onBackPressed = {}, viewModel = vm
	)
	
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LoginScreenContent(
	screenUiState: LoginScreenUiState,
	onRegisterPressed: () -> Unit,
	onBackPressed: () -> Unit,
	viewModel: LoginScreenViewModel,
) {
	val keyboardState by keyboardAsState()
	val focusManager = LocalFocusManager.current
	val snackbarHostState = remember { SnackbarHostState() }
	val theme = MaterialTheme.colorScheme
	var showPassword by remember {
		mutableStateOf(false)
	}
	var showError by remember {
		mutableStateOf(false)
	}
	
	LaunchedEffect(screenUiState.loginIsSuccess) {
		if (screenUiState.loginIsSuccess == false) {
			showError = true
			delay(1000)
			showError = false
		}
	}
	
	DisposableEffect(Unit){
		onDispose {
			viewModel.clearState()
		}
	}
	Scaffold(
		topBar = {
			TopAppBar(
				navigationIcon = {
					IconButton(onClick = onBackPressed) {
						Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
					}
				},
				title = { Text(text = stringResource(id = R.string.login_screen_title)) },
			)
		},
		
		
		) {
		Column(
			Modifier
				.padding(it)
				.focusGroup()
				.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			
			OutlinedTextField(label = {
				Text(text = stringResource(id = R.string.label_email))
			}, leadingIcon = {
				Icon(Icons.Rounded.AlternateEmail, contentDescription = stringResource(id = R.string.label_email))
			},
				singleLine = true,
				value = screenUiState.email, onValueChange = viewModel::updateEmail, modifier = Modifier.fillMaxWidth(),
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
				keyboardActions = KeyboardActions(
					onNext = {
						
						focusManager.moveFocus(FocusDirection.Next)
					}
				)
			)
			
			OutlinedTextField(
				label = {
					Text(text = stringResource(id = R.string.label_password))
				},
				leadingIcon = {
					Icon(Icons.Rounded.Dialpad, contentDescription = stringResource(id = R.string.label_email))
				},
				maxLines = 1,
				value = screenUiState.password,
				onValueChange = viewModel::updatePassword,
				modifier = Modifier.fillMaxWidth(),
				singleLine = true,
				keyboardOptions = KeyboardOptions(
					imeAction = ImeAction.Done,
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
			Spacer(modifier = Modifier.weight(1f))
			AnimatedVisibility(visible = screenUiState.loginError.isNotEmpty()) {
				Text(text = screenUiState.loginError, overflow = TextOverflow.Ellipsis, maxLines = 2, color = theme.error)
			}
			FilledTonalButton(
				modifier = Modifier.fillMaxWidth(),
				onClick = viewModel::loginWithEmailAndPassword,
				enabled = screenUiState.email.isNotEmpty() and screenUiState.password.isNotEmpty(),
				colors = if (showError) ButtonDefaults.filledTonalButtonColors(
					containerColor = theme.errorContainer,
					contentColor = theme.onErrorContainer
				) else ButtonDefaults.filledTonalButtonColors()
			) {
				AnimatedVisibility(visible = screenUiState.isLoading) {
					CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
				}
				AnimatedVisibility(visible = showError) {
					Icon(imageVector = Icons.Outlined.Error, contentDescription = "Error")
				}
				Text(
					text = stringResource(id = R.string.btn_login),
					modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
				)
			}
			AnimatedVisibility(visible = keyboardState == Keyboard.Closed) {
				Row(
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
				) {
					
					Text(text = "Don't have an account?")
					TextButton(onClick = onRegisterPressed) {
						Text(text = "Register")
					}
				}
			}
			
			
		}
	}
}

enum class Keyboard {
	Opened, Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
	val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
	val view = LocalView.current
	DisposableEffect(view) {
		val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
			val rect = Rect()
			view.getWindowVisibleDisplayFrame(rect)
			val screenHeight = view.rootView.height
			val keypadHeight = screenHeight - rect.bottom
			keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
				Keyboard.Opened
			} else {
				Keyboard.Closed
			}
		}
		view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
		
		onDispose {
			view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
		}
	}
	
	return keyboardState
}

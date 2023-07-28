package miv.dev.wheelchair.friendly.presentation.auth

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Dialpad
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.layout.DisplayFeature
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
	val screenUiState by vm.screenState.collectAsState()
	
	LoginScreenContent(
		screenUiState = screenUiState, onRegisterPressed = {}, onBackPressed = {}, viewModel = vm
	)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
	screenUiState: LoginScreenUiState,
	onRegisterPressed: () -> Unit,
	onBackPressed: () -> Unit,
	viewModel: LoginScreenViewModel,
) {
	val keyboardState by keyboardAsState()
	
	Scaffold(
		topBar = {
			TopAppBar(
				navigationIcon = {
					IconButton(onClick = onBackPressed) {
						Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
					}
				},
				title = { Text(text = stringResource(id = R.string.login_screen_title)) }
			)
		}
	) {
		Column(
			Modifier
				.padding(it)
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp)
		) {
			
			OutlinedTextField(
				label = {
					Text(text = stringResource(id = R.string.label_email))
				},
				leadingIcon = {
					Icon(Icons.Rounded.AlternateEmail, contentDescription = stringResource(id = R.string.label_email))
				},
				
				value = screenUiState.email,
				onValueChange = viewModel::updateEmail,
				modifier = Modifier.fillMaxWidth()
			)
			OutlinedTextField(
				label = {
					Text(text = stringResource(id = R.string.label_password))
				},
				leadingIcon = {
					Icon(Icons.Rounded.Dialpad, contentDescription = stringResource(id = R.string.label_email))
				},
				value = screenUiState.password,
				onValueChange = viewModel::updatePassword,
				modifier = Modifier.fillMaxWidth()
			
			)
			Spacer(modifier = Modifier.weight(1f))
			
			FilledTonalButton(
				modifier = Modifier.fillMaxWidth(),
				onClick = viewModel::loginWithEmailAndPassword,
				enabled = screenUiState.email.isNotEmpty() and screenUiState.password.isNotEmpty()
			) {
				Text(text = stringResource(id = R.string.btn_login), modifier = Modifier.padding(vertical = 6.dp))
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

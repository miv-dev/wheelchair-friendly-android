package miv.dev.wheelchair.friendly.presentation.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.layout.DisplayFeature
import kotlinx.coroutines.delay
import miv.dev.wheelchair.friendly.R
import miv.dev.wheelchair.friendly.getApplicationComponent
import miv.dev.wheelchair.friendly.presentation.auth.components.EmailTextField
import miv.dev.wheelchair.friendly.presentation.auth.components.LoadingButton
import miv.dev.wheelchair.friendly.presentation.auth.components.PasswordTextField
import miv.dev.wheelchair.friendly.utils.AppContentType
import miv.dev.wheelchair.friendly.utils.AppNavigationType
import miv.dev.wheelchair.friendly.utils.Keyboard
import miv.dev.wheelchair.friendly.utils.keyboardAsState

@Composable
fun LoginScreen(
	contentType: AppContentType,
	navigationType: AppNavigationType,
	displayFeatures: List<DisplayFeature>,
	onBackPressed: () -> Unit,
	onRegisterPressed: () -> Unit
) {
	val component = getApplicationComponent()
	val vm: LoginScreenViewModel = viewModel(factory = component.getViewModelFactory())
	val screenUiState = vm.screenState.collectAsState()
	
	LoginScreenContent(
		screenUiState = screenUiState.value,
		onRegisterPressed = onRegisterPressed,
		onBackPressed = onBackPressed,
		viewModel = vm
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
	val theme = MaterialTheme.colorScheme
	
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
	
	DisposableEffect(Unit) {
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
			Spacer(modifier = Modifier.height(10.dp))
			
			FilledTonalButton(
				onClick = { /*TODO*/ },
				modifier = Modifier.fillMaxWidth(),
				contentPadding = PaddingValues(vertical = 14.dp)
			) {
				Image(
					painter = painterResource(id = R.drawable.google_logo),
					contentDescription = "Google Logo",
					modifier = Modifier.size(18.dp)
				)
				Text(text = "Login with Google", modifier = Modifier.padding(horizontal = 8.dp))
			}
			Spacer(modifier = Modifier.height(20.dp))
			
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(16.dp)
			) {
				HorizontalDivider(modifier = Modifier.weight(1f))
				Text(text = stringResource(id = R.string.or_continue_with_email), color = theme.outline)
				HorizontalDivider(modifier = Modifier.weight(1f))
			}
			Spacer(modifier = Modifier.heightIn(min = 0.dp, max = 20.dp))
			
			EmailTextField(
				value = screenUiState.email,
				onValueChange = viewModel::updateEmail,
				imeAction = ImeAction.Next
			)
			
			
			PasswordTextField(
				value = screenUiState.password,
				onValueChange = viewModel::updatePassword,
				imeAction = ImeAction.None
			)
			Spacer(modifier = Modifier.weight(3f))
			AnimatedVisibility(visible = screenUiState.loginError.isNotEmpty()) {
				Text(text = screenUiState.loginError, overflow = TextOverflow.Ellipsis, maxLines = 2, color = theme.error)
			}
			
			LoadingButton(
				onClick = viewModel::loginWithEmailAndPassword,
				enabled = screenUiState.email.isNotEmpty() and screenUiState.password.isNotEmpty(),
				showError = showError,
				isLoading = screenUiState.isLoading,
				label = stringResource(id = R.string.btn_register)
			)
			
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

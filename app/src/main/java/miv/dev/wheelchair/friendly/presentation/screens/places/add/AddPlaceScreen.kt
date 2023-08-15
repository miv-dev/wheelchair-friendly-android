package miv.dev.wheelchair.friendly.presentation.screens.places.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import miv.dev.wheelchair.friendly.R
import miv.dev.wheelchair.friendly.getApplicationComponent


@Composable
fun AddPlaceScreen() {
	val component = getApplicationComponent()
	val vm: AddPlaceScreenViewModel = viewModel(factory = component.getViewModelFactory())
	val screenUiState = vm.screenState.collectAsState()
	
	AddPlaceScreenContent(screenUiState = screenUiState.value, viewModel = vm)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreenContent(
	screenUiState: AddPlaceScreenState,
	viewModel: AddPlaceScreenViewModel
) {
	
	Scaffold(
		topBar = {
			TopAppBar(title = { Text(text = "New Place") })
		}
	)
	{
		Column(Modifier.padding(it).padding(horizontal = 16.dp)) {
			OutlinedTextField(
				label = {
					Text(text = stringResource(id = R.string.label_address))
				},
				leadingIcon = {
					Icon(Icons.Rounded.AlternateEmail, contentDescription = stringResource(id = R.string.label_address))
				},
				value = screenUiState.address, onValueChange = viewModel::updateAddress, modifier = Modifier.fillMaxWidth(),
				minLines = 2,
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Text,
					imeAction = ImeAction.Next
				),
				
				)
			OutlinedTextField(
				label = {
					Text(text = stringResource(id = R.string.label_name))
				},
				leadingIcon = {
					Icon(Icons.Rounded.AlternateEmail, contentDescription = stringResource(id = R.string.label_name))
				},
				value = screenUiState.name, onValueChange = viewModel::updateName, modifier = Modifier.fillMaxWidth(),
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Text,
					imeAction = ImeAction.Next
				),
				
				)
			
		}
	}
}

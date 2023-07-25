package miv.dev.wheelchair.friendly.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDockedSearchBar(
	modifier: Modifier = Modifier
) {
	var query by remember { mutableStateOf("") }
	var active by remember { mutableStateOf(false) }
	
	DockedSearchBar(
		modifier = modifier,
		query = query,
		onQueryChange = { query = it },
		onSearch = { active = true },
		active = active,
		onActiveChange = {
			active = it
		},
		leadingIcon = {
			if (active) {
				Icon(
					imageVector = Icons.Rounded.ArrowBack,
					contentDescription = "Back",
					modifier = Modifier
						.padding(start = 16.dp)
						.clickable {
							active = false
							query = ""
						},
				)
			} else {
				Icon(
					imageVector = Icons.Rounded.Search,
					contentDescription = "Search",
					modifier = Modifier.padding(start = 16.dp),
				)
			}
		},
	) {
	
	}
}

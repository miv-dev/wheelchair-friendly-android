package miv.dev.wheelchair.friendly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import miv.dev.wheelchair.friendly.presentation.screens.ShallScreen
import miv.dev.wheelchair.friendly.ui.theme.WheelchairFriendlyTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			WheelchairFriendlyTheme {
				ShallScreen()
			}
		}
	}
}


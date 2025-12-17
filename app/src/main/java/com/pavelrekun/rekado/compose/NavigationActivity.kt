package com.pavelrekun.rekado.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pavelrekun.rekado.compose.NavigationUiState.Loading
import com.pavelrekun.rekado.compose.extensions.darkScrim
import com.pavelrekun.rekado.compose.extensions.isSystemInDarkTheme
import com.pavelrekun.rekado.compose.extensions.lightScrim
import com.pavelrekun.rekado.compose.ui.RekadoApp
import com.pavelrekun.rekado.compose.ui.rememberAppState
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NavigationActivity : ComponentActivity() {

    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // We keep this as a mutable state, so that we can track changes inside the composition.
        // This allows us to react to dark/light mode changes.
        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme,
                androidTheme = Loading.shouldUseAndroidTheme,
                disableDynamicTheming = Loading.shouldDisableDynamicTheming,
            ),
        )

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    isSystemInDarkTheme(),
                    viewModel.uiState,
                ) { systemDark, uiState ->
                    ThemeSettings(
                        darkTheme = uiState.shouldUseDarkTheme(systemDark),
                        androidTheme = uiState.shouldUseAndroidTheme,
                        disableDynamicTheming = uiState.shouldDisableDynamicTheming,
                    )
                }
                    .onEach { themeSettings = it }
                    .map { it.darkTheme }
                    .distinctUntilChanged()
                    .collect { darkTheme ->
                        // Turn off the decor fitting system windows, which allows us to handle insets,
                        // including IME animations, and go edge-to-edge.
                        // This is the same parameters as the default enableEdgeToEdge call, but we manually
                        // resolve whether or not to show dark theme using uiState, since it can be different
                        // than the configuration's dark theme value based on the user preference.
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                lightScrim = android.graphics.Color.TRANSPARENT,
                                darkScrim = android.graphics.Color.TRANSPARENT,
                            ) { darkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                lightScrim = lightScrim,
                                darkScrim = darkScrim,
                            ) { darkTheme }
                        )
                    }
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.shouldKeepSplashScreen() }

        setContent {
            val appState = rememberAppState()

            RekadoTheme(
                useDarkTheme = false,
                useDynamicColors = themeSettings.disableDynamicTheming
            ) {
                RekadoApp(appState = appState)
            }
        }
    }
}

/**
 * Class for the system theme settings.
 * This wrapping class allows us to combine all the changes and prevent unnecessary recompositions.
 */
data class ThemeSettings(
    val darkTheme: Boolean,
    val androidTheme: Boolean,
    val disableDynamicTheming: Boolean,
)
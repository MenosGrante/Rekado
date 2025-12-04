package com.pavelrekun.rekado.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.pavelrekun.rekado.core.ui.R
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RekadoTopBar(
    isOnTopLevelRoute: Boolean,
    currentRouteTitleId: Int,
    goBack: () -> Unit,
    onSerialCheckerClick: () -> Unit,
    onDonateClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    var showOptionsMenu by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = stringResource(currentRouteTitleId),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            if (!isOnTopLevelRoute) {
                IconButton(onClick = { goBack() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigation_back),
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (isOnTopLevelRoute) {
                IconButton(onClick = onDonateClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigation_donate),
                        contentDescription = stringResource(R.string.navigation_donate)
                    )
                }

                Box {
                    IconButton(onClick = { showOptionsMenu = true }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_navigation_more),
                            contentDescription = stringResource(R.string.navigation_content_description_more)
                        )
                    }

                    RekadoTopBarDropdown(
                        expanded = showOptionsMenu,
                        onDismissRequest = { showOptionsMenu = false },
                        onSerialCheckerClick = {
                            onSerialCheckerClick()
                            showOptionsMenu = false
                        },
                        onSettingsClick = {
                            onSettingsClick()
                            showOptionsMenu = false
                        },
                        onAboutClick = {
                            onAboutClick()
                            showOptionsMenu = false
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun RekadoTopBarDropdown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSerialCheckerClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = DpOffset(x = (-8).dp, y = 0.dp)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.navigation_serial_checker)) },
            onClick = onSerialCheckerClick,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_navigation_serial_checker),
                    contentDescription = null
                )
            }
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.navigation_settings)) },
            onClick = onSettingsClick,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_navigation_settings),
                    contentDescription = null
                )
            }
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.navigation_about)) },
            onClick = onAboutClick,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_navigation_about),
                    contentDescription = null
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Top Bar")
@Composable
private fun RekadoTopBarPreview() = RekadoTheme {
    RekadoTopBar(
        isOnTopLevelRoute = false,
        currentRouteTitleId = R.string.navigation_serial_checker,
        goBack = {},
        onSerialCheckerClick = {},
        onDonateClick = {},
        onSettingsClick = {},
        onAboutClick = {}
    )
}

@Preview(name = "Top Bar Dropdown")
@Composable
private fun RekadoTopBarDropdownPreview() = RekadoTheme {
    RekadoTopBarDropdown(
        expanded = true,
        onDismissRequest = { },
        onSerialCheckerClick = { },
        onSettingsClick = { },
        onAboutClick = { }
    )
}

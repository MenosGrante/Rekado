package com.pavelrekun.rekado.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pavelrekun.rekado.core.ui.R
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme

data class BottomBarItem(
    @param:DrawableRes val iconId: Int,
    @param:StringRes val titleId: Int,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

@Composable
fun RekadoBottomBar(
    items: List<BottomBarItem>
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.isSelected,
                onClick = item.onClick,
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(item.titleId)) }
            )
        }
    }
}

@Preview(name = "Bottom Bar")
@Composable
private fun RekadoBottomBarPreview() = RekadoTheme {
    RekadoBottomBar(
        items = listOf(
            BottomBarItem(
                iconId = R.drawable.ic_navigation_payloads,
                titleId = R.string.navigation_payloads,
                isSelected = true,
                onClick = { }
            ),
            BottomBarItem(
                iconId = R.drawable.ic_navigation_instructions,
                titleId = R.string.navigation_instructions,
                isSelected = false,
                onClick = { }
            ),
            BottomBarItem(
                iconId = R.drawable.ic_navigation_logs,
                titleId = R.string.navigation_logs,
                isSelected = false,
                onClick = { }
            )
        )
    )
}

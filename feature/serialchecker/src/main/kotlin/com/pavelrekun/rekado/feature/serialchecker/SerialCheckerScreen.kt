package com.pavelrekun.rekado.feature.serialchecker

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.journeyapps.barcodescanner.ScanOptions.ONE_D_CODE_TYPES
import com.pavelrekun.rekado.core.common.Links.HELP_SERIAL_CHECKER
import com.pavelrekun.rekado.core.navigation.Navigator
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import com.pavelrekun.rekado.feature.serialchecker.ui.CheckerCard
import com.pavelrekun.rekado.feature.serialchecker.ui.InformationCard

@Composable
fun SerialCheckerScreen(
    navigator: Navigator? = null,
    viewModel: SerialCheckerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val barcodeScannerLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = viewModel::onScanResult
    )

    SerialCheckerContent(
        state = state,
        onSerialNumberChanged = viewModel::onSerialNumberChanged,
        onScanBarcode = {
            val scanOptions = ScanOptions().apply {
                setDesiredBarcodeFormats(ONE_D_CODE_TYPES)
                setOrientationLocked(false)
                setBeepEnabled(false)
            }
            barcodeScannerLauncher.launch(scanOptions)
        },
        onHelpClick = {
            navigator?.openLink(
                context = context,
                url = HELP_SERIAL_CHECKER
            )
        }
    )
}

@Composable
private fun SerialCheckerContent(
    modifier: Modifier = Modifier,
    state: SerialCheckerState,
    onSerialNumberChanged: (String) -> Unit,
    onScanBarcode: () -> Unit,
    onHelpClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CheckerCard(
            serialNumber = state.serialNumber,
            status = state.status,
            onSerialNumberChanged = onSerialNumberChanged,
            onScanBarcode = onScanBarcode
        )

        InformationCard(
            onHelpClick = onHelpClick
        )
    }
}

@Preview(name = "Serial Checker - Empty")
@Composable
private fun SerialCheckerContentEmptyPreview() = RekadoTheme {
    SerialCheckerContent(
        state = SerialCheckerState(),
        onSerialNumberChanged = {},
        onScanBarcode = {},
        onHelpClick = {}
    )
}

@Preview(name = "Serial Checker - Not Patched")
@Composable
private fun SerialCheckerContentNotPatchedPreview() = RekadoTheme {
    val state = SerialCheckerState(
        serialNumber = "XAW10050000000",
        status = SerialCheckerState().status
    )
    SerialCheckerContent(
        state = state,
        onSerialNumberChanged = {},
        onScanBarcode = {},
        onHelpClick = {}
    )
}

@Preview(name = "Serial Checker - Patched")
@Composable
private fun SerialCheckerContentPatchedPreview() = RekadoTheme {
    val state = SerialCheckerState(
        serialNumber = "XKW10000000000",
        status = SerialCheckerState().status
    )
    SerialCheckerContent(
        state = state,
        onSerialNumberChanged = {},
        onScanBarcode = {},
        onHelpClick = {}
    )
}

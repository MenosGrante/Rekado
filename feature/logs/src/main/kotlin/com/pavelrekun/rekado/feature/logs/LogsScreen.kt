package com.pavelrekun.rekado.feature.logs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavelrekun.rekado.core.common.extensions.formatTimestamp
import com.pavelrekun.rekado.core.model.logs.Log
import com.pavelrekun.rekado.core.model.logs.LogLevel
import com.pavelrekun.rekado.core.ui.R
import com.pavelrekun.rekado.core.ui.theme.RekadoTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun LogsScreen(
    viewModel: LogsViewModel = hiltViewModel()
) {
    val logs by viewModel.logs.collectAsStateWithLifecycle()

    LogsContent(
        logs = logs,
        onClearLogs = { viewModel.clearLogs() }
    )
}

@Composable
private fun LogsContent(
    logs: PersistentList<Log>,
    onClearLogs: () -> Unit
) {
    val listState = rememberLazyListState()
    val showClearButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (logs.isEmpty()) {
            EmptyLogsState()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = logs,
                    key = { it.timestamp }
                ) { log ->
                    LogItem(log = log)
                }
            }
        }

        if (logs.isNotEmpty() && showClearButton) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = onClearLogs
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logs_clear),
                    contentDescription = stringResource(R.string.logs_content_description_clear)
                )
            }
        }
    }
}

@Composable
private fun EmptyLogsState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(R.drawable.ic_logs_empty),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.logs_empty_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = stringResource(R.string.logs_empty_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LogItem(log: Log) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogLevelIndicator(level = log.level)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = log.message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = log.timestamp.formatTimestamp(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun LogLevelIndicator(level: LogLevel) {
    val color = when (level) {
        LogLevel.INFO -> Color(0xFF4CAF50) // Green
        LogLevel.WARN -> Color(0xFFFFC107) // Amber
        LogLevel.ERROR -> Color(0xFFF44336) // Red
    }

    Box(
        modifier = Modifier
            .size(12.dp)
            .background(color = color, shape = CircleShape)
    )
}

@Preview(name = "Logs - Empty")
@Composable
private fun LogsContentEmptyPreview() = RekadoTheme {
    LogsContent(
        logs = persistentListOf(),
        onClearLogs = { }
    )
}

@Preview(name = "Logs - With Content")
@Composable
private fun LogsContentWithLogsPreview() = RekadoTheme {
    LogsContent(
        logs = persistentListOf(
            Log(
                message = "Application started successfully",
                level = LogLevel.INFO,
                timestamp = System.currentTimeMillis()
            ),
            Log(
                message = "Low memory warning detected",
                level = LogLevel.WARN,
                timestamp = System.currentTimeMillis() - 5000
            ),
            Log(
                message = "Failed to connect to server",
                level = LogLevel.ERROR,
                timestamp = System.currentTimeMillis() - 10000
            ),
            Log(
                message = "User logged in",
                level = LogLevel.INFO,
                timestamp = System.currentTimeMillis() - 15000
            ),
            Log(
                message = "Network connection established",
                level = LogLevel.INFO,
                timestamp = System.currentTimeMillis() - 20000
            )
        ),
        onClearLogs = { }
    )
}
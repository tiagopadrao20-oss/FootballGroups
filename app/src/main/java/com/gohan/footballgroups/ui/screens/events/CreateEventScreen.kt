package com.gohan.footballgroups.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.*

/**
 * Form screen for creating a new football event.
 *
 * The groupId is picked up automatically from the nav back-stack
 * by [CreateEventViewModel] via [SavedStateHandle].
 *
 * @param groupId       Used for back-navigation context display only.
 * @param onEventCreated Called with the new event's id once creation succeeds.
 * @param onBack         Called when the back arrow is pressed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    groupId: Long,
    onEventCreated: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: CreateEventViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var maxPlayersText by remember { mutableStateOf("10") }
    var notes by remember { mutableStateOf("") }
    var createdBy by remember { mutableStateOf("") }

    // Simple date/time state — in production use DatePickerDialog + TimePickerDialog
    var dateTimeMs by remember { mutableStateOf(System.currentTimeMillis() + 86_400_000L) }

    LaunchedEffect(uiState.createdEventId) {
        uiState.createdEventId?.let { onEventCreated(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Event") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it },
                label = { Text("Title *") }, singleLine = true, modifier = Modifier.fillMaxWidth())

            OutlinedTextField(value = location, onValueChange = { location = it },
                label = { Text("Location *") }, singleLine = true, modifier = Modifier.fillMaxWidth())

            OutlinedTextField(
                value = maxPlayersText,
                onValueChange = { if (it.length <= 2) maxPlayersText = it },
                label = { Text("Max players *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(value = createdBy, onValueChange = { createdBy = it },
                label = { Text("Your name *") }, singleLine = true, modifier = Modifier.fillMaxWidth())

            OutlinedTextField(value = notes, onValueChange = { notes = it },
                label = { Text("Notes") }, maxLines = 3, modifier = Modifier.fillMaxWidth())

            // Date/time picker placeholder — uses a simple offset for now.
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Date & Time", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        java.text.SimpleDateFormat("EEE, d MMM yyyy · HH:mm", Locale.getDefault())
                            .format(Date(dateTimeMs)),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                        OutlinedButton(onClick = { dateTimeMs -= 3_600_000L }) { Text("−1h") }
                        OutlinedButton(onClick = { dateTimeMs += 3_600_000L }) { Text("+1h") }
                        OutlinedButton(onClick = { dateTimeMs += 86_400_000L }) { Text("+1d") }
                    }
                }
            }

            uiState.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val maxPlayers = maxPlayersText.toIntOrNull() ?: 0
                    viewModel.createEvent(title, dateTimeMs, location, maxPlayers, notes, createdBy)
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Create Event")
                }
            }
        }
    }
}

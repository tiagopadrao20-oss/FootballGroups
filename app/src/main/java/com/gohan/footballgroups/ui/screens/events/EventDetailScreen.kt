package com.gohan.footballgroups.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Screen showing full details of a football event and the confirmed participants list.
 *
 * Players can confirm or cancel their participation from this screen.
 *
 * @param eventId  Loaded from the nav back-stack by the ViewModel.
 * @param onBack   Called when the back arrow is pressed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: Long,
    onBack: () -> Unit,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event = uiState.event

    var showJoinDialog by remember { mutableStateOf(false) }
    var playerName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(event?.title ?: "Event") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            if (event != null && event.isUpcoming && !event.isFull) {
                ExtendedFloatingActionButton(
                    onClick = { showJoinDialog = true },
                    icon = { Icon(Icons.Default.HowToReg, null) },
                    text = { Text("Join") }
                )
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            event == null -> Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { Text("Event not found.") }

            else -> {
                val dateFormatter = SimpleDateFormat("EEEE, d MMMM yyyy · HH:mm", Locale.getDefault())

                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Info card
                    item {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CalendarToday, null, tint = MaterialTheme.colorScheme.primary)
                                    Text(dateFormatter.format(Date(event.dateTime)), style = MaterialTheme.typography.bodyLarge)
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocationOn, null, tint = MaterialTheme.colorScheme.primary)
                                    Text(event.location, style = MaterialTheme.typography.bodyLarge)
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.People, null, tint = MaterialTheme.colorScheme.primary)
                                    Text("${event.participants.size} / ${event.maxPlayers} players")
                                    if (event.isFull) {
                                        Surface(color = MaterialTheme.colorScheme.errorContainer, shape = MaterialTheme.shapes.small) {
                                            Text("FULL", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                    }
                                }
                                if (event.notes.isNotBlank()) {
                                    HorizontalDivider()
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Icon(Icons.Default.Notes, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(event.notes, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }

                    // Participants header
                    item {
                        Text(
                            "Confirmed (${event.participants.size})",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (event.participants.isEmpty()) {
                        item {
                            Text(
                                "No one confirmed yet. Be the first!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(event.participants, key = { it.id }) { participant ->
                            ListItem(
                                headlineContent = { Text(participant.playerName) },
                                leadingContent = { Icon(Icons.Default.Person, null) },
                                trailingContent = {
                                    if (event.isUpcoming) {
                                        IconButton(onClick = { viewModel.cancelParticipation(participant.id) }) {
                                            Icon(Icons.Default.Close, "Cancel", tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                // Error snackbar
                uiState.error?.let { error ->
                    LaunchedEffect(error) { viewModel.clearError() }
                    // Surface the error via a simple text for now; use SnackbarHost in a full implementation
                }
            }
        }

        // Join dialog
        if (showJoinDialog) {
            AlertDialog(
                onDismissRequest = { showJoinDialog = false; playerName = "" },
                title = { Text("Join Event") },
                text = {
                    OutlinedTextField(
                        value = playerName,
                        onValueChange = { playerName = it },
                        label = { Text("Your name") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (playerName.isNotBlank()) {
                            viewModel.confirmParticipation(playerName)
                            showJoinDialog = false
                            playerName = ""
                        }
                    }) { Text("Confirm") }
                },
                dismissButton = {
                    TextButton(onClick = { showJoinDialog = false; playerName = "" }) { Text("Cancel") }
                }
            )
        }
    }
}

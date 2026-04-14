package com.gohan.footballgroups.ui.screens.groups

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gohan.footballgroups.ui.components.EmptyState
import com.gohan.footballgroups.ui.components.EventCard

/**
 * Screen showing a group's info, member list, and events (upcoming + past).
 *
 * @param groupId     Loaded from the nav back-stack; passed to the ViewModel.
 * @param onEventClick   Called with the eventId when an event card is tapped.
 * @param onCreateEvent  Called when the FAB is tapped.
 * @param onBack         Called when the back arrow is pressed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailScreen(
    groupId: Long,
    onEventClick: (Long) -> Unit,
    onCreateEvent: () -> Unit,
    onBack: () -> Unit,
    viewModel: GroupDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddMemberDialog by remember { mutableStateOf(false) }
    var memberName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.group?.name ?: "Group") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddMemberDialog = true }) {
                        Icon(Icons.Default.PersonAdd, "Add member")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateEvent) {
                Icon(Icons.Default.Add, "Create event")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Description
            uiState.group?.description?.takeIf { it.isNotBlank() }?.let { desc ->
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(desc, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // Members
            item {
                Text("Members (${uiState.group?.members?.size ?: 0})", style = MaterialTheme.typography.titleMedium)
            }
            items(uiState.group?.members ?: emptyList()) { player ->
                ListItem(headlineContent = { Text(player.name) }, leadingContent = {
                    Text("⚽", style = MaterialTheme.typography.bodyLarge)
                })
            }

            // Upcoming events
            item { Spacer(Modifier.height(8.dp)) }
            item { Text("Upcoming Events", style = MaterialTheme.typography.titleMedium) }
            if (uiState.upcomingEvents.isEmpty()) {
                item {
                    Text(
                        "No upcoming events. Tap + to create one.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            } else {
                items(uiState.upcomingEvents, key = { it.id }) { event ->
                    EventCard(event = event, onClick = { onEventClick(event.id) })
                }
            }

            // Past events
            if (uiState.pastEvents.isNotEmpty()) {
                item { Spacer(Modifier.height(8.dp)) }
                item { Text("Past Events", style = MaterialTheme.typography.titleMedium) }
                items(uiState.pastEvents, key = { "past_${it.id}" }) { event ->
                    EventCard(event = event, onClick = { onEventClick(event.id) })
                }
            }
        }

        // Add-member dialog
        if (showAddMemberDialog) {
            AlertDialog(
                onDismissRequest = { showAddMemberDialog = false; memberName = "" },
                title = { Text("Add Member") },
                text = {
                    OutlinedTextField(
                        value = memberName,
                        onValueChange = { memberName = it },
                        label = { Text("Player name") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (memberName.isNotBlank()) {
                                viewModel.addMember(memberName)
                                showAddMemberDialog = false
                                memberName = ""
                            }
                        }
                    ) { Text("Add") }
                },
                dismissButton = {
                    TextButton(onClick = { showAddMemberDialog = false; memberName = "" }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

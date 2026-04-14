package com.gohan.footballgroups.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gohan.footballgroups.ui.screens.events.CreateEventScreen
import com.gohan.footballgroups.ui.screens.events.EventDetailScreen
import com.gohan.footballgroups.ui.screens.groups.CreateGroupScreen
import com.gohan.footballgroups.ui.screens.groups.GroupDetailScreen
import com.gohan.footballgroups.ui.screens.groups.GroupsScreen

/**
 * Root navigation graph for the entire app.
 *
 * Each [composable] entry maps a [Screen.route] to its Composable screen.
 * All navigation actions are passed as lambdas to keep screens decoupled
 * from the [NavHostController].
 */
@Composable
fun FootballGroupsNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Groups.route
    ) {

        // ── Groups list ──────────────────────────────────────────────────────
        composable(Screen.Groups.route) {
            GroupsScreen(
                onGroupClick = { groupId ->
                    navController.navigate(Screen.GroupDetail.createRoute(groupId))
                },
                onCreateGroup = {
                    navController.navigate(Screen.CreateGroup.route)
                }
            )
        }

        // ── Create group ─────────────────────────────────────────────────────
        composable(Screen.CreateGroup.route) {
            CreateGroupScreen(
                onGroupCreated = { groupId ->
                    navController.navigate(Screen.GroupDetail.createRoute(groupId)) {
                        popUpTo(Screen.Groups.route)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Group detail ─────────────────────────────────────────────────────
        composable(
            route = Screen.GroupDetail.route,
            arguments = listOf(navArgument("groupId") { type = NavType.LongType })
        ) { backStack ->
            val groupId = backStack.arguments!!.getLong("groupId")
            GroupDetailScreen(
                groupId = groupId,
                onEventClick = { eventId ->
                    navController.navigate(Screen.EventDetail.createRoute(eventId))
                },
                onCreateEvent = {
                    navController.navigate(Screen.CreateEvent.createRoute(groupId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Create event ─────────────────────────────────────────────────────
        composable(
            route = Screen.CreateEvent.route,
            arguments = listOf(navArgument("groupId") { type = NavType.LongType })
        ) { backStack ->
            val groupId = backStack.arguments!!.getLong("groupId")
            CreateEventScreen(
                groupId = groupId,
                onEventCreated = { eventId ->
                    navController.navigate(Screen.EventDetail.createRoute(eventId)) {
                        popUpTo(Screen.GroupDetail.createRoute(groupId))
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Event detail ─────────────────────────────────────────────────────
        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(navArgument("eventId") { type = NavType.LongType })
        ) { backStack ->
            val eventId = backStack.arguments!!.getLong("eventId")
            EventDetailScreen(
                eventId = eventId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

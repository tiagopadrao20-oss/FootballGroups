package com.gohan.footballgroups.ui.navigation

/**
 * Sealed hierarchy of all navigation destinations in the app.
 *
 * Using a sealed class ensures exhaustive `when` expressions and
 * makes adding new screens a compile-time-safe operation.
 */
sealed class Screen(val route: String) {

    /** Home screen listing all groups. */
    data object Groups : Screen("groups")

    /** Detail screen for a specific group. */
    data object GroupDetail : Screen("group/{groupId}") {
        fun createRoute(groupId: Long) = "group/$groupId"
    }

    /** Screen for creating a new group. */
    data object CreateGroup : Screen("create_group")

    /** Detail screen for a specific event. */
    data object EventDetail : Screen("event/{eventId}") {
        fun createRoute(eventId: Long) = "event/$eventId"
    }

    /** Screen for creating a new event inside a group. */
    data object CreateEvent : Screen("create_event/{groupId}") {
        fun createRoute(groupId: Long) = "create_event/$groupId"
    }
}

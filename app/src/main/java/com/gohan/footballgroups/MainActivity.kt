package com.gohan.footballgroups

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gohan.footballgroups.ui.navigation.FootballGroupsNavGraph
import com.gohan.footballgroups.ui.theme.FootballGroupsTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity host for the entire Compose UI.
 *
 * Hilt injects dependencies through the [@AndroidEntryPoint] annotation.
 * Navigation is handled entirely by [FootballGroupsNavGraph].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootballGroupsTheme {
                FootballGroupsNavGraph()
            }
        }
    }
}

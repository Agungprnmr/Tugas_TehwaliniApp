package com.example.tea.tea_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tea.tea_note.presentation.add_edit_tea.AddEditTeaScreen
import com.example.tea.tea_note.presentation.myTea.TeaScreen
import com.example.tea.tea_note.presentation.util.Screen
import com.example.tea.ui.theme.TeaAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeaAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.TeaScreen.route
                    ) {
                        composable(route = Screen.TeaScreen.route) {
                            TeaScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditTeaScreen.route +
                                    "?teaId={teaId}&teaColor={teaColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "teaId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "teaColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("teaColor") ?: -1
                            AddEditTeaScreen(
                                navController = navController,
                                teaColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}

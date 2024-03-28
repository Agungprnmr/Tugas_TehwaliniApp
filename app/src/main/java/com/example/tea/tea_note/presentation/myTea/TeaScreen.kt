package com.example.tea.tea_note.presentation.myTea

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tea.R
import com.example.tea.tea_note.presentation.myTea.components.OrderSection
import com.example.tea.tea_note.presentation.myTea.components.TeaItem
import com.example.tea.tea_note.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun TeaScreen(
    navController: NavController,
    viewModel: TeaViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditTeaScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add tea")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.teh2),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Teh Walini",
                    style = MaterialTheme.typography.h4.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp) // Ubah angka sesuai keinginan untuk border radius
                        )
                        .padding(8.dp)
                )
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    teaOrder = state.teasOrder,
                    onOrderChange = {
                        viewModel.onEvent(TeaEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.teas) { tea ->
                    TeaItem(
                        tea = tea,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp,)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditTeaScreen.route +
                                            "?teaId=${tea.id}&teaColor=${tea.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(TeaEvent.DeleteTea(tea))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(TeaEvent.RestoreTea)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
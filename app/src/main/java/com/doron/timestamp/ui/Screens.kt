package com.doron.timestamp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doron.timestamp.ui.theme.Orange500
import com.doron.timestamp.ui.theme.Purple40
import com.doron.timestamp.viewmodel.ContentViewModel
import com.doron.timestamp.viewmodel.DrawerViewModel
import com.doron.timestamp.viewmodel.MainViewModel
import com.doron.timestamp.viewmodel.SubContentViewModel

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    Column(modifier) {
        val tsList by viewModel.timestamps.collectAsState()
        ScreenContent(
            Modifier.weight(1f).fillMaxWidth(),
            tsList,
            Purple40
        ) { viewModel.sendTimestamp() }
        ContentView(Modifier.weight(2f))
    }
}

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    viewModel: ContentViewModel = hiltViewModel()
) {
    Column(modifier) {
        val tsList by viewModel.timestamps.collectAsState()
        ScreenContent(
            Modifier.weight(1f).fillMaxWidth(),
            tsList,
            Orange500
        ) { viewModel.sendTimestamp() }
        SubContentView(Modifier.weight(1f))
    }
}

@Composable
fun SubContentView(
    modifier: Modifier = Modifier,
    viewModel: SubContentViewModel = hiltViewModel()
) {
    val tsList by viewModel.timestamps.collectAsState()
    ScreenContent(
        modifier.fillMaxWidth(),
        tsList,
        Color.Blue
    ) { viewModel.sendTimestamp() }
}

@Composable
fun DrawerView(
    modifier: Modifier = Modifier,
    viewModel: DrawerViewModel = hiltViewModel()
) {
    val tsList by viewModel.timestamps.collectAsState()
    ScreenContentForDrawer(
        modifier,
        tsList,
        Color.Green
    ) { viewModel.sendTimestamp() }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    tsList: List<Long>,
    color: Color,
    onClick: ()->Unit
) {
    Box(
        modifier = modifier.background(color)
    ) {
        SmallFloatingActionButton(
            onClick = onClick,
            containerColor = Color.Red,
            shape = CircleShape,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Add timestamp"
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            tsList.forEachIndexed { index, ts ->
                Text("${index + 1} - $ts")
            }
        }
    }
}

@Composable
fun ScreenContentForDrawer(
    modifier: Modifier = Modifier,
    tsList: List<Long>,
    color: Color,
    onClick: ()->Unit
) {
    Box(
        modifier = modifier.background(color)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            SmallFloatingActionButton(
                onClick = onClick,
                containerColor = Color.Red,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Add timestamp"
                )
            }

            tsList.forEachIndexed { index, ts ->
                Text("${index + 1} - $ts")
            }
        }
    }
}

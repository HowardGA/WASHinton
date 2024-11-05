package com.example.washinton.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.washinton.feature.menu.MenuScreen
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.LightBlue
import com.example.washinton.ui.theme.LightMint
import com.example.washinton.ui.theme.MidBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = DarkBlue,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(text = "Washinton", fontWeight = FontWeight.Bold)
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = DarkBlue,
                contentColor = Color.White,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Icons",
                )
            }
        },

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            //Menu goes here
            MenuScreen(navController = navController)
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}
package com.example.washinton.feature.products

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.LightBlue
import com.example.washinton.ui.theme.MidBlue

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.example.washinton.feature.camera.AnalyzerType
import com.example.washinton.feature.camera.CameraScreen
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SearchProductsScreen(navController: NavController) {
    val viewModel = SearchProductsViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val countriesList by viewModel.countriesList.collectAsState()

    //Botton Sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,)

    // Camera permission and analyzer state
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var analyzerType by remember { mutableStateOf(AnalyzerType.UNDEFINED) }
    var isCameraButtonClicked by remember { mutableStateOf(false) }

    var scannedData by rememberSaveable { mutableStateOf<String?>(null) }


    // Handle scanned data retrieval and bottom sheet state
    LaunchedEffect(navController.currentBackStackEntry?.savedStateHandle?.get<String>("scannedData")) {
        val newScannedData = navController.currentBackStackEntry
            ?.savedStateHandle?.get<String>("scannedData")

        newScannedData?.let {
            scannedData = it // Save scanned data locally
            viewModel.onSearchTextChange(it) // Update view model with scanned data
            showBottomSheet = true // Show bottom sheet after returning
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("scannedData")
        }
    }

    // Handle camera permission logic based on button click
    if (isCameraButtonClicked) {
        Log.d("SearchProductsScreen", "isCameraButtonClicked: $isCameraButtonClicked")
        when {
            cameraPermissionState.status.isGranted -> {
                // Set analyzerType to BARCODE directly since we only want barcode scanning
                analyzerType = AnalyzerType.BARCODE
                Log.d("SearchProductsScreen", "AnalyzerType: $analyzerType")
                // Navigate to the camera screen with BARCODE analyzer type
                navController.navigate("camera/${analyzerType.name}")
                isCameraButtonClicked = false // Reset after navigation
            }
            cameraPermissionState.status.shouldShowRationale -> {
                Text("Camera permission is required to scan QR codes.")
            }
            else -> {
                LaunchedEffect(Unit) {
                    cameraPermissionState.launchPermissionRequest()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBlue,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(text = "Search for a Product", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isCameraButtonClicked = true; Log.d("SearchProductsScreen", "Camera button clicked") },
                containerColor = MidBlue
            ) {
                Icon(Icons.Rounded.QrCodeScanner, contentDescription = "Add", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            SearchBar(
                placeholder = {
                    Text(
                        text = "Search SKU",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                colors = SearchBarDefaults.colors(MidBlue),
                query = searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = viewModel::onSearchTextChange,
                active = isSearching,
                onActiveChange = { viewModel.onToogleSearch() },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                LazyColumn {
                    items(countriesList) { country ->
                        Text(
                            text = country,
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 4.dp,
                                end = 8.dp,
                                bottom = 4.dp
                            )
                        )
                    }
                }
            }

            if (showBottomSheet && !scannedData.isNullOrEmpty()) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false }
                ) {
                    ProductsBottomSheetScreen()//Pass data so it can be shown in the sheet
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchProductsScreenPreview() {
    SearchProductsScreen(navController = NavController(LocalContext.current))
}
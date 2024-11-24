package com.example.washinton.feature.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.MidBlue
import androidx.lifecycle.Observer

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.washinton.feature.camera.BarcodeScanner
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductsScreen(navController: NavController, viewModel: SearchProductsViewModel = hiltViewModel()) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val productNamesList by viewModel.productNames.collectAsState()
    val filteredProductNames by viewModel.filteredProductNames.collectAsState()
    val productDetails by viewModel.productDetails.collectAsState()

    //Bottom Sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,)
    var bottomSheetDetails by remember { mutableStateOf<ProductDetails?>(null) }

    // Camera permission and analyzer state
    val context = LocalContext.current
    val barcodeScanner = remember { BarcodeScanner(context) }
    var scannedData by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()


    // Handle scanned data retrieval and bottom sheet state
    DisposableEffect(scannedData) {
        if (scannedData.isNotEmpty()) {
            viewModel.fetchProductDetails(scannedData) // Fetch details using scanned data
            Log.d("scannedData", scannedData)
            showBottomSheet = true
        }
        onDispose { /* i think i don't need nothing here */ }
    }

    LaunchedEffect(productDetails) { // Trigger when productDetails changes
        bottomSheetDetails = productDetails // Update bottomSheetDetails
    }


    LaunchedEffect(Unit) {
      viewModel.fetchProductNames()
        Log.d("fetched Data", productNamesList.toString())
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
                onClick = { scope.launch {
                    scannedData = barcodeScanner.startScan().toString()
                } },
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
                onActiveChange = { viewModel.onToggleSearch() },
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
                    items(filteredProductNames) { country ->
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
                    onDismissRequest = { showBottomSheet = false
                        scannedData = "" }
                ) {
                    ProductsBottomSheetScreen(details = bottomSheetDetails)//Pass data so it can be shown in the sheet
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
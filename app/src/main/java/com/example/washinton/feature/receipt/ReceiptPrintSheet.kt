package com.example.washinton.feature.receipt

import android.Manifest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.washinton.feature.camera.AnalyzerType
import com.example.washinton.feature.products.ProductsBottomSheetScreen
import com.example.washinton.feature.receipt.ReceiptComponent
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.MidBlue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ReceiptPrintSheet(navController: NavController) {

// Camera permission and analyzer state
val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
var analyzerType by remember { mutableStateOf(AnalyzerType.UNDEFINED) }
var isCameraButtonClicked by remember { mutableStateOf(false) }

var scannedData by rememberSaveable { mutableStateOf<String?>(null) }

//Botton Sheet
var showBottomSheet by remember { mutableStateOf(false) }
val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = false,)

// Handle scanned data retrieval and bottom sheet state
LaunchedEffect(navController.currentBackStackEntry?.savedStateHandle?.get<String>("scannedData")) {
    val newScannedData = navController.currentBackStackEntry
        ?.savedStateHandle?.get<String>("scannedData")

    newScannedData?.let {
        scannedData = it // Save scanned data locally
        showBottomSheet = true // Show bottom sheet after returning
    }
}

    // Handle camera permission logic based on button click
    if (isCameraButtonClicked) {
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
                    Text(text = "Orders", fontWeight = FontWeight.Bold)
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
    ){innerPadding ->
        Column (modifier = Modifier.padding(innerPadding).fillMaxWidth().fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier = Modifier.size(150.dp).clip(RoundedCornerShape(30.dp)).background(MidBlue).clickable { isCameraButtonClicked = true }){
                Column (modifier = Modifier.fillMaxWidth().fillMaxHeight(),verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.QrCodeScanner, contentDescription = "QR", tint = Color.White, modifier = Modifier.size(100.dp))
                    Text(
                        text = "Scan a Receipt",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                }
            }

            Spacer(modifier = Modifier.size(50.dp))

            Box(modifier = Modifier.size(150.dp).clip(RoundedCornerShape(30.dp)).background(MidBlue).clickable{navController.navigate("printReceipts")}){
                Column (modifier = Modifier.fillMaxWidth().fillMaxHeight(),verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.Receipt, contentDescription = "QR", tint = Color.White, modifier = Modifier.size(100.dp))
                    Text(
                        text = "Print a Receipt",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
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
                ReceiptComponent(orderID = scannedData!!, scanned = true)//This is to show the receipt but  with the button to confirm the order
            }
        }

    }



}

@Preview
@Composable
private fun ReceiptPrintSheetPreview() {
    ReceiptPrintSheet(navController = NavController(LocalContext.current))
}
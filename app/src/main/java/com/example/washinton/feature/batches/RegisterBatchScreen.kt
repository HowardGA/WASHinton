package com.example.washinton.feature.batches

import android.Manifest
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.washinton.feature.camera.AnalyzerType
import com.example.washinton.feature.receipt.ReceiptComponent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterBatchScreen(navController: NavController) {
//
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

    if (isCameraButtonClicked) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false }
        ) {
            ReceiptComponent(orderID = scannedData!!, scanned = true)//This is to show the receipt but  with the button to confirm the order
        }    }

}

@Preview
@Composable
private fun RegisterBatchScreenPreview() {
    RegisterBatchScreen(navController = NavController(LocalContext.current))
}
package com.example.washinton.feature.batches

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.washinton.feature.camera.BarcodeScanner
import com.example.washinton.feature.receipt.ReceiptComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBatchScreen(navController: NavController) {
//
    // Camera permission and analyzer state
    val context = LocalContext.current
    val barcodeScanner = remember { BarcodeScanner(context) }
    var scannedData by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isCameraButtonClicked by remember { mutableStateOf(false) }


//Botton Sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,)

    // Handle scanned data retrieval and bottom sheet state
    DisposableEffect(scannedData) {
        if (scannedData.isNotEmpty()) {
           // viewModel.fetchProductDetails(scannedData)
            showBottomSheet = true
        }
        onDispose { /* i think i don't need nothing here */ }
    }

    if (isCameraButtonClicked) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false }
        ) {
            ReceiptComponent( scanned = true, onClose = { showBottomSheet = false })//This is to show the receipt but  with the button to confirm the order
        }    }

}

@Preview
@Composable
private fun RegisterBatchScreenPreview() {
    RegisterBatchScreen(navController = NavController(LocalContext.current))
}
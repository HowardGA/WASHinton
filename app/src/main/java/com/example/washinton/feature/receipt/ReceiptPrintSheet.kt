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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.washinton.feature.camera.BarcodeScanner
import com.example.washinton.feature.products.SearchProductsViewModel
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.MidBlue
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptPrintSheet(navController: NavController, viewModel: TransferOrderDetailsViewModel = hiltViewModel()) {
    //Transfer orders from the API
    val transferOrdersDetails by viewModel.orderDetails.collectAsState()
    val orderDetails = transferOrdersDetails ?: TransferOrderDetails()

// Camera permission and analyzer state
    val context = LocalContext.current
    val barcodeScanner = remember { BarcodeScanner(context) }
    var scannedData by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

//Botton Sheet
var showBottomSheet by remember { mutableStateOf(false) }
val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = false,)

// Handle scanned data retrieval and bottom sheet state
    LaunchedEffect(scannedData) {
        if (scannedData.isNotEmpty()) {
            viewModel.getTransferOrderDetails(scannedData) // Initiate the data fetch
            showBottomSheet = true
            scannedData = ""
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
            Box(modifier = Modifier.size(150.dp).clip(RoundedCornerShape(30.dp)).background(MidBlue).clickable { scope.launch {
                scannedData = barcodeScanner.startScan().toString()}}){
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
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                ReceiptComponent(orderDetails = orderDetails, scanned = true, onClose = { showBottomSheet = false })//This is to show the receipt but  with the button to confirm the order
            }
        }

    }



}

@Preview
@Composable
private fun ReceiptPrintSheetPreview() {
    ReceiptPrintSheet(navController = NavController(LocalContext.current))
}
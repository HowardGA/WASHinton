package com.example.washinton.feature.receipt

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Store
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.Cream
import com.example.washinton.ui.theme.MidBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptsTableScreen(navController: NavController) {
    val viewModel: TransferOrderDetailsViewModel = hiltViewModel()
    var showPrintBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,)
    //Show the bottom sheet with the QR so other one can scan it

    //Transfer orders from the API
    val transferOrdersDetails by viewModel.orderDetails.collectAsState()
    val orderDetails = transferOrdersDetails ?: TransferOrderDetails()
    val transferOrders by viewModel.order.collectAsState()
    var orderIdToShow by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getTransferOrder() // Initiate the data fetch

        // Collect the state flow and wait for the first non-null value
        viewModel.order.collect { details ->
            Log.d("ReceiptComponent", "Order Details: $details")
        }
    }

    LaunchedEffect(orderIdToShow) {
        if (orderIdToShow.isNotEmpty()) {
            viewModel.getTransferOrderDetails(orderIdToShow) // Initiate the data fetch
        }
    }

    //filters
    // Filter states
    var filteredOrders by remember { mutableStateOf(transferOrders) }
    var selectedStatus by remember { mutableStateOf("") }
    var selectedStore by remember { mutableStateOf("") }

    // Apply filters whenever the filter criteria or orders change
    LaunchedEffect(transferOrders, selectedStatus, selectedStore) {
        filteredOrders = transferOrders.filter {
            (it.status in listOf("Preparing", "Delivering")) &&
                    (selectedStatus.isEmpty() || it.status.equals(selectedStatus, true)) &&
                    (selectedStore.isEmpty() || it.store.contains(selectedStore, true))
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
                    Text(text = "Pending Receipts", fontWeight = FontWeight.Bold)
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


        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter) // Keep filters at the top
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Filter by:",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    DropdownMenuFilter(
                        label = "Status",
                        options = listOf("Preparing", "Delivering"),
                        selectedOption = selectedStatus,
                        onOptionSelected = { selectedStatus = it }
                    )

                    DropdownMenuFilter(
                        label = "Store",
                        options = transferOrders.map { it.store }.distinct(),
                        selectedOption = selectedStore,
                        onOptionSelected = { selectedStore = it },

                        )
                }

                Spacer(modifier = Modifier.size(20.dp))
            }

            LazyColumn(
                modifier = with(Modifier) { padding(innerPadding).padding(20.dp) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(filteredOrders) { transferDetail ->


                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MidBlue)
                        .clickable {
                            orderIdToShow = transferDetail.transfer_id.toString()
                            showPrintBottomSheet = true
                        }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                        ) {


                            Icon(
                                imageVector = Icons.Rounded.Description,
                                contentDescription = "Arrivals & Departures",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(60.dp)
                                    .weight(1f)
                            )


                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Store,
                                        contentDescription = transferDetail.store,
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )


                                    Text(
                                        text = transferDetail.store,
                                        color = Cream,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        lineHeight = 40.sp,
                                        modifier = Modifier.width(250.dp)
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.BarChart,
                                        contentDescription = "status",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )


                                    Text(
                                        text = transferDetail.status,
                                        color = Cream,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        lineHeight = 40.sp,
                                        modifier = Modifier.width(250.dp)
                                    )
                                }
                            }


                        }

                    }

                    Spacer(modifier = Modifier.size(20.dp))
                }
            } // here
        }
        //We should show the bottom sheet after scanning a receipt
        if (showPrintBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showPrintBottomSheet = false }
            ) {
                Log.d("ReceiptComponent", "Clicked Order Details: $orderDetails")
                ReceiptComponent(orderDetails = orderDetails, onClose = { showPrintBottomSheet = false })
            }
        }
    }
}

@Composable
fun DropdownMenuFilter(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.width(180.dp)
        ) {
            Text(text = selectedOption.ifEmpty { label })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

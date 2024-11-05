package com.example.washinton.feature.receipt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.washinton.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptsTableScreen(navController: NavController) {
    var showPrintBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,)
    //Show the bottom sheet with the QR so other one can scan it


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

        Column (modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Pick an Receipt to print:",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(innerPadding)
            )

            Spacer(modifier = Modifier.size(20.dp))

            //Items to deliver
            Row (verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)){
                //first col
                Column (modifier = Modifier.weight(1f)){

                    Text(
                        text = "ID",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 10.dp))



                }
                //seccond col
                Column (modifier = Modifier.weight(1f)){
                    Text(
                        text = "Store to Deliver",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 10.dp))

                }
            }
            //Delete this row
            Row (verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)){
                //first col
                Column (modifier = Modifier.weight(1f).clickable{showPrintBottomSheet = true}){//make so that the click opens the bottom sheet with the info of that order

                    Text(
                        text = "Item 1",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 10.dp))

                }
                //seccond col
                Column (modifier = Modifier.weight(1f).clickable{showPrintBottomSheet = true}){
                    Text(
                        text = "Store A",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 10.dp))

                }
            }
        }

        //We should show the bottom sheet after scanning a receipt
        if (showPrintBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showPrintBottomSheet = false }
            ) {
                ReceiptComponent()
            }
        }
    }
}
package com.example.washinton.feature.receipt

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.washinton.R
import com.example.washinton.ui.theme.MidBlue
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptComponent() {
    val barcodeValue = "123456789012"

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxHeight().fillMaxWidth()
    ) {
        Column (modifier = Modifier.padding(10.dp)){ //make this column scrollable

            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(10.dp)){
                Box (modifier = Modifier.size(100.dp).clip(RoundedCornerShape(100.dp))) {
                    Image(
                        painter = painterResource(id = R.drawable.washinton_logo_large),
                        contentDescription = "logo"
                    )
                }
            }

            TextField(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Order ID", color = Color.White) },
                value = "1234567890",//add here the values from the DB
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Deliver To", color = Color.White) },
                value = "Store A",//add here the values from the DB
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Something Else", color = Color.White) },
                value = "Place Holder",//add here the values from the DB
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "Sending the following items:",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.size(20.dp))

            //Items to deliver
            Row (verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)){
                //first col
                Column (modifier = Modifier.weight(1f)){

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
                Column (modifier = Modifier.weight(1f)){
                    Text(
                        text = "QTY 1",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 10.dp))

                }
            }
            //Delete this row
            Row (verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)){
                //first col
                Column (modifier = Modifier.weight(1f)){

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
                Column (modifier = Modifier.weight(1f)){
                    Text(
                        text = "QTY 1",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 10.dp))

                }


            }


            if (BarcodeType.UPC_A.isValueValid(barcodeValue)) {
                Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Barcode(
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        resolutionFactor = 20, // Optionally, increase the resolution of the generated image
                        type = BarcodeType.UPC_A, // pick the type of barcode you want to render
                        value = barcodeValue
                    )
                }
            }

// You must handle invalid data yourself
            if (!BarcodeType.UPC_A.isValueValid(barcodeValue)) {
                Text("this is not code 128 compatible")
            }
        }
    }
}

@Preview
@Composable
private fun ReceiptComponentPreview() {
    ReceiptComponent()
}
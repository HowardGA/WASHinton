package com.example.washinton.feature.batches

import android.widget.Toast
import com.example.washinton.feature.receipt.TransferOrderDetails
import com.example.washinton.feature.receipt.TransferOrderDetailsViewModel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.washinton.R
import com.example.washinton.ui.theme.Cream
import com.example.washinton.ui.theme.LightBlue
import com.example.washinton.ui.theme.MidBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchesReceiptComponent(batchDetails: Batches? = null, onClose: () -> Unit) {
    val viewModel: RegisterBatchViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) { //make this column scrollable

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Box(modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(100.dp))) {
                    Image(
                        painter = painterResource(id = R.drawable.washinton_logo_large),
                        contentDescription = "logo"
                    )
                }
            }

            // Order Information
            Text(
                text = "Batch details:",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MidBlue)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(label = "Batch ID", value = batchDetails?.batchID.toString())
                    InfoRow(label = "Code", value = batchDetails?.code.toString())
                    InfoRow(label = "Name", value = batchDetails?.batchName.toString())
                    InfoRow(label = "Status", value = batchDetails?.status.orEmpty())
                    InfoRow(label = "Requested At", value = batchDetails?.requestedAt.orEmpty())
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "Receiving the following items:",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.size(20.dp))

            //Delete this row
            LazyColumn (
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                items(batchDetails?.products.orEmpty()){products ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MidBlue)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                        ) {


                            Icon(
                                imageVector = Icons.Rounded.ShoppingBag,
                                contentDescription = "item",
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
                                        imageVector = Icons.Rounded.Inventory,
                                        contentDescription = "icon",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )


                                    Text(
                                        text = products.name,
                                        color = Cream,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        lineHeight = 40.sp,
                                        maxLines = 1, // Restrict to a single line
                                        overflow = TextOverflow.Ellipsis, // Add ellipsis for long names
                                        modifier = Modifier.width(250.dp)
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.FormatListNumbered,
                                        contentDescription = "qty",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )


                                    Text(
                                        text = products.quantity.toString(),
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

                item {
                    if (batchDetails?.status != "received") {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.updateBatchStock(batchDetails?.code.toString()) { message ->
                                        Toast.makeText(context, "Admitted successfully: $message", Toast.LENGTH_SHORT).show()
                                    }
                                    onClose()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(LightBlue)
                        ) {
                            Text(
                                text = "Admit to Inventory",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                }
            }

        }
    }

}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Cream)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Cream)
    }
}


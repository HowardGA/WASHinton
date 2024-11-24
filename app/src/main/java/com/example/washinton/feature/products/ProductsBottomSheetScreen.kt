package com.example.washinton.feature.products

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
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
import coil.compose.AsyncImage
import com.example.washinton.R
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.LightBlue
import com.example.washinton.ui.theme.MidBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsBottomSheetScreen(details: ProductDetails? = null) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Log.d("details in Sheet", details.toString())
        Column(modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) { //make this column scrollable
            if (details.toString().isNotEmpty()){
        Text(
            text = "Product Details",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model = "https://washintonbackend.store/${details?.image.toString()}",
                    contentDescription = "product image",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    error = painterResource(id = R.drawable.missing_image) // Display error image
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "SKU", color = Color.White) },
                value = details?.sku ?: "",
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Name", color = Color.White) },
                value = details?.name.toString(),
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Type", color = Color.White) },
                value = details?.type.toString(),
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Description", color = Color.White) },
                value = details?.description.toString(),
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Price", color = Color.White) },
                value = details?.price.toString(),
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Category", color = Color.White) },
                value = details?.category.toString(),
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MidBlue,
                    disabledTextColor = Color.White
                ),
                label = { Text(text = "Supplier", color = Color.White) },
                value = details?.supplier.toString(),
                shape = RoundedCornerShape(16.dp),
                enabled = false,
                onValueChange = {})

                Spacer(modifier = Modifier.size(10.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MidBlue,
                        disabledTextColor = Color.White
                    ),
                    label = { Text(text = "Brand", color = Color.White) },
                    value = details?.brand.toString(),
                    shape = RoundedCornerShape(16.dp),
                    enabled = false,
                    onValueChange = {})

                Spacer(modifier = Modifier.size(10.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MidBlue,
                        disabledTextColor = Color.White
                    ),
                    label = { Text(text = "Volume", color = Color.White) },
                    value = "${details?.volume} ${details?.unit}",
                    shape = RoundedCornerShape(16.dp),
                    enabled = false,
                    onValueChange = {})

            SuggestionChip(
                onClick = {},
                label = { Text(text = details?.status.toString(), color = Color.Green, modifier = Modifier.padding(8.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                border = BorderStroke(2.dp, Color.Green),
            )


            }else{
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column (modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Rounded.Error,
                            contentDescription = "error",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(230.dp)
                        )

                        Text(
                            text = "404, product not found",
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun BtmSheetScreenPreview() {
    ProductsBottomSheetScreen()
}
package com.example.washinton.feature.products

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.washinton.R
import com.example.washinton.ui.theme.DarkBlue

@Composable
fun ProductsBottomSheetScreen(details: ProductDetails? = null) {
Column (modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)){
    Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center)
    {
        Image(painter = painterResource(id = R.drawable.pinol), contentDescription = null, modifier = Modifier
            .size(130.dp)
            .weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)){
            Text(
                text = "SKU: ${details?.sku}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color  = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = "Name: Product Name",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = "Price: Product Name",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )


        }
    }

    Spacer(modifier = Modifier.size(25.dp))

    Text(
        text = "Description",
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .height(70.dp)
    )

    Text(
        text = "Status: Product Status",
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Text(
        text = "Category: Product Category",
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
}

@Preview
@Composable
private fun BtmSheetScreenPreview() {
    ProductsBottomSheetScreen()
}
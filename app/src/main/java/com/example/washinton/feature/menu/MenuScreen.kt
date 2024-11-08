package com.example.washinton.feature.menu

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AirportShuttle
import androidx.compose.material.icons.rounded.BackHand
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.washinton.ui.theme.Cream
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.LightBlue
import com.example.washinton.ui.theme.LightMint
import com.example.washinton.ui.theme.MidBlue
import com.example.washinton.ui.theme.WashintonTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun MenuScreen(navController: NavController) {

    Column (
        verticalArrangement = Arrangement.spacedBy(30.dp),modifier = Modifier.padding(20.dp)
    ){

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(LightBlue)
            .clickable{navController.navigate("products")
        },
        ){
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),) {


                    Icon(
                        imageVector = Icons.Rounded.Inventory2,
                        contentDescription = "inventory icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(70.dp)
                            .weight(1f)
                    )



                    Text(
                        text = "Search Products",
                        color = Cream,
                        fontWeight = FontWeight.Bold,
                        fontSize = 39.sp,
                        lineHeight = 40.sp,
                        modifier = Modifier.width(250.dp)
                    )
                }

        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MidBlue)
            .clickable{navController.navigate("receipts")
            }
            ,
        ){
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()) {


                Icon(
                    imageVector = Icons.Rounded.AirportShuttle,
                    contentDescription = "Arrivals & Departures",
                    tint = Color.White,
                    modifier = Modifier
                        .size(70.dp)
                        .weight(1f)
                )



                Text(
                    text = "Arrivals & Departures",
                    color = Cream,
                    fontWeight = FontWeight.Bold,
                    fontSize = 39.sp,
                    lineHeight = 40.sp,
                    modifier = Modifier.width(250.dp)
                )

            }

        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(LightBlue),
        ){
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()) {


                Icon(
                    imageVector = Icons.Rounded.Inventory,
                    contentDescription = "batches icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(70.dp)
                        .weight(1f)
                )



                Text(
                    text = "Register Incoming Batches",
                    color = Cream,
                    fontWeight = FontWeight.Bold,
                    fontSize = 39.sp,
                    lineHeight = 40.sp,
                    modifier = Modifier.width(250.dp)
                )

            }

        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MidBlue),
        ){
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()) {


                    Icon(
                        imageVector = Icons.Rounded.AirportShuttle,
                        contentDescription = "Arrivals & Departures",
                        tint = Color.White,
                        modifier = Modifier
                            .size(70.dp)
                            .weight(1f)
                    )



                Text(
                    text = "Arrivals & Departures",
                    color = Cream,
                    fontWeight = FontWeight.Bold,
                    fontSize = 39.sp,
                    lineHeight = 40.sp,
                    modifier = Modifier.width(250.dp)
                )

            }

        }

    }

}


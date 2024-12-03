package com.example.washinton.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.washinton.R
import com.example.washinton.ui.theme.Cream
import com.example.washinton.ui.theme.DarkBlue
import com.example.washinton.ui.theme.MidBlue
import com.example.washinton.ui.theme.RedLogOut
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val profile by viewModel.profile.collectAsState()
    val userId = Firebase.auth.currentUser?.uid


    LaunchedEffect(Unit) {
        viewModel.getProfile(userId.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBlue,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(text = "Profile", fontWeight = FontWeight.Bold)
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

    ){
        Column(modifier = Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.unknown_user),
                contentDescription = "logo",
                modifier = Modifier.clip(RoundedCornerShape(100.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                colors = CardDefaults.cardColors(containerColor = MidBlue)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    com.example.washinton.feature.receipt.InfoRow(label = "Name", value = profile?.name.toString())
                    com.example.washinton.feature.receipt.InfoRow(label = "Email", value = profile?.email.toString())
                    com.example.washinton.feature.receipt.InfoRow(label = "Phone", value = profile?.phone.toString())
                    com.example.washinton.feature.receipt.InfoRow(label = "Role", value = profile?.role?.name.toString())
                    com.example.washinton.feature.receipt.InfoRow(label = "Location", value = profile?.locationType.toString())
                    com.example.washinton.feature.receipt.InfoRow(label = "Status", value = profile?.status.toString())
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(20.dp)){
                Button(modifier = Modifier.fillMaxWidth().height(50.dp),colors = ButtonDefaults.buttonColors(RedLogOut), onClick = {  Firebase.auth.signOut()
                    navController.navigate("login") }) {
                    Text(text = "Sign Out", color = Color.White)
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
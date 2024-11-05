package com.example.washinton

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.washinton.feature.signin.SignInScreen
import com.example.washinton.feature.home.HomeScreen
import com.example.washinton.feature.products.SearchProductsScreen
import com.example.washinton.feature.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import com.example.washinton.feature.camera.AnalyzerType
import com.example.washinton.feature.camera.CameraScreen
import com.example.washinton.feature.receipt.ReceiptComponent
import com.example.washinton.feature.receipt.ReceiptPrintSheet
import com.example.washinton.feature.receipt.ReceiptsTableScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    Surface (modifier = Modifier.fillMaxSize())
    {
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val start = if (currentUser != null) "home" else "login"

        NavHost(navController = navController, startDestination = start) {

            composable("login") {
                SignInScreen(navController)
            }

            composable("register") {
                SignUpScreen(navController)
            }

            composable("home") {
                HomeScreen(navController)
            }

            composable("products") {
                SearchProductsScreen(navController)
            }

            // Add the camera screen route
            composable("camera/{analyzerType}") { backStackEntry ->
                val analyzerType = backStackEntry.arguments?.getString("analyzerType")
                    ?.let { AnalyzerType.valueOf(it) }
                if (analyzerType != null) {
                    Log.d("MainApp", "AnalyzerType: $analyzerType")
                    CameraScreen(analyzerType = analyzerType, onScanCompleted = { scannedData ->
                        // Navigate back to SearchProductsScreen with data
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "scannedData",
                            scannedData
                        )
                        navController.popBackStack()
                    })

                }
            }

            composable("receipts") {
                ReceiptPrintSheet(navController)
            }

            composable("printReceipts") {
                ReceiptsTableScreen(navController)
            }
        }
    }
}


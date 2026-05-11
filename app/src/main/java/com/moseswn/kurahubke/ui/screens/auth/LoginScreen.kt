package com.moseswn.kurahubke.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.moseswn.kurahubke.navigation.ROUT_FAQS
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(200)
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000000),
                        Color(0xFFB71C1C),
                        Color(0xFF1B5E20)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            verticalArrangement = Arrangement.Center
        ) {

            AnimatedVisibility(
                visible = startAnimation,
                enter = fadeIn() + slideInVertically()
            ) {

                Column {

                    // ================= HEADER CARD =================

                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(30.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.10f)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(24.dp),

                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.12f)
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Login,
                                    contentDescription = null,

                                    tint = Color.White,

                                    modifier = Modifier
                                        .padding(18.dp)
                                        .size(36.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Welcome Back",

                                fontSize = 32.sp,

                                fontWeight = FontWeight.ExtraBold,

                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Login to continue accessing trusted civic information.",

                                fontSize = 15.sp,

                                color = Color.White.copy(alpha = 0.82f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // ================= EMAIL =================

                    OutlinedTextField(
                        value = email.value,

                        onValueChange = {
                            email.value = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Email Address")
                        },

                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),

                        singleLine = true,

                        shape = RoundedCornerShape(20.dp),

                        colors = authTextFieldColors()
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // ================= PASSWORD =================

                    OutlinedTextField(
                        value = password.value,

                        onValueChange = {
                            password.value = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Password")
                        },

                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),

                        visualTransformation =
                            PasswordVisualTransformation(),

                        singleLine = true,

                        shape = RoundedCornerShape(20.dp),

                        colors = authTextFieldColors()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // ================= FORGOT PASSWORD =================

                    TextButton(
                        onClick = {
                            // TODO: Forgot password logic
                        },

                        modifier = Modifier.align(Alignment.End)
                    ) {

                        Text(
                            text = "Forgot Password?",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    // ================= LOGIN BUTTON =================

                    Button(
                        onClick = {

                            if (
                                email.value.isBlank() ||
                                password.value.isBlank()
                            ) {

                                Toast.makeText(
                                    context,
                                    "Fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }

                            isLoading.value = true

                            val auth =
                                FirebaseAuth.getInstance()

                            auth.signInWithEmailAndPassword(
                                email.value,
                                password.value
                            ).addOnCompleteListener { task ->

                                isLoading.value = false

                                if (task.isSuccessful) {

                                    Toast.makeText(
                                        context,
                                        "Login successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate(
                                        ROUT_FAQS
                                    ) {

                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }

                                } else {

                                    Toast.makeText(
                                        context,
                                        task.exception?.message
                                            ?: "Login failed",

                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),

                        shape = RoundedCornerShape(22.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {

                        if (isLoading.value) {

                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),

                                color = Color(0xFF1B5E20),

                                strokeWidth = 2.dp
                            )

                        } else {

                            Text(
                                text = "Login",

                                color = Color(0xFF1B5E20),

                                fontWeight = FontWeight.Bold,

                                fontSize = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // ================= SIGNUP =================

                    TextButton(
                        onClick = {
                            navController.navigate(ROUT_FAQS)
                        },

                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    ) {

                        Text(
                            text =
                                "Don't have an account? Create Account",

                            color = Color.White,

                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    // ================= FOOTER =================

                    Column(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Neutral Civic Information Only",

                            color = Color.White.copy(alpha = 0.75f),

                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Powered by Official Kenyan Civic Sources",

                            color = Color.White.copy(alpha = 0.55f),

                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun authTextFieldColors() = OutlinedTextFieldDefaults.colors(

    focusedTextColor = Color.White,

    unfocusedTextColor = Color.White,

    focusedBorderColor = Color.White,

    unfocusedBorderColor =
        Color.White.copy(alpha = 0.45f),

    focusedLabelColor = Color.White,

    unfocusedLabelColor =
        Color.White.copy(alpha = 0.7f),

    cursorColor = Color.White,

    focusedLeadingIconColor = Color.White,

    unfocusedLeadingIconColor =
        Color.White.copy(alpha = 0.7f)
)


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    LoginScreen(
        rememberNavController()
    )
}
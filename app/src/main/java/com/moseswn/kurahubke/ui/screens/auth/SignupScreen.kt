package com.moseswn.kurahubke.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.moseswn.kurahubke.R
import com.moseswn.kurahubke.navigation.ROUT_LOGIN

@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current

    val fullName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000000),
                        Color(0xFF8B0000),
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
                enter = fadeIn() + scaleIn()
            ) {

                Column {

                    // ================= HEADER =================

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f)
                    ) {

                        Box(
                            modifier = Modifier
                                .size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.img_4),

                        contentDescription = "App Logo",

                        modifier = Modifier
                            .size(200.dp)
                            .padding(18.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Join Kura Hub KE for trusted civic information.",
                        fontSize = 15.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // ================= FULL NAME =================

                    OutlinedTextField(
                        value = fullName.value,

                        onValueChange = {
                            fullName.value = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Full Name",
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },

                        singleLine = true,

                        shape = RoundedCornerShape(18.dp),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedTextColor = Color.White,

                            unfocusedTextColor = Color.White,

                            focusedBorderColor = Color.White,

                            unfocusedBorderColor =
                                Color.White.copy(alpha = 0.4f),

                            focusedLabelColor = Color.White,

                            unfocusedLabelColor =
                                Color.White.copy(alpha = 0.7f),

                            cursorColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // ================= EMAIL =================

                    OutlinedTextField(
                        value = email.value,

                        onValueChange = {
                            email.value = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text(
                                "Email Address",
                                color = Color.White.copy(alpha = 0.8f)
                            )
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

                        shape = RoundedCornerShape(18.dp),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedTextColor = Color.White,

                            unfocusedTextColor = Color.White,

                            focusedBorderColor = Color.White,

                            unfocusedBorderColor =
                                Color.White.copy(alpha = 0.4f),

                            focusedLabelColor = Color.White,

                            unfocusedLabelColor =
                                Color.White.copy(alpha = 0.7f),

                            cursorColor = Color.White
                        )
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
                            Text(
                                "Password",
                                color = Color.White.copy(alpha = 0.8f)
                            )
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

                        shape = RoundedCornerShape(18.dp),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedTextColor = Color.White,

                            unfocusedTextColor = Color.White,

                            focusedBorderColor = Color.White,

                            unfocusedBorderColor =
                                Color.White.copy(alpha = 0.4f),

                            focusedLabelColor = Color.White,

                            unfocusedLabelColor =
                                Color.White.copy(alpha = 0.7f),

                            cursorColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(34.dp))

                    // ================= SIGNUP BUTTON =================

                    Button(
                        onClick = {

                            if (
                                email.value.isBlank() ||
                                password.value.isBlank() ||
                                fullName.value.isBlank()
                            ) {

                                Toast.makeText(
                                    context,
                                    "Fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }

                            isLoading.value = true

                            val auth = FirebaseAuth.getInstance()

                            val database =
                                FirebaseDatabase
                                    .getInstance()
                                    .reference

                            auth.createUserWithEmailAndPassword(
                                email.value,
                                password.value
                            ).addOnCompleteListener { task ->

                                if (task.isSuccessful) {

                                    val userId =
                                        auth.currentUser?.uid ?: ""

                                    val userMap = mapOf(
                                        "fullName" to fullName.value,
                                        "email" to email.value,
                                        "userId" to userId
                                    )

                                    database.child("users")
                                        .child(userId)
                                        .setValue(userMap)
                                        .addOnCompleteListener { dbTask ->

                                            isLoading.value = false

                                            if (dbTask.isSuccessful) {

                                                Toast.makeText(
                                                    context,
                                                    "Account created successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                navController.navigate(
                                                    ROUT_LOGIN
                                                ) {

                                                    popUpTo("signup") {
                                                        inclusive = true
                                                    }
                                                }

                                            } else {

                                                Toast.makeText(
                                                    context,
                                                    "Database error",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                } else {

                                    isLoading.value = false

                                    Toast.makeText(
                                        context,
                                        task.exception?.message
                                            ?: "Signup failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {

                        if (isLoading.value) {

                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color(0xFF1B5E20),
                                strokeWidth = 2.dp
                            )

                        } else {

                            Text(
                                text = "Create Account",
                                color = Color(0xFF1B5E20),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // ================= LOGIN NAV =================

                    TextButton(
                        onClick = {
                            navController.navigate("login")
                        }
                    ) {

                        Text(
                            text = "Already have an account? Login",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // ================= FOOTER =================

                    Text(
                        text = "Neutral Civic Information Only",

                        color = Color.White.copy(alpha = 0.7f),

                        fontSize = 12.sp,

                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SignupScreenPreview() {

    SignupScreen(
        rememberNavController()
    )
}
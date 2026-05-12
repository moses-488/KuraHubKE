package com.moseswn.kurahubke.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.moseswn.kurahubke.navigation.ROUT_LOGIN
import com.moseswn.kurahubke.navigation.ROUT_SIGNUP

@Composable
fun SignupScreen(navController: NavController) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    var startAnimation by remember { mutableStateOf(false) }

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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = startAnimation,
                enter = fadeIn() + scaleIn()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ================= HEADER =================
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier.size(90.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Join Kura Hub KE",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Trusted civic information for every Kenyan.",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // ================= FULL NAME =================
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Full Name") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = signupTextFieldColors()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ================= EMAIL =================
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email Address") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = signupTextFieldColors()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ================= PASSWORD =================
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Toggle password" else "Toggle password"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = signupTextFieldColors()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // ================= SIGNUP BUTTON =================
                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank() || fullName.isBlank()) {
                                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (password.length < 6) {
                                Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            isLoading = true
                            val auth = FirebaseAuth.getInstance()
                            val database = FirebaseDatabase.getInstance().reference

                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val userId = auth.currentUser?.uid ?: ""
                                        val userMap = mapOf(
                                            "fullName" to fullName,
                                            "email" to email,
                                            "userId" to userId,
                                            "createdAt" to System.currentTimeMillis()
                                        )

                                        database.child("users").child(userId).setValue(userMap)
                                            .addOnCompleteListener { dbTask ->
                                                isLoading = false
                                                if (dbTask.isSuccessful) {
                                                    Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                                    navController.navigate(ROUT_LOGIN) {
                                                        popUpTo(ROUT_SIGNUP) { inclusive = true }
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Failed to save user data", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                    } else {
                                        isLoading = false
                                        Toast.makeText(context, task.exception?.message ?: "Signup failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF1B5E20)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color(0xFF1B5E20))
                        } else {
                            Text("CREATE ACCOUNT", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ================= LOGIN LINK =================
                    TextButton(
                        onClick = { navController.navigate(ROUT_LOGIN) }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Already have an account?", color = Color.White.copy(alpha = 0.8f))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // ================= FOOTER =================
                    Text(
                        text = "Official Kenyan Civic Information Only",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun signupTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
    cursorColor = Color.White,
    focusedLeadingIconColor = Color.White,
    unfocusedLeadingIconColor = Color.White.copy(alpha = 0.7f),
    focusedTrailingIconColor = Color.White,
    unfocusedTrailingIconColor = Color.White.copy(alpha = 0.7f)
)

@Preview(showBackground = true)
@Composable
fun SignupScreenModernPreview() {
    SignupScreen(rememberNavController())
}

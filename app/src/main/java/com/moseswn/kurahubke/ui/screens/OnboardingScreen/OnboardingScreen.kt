package com.moseswn.kurahubke.ui.screens.OnboardingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.R
import com.moseswn.kurahubke.navigation.ROUT_SIGNUP
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    navController: NavController
) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    var loadingProgress by remember {
        mutableFloatStateOf(0f)
    }

    val alphaAnim by animateFloatAsState(

        targetValue =
            if (startAnimation) 1f
            else 0f,

        animationSpec = tween(
            durationMillis = 1500,
            easing = FastOutSlowInEasing
        ),

        label = "Onboarding Animation"
    )

    LaunchedEffect(Unit) {

        startAnimation = true

        repeat(100) {

            loadingProgress += 0.01f

            delay(10)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(

                Brush.verticalGradient(

                    colors = listOf(

                        // Kenyan Flag Inspired Colors
                        Color(0xFF000000),
                        Color(0xFFB71C1C),
                        Color(0xFF1B5E20)
                    )
                )
            )
            .alpha(alphaAnim)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),

            verticalArrangement = Arrangement.SpaceBetween,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            // LOGO + TEXT SECTION
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(

                    visible = startAnimation,

                    enter = fadeIn() +
                            scaleIn() +
                            slideInVertically()
                ) {

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.img_2),

                            contentDescription = "App Logo",

                            modifier = Modifier
                                .size(200.dp)
                                .padding(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Kura Hub KE",

                    fontSize = 34.sp,

                    fontWeight = FontWeight.ExtraBold,

                    color = Color.White
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Verified. Reliable. Neutral.",

                    fontSize = 16.sp,

                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your vote. Your future.",

                    fontSize = 15.sp,

                    color = Color(0xFFDDE5DD)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // LOADING BAR
                LinearProgressIndicator(

                    progress = {
                        loadingProgress
                    },

                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(50.dp)),

                    color = Color.White,

                    trackColor = Color.White.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.5.dp
                )
            }

            // BUTTONS SECTION
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        navController.navigate(ROUT_SIGNUP)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {

                    Text(
                        text = "Get Started",

                        color = Color(0xFF1B5E20),

                        fontWeight = FontWeight.Bold,

                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        navController.navigate(ROUT_SIGNUP)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(18.dp),

                    border = BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.5f)
                    )
                ) {

                    Text(
                        text = "I already have an account",

                        color = Color.White,

                        fontSize = 15.sp,

                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // FOOTER
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Neutral Civic Information Only",

                    fontSize = 12.sp,

                    color = Color.White.copy(alpha = 0.75f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Powered by Official Kenyan Civic Sources",

                    fontSize = 11.sp,

                    color = Color.White.copy(alpha = 0.55f)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OnboardingScreenPreview() {

    OnboardingScreen(
        rememberNavController()
    )
}
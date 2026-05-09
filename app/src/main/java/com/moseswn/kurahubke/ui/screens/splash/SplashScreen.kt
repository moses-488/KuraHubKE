package com.moseswn.kurahubke.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.R
import com.moseswn.kurahubke.navigation.ROUT_ONBOARDING
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
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
            durationMillis = 1800,
            easing = FastOutSlowInEasing
        ),

        label = "Splash Alpha Animation"
    )

    LaunchedEffect(Unit) {

        startAnimation = true

        // FAKE LOADING PROGRESS
        repeat(100) {

            loadingProgress += 0.01f

            delay(20)
        }

        delay(1200)

        // NAVIGATION
        navController.navigate(ROUT_ONBOARDING) {

            popUpTo("splash") {
                inclusive = true
            }
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

        // MAIN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(

                visible = startAnimation,

                enter = fadeIn() + scaleIn(),

                exit = fadeOut() + scaleOut()
            ) {

                Surface(

                    shape = CircleShape,

                    color = Color.White.copy(alpha = 0.12f)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.img_3),

                        contentDescription = "Kenya Voter Hub Logo",

                        modifier = Modifier
                            .size(180.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Kenya Voter Hub",

                fontSize = 32.sp,

                fontWeight = FontWeight.ExtraBold,

                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Neutral Civic Information for Kenyan Voters",

                fontSize = 15.sp,

                color = Color.White.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // LOADING PROGRESS
            LinearProgressIndicator(

                progress = {
                    loadingProgress
                },

                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .height(10.dp),

                color = Color.White,

                trackColor = Color.White.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "${(loadingProgress * 100).toInt()}%",

                fontSize = 14.sp,

                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(22.dp))

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Verified • Reliable • Neutral",

                fontSize = 13.sp,

                color = Color(0xFFDDE5DD)
            )
        }

        // FOOTER
        Text(
            text = "Powered by IEBC Civic Information",

            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),

            fontSize = 12.sp,

            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SplashScreenPreview() {

    SplashScreen(
        rememberNavController()
    )
}
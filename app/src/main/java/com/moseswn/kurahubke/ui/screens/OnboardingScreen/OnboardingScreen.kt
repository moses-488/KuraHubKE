package com.moseswn.kurahubke.ui.screens.OnboardingScreen


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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.R




@Composable
fun OnboardingScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B5E20))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            verticalArrangement = Arrangement.SpaceBetween,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // LOGO SECTION
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.img_2),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(180.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Kura Hub KE",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Verified. Reliable. Neutral.",
                    fontSize = 16.sp,
                    color = Color(0xFFDDE5DD)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your vote. Your future.",
                    fontSize = 15.sp,
                    color = Color(0xFFDDE5DD)
                )
            }

            // BUTTON SECTION
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        navController.navigate("signup")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(16.dp),

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

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = {
                        navController.navigate("login")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(16.dp),

                    border = BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.5f)
                    )
                ) {

                    Text(
                        text = "I already have an account",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }

            // FOOTER
            Text(
                text = "Neutral Civic Information Only",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
@Composable
@Preview(showBackground = true)
fun OnboardingScreenPreview(){
    OnboardingScreen(rememberNavController())
}
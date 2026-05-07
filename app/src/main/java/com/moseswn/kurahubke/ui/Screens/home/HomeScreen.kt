package com.moseswn.kurahubke.ui.Screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.R.drawable.img_3
import com.moseswn.kurahubke.modules.ChecklistItem



        @Composable
        fun HomeScreen(navController: NavController) {

            // Placeholder for dynamic data (you can update it from ViewModel)
            val checklistItems = listOf(
                "Original National ID Card",
                "Kenya Passport (alternative to ID)",
                "Confirm you're on the voter register",
                "Know your polling station name/code",
                "Arrive between 06:00 - 17:00",
                "No phone or photos in the booth"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE1E8EB)) // Light grey background for the main body
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Top
                ) {

                    // Header with Logo and App Name
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painterResource(id = img_3), "Kura Hub KE Logo", Modifier.run { size(40.dp) }
                        )
                        Text(
                            text = "Kura Hub KE",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Welcome Section
                    Text(
                        text = "Welcome to Kura Hub KE",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your vote. Your future. Get ready to vote with confidence.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Spacer(modifier = Modifier.height(16.dp))

                    // Voting Checklist Section
                    Text(
                        text = "Voting Day Checklist",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    checklistItems.forEach { item ->
                        ChecklistItem(item)
                    }

                    Spacer(modifier = Modifier.height(16.dp))



                }
            }
        }

        @Composable
        fun VoterReadinessBar(completed: Int, total: Int) {
            Column {
                Text(
                    text = "Voter Readiness",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = completed.toFloat() / total.toFloat(),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF1B5E20),
                    trackColor = Color.LightGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$completed / $total required",
                    fontSize = 14.sp,
                    color = Color(0xFF1B5E20)
                )
            }
        }

        @Composable
        fun ChecklistItem(item: String) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1B5E20))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        @Composable
        fun QuickAccessButton(text: String) {
            Button(
                onClick = { /* Navigate to corresponding screen */ },
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B5E20)
                )
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }


























@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    HomeScreen(rememberNavController())
}
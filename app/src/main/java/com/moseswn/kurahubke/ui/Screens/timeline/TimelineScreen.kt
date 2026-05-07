package com.moseswn.kurahubke.ui.Screens.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.Timelineitems.TimelineItem


        @Composable
        fun TimelineScreen(navController: NavController) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F7F7))
            ) {

                // 🌿 HEADER (GREEN TOP BAR)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1B5E20))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Kura Hub KE",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "VERIFIED • RELIABLE • NEUTRAL",
                            color = Color(0xFFD4AF37),
                            fontSize = 12.sp
                        )
                    }
                }

                // 📄 CONTENT
                Column(modifier = Modifier.padding(16.dp)) {

                    // ⚠️ Neutral Info Card
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "Neutral civic information only. No endorsements. All data sourced from IEBC and official government notices.",
                            modifier = Modifier.padding(12.dp),
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🗓️ TITLE
                    Text(
                        text = "Key Dates & Timeline",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Dates marked TBA will be updated when officially announced.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // ⏳ TIMELINE ITEMS
                    TimelineItem(
                        title = "Voter Registration",
                        subtitle = "TBA – IEBC to announce",
                        description = "Register as a new voter or update your details at any IEBC center.",
                        source = "IEBC Kenya"
                    )

                    TimelineItem(
                        title = "Register Verification",
                        subtitle = "TBA – ~30 days before election",
                        description = "Confirm your details appear correctly on the register.",
                        source = "Elections Act"
                    )
                }
            }
        }
























@Composable
@Preview(showBackground = true)
fun TimelineScreenPreview(){
    TimelineScreen(rememberNavController())
}
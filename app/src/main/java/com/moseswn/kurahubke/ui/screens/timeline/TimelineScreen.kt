package com.moseswn.kurahubke.ui.screens.timeline

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class TimelineEvent(
    val title: String,
    val date: String,
    val description: String
)

@Composable
fun TimelineScreen(
    navController: NavController
) {

    val timelineEvents = listOf(
        TimelineEvent(
            title = "Voter Registration",
            date = "12 May 2026",
            description = "Eligible citizens can register as voters at designated IEBC centers."
        ),
        TimelineEvent(
            title = "Voter Verification",
            date = "08 June 2026",
            description = "Verify your registration details and polling station information."
        ),
        TimelineEvent(
            title = "Election Campaign Period",
            date = "20 July 2026",
            description = "Official campaign activities begin under IEBC regulations."
        ),
        TimelineEvent(
            title = "Election Day",
            date = "10 August 2026",
            description = "Polling stations open from 6:00 AM to 5:00 PM."
        ),
        TimelineEvent(
            title = "Results Announcement",
            date = "12 August 2026",
            description = "Official election results released by IEBC."
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B5E20))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            // TOP BAR
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Election Timeline",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Track important election dates and official civic activities.",
                fontSize = 14.sp,
                color = Color(0xFFDDE5DD)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // TIMELINE LIST
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                items(timelineEvents) { event ->
                    TimelineCard(event)
                }
            }
        }
    }
}

@Composable
fun TimelineCard(
    event: TimelineEvent
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            // ICON CIRCLE
            Box(
                modifier = Modifier
                    .background(
                        Color.White.copy(alpha = 0.15f),
                        CircleShape
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Info, // Changed from Event to Info to fix NoClassDefFoundError in Preview
                    contentDescription = "Event",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // TEXT CONTENT
            Column {

                Text(
                    text = event.date,
                    fontSize = 13.sp,
                    color = Color(0xFFDDE5DD)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = event.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Source: IEBC",
                    fontSize = 12.sp,
                    color = Color(0xFFA5D6A7),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TimelineScreenPreview(){
    TimelineScreen(rememberNavController())
}

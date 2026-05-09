package com.moseswn.kurahubke.ui.screens.checklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// ---------------- DATA ----------------
data class ChecklistItem(
    val title: String,
    val description: String
)

// ---------------- SCREEN ----------------
@Composable
fun ChecklistScreen(navController: NavController) {

    val items = listOf(
        ChecklistItem("Valid National ID", "Carry your original Kenyan ID card."),
        ChecklistItem("Check Voter Register", "Confirm your name appears in IEBC register."),
        ChecklistItem("Know Polling Station", "Verify your assigned voting location."),
        ChecklistItem("Voting Time", "6:00 AM to 5:00 PM only."),
        ChecklistItem("No Phones in Booth", "Photography and phones are not allowed."),
        ChecklistItem("Arrive Early", "Avoid long queues by arriving early.")
    )

    val checkedStates = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(items.size) { add(false) }
        }
    }

    val completed = checkedStates.count { it }

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
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // ================= HEADER (ONBOARDING STYLE) =================
            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f)
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Voting Checklist",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )

                        Text(
                            text = "Prepare for election day",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Track your readiness before voting day and ensure you are fully prepared.",
                    fontSize = 14.sp,
                    color = Color(0xFFDDE5DD)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ================= PROGRESS (ONBOARDING STYLE) =================
                LinearProgressIndicator(
                    progress = completed.toFloat() / items.size.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$completed / ${items.size} Completed",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            // ================= LIST =================
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                itemsIndexed(items) { index, item ->

                    ChecklistCard(
                        item = item,
                        checked = checkedStates[index],
                        onToggle = {
                            checkedStates[index] = !checkedStates[index]
                        }
                    )
                }
            }

            // ================= FOOTER (MATCH ONBOARDING STYLE) =================
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Neutral Civic Information Only",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.75f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Kura Hub KE Civic System",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.55f)
                )
            }
        }
    }
}

// ---------------- CARD ----------------
@Composable
fun ChecklistCard(
    item: ChecklistItem,
    checked: Boolean,
    onToggle: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onToggle) {

                Icon(
                    imageVector = if (checked)
                        Icons.Default.CheckCircle
                    else
                        Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// ---------------- PREVIEW ----------------
@Preview(showBackground = true)
@Composable
fun ChecklistPreview() {
    ChecklistScreen(rememberNavController())
}
package com.moseswn.kurahubke.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.moseswn.kurahubke.R.drawable.img_3
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val checklistItems = listOf(
        "Original National ID Card",
        "Kenya Passport (alternative to ID)",
        "Confirm you're on the voter register",
        "Know your polling station name/code",
        "Arrive between 06:00 - 17:00",
        "No phone or photos in the booth"
    )

    val checkedStates = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(checklistItems.size) { add(false) }
        }
    }

    val completedCount = checkedStates.count { it }
    val totalItems = checklistItems.size

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF000000),
                        Color(0xFFB71C1C),
                        Color(0xFF1B5E20)
                    )
                )
            )
    ) {

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // ================= HEADER =================
            Column {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f)
                    ) {
                        Image(
                            painter = painterResource(id = img_3),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(44.dp)
                                .padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Kura Hub KE",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )

                        Text(
                            text = "Your vote. Your future.",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Welcome to your civic readiness dashboard. Track your voting preparation easily.",
                    fontSize = 14.sp,
                    color = Color(0xFFDDE5DD)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ================= PROGRESS =================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.08f)
                    )
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Voter Readiness",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = completedCount.toFloat() / totalItems.toFloat(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50.dp)),
                            color = Color.White,
                            trackColor = Color.White.copy(alpha = 0.2f)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "$completedCount / $totalItems Completed",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Voting Day Checklist",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // ================= CHECKLIST =================
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(checklistItems.size) { index ->

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

                            IconButton(
                                onClick = {
                                    checkedStates[index] = !checkedStates[index]

                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            if (checkedStates[index])
                                                "Checklist completed"
                                            else
                                                "Checklist removed"
                                        )
                                    }
                                }
                            ) {

                                Icon(
                                    imageVector = if (checkedStates[index])
                                        Icons.Default.CheckCircle
                                    else
                                        Icons.Default.RadioButtonUnchecked,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = checklistItems[index],
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // ================= FOOTER =================
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Neutral Civic Information Only",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.75f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Powered by Kura Hub KE",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.55f)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
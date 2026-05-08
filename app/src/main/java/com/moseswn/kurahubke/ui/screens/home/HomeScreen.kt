package com.moseswn.kurahubke.ui.screens.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.moseswn.kurahubke.R.drawable.img_3
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val checklistItems = listOf(
        "Original National ID Card",
        "Kenya Passport (alternative to ID)",
        "Confirm you're on the voter register",
        "Know your polling station name/code",
        "Arrive between 06:00 - 17:00",
        "No phone or photos in the booth"
    )

    // Checkbox states
    val checkedStates = remember {
        mutableStateListOf(
            false,
            false,
            false,
            false,
            false,
            false
        )
    }

    val completedCount = checkedStates.count { it }

    val totalItems = checklistItems.size

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B5E20))
    ) {

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            verticalArrangement = Arrangement.Top
        ) {

            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = img_3),
                        contentDescription = "Kura Hub KE Logo",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Kura Hub KE",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Text(
                    text = "Offline",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // WELCOME SECTION
            Text(
                text = "Welcome to Kura Hub KE",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your vote. Your future. Get ready to vote with confidence.",
                fontSize = 14.sp,
                color = Color(0xFFDDE5DD)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // READINESS CARD
            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.08f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Voter Readiness",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = {
                            completedCount.toFloat() / totalItems.toFloat()
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),

                        color = Color.White,

                        trackColor = Color.White.copy(alpha = 0.2f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "$completedCount / $totalItems Completed",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // CHECKLIST TITLE
            Text(
                text = "Voting Day Checklist",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CHECKLIST ITEMS
            checklistItems.forEachIndexed { index, item ->

                ChecklistItem(
                    item = item,
                    checked = checkedStates[index],

                    onCheckedChange = { isChecked ->

                        checkedStates[index] = isChecked

                        scope.launch {

                            snackbarHostState.showSnackbar(

                                if (isChecked)
                                    "Checklist item completed"
                                else
                                    "Checklist item unchecked"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            // QUICK ACTIONS
            Text(
                text = "Quick Access",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                QuickAccessButton(
                    text = "Timeline"
                ) {
                    navController.navigate("timeline")
                }

                QuickAccessButton(
                    text = "FAQs"
                ) {
                    navController.navigate("faq")
                }

                QuickAccessButton(
                    text = "Polling"
                ) {
                    navController.navigate("polling")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // FOOTER
            Text(
                text = "Neutral Civic Information Only",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ChecklistItem(
    item: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = checked,

                onCheckedChange = {
                    onCheckedChange(it)
                },

                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color(0xFF1B5E20)
                )
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = item,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun QuickAccessButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = {
            onClick()
        },

        modifier = Modifier
            .width(100.dp)
            .height(50.dp),

        shape = RoundedCornerShape(14.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {

        Text(
            text = text,
            color = Color(0xFF1B5E20),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    HomeScreen(
        rememberNavController()
    )
}
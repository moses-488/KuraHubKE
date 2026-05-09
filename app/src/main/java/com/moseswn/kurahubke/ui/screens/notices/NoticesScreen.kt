package com.moseswn.kurahubke.ui.screens.notices



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

// ---------------- DATA ----------------

data class Notice(
    val title: String,
    val message: String,
    val category: String,
    val time: String
)

// ---------------- MOCK DATA ----------------

object NoticeRepository {

    fun getNotices() = listOf(
        Notice(
            "IEBC Update",
            "Voter verification deadline extended to next week.",
            "IEBC",
            "2 hrs ago"
        ),
        Notice(
            "Security Alert",
            "All polling stations will have enhanced security on election day.",
            "Security",
            "5 hrs ago"
        ),
        Notice(
            "Voting Hours Confirmed",
            "Voting will run from 6:00 AM to 5:00 PM nationwide.",
            "Info",
            "1 day ago"
        ),
        Notice(
            "New Polling Stations",
            "Additional polling stations added in Nairobi and Kisumu.",
            "IEBC",
            "2 days ago"
        ),
        Notice(
            "Civic Reminder",
            "Carry your original ID or passport when voting.",
            "Reminder",
            "3 days ago"
        )
    )
}

// ---------------- SCREEN ----------------

@Composable
fun NoticesScreen(navController: NavController) {

    var search by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    val notices = remember { NoticeRepository.getNotices() }

    val filtered = notices.filter {
        it.title.contains(search, true) ||
                it.message.contains(search, true) ||
                it.category.contains(search, true)
    }

    LaunchedEffect(Unit) {
        delay(900)
        loading = false
    }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            /* ---------------- HEADER ---------------- */

            Row(verticalAlignment = Alignment.CenterVertically) {

                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.12f)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        "Civic Notices",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        "Official IEBC & civic updates",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------------- SEARCH ---------------- */

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search notices...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                    cursorColor = Color.White,
                    focusedContainerColor = Color.White.copy(alpha = 0.05f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.05f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------------- LOADING ---------------- */

            if (loading) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
                return
            }

            /* ---------------- LIST ---------------- */

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(filtered) { notice ->

                    AnimatedVisibility(
                        true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        NoticeCard(notice)
                    }
                }
            }
        }
    }
}

// ---------------- CARD ----------------

@Composable
fun NoticeCard(notice: Notice) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            /* CATEGORY TAG */
            Surface(
                shape = RoundedCornerShape(50),
                color = Color.White.copy(alpha = 0.15f)
            ) {
                Text(
                    text = notice.category,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    fontSize = 11.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = notice.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = notice.message,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = notice.time,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

// ---------------- PREVIEW ----------------

@Composable
@androidx.compose.ui.tooling.preview.Preview
fun NoticesPreview() {
    NoticesScreen(rememberNavController())
}
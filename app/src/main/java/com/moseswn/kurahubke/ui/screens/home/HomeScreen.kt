package com.moseswn.kurahubke.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.navigation.ROUT_TIMELINE
import com.moseswn.kurahubke.navigation.ROUT_VOTING
import kotlinx.coroutines.launch

// ==========================
// DATA MODELS
// ==========================

data class ReadinessItem(
    val title: String,
    val required: Boolean,
    val completed: Boolean
)

data class QuickAccessItem(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val description: String
)

// ==========================
// HOME SCREEN
// ==========================

@Composable
fun HomeScreen(
    navController: NavController
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val expandedStates = remember {
        mutableStateMapOf<String, Boolean>()
    }

    val checklistItems = remember {

        mutableStateListOf(

            ReadinessItem(
                title = "National ID Card",
                required = true,
                completed = false
            ),

            ReadinessItem(
                title = "Know your polling station",
                required = true,
                completed = false
            ),

            ReadinessItem(
                title = "Confirmed on register",
                required = true,
                completed = false
            ),

            ReadinessItem(
                title = "Arrive 06:00 – 17:00",
                required = true,
                completed = false
            ),

            ReadinessItem(
                title = "No phone in booth",
                required = false,
                completed = false
            )
        )
    }

    val quickAccessItems = listOf(

        QuickAccessItem(
            emoji = "🇰🇪",
            title = "Next General Election",
            subtitle = "August 2027",
            description = "~15 months · 455 days away"
        ),

        QuickAccessItem(
            emoji = "📶",
            title = "Works Offline",
            subtitle = "Cached locally",
            description = "FAQs, Timeline & Checklist available without internet."
        )
    )

    val completedCount = checklistItems.count { it.completed }

    val requiredCount = checklistItems.count { it.required }

    val progress =
        completedCount.toFloat() /
                requiredCount.toFloat()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(

                Brush.verticalGradient(

                    colors = listOf(

                        Color(0xFF000000),
                        Color(0xFF7A1010),
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
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp)
        ) {

            // ==========================
            // TOP BAR
            // ==========================

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f)
                    ) {

                        Box(
                            modifier = Modifier.padding(14.dp)
                        ) {

                            TextButton(
                                onClick = {navController.navigate(ROUT_VOTING)}
                            ){
                              Text(
                                  text = "🗳️",
                                  fontSize = 22.sp
                              )
                            }


                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "Kura Hub KE",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )

                        Text(
                            text = "Neutral Civic Info",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.12f)
                ) {

                    IconButton(
                        onClick = {
                            navController.navigate("profile")
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==========================
            // OFFICIAL CARD
            // ==========================

            ModernInfoCard(
                title = "Official",
                icon = Icons.Default.Info,
                description =
                    "Neutral civic information only. No endorsements. No party colours. All data sourced from IEBC, Kenya Law & official government notices."
            )

            Spacer(modifier = Modifier.height(26.dp))

            // ==========================
            // HERO SECTION
            // ==========================

            Text(
                text = "Welcome to",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your Vote,\nYour Future.",
                fontSize = 40.sp,
                lineHeight = 46.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Kenya's trusted civic information hub. Know your rights, prepare your documents, and vote with confidence.",
                fontSize = 15.sp,
                lineHeight = 25.sp,
                color = Color.White.copy(alpha = 0.92f)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ==========================
            // PROGRESS CARD
            // ==========================

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(30.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        Color.White.copy(alpha = 0.10f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.HowToVote,
                            contentDescription = null,
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Voter Readiness",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Text(
                            text = "$completedCount / $requiredCount",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text =
                                "${(progress * 100).toInt()}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFA5D6A7)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    LinearProgressIndicator(
                        progress = {
                            progress
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(
                                RoundedCornerShape(50.dp)
                            ),

                        color = Color.White,

                        trackColor =
                            Color.White.copy(alpha = 0.15f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text =
                            "${requiredCount - completedCount} items remaining — tap Checklist to complete",

                        fontSize = 14.sp,

                        color =
                            Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    TextButton(
                        onClick = {
                            navController.navigate(ROUT_TIMELINE)
                        }
                    ) {

                        Text(
                            text = "Open Checklist",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==========================
            // ALERTS
            // ==========================

            AlertCard(
                icon = Icons.Default.Warning,
                text =
                    "Voter registration window opens soon — check iebc.or.ke for exact dates."
            )

            Spacer(modifier = Modifier.height(14.dp))

            AlertCard(
                icon = Icons.Default.DateRange,
                text =
                    "2027 General Election: Next major election is scheduled for August 2027."
            )

            Spacer(modifier = Modifier.height(30.dp))

            // ==========================
            // CHECKLIST SECTION
            // ==========================

            Text(
                text = "Voting Day Checklist",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(18.dp))

            checklistItems.forEachIndexed { index, item ->

                val expanded =
                    expandedStates[item.title] ?: false

                ModernChecklistCard(

                    item = item,

                    expanded = expanded,

                    onExpand = {

                        expandedStates[item.title] =
                            !expanded
                    },

                    onToggle = {

                        checklistItems[index] =
                            item.copy(
                                completed =
                                    !item.completed
                            )

                        scope.launch {

                            snackbarHostState.showSnackbar(

                                if (!item.completed)
                                    "${item.title} completed"
                                else
                                    "${item.title} unchecked"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ==========================
            // QUICK ACCESS
            // ==========================

            Text(
                text = "Quick Access",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Note: Replaced FlowRow with Column to fix a NoSuchMethodError in the Preview
            // caused by a binary signature change in Compose 1.7.0.
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                quickAccessItems.forEach { item ->

                    QuickAccessCard(item)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // ==========================
            // SOURCES
            // ==========================

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(28.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        Color.White.copy(alpha = 0.08f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    Text(
                        text = "Official Sources",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    SourceItem("IEBC Kenya")
                    SourceItem("Kenya Law")
                    SourceItem("Huduma Kenya")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // ==========================
            // NAVIGATION BUTTONS
            // ==========================

            // Note: Replaced FlowRow with Row to fix a NoSuchMethodError in the Preview.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                NavigationButton(
                    title = "FAQs"
                ) {
                    navController.navigate("faqs")
                }

                NavigationButton(
                    title = "Timeline"
                ) {
                    navController.navigate("timeline")
                }

                NavigationButton(
                    title = "Checklist"
                ) {
                    navController.navigate("checklist")
                }

                NavigationButton(
                    title = "Notices"
                ) {
                    navController.navigate("notices")
                }

                NavigationButton(
                    title = "Vote"
                ) {
                    navController.navigate(ROUT_VOTING)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // ==========================
            // FOOTER
            // ==========================

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                HorizontalDivider(
                    color =
                        Color.White.copy(alpha = 0.10f)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text =
                        "v1.0 MVP · Kura Hub KE · Not affiliated with any political party",

                    fontSize = 11.sp,

                    lineHeight = 18.sp,

                    textAlign = TextAlign.Center,

                    color =
                        Color.White.copy(alpha = 0.65f)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ==========================
// INFO CARD
// ==========================

@Composable
fun ModernInfoCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                Color.White.copy(alpha = 0.10f)
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 24.sp,
                color = Color.White.copy(alpha = 0.92f)
            )
        }
    }
}

// ==========================
// ALERT CARD
// ==========================

@Composable
fun AlertCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                Color.White.copy(alpha = 0.08f)
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFFD54F)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = Color.White
            )
        }
    }
}

// ==========================
// CHECKLIST CARD
// ==========================

@Composable
fun ModernChecklistCard(
    item: ReadinessItem,
    expanded: Boolean,
    onExpand: () -> Unit,
    onToggle: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onExpand()
            },

        shape = RoundedCornerShape(26.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                Color.White.copy(alpha = 0.08f)
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        onToggle()
                    }
                ) {

                    Icon(
                        imageVector =
                            if (item.completed)
                                Icons.Default.CheckCircle
                            else
                                Icons.AutoMirrored.Filled.Article,

                        contentDescription = null,

                        tint =
                            if (item.completed)
                                Color(0xFF81C784)
                            else
                                Color.White
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text =
                            if (item.required)
                                "Required"
                            else
                                "Optional",

                        fontSize = 12.sp,

                        color =
                            Color.White.copy(alpha = 0.75f)
                    )
                }

                Icon(
                    imageVector =
                        if (expanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,

                    contentDescription = null,

                    tint = Color.White
                )
            }

            AnimatedVisibility(
                visible = expanded,

                enter = fadeIn() + expandVertically(),

                exit = fadeOut() + shrinkVertically()
            ) {

                Column {

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(
                        color =
                            Color.White.copy(alpha = 0.1f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text =
                            "This item is important for election day readiness and voter verification.",

                        fontSize = 13.sp,

                        lineHeight = 22.sp,

                        color =
                            Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

// ==========================
// QUICK ACCESS CARD
// ==========================

@Composable
fun QuickAccessCard(
    item: QuickAccessItem
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                Color.White.copy(alpha = 0.08f)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = item.emoji,
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.subtitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFA5D6A7)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.description,
                fontSize = 13.sp,
                lineHeight = 22.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

// ==========================
// SOURCE ITEM
// ==========================

@Composable
fun SourceItem(
    title: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    Color.White,
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            color = Color.White
        )
    }

    Spacer(modifier = Modifier.height(14.dp))
}

// ==========================
// NAVIGATION BUTTON
// ==========================

@Composable
fun NavigationButton(
    title: String,
    onClick: () -> Unit
) {

    TextButton(
        onClick = onClick,

        modifier = Modifier
            .background(
                Color.White.copy(alpha = 0.10f),
                RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 10.dp)
    ) {

        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

// ==========================
// PREVIEW
// ==========================

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    MaterialTheme {

        HomeScreen(
            rememberNavController()
        )
    }
}
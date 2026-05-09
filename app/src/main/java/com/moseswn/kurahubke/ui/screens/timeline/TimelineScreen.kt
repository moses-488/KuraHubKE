package com.moseswn.kurahubke.ui.screens.timeline

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

data class TimelineEvent(
    val title: String,
    val date: String,
    val description: String,
    val status: String
)

@Composable
fun TimelineScreen(
    navController: NavController
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val expandedStates = remember {
        mutableStateListOf<Boolean>()
    }

    val savedEvents = remember {
        mutableStateListOf<Boolean>()
    }

    val reminderStates = remember {
        mutableStateListOf<Boolean>()
    }

    val completedStates = remember {
        mutableStateListOf<Boolean>()
    }

    val timelineEvents = listOf(

        TimelineEvent(
            title = "Voter Registration",
            date = "12 May 2026",
            description = "Eligible citizens can register as voters at designated IEBC centers.",
            status = "Upcoming"
        ),

        TimelineEvent(
            title = "Voter Verification",
            date = "08 June 2026",
            description = "Verify your registration details and polling station information.",
            status = "Upcoming"
        ),

        TimelineEvent(
            title = "Campaign Period",
            date = "20 July 2026",
            description = "Official campaign activities begin under IEBC regulations.",
            status = "Pending"
        ),

        TimelineEvent(
            title = "Election Day",
            date = "10 August 2026",
            description = "Polling stations open from 6:00 AM to 5:00 PM.",
            status = "Important"
        ),

        TimelineEvent(
            title = "Results Announcement",
            date = "12 August 2026",
            description = "Official election results released by IEBC.",
            status = "Final"
        )
    )

    while (expandedStates.size < timelineEvents.size) {
        expandedStates.add(false)
    }

    while (savedEvents.size < timelineEvents.size) {
        savedEvents.add(false)
    }

    while (reminderStates.size < timelineEvents.size) {
        reminderStates.add(false)
    }

    while (completedStates.size < timelineEvents.size) {
        completedStates.add(false)
    }

    val completedCount = completedStates.count { it }

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
    ) {

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp),

            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(10.dp))

                // HEADER
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f)
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
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {

                        Text(
                            text = "Kenya Election Timeline",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )

                        Text(
                            text = "Official IEBC civic schedule",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // PROGRESS CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(30.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.08f)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.HowToVote,
                                contentDescription = null,
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Election Preparation",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        LinearProgressIndicator(
                            progress = {
                                completedCount.toFloat() /
                                        timelineEvents.size.toFloat()
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(50.dp)),

                            color = Color(0xFFFFFFFF),

                            trackColor = Color.White.copy(alpha = 0.2f)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "$completedCount of ${timelineEvents.size} events completed",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }

            // TIMELINE EVENTS
            itemsIndexed(timelineEvents) { index, event ->

                TimelineCard(

                    event = event,

                    expanded = expandedStates[index],

                    saved = savedEvents[index],

                    reminderEnabled = reminderStates[index],

                    completed = completedStates[index],

                    onExpandClick = {

                        expandedStates[index] =
                            !expandedStates[index]
                    },

                    onSaveClick = {

                        savedEvents[index] =
                            !savedEvents[index]

                        scope.launch {

                            snackbarHostState.showSnackbar(

                                if (savedEvents[index])
                                    "Event saved successfully"
                                else
                                    "Saved event removed"
                            )
                        }
                    },

                    onReminderToggle = { enabled ->

                        reminderStates[index] = enabled

                        scope.launch {

                            snackbarHostState.showSnackbar(

                                if (enabled)
                                    "Reminder enabled"
                                else
                                    "Reminder disabled"
                            )
                        }
                    },

                    onCompletedToggle = {

                        completedStates[index] =
                            !completedStates[index]

                        scope.launch {

                            snackbarHostState.showSnackbar(

                                if (completedStates[index])
                                    "Event marked completed"
                                else
                                    "Completion removed"
                            )
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun TimelineCard(
    event: TimelineEvent,
    expanded: Boolean,
    saved: Boolean,
    reminderEnabled: Boolean,
    completed: Boolean,
    onExpandClick: () -> Unit,
    onSaveClick: () -> Unit,
    onReminderToggle: (Boolean) -> Unit,
    onCompletedToggle: () -> Unit
) {

    val statusColor = when (event.status) {

        "Upcoming" -> Color(0xFFFFFFFF)

        "Important" -> Color(0xFFFFD54F)

        "Final" -> Color(0xFF81C784)

        else -> Color(0xFFE0E0E0)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onExpandClick()
            },

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically
            ) {

                // EVENT ICON
                Box(
                    modifier = Modifier
                        .background(
                            Color.White.copy(alpha = 0.12f),
                            CircleShape
                        )
                        .padding(14.dp),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Timeline Event",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = event.date,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = event.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = statusColor.copy(alpha = 0.15f)
                    ) {

                        Text(
                            text = event.status,

                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 5.dp
                            ),

                            color = statusColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
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

                    Spacer(modifier = Modifier.height(18.dp))

                    HorizontalDivider(
                        color = Color.White.copy(alpha = 0.08f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = event.description,
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        color = Color.White.copy(alpha = 0.92f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Source: IEBC",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF81C784)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // ACTION BUTTONS
                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // SAVE BUTTON
                        TextButton(
                            onClick = {
                                onSaveClick()
                            }
                        ) {

                            Icon(
                                imageVector =
                                    if (saved)
                                        Icons.Default.Bookmark
                                    else
                                        Icons.Default.BookmarkBorder,

                                contentDescription = null,
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text =
                                    if (saved)
                                        "Saved"
                                    else
                                        "Save",

                                color = Color.White
                            )
                        }

                        // COMPLETE BUTTON
                        TextButton(
                            onClick = {
                                onCompletedToggle()
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint =
                                    if (completed)
                                        Color(0xFF81C784)
                                    else
                                        Color.White
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text =
                                    if (completed)
                                        "Completed"
                                    else
                                        "Complete",

                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // REMINDER SWITCH
                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Enable Reminder",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }

                        Switch(
                            checked = reminderEnabled,

                            onCheckedChange = {
                                onReminderToggle(it)
                            },

                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF1B5E20)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TimelineScreenPreview() {

    TimelineScreen(
        rememberNavController()
    )
}
package com.moseswn.kurahubke.ui.screens.faqs

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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moseswn.kurahubke.navigation.ROUT_HOME

// ---------------- DATA MODEL ----------------
data class FAQItem(
    val question: String,
    val answer: String,
    val isPinned: Boolean = false
)

// ---------------- SCREEN ----------------
@Composable
fun FAQScreen(navController: NavController) {

    var searchText by remember {
        mutableStateOf("")
    }

    val expandedStates = remember {
        mutableStateMapOf<String, Boolean>()
    }

    val faqList = listOf(

        FAQItem(
            "What documents do I need to vote?",
            "You need a valid Kenyan National ID or a valid Kenyan passport.",
            isPinned = true
        ),

        FAQItem(
            "What if I lost my ID?",
            "You may use an official waiting card or passport depending on IEBC guidance."
        ),

        FAQItem(
            "How do I confirm my polling station?",
            "You can verify your polling station through official IEBC voter verification channels.",
            isPinned = true
        ),

        FAQItem(
            "Can I vote without registering?",
            "No. Only registered voters are eligible to vote."
        ),

        FAQItem(
            "What time do polling stations open?",
            "Polling stations typically open from 6:00 AM to 5:00 PM."
        )
    )

    val filteredFaqs = faqList
        .filter {
            it.question.contains(searchText, ignoreCase = true)
        }
        .sortedByDescending { it.isPinned }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(

                Brush.verticalGradient(

                    colors = listOf(

                        Color(0xFF000000),
                        Color(0xFF8B0000),
                        Color(0xFF1B5E20)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),

            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(10.dp))

                // ---------------- HEADER ----------------
                Row(
                    modifier = Modifier.fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically,

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.10f)
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
                                text = "FAQs",

                                fontSize = 32.sp,

                                fontWeight = FontWeight.ExtraBold,

                                color = Color.White,

                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        blurRadius = 6f
                                    )
                                )
                            )

                            Text(
                                text = "Official civic election guidance",

                                fontSize = 13.sp,

                                color = Color.White.copy(alpha = 0.75f)
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            navController.navigate(ROUT_HOME)
                        }
                    ) {

                        Text(
                            text = "Skip",

                            color = Color.White,

                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ---------------- DESCRIPTION ----------------
                Text(
                    text = "Find answers to common voting and election questions from trusted civic sources.",

                    fontSize = 14.sp,

                    color = Color.White.copy(alpha = 0.85f),

                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ---------------- SEARCH CARD ----------------
                Card(
                    shape = RoundedCornerShape(22.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.10f)
                    )
                ) {

                    OutlinedTextField(

                        value = searchText,

                        onValueChange = {
                            searchText = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        placeholder = {

                            Text(
                                "Search questions...",

                                color = Color.White.copy(alpha = 0.55f)
                            )
                        },

                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.75f)
                            )
                        },

                        singleLine = true,

                        shape = RoundedCornerShape(22.dp),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedTextColor = Color.White,

                            unfocusedTextColor = Color.White,

                            focusedBorderColor = Color.Transparent,

                            unfocusedBorderColor = Color.Transparent,

                            cursorColor = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // ---------------- RESULT COUNT ----------------
                Text(
                    text = "${filteredFaqs.size} questions found",

                    fontSize = 13.sp,

                    color = Color.White.copy(alpha = 0.70f)
                )
            }

            // ---------------- FAQ LIST ----------------
            items(
                items = filteredFaqs,
                key = { it.question }
            ) { faq ->

                val expanded = expandedStates[faq.question] ?: false

                FAQCard(

                    faq = faq,

                    expanded = expanded,

                    onExpandToggle = {

                        expandedStates[faq.question] = !expanded
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }

        // ---------------- FLOATING BUTTON ----------------
        FloatingActionButton(

            onClick = {
                navController.navigate("ask_iebc_chat")
            },

            containerColor = Color.White,

            contentColor = Color(0xFF1B5E20),

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Help,
                contentDescription = "Ask IEBC"
            )
        }
    }
}

// ---------------- FAQ CARD ----------------
@Composable
fun FAQCard(
    faq: FAQItem,
    expanded: Boolean,
    onExpandToggle: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onExpandToggle()
            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.10f)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.10f)
                ) {

                    Icon(
                        imageVector = Icons.Default.Help,

                        contentDescription = null,

                        tint = Color.White,

                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    if (faq.isPinned) {

                        Text(
                            text = "Pinned",

                            fontSize = 11.sp,

                            color = Color(0xFFA5D6A7),

                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Text(
                        text = faq.question,

                        fontSize = 16.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color.White,

                        maxLines = 2,

                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector =
                        if (expanded)
                            Icons.Default.ExpandLess
                        else
                            Icons.Default.ExpandMore,

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
                        text = faq.answer,

                        fontSize = 14.sp,

                        color = Color.White.copy(alpha = 0.92f),

                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Source: IEBC",

                        fontSize = 12.sp,

                        color = Color(0xFFA5D6A7),

                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ---------------- PREVIEW ----------------
@Preview(showBackground = true)
@Composable
fun FaqsScreenPreview() {

    FAQScreen(
        rememberNavController()
    )
}
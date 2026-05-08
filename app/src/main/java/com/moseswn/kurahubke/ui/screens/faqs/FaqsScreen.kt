package com.moseswn.kurahubke.ui.screens.faqs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// ---------------- DATA MODEL ----------------
data class FAQItem(
    val question: String,
    val answer: String,
    val isPinned: Boolean = false
)

// ---------------- SCREEN ----------------
@Composable
fun FAQScreen(navController: NavController) {

    val searchText = remember { mutableStateOf("") }

    // Stable expand state (no index bugs)
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

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
            it.question.contains(searchText.value, ignoreCase = true)
        }
        .sortedByDescending { it.isPinned }

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

            // ---------------- TOP BAR ----------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "FAQs",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                TextButton(
                    onClick = {
                        navController.navigate("home") // change route if needed
                    }
                ) {
                    Text(
                        text = "Skip",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Frequently asked questions about voting and elections.",
                fontSize = 14.sp,
                color = Color(0xFFDDE5DD)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------- SEARCH ----------------
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Search questions...", color = Color.White.copy(alpha = 0.6f))
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------- FAQ LIST ----------------
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    items = filteredFaqs,
                    key = { it.question }
                ) { faq ->

                    val expanded = expandedStates[faq.question] ?: false

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {

                        FAQCard(
                            faq = faq,
                            expanded = expanded,
                            onExpandToggle = {
                                expandedStates[faq.question] = !expanded
                            }
                        )
                    }
                }
            }
        }

        // ---------------- ASK IEBC BUTTON ----------------
        FloatingActionButton(
            onClick = {
                navController.navigate("ask_iebc_chat")
            },
            containerColor = Color(0xFF2E7D32),
            contentColor = Color.White,
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

// ---------------- CARD ----------------
@Composable
fun FAQCard(
    faq: FAQItem,
    expanded: Boolean,
    onExpandToggle: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandToggle() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Column(modifier = Modifier.padding(18.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.Help,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = faq.question,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Icon(
                    imageVector = if (expanded)
                        Icons.Default.ExpandLess
                    else
                        Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            AnimatedVisibility(visible = expanded) {

                Column {

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = faq.answer,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

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
}

// ---------------- PREVIEW ----------------
@Preview(showBackground = true)
@Composable
fun FaqsScreenPreview() {
    FAQScreen(rememberNavController())
}
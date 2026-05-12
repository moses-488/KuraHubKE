package com.moseswn.kurahubke.ui.screens.voting

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moseswn.kurahubke.data.VotingViewModel
import com.moseswn.kurahubke.models.Candidate

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VotingScreen(navController: NavController) {
    val context = LocalContext.current
    val votingViewModel: VotingViewModel = remember { VotingViewModel(navController, context) }
    val candidates = votingViewModel.candidates.value
    val isLoading = votingViewModel.isLoading.value

    var showDialog by remember { mutableStateOf(false) }
    var candidateName by remember { mutableStateOf("") }
    var candidateParty by remember { mutableStateOf("") }
    var candidatePosition by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cast Your Vote", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B5E20)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF1B5E20),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Candidate")
            }
        },
        containerColor = Color(0xFFF1F8E9)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (candidates.isEmpty()) {
                Text(
                    text = "No candidates available at the moment.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Proposed Candidates",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(candidates) { candidate ->
                        CandidateCard(candidate) {
                            votingViewModel.castVote(candidate.id)
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add New Candidate") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = candidateName,
                            onValueChange = { candidateName = it },
                            label = { Text("Candidate Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = candidateParty,
                            onValueChange = { candidateParty = it },
                            label = { Text("Party") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = candidatePosition,
                            onValueChange = { candidatePosition = it },
                            label = { Text("Position (e.g. President)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = imageUrl,
                            onValueChange = { imageUrl = it },
                            label = { Text("Image URL (Optional)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            votingViewModel.addCandidate(candidateName, candidateParty, candidatePosition, imageUrl)
                            showDialog = false
                            candidateName = ""
                            candidateParty = ""
                            candidatePosition = ""
                            imageUrl = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun CandidateCard(candidate: Candidate, onVoteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = candidate.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = "${candidate.position} - ${candidate.party}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Votes: ${candidate.voteCount}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = onVoteClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Vote")
            }
        }
    }
}


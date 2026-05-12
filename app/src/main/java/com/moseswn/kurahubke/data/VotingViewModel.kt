package com.moseswn.kurahubke.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.moseswn.kurahubke.models.Candidate
import com.moseswn.kurahubke.navigation.ROUT_LOGIN

class VotingViewModel(
    var navController: NavController,
    var context: Context
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val candidatesRef = database.getReference("Candidates")
    private val votesRef = database.getReference("Votes")

    val candidates = mutableStateOf<List<Candidate>>(emptyList())
    var isLoading = mutableStateOf(false)

    init {
        fetchCandidates()
    }

    private fun fetchCandidates() {
        isLoading.value = true
        candidatesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<Candidate>()
                for (childSnapshot in snapshot.children) {
                    val candidate = childSnapshot.getValue(Candidate::class.java)
                    candidate?.let { tempList.add(it) }
                }
                candidates.value = tempList
                isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading.value = false
                Toast.makeText(context, "Failed to load candidates: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun castVote(candidateId: String) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            navController.navigate(ROUT_LOGIN)
            return
        }

        val userId = currentUser.uid
        // First check if user has already voted for this position
        // For simplicity, we assume one global vote for now, or you can key by position
        votesRef.child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                Toast.makeText(context, "You have already cast your vote!", Toast.LENGTH_SHORT).show()
            } else {
                // Record the vote
                votesRef.child(userId).setValue(candidateId).addOnSuccessListener {
                    // Increment the candidate's vote count
                    candidatesRef.child(candidateId).child("voteCount").get().addOnSuccessListener { voteSnapshot ->
                        val currentVotes = voteSnapshot.getValue(Int::class.java) ?: 0
                        candidatesRef.child(candidateId).child("voteCount").setValue(currentVotes + 1)
                        Toast.makeText(context, "Vote cast successfully!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Error checking vote status", Toast.LENGTH_SHORT).show()
        }
    }

    fun addCandidate(name: String, party: String, position: String, imageUrl: String) {
        if (name.isBlank() || party.isBlank() || position.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        val id = candidatesRef.push().key ?: return
        val candidate = Candidate(id, name, party, position, imageUrl, 0)
        candidatesRef.child(id).setValue(candidate).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Candidate added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to add candidate: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

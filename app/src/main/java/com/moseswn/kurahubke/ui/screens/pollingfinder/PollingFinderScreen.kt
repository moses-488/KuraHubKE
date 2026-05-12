package com.moseswn.kurahubke.ui.screens.pollingfinder

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.moseswn.kurahubke.navigation.ROUT_NOTICES

import kotlinx.coroutines.delay
import kotlin.math.*

/* ---------------- DATA ---------------- */

data class PollingStation(
    val name: String,
    val county: String,
    val code: String,
    val lat: Double,
    val lng: Double
)

/* ---------------- MOCK IEBC DATA ---------------- */

object IEBCMockApi {
    fun getStations() = listOf(
        PollingStation("Nairobi Primary School", "Nairobi", "001A", -1.286389, 36.817223),
        PollingStation("Kasarani Hall", "Nairobi", "002B", -1.2210, 36.8960),
        PollingStation("Kibra Center", "Nairobi", "003C", -1.3145, 36.7850),
        PollingStation("Kisumu Central Hall", "Kisumu", "101D", -0.0917, 34.7680),
        PollingStation("Mombasa Town Hall", "Mombasa", "201E", -4.0435, 39.6682)
    )
}

/* ---------------- DISTANCE ---------------- */

fun distanceKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val radius = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(lat1)) *
            cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2.0)

    return 2 * radius * atan2(sqrt(a), sqrt(1 - a))
}

/* ---------------- SCREEN ---------------- */

@Composable
fun PollingMapScreen(navController: NavController) {

    var search by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    // Simulated user GPS (Nairobi)
    val userLocation = LatLng(-1.286389, 36.817223)

    val stations = remember { IEBCMockApi.getStations() }

    val filtered = stations.filter {
        it.name.contains(search, true) ||
                it.county.contains(search, true) ||
                it.code.contains(search, true)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 11f)
    }

    LaunchedEffect(Unit) {
        delay(1000)
        loading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF000000), Color(0xFFB71C1C), Color(0xFF1B5E20))
                )
            )
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            /* ---------------- HEADER ---------------- */

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.12f)
                ) {
                    IconButton(onClick = { navController.navigate(ROUT_NOTICES) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                }

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        "Live Polling Map",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        "Real-time IEBC polling locator",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            /* ---------------- MAP ---------------- */

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                cameraPositionState = cameraPositionState
            ) {

                // USER LOCATION
                Marker(
                    state = rememberMarkerState(position = userLocation),
                    title = "You are here",
                    snippet = "Current Location"
                )

                // POLLING STATIONS
                filtered.forEach { station ->

                    Marker(
                        state = rememberMarkerState(position = LatLng(station.lat, station.lng)),
                        title = station.name,
                        snippet = "${station.county} • ${station.code}"
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            /* ---------------- SEARCH ---------------- */

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Search polling station") },
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                },
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                    cursorColor = Color.White
                )
            )

            Spacer(Modifier.height(10.dp))

            /* ---------------- LIST ---------------- */

            if (loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(filtered) { station ->

                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically()
                        ) {

                            Card(
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(alpha = 0.08f)
                                )
                            ) {

                                Row(
                                    Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        Icons.Default.LocationOn,
                                        null,
                                        tint = Color.White
                                    )

                                    Spacer(Modifier.width(10.dp))

                                    Column {

                                        Text(
                                            station.name,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            "${station.county} • ${station.code}",
                                            color = Color.White.copy(alpha = 0.7f),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/* ---------------- PREVIEW ---------------- */

@Composable
@Preview
fun MapPreview() {
    PollingMapScreen(rememberNavController())
}

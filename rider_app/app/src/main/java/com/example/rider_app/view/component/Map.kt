package com.example.rider_app.view.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.maps.android.compose.streetview.StreetView
import com.google.maps.android.ktx.MapsExperimentalFeature

@OptIn(MapsExperimentalFeature::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen() {
    var showStreetView by remember { mutableStateOf(false) }

    Column {
        // Toggle Button to switch between Map and Street View
        Button(
            onClick = { showStreetView = !showStreetView },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = if (showStreetView) "Show Map" else "Show Street View")
        }

        // The Map or StreetView component
        Map(showStreetView = showStreetView)
    }
}

@OptIn(MapsExperimentalFeature::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Map(showStreetView: Boolean) {
    val location = LatLng(19.142400, 72.930390)
    val dropLocation = LatLng(19.149950, 72.936700)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }

    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }

    val pattern = listOf(Dot(), Gap(20f), Dash(30f), Gap(20f))

    if (showStreetView) {
        // Show Street View
        StreetView(
            streetViewPanoramaOptionsFactory = {
                StreetViewPanoramaOptions().position(dropLocation)
            },
            isPanningGesturesEnabled = true,
            isStreetNamesEnabled = true,
            isUserNavigationEnabled = true,
            isZoomGesturesEnabled = true
        )
    } else {
        // Show Google Map with Markers and Polyline
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            Marker(
                state = MarkerState(position = location)
            )
            Marker(
                state = MarkerState(position = dropLocation)
            )
            Polyline(
                points = listOf(location, dropLocation),
                clickable = true,
                color = Color.DarkGray,
                pattern = pattern,
                width = 5f,
                zIndex = 50f
            )
        }
    }

    // Log message to indicate function execution
    Log.i("MAP", "Map composable rendered with current location")
}

@file:OptIn(ExperimentalPermissionsApi::class)

package com.ably.tracking.demo.subscriber.ui.screen.dashboard

import android.Manifest
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.common.toStringRes
import com.ably.tracking.demo.subscriber.ui.bottomsheet.LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT
import com.ably.tracking.demo.subscriber.ui.bottomsheet.LocationUpdateBottomSheet
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.map.DashboardScreenMap
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.map.DashboardScreenMapState
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import com.ably.tracking.demo.subscriber.ui.widget.AATAppBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.LocationSource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    trackableId: String,
    locationSource: LocationSource,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) =
    AATSubscriberDemoTheme {
        val viewState: State<DashboardScreenState> = dashboardViewModel.state.collectAsState()
        val mapState: State<DashboardScreenMapState> = dashboardViewModel.mapState.collectAsState()
        val locationPermissionState = rememberPermissionState(
            permission = Manifest.permission.ACCESS_FINE_LOCATION
        )
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )

        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                LocationUpdateBottomSheet(locationUpdate = viewState.value.trackableLocation)
            },
            floatingActionButton = {
                if (locationPermissionState.status.isGranted) {
                    ChangeMapModeFloatingActionButton(mapState.value) {
                        dashboardViewModel.onSwitchMapModeButtonClicked()
                    }
                }
            },
            sheetPeekHeight = LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            topBar = { AATAppBar() }
        ) {

            val state = dashboardViewModel.state.collectAsState()

            LaunchedEffect(key1 = "BEGIN_TRACKING") {
                dashboardViewModel.beginTracking(trackableId)
            }

            LaunchedEffect(key1 = "REQUEST_LOCATION_PERMISSION") {
                locationPermissionState.launchPermissionRequest()
            }

            Crossfade(targetState = state.value.isAssetTrackerReady) { isAssetTrackerReady ->
                if (isAssetTrackerReady) {
                    DashboardScreenContent(
                        viewModel = dashboardViewModel,
                        locationPermissionState = locationPermissionState,
                        locationSource = locationSource
                    )
                } else {
                    DashboardScreenLoadingIndicator()
                }
            }
        }
    }

@Preview
@Composable
fun DashboardScreenLoadingIndicator() = AATSubscriberDemoTheme {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.loading))
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreenContent(
    viewModel: DashboardViewModel,
    locationPermissionState: PermissionState,
    locationSource: LocationSource
) = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val viewState = viewModel.state.collectAsState().value

        TrackableStateRow(state = viewState)

        when (viewState.trackableState) {
            is TrackableState.Offline -> TrackableStateErrorRow(state = viewState)
            is TrackableState.Failed -> TrackableStateErrorRow(state = viewState)
            else -> Unit
        }

        DashboardScreenMap(
            viewModel = viewModel,
            locationPermissionState = locationPermissionState,
            locationSource = locationSource
        )
    }
}

@Preview
@Composable
fun TrackableStateRow(
    @PreviewParameter(DashboardScreenStatePreview::class) state: DashboardScreenState
) = AATSubscriberDemoTheme {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.trackable_state_label_normal
            )
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        when (state.trackableState) {
            null -> Text(
                text = stringResource(
                    id = R.string.trackable_state_no_data_reported
                )
            )
            else -> Text(
                text = stringResource(
                    id = state.trackableState.toStringRes()
                )
            )
        }
    }
}

@Preview
@Composable
fun TrackableStateErrorRow(
    @PreviewParameter(DashboardScreenStatePreview::class) state: DashboardScreenState
) = AATSubscriberDemoTheme {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.trackable_state_label_error
            )
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text(
            text = when (state.trackableState) {
                is TrackableState.Failed -> state.trackableState.errorInformation.message
                is TrackableState.Offline -> state.trackableState.errorInformation?.message.orEmpty()
                else -> ""
            }
        )
    }
}

@Composable
fun ChangeMapModeFloatingActionButton(
    mapState: DashboardScreenMapState,
    onClick: () -> Unit
) = AATSubscriberDemoTheme {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = Color.White
    ) {
        if (mapState.shouldCameraFollowUserAndTrackable) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(
                    id = R.string.image_content_description_icon_switch_map_to_trackable
                )
            )
        } else {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = stringResource(
                    id = R.string.image_content_description_icon_switch_map_to_location
                )
            )
        }

    }
}

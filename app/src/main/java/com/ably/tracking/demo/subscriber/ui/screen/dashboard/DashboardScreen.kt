@file:OptIn(ExperimentalPermissionsApi::class)

package com.ably.tracking.demo.subscriber.ui.screen.dashboard

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.common.FusedLocationSource
import com.ably.tracking.demo.subscriber.common.doOnLifecycleEvent
import com.ably.tracking.demo.subscriber.common.toStringRes
import com.ably.tracking.demo.subscriber.ui.bottomsheet.LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT
import com.ably.tracking.demo.subscriber.ui.bottomsheet.LocationUpdateBottomSheet
import com.ably.tracking.demo.subscriber.ui.bottomsheet.LocationUpdateBottomSheetData
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.map.DashboardScreenMap
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.map.DashboardScreenMapState
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import com.ably.tracking.demo.subscriber.ui.widget.AATAppBar
import com.ably.tracking.demo.subscriber.ui.widget.SingleButtonAlertDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.getViewModel

@ExperimentalPermissionsApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    locationSource: FusedLocationSource,
    navController: NavController,
    dashboardViewModel: DashboardViewModel = getViewModel()
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
                LocationUpdateBottomSheet(viewState.toLocationUpdateBottomSheetData())
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

            if (state.value.showSubscriptionFailedDialog) {
                SingleButtonAlertDialog(
                    title = R.string.trackable_subscription_failed_dialog_title,
                    text = R.string.trackable_subscription_failed_dialog_message,
                    buttonText = R.string.ok
                ) {
                    dashboardViewModel.onSubscriptionFailedDialogClose()
                    navController.popBackStack()
                }
            }

            doOnLifecycleEvent { event ->
                onLifecycleEvent(event, dashboardViewModel, locationPermissionState)
            }

            DashboardScreenContent(
                viewModel = dashboardViewModel,
                locationPermissionState = locationPermissionState,
                locationSource = locationSource
            )
        }
    }

@ExperimentalPermissionsApi
private fun onLifecycleEvent(
    event: Lifecycle.Event,
    dashboardViewModel: DashboardViewModel,
    locationPermissionState: PermissionState
) {
    when (event) {
        Lifecycle.Event.ON_CREATE -> dashboardViewModel.onCreated()
        Lifecycle.Event.ON_START -> locationPermissionState.launchPermissionRequest()
        Lifecycle.Event.ON_RESUME -> dashboardViewModel.onEnterForeground()
        Lifecycle.Event.ON_PAUSE -> dashboardViewModel.onEnterBackground()
        else -> Unit
    }
}

private fun State<DashboardScreenState>.toLocationUpdateBottomSheetData() =
    LocationUpdateBottomSheetData(
        locationUpdate = value.trackableLocation,
        resolution = value.resolution,
        remainingDistance = value.remainingDistance,
        lastLocationUpdateInterval = value.lastLocationUpdateInterval,
        averageLocationUpdateInterval = value.averageLocationUpdateInterval
    )

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreenContent(
    viewModel: DashboardViewModel,
    locationPermissionState: PermissionState,
    locationSource: FusedLocationSource
) = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val viewState = viewModel.state.collectAsState().value

        TrackableIdRow(state = viewState)

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
fun TrackableIdRow(
    @PreviewParameter(DashboardScreenStatePreview::class) state: DashboardScreenState
) = AATSubscriberDemoTheme {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = stringResource(R.string.trackable_id_label, state.trackableId)
    )
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

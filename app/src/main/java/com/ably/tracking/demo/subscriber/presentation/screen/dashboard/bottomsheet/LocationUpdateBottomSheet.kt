package com.ably.tracking.demo.subscriber.presentation.screen.dashboard.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.presentation.theme.AATSubscriberDemoTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT = 108.dp

@Composable
@Preview
fun LocationUpdateBottomSheet(
    @PreviewParameter(LocationUpdatePreview::class)
    locationUpdateBottomSheetData: LocationUpdateBottomSheetData
) =
    AATSubscriberDemoTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        ) {
            if (locationUpdateBottomSheetData.locationUpdate == null) {
                LocationUpdateBottomSheetContentPlaceholder()
            } else {
                LocationUpdateBottomSheetContent(locationUpdateBottomSheetData)
            }
        }
    }

@Composable
@Preview
fun LocationUpdateBottomSheetContentPlaceholder() = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                id = R.string.order_location_no_data_reported
            )
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}

@Composable
@Preview
fun LocationUpdateBottomSheetContent(
    @PreviewParameter(LocationUpdatePreview::class)
    locationUpdateBottomSheetData: LocationUpdateBottomSheetData
) = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        locationUpdateBottomSheetData.toLabelValueMap()
            .filter { it.value != null }
            .map { it.key to it.value.toString() }
            .forEach { (stringResource, data) ->
                LocationDataRow(
                    label = stringResource(id = stringResource),
                    data = data
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
    }
}

private fun LocationUpdateBottomSheetData.toLabelValueMap(): Map<Int, Any?> {
    val location = locationUpdate?.location
    return mapOf(
        R.string.order_location_time to location?.time?.formatToDate(),
        R.string.order_location_latitude to location?.latitude,
        R.string.order_location_longitude to location?.longitude,
        R.string.order_location_speed to location?.speed,
        R.string.order_location_bearing to location?.bearing,
        R.string.order_location_altitude to location?.altitude,
        R.string.order_location_accuracy to location?.accuracy,
        R.string.order_remaining_distance to remainingDistance,
        R.string.order_location_last_interval to
            lastLocationUpdateInterval?.toIntervalWithUnit(),
        R.string.order_location_average_interval to
            averageLocationUpdateInterval?.toIntervalWithUnit(),
        R.string.order_location_desired_interval to
            resolution?.desiredInterval?.toIntervalWithUnit(),
        R.string.order_location_skipped_locations to
            locationUpdate?.skippedLocations?.size,
    )
}

private fun Long.formatToDate(): String =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))

private fun Long.toIntervalWithUnit(): String = "$this ms"

@Composable
fun LocationDataRow(
    label: String,
    data: String
) = AATSubscriberDemoTheme {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = data,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

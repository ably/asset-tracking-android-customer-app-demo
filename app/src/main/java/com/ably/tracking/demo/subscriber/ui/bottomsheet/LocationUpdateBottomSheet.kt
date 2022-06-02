package com.ably.tracking.demo.subscriber.ui.bottomsheet

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
import com.ably.tracking.LocationUpdate
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT = 108.dp

@Composable
@Preview
fun LocationUpdateBottomSheet(
    @PreviewParameter(LocationUpdatePreview::class) locationUpdate: LocationUpdate?
) = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = LOCATION_UPDATE_BOTTOM_SHEET_PEEK_HEIGHT)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
    ) {
        when (locationUpdate) {
            null -> LocationUpdateBottomSheetContentPlaceholder()
            else -> LocationUpdateBottomSheetContent(locationUpdate)
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
                id = R.string.trackable_location_no_data_reported
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
    @PreviewParameter(LocationUpdatePreview::class) locationUpdate: LocationUpdate
) = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        mapOf(
            R.string.trackable_location_time to Instant.ofEpochMilli(locationUpdate.location.time)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
            R.string.trackable_location_latitude to locationUpdate.location.latitude.toString(),
            R.string.trackable_location_longitude to locationUpdate.location.longitude.toString(),
            R.string.trackable_location_speed to locationUpdate.location.speed.toString(),
            R.string.trackable_location_bearing to locationUpdate.location.bearing.toString(),
            R.string.trackable_location_altitude to locationUpdate.location.altitude.toString(),
            R.string.trackable_location_accuracy to locationUpdate.location.accuracy.toString(),
            R.string.trackable_location_skipped_locations to locationUpdate.skippedLocations.size.toString(),
        ).forEach { (stringResource, data) ->
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
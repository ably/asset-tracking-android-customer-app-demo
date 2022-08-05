# Ably Asset Tracking Demo: Android Customer

_[Ably](https://ably.com) is the platform that powers synchronized digital experiences in realtime. Whether attending an event in a virtual venue, receiving realtime financial information, or monitoring live car performance data – consumers simply expect realtime digital experiences as standard. Ably provides a suite of APIs to build, extend, and deliver powerful digital experiences in realtime for more than 250 million devices across 80 countries each month. Organizations like Bloomberg, HubSpot, Verizon, and Hopin depend on Ably’s platform to offload the growing complexity of business-critical realtime data synchronization at global scale. For more information, see the [Ably documentation](https://ably.com/documentation)._

## Overview

This demo presents a mock customer-facing app with functionality matching that expected for the typical use case for
[Ably's Asset Tracking solution](https://ably.com/solutions/asset-tracking),
being the tracking of a delivery being made to that customer, where that customer is using an Android device.
Such deliveries could be food, groceries or other packages ordered for home delivery.

This app is a member of our
[suite of **Ably Asset Tracking Demos**](https://github.com/ably/asset-tracking-demos),
developed by Ably's SDK Team to demonstrate best practice for adopting and deploying Asset Tracking.

## Running the example

To build these apps from source you will need to specify credentials in Gradle properties.

The following secrets need to be injected into Gradle by storing them in `gradle.properties` file in
the project root:

- `MAPBOX_DOWNLOADS_TOKEN`: On
  the [Mapbox Access Tokens page](https://account.mapbox.com/access-tokens/), create a token with
  the `DOWNLOADS:READ` secret scope.
- `AUTHORIZATION_HEADER_BASE_64`: Base64 encoded "user:password" used to authorize with the backend service. For more details see [Ably Asset Tracking Backend Demo](https://github.com/ably/asset-tracking-backend-demo)
- `AUTHORIZATION_USERNAME`: username used to authorize with the backend service and as clientId
- `FIREBASE_REGION`: Firebase region to which the backend is deployed, used to determine api host
- `FIREBASE_PROJECT_NAME`: Backend Firebase project name, used to determine api host

To do this, create a file in the project root (if it doesn't exist already) named `local.properties`
, and add the following values:

```bash
MAPBOX_DOWNLOADS_TOKEN=create_token_with_downloads_read_secret_scope
GOOGLE_MAPS_API_KEY=create_api_key_with_geolocation_maps_sdk
AUTHORIZATION_HEADER_BASE_64=create_user_in_firestore
AUTHORIZATION_USERNAME=create_user_in_firestore
FIREBASE_REGION=create_firebase_action
FIREBASE_PROJECT_NAME=create_firebase_action
```

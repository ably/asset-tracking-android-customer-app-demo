# AAT Subscriber Demo

## Running the example

To build these apps from source you will need to specify credentials in Gradle properties.

The following secrets need to be injected into Gradle by storing them in `local.properties` file in
the project root:

- `ABLY_API_KEY`: On your [Ably accounts page](https://ably.com/accounts/), select your application,
  and paste an API Key from the API Keys tab (with relevant capabilities for either subscriber/
  publisher). This API key needs the following capabilities: `publish`, `subscribe`, `history`
  and `presence`.
- `MAPBOX_DOWNLOADS_TOKEN`: On
  the [Mapbox Access Tokens page](https://account.mapbox.com/access-tokens/), create a token with
  the `DOWNLOADS:READ` secret scope.

To do this, create a file in the project root (if it doesn't exist already) named `local.properties`
, and add the following values:

```bash
ABLY_API_KEY=get_value_from_ably_dashboard
MAPBOX_DOWNLOADS_TOKEN=create_token_with_downloads_read_secret_scope
```
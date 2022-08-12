package com.ably.tracking.demo.subscriber.secrets

import android.content.Context

class SharedPreferencesSecretsStorage(context: Context) : SecretsStorage {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "riderPrefs"
        private const val USERNAME_PREFERENCES_KEY = "username"
        private const val AUTHORIZATION_PREFERENCE_KEY = "authorization"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun readUsername(): String? =
        sharedPreferences.getString(USERNAME_PREFERENCES_KEY, null)

    override fun writeUsername(value: String) {
        sharedPreferences.edit().putString(USERNAME_PREFERENCES_KEY, value).apply()
    }

    override fun readAuthorization(): String? =
        sharedPreferences.getString(AUTHORIZATION_PREFERENCE_KEY, null)

    override fun writeAuthorization(value: String) {
        sharedPreferences.edit().putString(AUTHORIZATION_PREFERENCE_KEY, value).apply()
    }
}

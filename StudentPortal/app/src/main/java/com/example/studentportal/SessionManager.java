
package com.example.studentportal;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "StudentSession";
    private static final String KEY_EMAIL = "loggedInEmail";

    private final SharedPreferences preferences;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE);
    }

    public void createSession(String email) {
        preferences.edit()
                .putString(KEY_EMAIL, email)
                .apply();
    }

    public boolean isLoggedIn() {
        return preferences.contains(KEY_EMAIL);
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, "");
    }

    public void logout() {
        preferences.edit().clear().apply();
    }
}

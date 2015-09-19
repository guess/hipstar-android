package me.hipstar.android;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.hipstar.android.utils.Common;

public class DispatchActivity extends Activity {

    public DispatchActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if there is current user info

        if (Common.isLoggedIn(getApplicationContext())) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}

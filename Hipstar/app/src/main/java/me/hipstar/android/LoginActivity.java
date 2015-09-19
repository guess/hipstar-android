package me.hipstar.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.hipstar.android.models.ErrorResponse;
import me.hipstar.android.models.UserResponse;
import me.hipstar.android.network.HypeMachineApi;
import me.hipstar.android.network.ServiceGenerator;
import me.hipstar.android.utils.Common;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI references
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLogoView;

    private Callback<UserResponse> mUserResponse = new Callback<UserResponse>() {
        @Override
        public void success(UserResponse userResponse, Response response) {
            showProgress(false);
            Log.d(TAG, userResponse.getToken());

            // Save the token to a private shared preference
            SharedPreferences pref = getSharedPreferences(Common.PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(Common.PREF_TOKEN, userResponse.getToken());
            editor.apply();

            // Close the login activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        @Override
        public void failure(RetrofitError error) {
            showProgress(false);
            ErrorResponse response = (ErrorResponse) error.getBodyAs(ErrorResponse.class);

            if (response.getMessage().toLowerCase().contains("username")) {
                mUsernameView.setError(response.getMessage());
                mUsernameView.requestFocus();
            } else if (response.getMessage().toLowerCase().contains("password")) {
                mPasswordView.setError(response.getMessage());
                mPasswordView.requestFocus();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
        mLogoView = findViewById(R.id.logo);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String deviceId = Common.getDeviceId(getApplicationContext());

        // Check for a valid password, if the user entered one.
        if (isValid(username, password)) {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Log.d(TAG, "Attempting login");
            showProgress(true);
            HypeMachineApi client = ServiceGenerator.createService(
                    HypeMachineApi.class,
                    HypeMachineApi.BASE_URL
            );
            client.getToken(username, password, deviceId, mUserResponse);
        }
    }


    /**
     * Check the form of the login credentials. If there are form errors (invalid username,
     * missing fields, etc.), the errors are presented.
     * @param username  The attempted username
     * @param password  The attempted password
     * @return  True if the credentials are valid, False otherwise.
     */
    private boolean isValid(String username, String password) {
        boolean isValid = true;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            isValid = false;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            isValid = false;
        }

        if (!isValid) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return isValid;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLogoView.setVisibility(!show ? View.VISIBLE : View.GONE);
    }
}


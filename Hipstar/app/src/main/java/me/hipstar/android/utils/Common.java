package me.hipstar.android.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import java.util.UUID;

public class Common {

    // Shared preferences
    public static final String PREF_NAME = "me.hipstar.android";
    public static final String PREF_TOKEN = "token";
    public static final String PREF_USERNAME = "username";

    /**
     * Get a generally unique device ID for this user and device.
     *
     * The form of the ID will be:
     *      Android ID - Device ID - SIM serial number
     *
     * Android ID: will be unique for each user on the Android device and will be reset every time
     * the user wipes their phone. The user must have a Google account.
     *
     * Device ID: will be unique for the device the user is using.
     *
     * SIM Serial Number: All GSM devices with a SIM will return a unique number (note: CDMA
     * devices will return null in this field, as expected.
     *
     * For more on this topic, go here:
     * http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id/2853253#2853253
     *
     * @return  A unique device ID
     */
    public static String getDeviceId(Context context) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        final String androidId, tmDevice, tmSerial;
        androidId = "" + android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();

        UUID deviceUuid = new UUID(
                androidId.hashCode(),
                ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode()
        );

        return deviceUuid.toString();
    }


    /**
     * Get the user's token if they are logged in
     * @param context   Application context
     * @return  The user's token. Null if the user is not logged in.
     */
    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Common.PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(Common.PREF_TOKEN, null);
    }


    /**
     * Get the user's username if they are logged in
     * @param context   Application context
     * @return  The user's username. Null if the user is not logged in.g
     */
    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Common.PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(Common.PREF_USERNAME, null);
    }


    /**
     * Find out if the user is logged into the web service.
     * @param context   Application context
     * @return  True if the user is logged in, False otherwise.
     */
    public static boolean isLoggedIn(Context context) {
        return Common.getToken(context) != null;
    }

}

package me.hipstar.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @Expose
    private String status;

    @Expose
    private String username;

    @SerializedName("hm_token")
    @Expose
    private String token;

    /**
     * @return The status of the response
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status of the response
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The user's Hype Machine token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The user's Hype Machine token
     */
    public void setToken(String token) {
        this.token = token;
    }
}

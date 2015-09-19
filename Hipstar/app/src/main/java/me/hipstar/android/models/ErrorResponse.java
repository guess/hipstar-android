package me.hipstar.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @Expose
    private String status;

    @SerializedName("error_msg")
    @Expose
    private String message;

    /**
     * @return  The status of the error esponse
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status  The status of the error response
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return  The response error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message  The response error message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

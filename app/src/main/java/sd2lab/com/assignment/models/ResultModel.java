package sd2lab.com.assignment.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepak on 22/7/16.
 */
public class ResultModel {

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    DataModel dataModel;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public DataModel getDataModel() {
        return dataModel;
    }
}

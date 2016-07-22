package sd2lab.com.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by deepak on 22/7/16.
 */
public class DataModel {

    @SerializedName("users")
    ArrayList<UserModel> userList;

    @SerializedName("has_more")
    boolean hasMore;

    public ArrayList<UserModel> getUserList() {
        return userList;
    }

    public boolean isHasMore() {
        return hasMore;
    }
}

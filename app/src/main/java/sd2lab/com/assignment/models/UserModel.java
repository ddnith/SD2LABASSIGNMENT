package sd2lab.com.assignment.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by deepak on 22/7/16.
 */
public class UserModel {

    @SerializedName("name")
    String userName;

    @SerializedName("image")
    String userImage;

    @SerializedName("items")
    ArrayList<String> itemImageList;

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public ArrayList<String> getItemImageList() {
        return itemImageList;
    }
}

package vttp2022.app.miniproject.Model;

import java.io.Serializable;

public class CurrentUser implements Serializable{
    private static String currentUser;

    public CurrentUser() {

    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        CurrentUser.currentUser = currentUser;
    }

}

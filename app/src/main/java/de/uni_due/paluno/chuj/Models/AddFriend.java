package de.uni_due.paluno.chuj.Models;

public class AddFriend {
    private String Username;
    private String Password;
    private String Friend;

    public AddFriend(String username, String password, String friend)
    {
        Username=username;
        Password=password;
        Friend=friend;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFriend() {
        return Friend;
    }

    public void setFriend(String friend) {
        Friend = friend;
    }
}

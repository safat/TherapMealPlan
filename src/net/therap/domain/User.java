package net.therap.domain;

/**
 * Created with IntelliJ IDEA.
 * User: shakhawat.hossain
 * Date: 5/20/14
 * Time: 10:23 AM
 */
public class User {
    private String id;
    private String name;
    private String password;
    private boolean isAdmin;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(String id, String name, String password, boolean isAdmin) {
        this(id, password);
        this.name = name;
        this.isAdmin = isAdmin;
     }

     public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

package model;

public class admin {
    private String username, password, level;
    private int id;

    public String getUsername() {
        return username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public admin(String username, String password, int id, String level) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.level = level;
    }
    public admin(){
        this.id = 0;
        this.password = "";
        this.username = "";
        this.level = "";

    }
    public admin(String username, String password, String level){
        this.id = 0;
        this.username = username;
        this.password = password;
        this.level = level;
    }
}

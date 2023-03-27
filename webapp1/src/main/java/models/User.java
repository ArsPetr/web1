package models;

public class User {
    private int id;
    private String login;
    private String password;
    private String role = "USER";
    private String profilePic;
    public User(){}
    public User(String login,String password){
        this.login = login;
        this.password = password;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getProfilePic() {
        return profilePic;
    }
    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}

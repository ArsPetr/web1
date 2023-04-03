package services;

import com.mysql.cj.protocol.a.DurationValueEncoder;
import models.User;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class UserService extends BDService{
    private static final String INSERT_NEW = "INSERT INTO users (login, password, role) values (?,?,?)";
    private static final String SELECT_LOGIN = "SELECT login FROM users WHERE login = ?";
    private static final String SELECT_PASSWORD = "SELECT password FROM users WHERE login = ?";
    private static final String UPDATE_ROLE = "UPDATE users SET role = ? WHERE id = ?";
    private static final String UPDATE_PROFILE_PICTURE = "UPDATE users SET profilePic = ? WHERE login = ?";
    private static final String SELECT_ROLE = "SELECT role FROM users WHERE login = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SELECT_USERS = "SELECT * FROM users";
    public UserService(){}
    public static boolean isPresent(User user){
        open();
        try {
            preparedStatement = connection.prepareStatement(SELECT_LOGIN);
            preparedStatement.setString(1,user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean res = resultSet.next();
            close();
            return res;

        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }

    }
    public static String getUserRole (String login){
        open();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ROLE);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String res = resultSet.getString("role");
            close();
            return res;
        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }

    }

    public static User getUserByLogin(String login){
        open();

        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setLogin(resultSet.getString("login"));
            user.setRole(resultSet.getString("role"));
            user.setProfilePic(resultSet.getString("profilePic"));

            close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean isAunthentificated(User user){
        if (!isPresent(user)) return false;
        open();
        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1,user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if (resultSet.getString("password").equals(user.getPassword())) {
                close();
                return true;
            }
            else {
                close();
                return false;
            }

        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }

    }
    public static void updateUser(User user){
        if (user.getRole() != null) updateRole(user.getId(),user.getRole());
        if (user.getProfilePic() != null) updateProfilePic(user);
    }

    public static boolean createUser(User user){
        if (isPresent(user)) return false;
        else {
            open();
            try {
                preparedStatement = connection.prepareStatement(INSERT_NEW);
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRole());
                preparedStatement.execute();
                close();
                return true;
            } catch (SQLException e) {
                close();
                throw new RuntimeException(e);
            }
        }
    }
    private static void updateProfilePic(User user){

        User oldUser = UserService.getUserByLogin(user.getLogin());
        if (oldUser.getProfilePic() != null){
            FileManager.deleteFile(oldUser.getProfilePic());
        }
        open();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROFILE_PICTURE);
            preparedStatement.setString(1, user.getProfilePic());
            preparedStatement.setString(2, user.getLogin());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }
        close();
    }
    private static void updateRole(int id, String role){
        open();

        try {

            preparedStatement = connection.prepareStatement(UPDATE_ROLE);
            preparedStatement.setString(1, role);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            close();
        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<User> getUsers (){
        open();

        try {
            preparedStatement = connection.prepareStatement(SELECT_USERS);
            ResultSet usersSet = preparedStatement.executeQuery();
            ArrayList<User> userArrayList = new ArrayList<>();
            while (usersSet.next()){
                User user = new User();
                user.setId(usersSet.getInt("id"));
                user.setLogin(usersSet.getString("login"));
                user.setRole(usersSet.getString("role"));
                user.setProfilePic(usersSet.getString("profilePic"));
                userArrayList.add(user);

            }
            close();
            return userArrayList;

        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }
    }



}

package DAO;

import models.News;
import services.UserService;

import java.sql.*;
import java.util.ArrayList;

public class NewsDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";
    private static final String INSERT_NEWS = "INSERT INTO news (tags, header, body, image, author) values (?,?,?,?,?)";
    private static final String SELECT_NEWS = "SELECT * FROM news";
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public NewsDAO(){}

    public static void insert(News news){
        open();

        try {
            preparedStatement = connection.prepareStatement(INSERT_NEWS);
            preparedStatement.setString(1, news.getTags());
            preparedStatement.setString(2,news.getHeader());
            preparedStatement.setString(3, news.getBody());
            preparedStatement.setString(4, news.getImage());
            preparedStatement.setString(5,news.getAuthor().getLogin());

            preparedStatement.execute();
            close();
        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<News> getNews(){
        open();

        try {
            preparedStatement = connection.prepareStatement(SELECT_NEWS);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<News> newsArrayList = new ArrayList<>();
            while (resultSet.next()){
                News news = new News();
                news.setTags(resultSet.getString("tags"));
                news.setId(resultSet.getInt("id"));
                news.setBody(resultSet.getString("body"));
                news.setHeader(resultSet.getString("header"));
                news.setAuthor(UserService.getUserByLogin(resultSet.getString("author")));
                news.setImage(resultSet.getString("image"));
                newsArrayList.add(news);
            }
            close();
            return newsArrayList;
        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }

    }

    private static void open(){
        try {
            connection = DriverManager.getConnection(URL,NAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

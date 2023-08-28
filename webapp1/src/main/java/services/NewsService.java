package services;

import models.News;

import java.sql.*;
import java.util.ArrayList;

public class NewsService extends BDService{
    private static final String INSERT_NEWS = "INSERT INTO news (tags, header, body, image, author) values (?,?,?,?,?)";
    private static final String SELECT_NEWS = "SELECT * FROM news";
    public NewsService(){}

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
}

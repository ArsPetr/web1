package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
public class MetaService extends BDService{
    private final String SELECT_META = "SELECT * FROM meta";
    private final String UPDATE_META = "UPDATE meta SET visitNumber = ? WHERE id = 1";
    private static int visitNumber;
    public MetaService(){
        try {
            visitNumber = getVisitNumberFromBD();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int getVisitNumberFromBD() throws java.sql.SQLException{
        open();
        preparedStatement = connection.prepareStatement(SELECT_META);
        ResultSet resultSet = preparedStatement.executeQuery();
        close();
        return resultSet.getInt("visitNumber");
    }
    private void saveVisitNumberInDB() throws SQLException {
        open();
        preparedStatement = connection.prepareStatement(UPDATE_META);
        preparedStatement.setInt(1,visitNumber);
        preparedStatement.executeQuery();
        close();
    }
    public static void increaseVisitNumber(){
        visitNumber++;
    }
    public static int getVisitNumber(){
        return visitNumber;
    }
    public static Date getTime(){
        return new Date();
    }
    @Override
    public void finalize(){
        try {
            saveVisitNumberInDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

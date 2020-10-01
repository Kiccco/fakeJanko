package me.kristjan.fakejanko;

import java.sql.*;

public class Database {

    private Connection connection;

    public Database() throws SQLException {
        openConnection();
    }

    public void openConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + Settings.HOSTNAME + ":" + Settings.PORT + "/" + Settings.DATABASE, Settings.USER, Settings.PASSWORD);
            System.out.println("Povezan z bazo podatkov");

            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS rezultati (id VARCHAR(255), odgovori INT(100), pravilniOdgovori INT(100))");
            statement.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

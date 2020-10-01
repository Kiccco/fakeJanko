package me.kristjan.fakejanko;

import me.kristjan.fakejanko.commands.IzzivCommand;
import me.kristjan.fakejanko.commands.OdgovorCommand;
import me.kristjan.fakejanko.commands.StatCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import sun.dc.pr.PRError;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Janko {


    public Janko()  {
        init();

    }



    private static Janko janko;
    private Database database;



    public void init(){
        janko = this;
        if(Settings.MYSQL) {
            try {
                database = new Database();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }


    public static Janko getJanko(){
        return janko;
    }

    public Database getDatabase(){
        return database;
    }

    public void addUser(User user){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            if(database.getConnection() != null){
                preparedStatement = database.getConnection().prepareStatement("SELECT * FROM rezultati WHERE id = ?");
                preparedStatement.setString(1,user.getId());
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()){
                    preparedStatement = getDatabase().getConnection().prepareStatement("INSERT INTO rezultati (id, odgovori, pravilniOdgovori) VALUES (?,?,?)");
                    preparedStatement.setString(1, user.getId());
                    preparedStatement.setInt(2, 0);
                    preparedStatement.setInt(3, 0);
                    preparedStatement.execute();
                    preparedStatement.close();

                }

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean checkUser(User user){
        try{
            PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT * FROM rezultati WHERE id = ?");
            preparedStatement.setString(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return true;
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public void addOdgovor(User user, boolean pravilno){
        try{
            if(pravilno) {
                PreparedStatement preparedStatement = getDatabase().getConnection().prepareStatement("UPDATE rezultati SET pravilniOdgovori = ? WHERE id = ?");
                preparedStatement.setInt(1, getOdgovor(user, true) + 1);
                preparedStatement.setString(2, user.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } else {
                PreparedStatement preparedStatement = getDatabase().getConnection().prepareStatement("UPDATE rezultati SET odgovori = ? WHERE id = ?");
                preparedStatement.setInt(1, getOdgovor(user, false) + 1);
                preparedStatement.setString(2, user.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public int getOdgovor(User user, boolean pravilno){
        try{
            if(pravilno) {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT pravilniOdgovori FROM rezultati WHERE id = ?");
                preparedStatement.setString(1, user.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getInt("pravilniOdgovori");
            } else {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT odgovori FROM rezultati WHERE id = ?");
                preparedStatement.setString(1, user.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getInt("odgovori");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }



}

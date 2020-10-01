package me.kristjan.fakejanko;

import me.kristjan.fakejanko.commands.IzzivCommand;
import me.kristjan.fakejanko.commands.OdgovorCommand;
import me.kristjan.fakejanko.commands.StatCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

public class Main {

    public static HashMap<User, Izziv> izzivi = new HashMap<User, Izziv>();


    private static JDA jda;

    public static void main(String[] args) throws LoginException {
        new Janko();
        jda = new JDABuilder().setToken(Settings.TOKEN).build();
        jda.addEventListener(new IzzivCommand());
        jda.addEventListener(new OdgovorCommand());
        jda.addEventListener(new StatCommand());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> Janko.getJanko().getDatabase().closeConnection()));


    }






}

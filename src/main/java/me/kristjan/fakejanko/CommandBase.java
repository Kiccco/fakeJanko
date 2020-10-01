package me.kristjan.fakejanko;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public abstract class CommandBase extends ListenerAdapter {

    private String command;
    private String[] args;

    public abstract void execute(GuildMessageReceivedEvent event, String[] args);

    public CommandBase(String command){
        this.command = command;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
         args = event.getMessage().getContentRaw().split(" ");
         if(args[0].equalsIgnoreCase(command)){
             execute(event, args);
         }
    }




}

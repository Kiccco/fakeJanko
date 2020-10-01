package me.kristjan.fakejanko.commands;

import me.kristjan.fakejanko.CommandBase;
import me.kristjan.fakejanko.Izziv;
import me.kristjan.fakejanko.Janko;
import me.kristjan.fakejanko.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class IzzivCommand extends CommandBase {
    public IzzivCommand() {
        super("!izziv");
    }

    public void execute(GuildMessageReceivedEvent event, String[] args) {
       if(!Main.izzivi.containsKey(event.getAuthor())) {
           if(!Janko.getJanko().checkUser(event.getAuthor()))
               Janko.getJanko().addUser(event.getAuthor());
           Izziv izziv = new Izziv(event.getAuthor(), event);
           Main.izzivi.put(event.getAuthor(), izziv);
       }else {
           event.getChannel().sendMessage("Prejšni izziv za gospoda " + event.getAuthor().getAsMention() + " še ni odgovorjen!").queue();
       }
    }
}

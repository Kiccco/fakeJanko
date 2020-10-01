package me.kristjan.fakejanko.commands;

import me.kristjan.fakejanko.CommandBase;
import me.kristjan.fakejanko.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class OdgovorCommand extends CommandBase {
    public OdgovorCommand() {
        super("!odgovor");
    }

    public void execute(GuildMessageReceivedEvent event, String[] args) {
        if(args.length == 2){
            if(Main.izzivi.containsKey(event.getAuthor())) {
                event.getChannel().sendMessage(Main.izzivi.get(event.getAuthor()).getRightAnswer(event.getAuthor(), args[1])).queue();
                Main.izzivi.remove(event.getAuthor());
           }
        }
    }
}

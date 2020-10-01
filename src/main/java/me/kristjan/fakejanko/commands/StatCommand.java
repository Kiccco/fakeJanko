package me.kristjan.fakejanko.commands;

import me.kristjan.fakejanko.CommandBase;
import me.kristjan.fakejanko.Janko;
import me.kristjan.fakejanko.Settings;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StatCommand extends CommandBase {
    public StatCommand() {
        super("!stat");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        if(Settings.MYSQL) {
            if (!Janko.getJanko().checkUser(event.getAuthor()))
                return;
            double a = (int) Janko.getJanko().getOdgovor(event.getAuthor(), true);
            double b = (int) Janko.getJanko().getOdgovor(event.getAuthor(), false);
            double c = a / b;
            NumberFormat percentageFormat = NumberFormat.getPercentInstance();
            percentageFormat.setMinimumFractionDigits(2);

            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " je odgovoril na skupaj " + (int) b + " vprašanj pravilno odgovoril " + (int) a + "x pravilno in je " + percentageFormat.format(c)).queue();
        }
    }
}

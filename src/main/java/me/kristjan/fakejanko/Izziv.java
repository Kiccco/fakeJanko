package me.kristjan.fakejanko;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


import java.util.ArrayList;
import java.util.Random;

public class Izziv {

    private Random random = new Random();


    private final User user;
    private int sb;
    private int sk;
    private int koncnbase;
    private int min;
    private int max;
    private int neki;

    //sesteavnje/odstevanje BIN
    private String prvostevilo;
    private String drugostevilo;
    private String resultat;
    private boolean sestevanje;


    private String rightanswer;
    private String stevilo;
    private GuildMessageReceivedEvent event;
    private ArrayList<String> potence = new ArrayList<String>();
    private String conv1;
    private String conv2;
    private String conv3;

    public Izziv(User user, GuildMessageReceivedEvent event){
        this.user = user;
        this.event = event;
        sestaviizziv();
    }


        public void sestaviizziv(){
            int c = random.nextInt(7) + 1;
                switch (c) {
                    case(0): // dec v bin
                        stevilo = String.valueOf(random.nextInt(100001));
                        rightanswer = convert(stevilo, sb = 10, sk = 2);
                        koncnbase = 2;
                        event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori desetiško(DEC) število " + stevilo + " v 2-iški(BIN) sistem").queue();
                        break;
                    case(1): // bin v dec
                        stevilo = String.valueOf(random.nextInt(100001));
                        String conv = convert(stevilo, sb = 10, sk = 2);
                        rightanswer = stevilo;
                        koncnbase = 10;
                        event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori dvojiško(BIN) število " + conv + " v desetiški(DEC) sistem").queue();
                        break;
                    case(2): //hex v bin
                        stevilo = String.valueOf(random.nextInt(100001));
                        conv1 = convert(stevilo, sb = 10, sk = 16).toUpperCase();
                        conv2 = convert(stevilo, sb = 10, sk = 2);
                        rightanswer = conv2;
                        koncnbase = 2;
                        event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori 16-iško(HEX) število " + conv1 + " v 2-iški(BIN) sistem\n" + "poštevanka za število 2 je sledeča:\n" + potenciranje(2, conv1.length())).queue();
                        break;

                    case(3): // hex v dec
                        rightanswer = String.valueOf(random.nextInt(100001));
                        conv1 = convert(rightanswer, sb = 10, sk = 16).toUpperCase();
                        //conv2 = convert(ba, sb = 10, sk = 2);
                        //rightanswer = conv2;
                        koncnbase = 10;
                        event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori število " + conv1 + " iz 16-iškega(HEX)  v desetiški(DEC) sistem\n" + "poštevanka za število 16 je sledeča:\n" + potenciranje(16, conv1.length())).queue();
                        break;
                    case(4): //poljubno v dec
                        min = 3;
                        max = 16;
                        neki = (int)(Math.random()*(max-min+1)+min);
                        rightanswer = String.valueOf(random.nextInt(10413));
                        conv1 = convert(rightanswer, sb = 10, sk = neki);
                        koncnbase = 10;
                        if(neki != 10)
                            event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori število " + conv1.toUpperCase() + " iz " + neki + "-škega v desetiški(DEC) sistem:\n" + "poštevanka za število " + neki + " je sledeča:\n" + potenciranje(neki, conv1.length())).queue();
                        else
                            event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori število " + conv1.toUpperCase() +  " iz "  + neki + "-škega v desetiški(DEC) sistem").queue();
                        break;
                    case(5): //bin v hex
                        stevilo = String.valueOf(random.nextInt(100001));
                        conv1 = convert(stevilo, sb = 10, sk = 2).toUpperCase();
                        conv2 = convert(stevilo, sb = 10, sk = 16).toUpperCase();
                        rightanswer = conv2;
                        koncnbase = 16;
                        event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori število " + conv1 + " iz dvojiškega(BIN) v 16-iški(HEX) sistem\n").queue();
                        break;
                    case(6): //poljubno v dec
                        stevilo = String.valueOf(random.nextInt(10413));
                        min = 3;
                        max = 16;
                        neki = (int)(Math.random()*(max-min+1)+min);
                        conv1 = convert(stevilo, sb = 10, sk = neki);
                        //conv2 = convert(stevilo, sb = neki, sk = 10);
                        koncnbase = neki;
                        rightanswer = conv1;
                        if(neki != 10)
                            event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori število " + stevilo + " iz desetiškega(DEC) v " + neki + "-iški sistem:\n" + "poštevanka za število " + neki + " je sledeča:\n" + potenciranje(neki, conv1.length())).queue();
                        else
                            event.getChannel().sendMessage("Izziv za gospoda " + event.getAuthor().getAsMention() + ": \nPretvori število " + stevilo +  " iz desetiškega(DEC) v desetiški(DEC) sistem").queue();
                        break;
                    case(7):
                        sestaviracun();
                        sestevanje = true;
                        break;


                }


        }


    public String convert(String num, int sb, int eb){
        return Integer.toString(Integer.parseInt(num, sb), eb);
    }

    public User getUser(User user){
        return user;
    }

    public String getRightAnswer(User user, String odg){
        String l;
        String c;
        String d;
        if(koncnbase != 10) {
            try{
                  c = convert(odg, koncnbase, 10);
                  l = convert(rightanswer, koncnbase, 10);


            } catch (NumberFormatException e){
                Janko.getJanko().addOdgovor(user, false);
                return "Odgovor je napačen " + user.getAsMention() + " pravilen odgovor je " + rightanswer;
            }
                if(sestevanje) {
                    if (l.equals(c)) {
                        Janko.getJanko().addOdgovor(user, true);
                        return "Odgovor je pravilen " + user.getAsMention();
                    } else {
                        Janko.getJanko().addOdgovor(user, false);
                        return "Odgovor je napačen " + user.getAsMention() + " pravilen odgovor je " + convert(rightanswer, 10, 2);
                    }
                } else {
                    if (l.equals(c)) {
                        Janko.getJanko().addOdgovor(user, true);
                        return "Odgovor je pravilen " + user.getAsMention();
                    } else {
                        Janko.getJanko().addOdgovor(user, false);
                        return "Odgovor je napačen " + user.getAsMention() + " pravilen odgovor je " + convert(rightanswer, 10, 2);
                    }
                }
            } else {
                if(rightanswer.equals(odg)) {
                    Janko.getJanko().addOdgovor(user, true);
                    return "Odgovor je pravilen " + user.getAsMention();
                }else {
                    Janko.getJanko().addOdgovor(user, false);
                    return "Odgovor je napačen " + user.getAsMention() + " pravilen odgovor je " + rightanswer;
                }

            }
    }


        public String potenciranje(int num, int power){
            for(int i = 0; i < power; i++){
                potence.add(String.valueOf((int) Math.pow(num, i)) + "\n");
            }
            StringBuilder b = new StringBuilder();
            potence.forEach(b::append);
            return b.toString();
        }

    public void sestaviracun(){
        int rand = random.nextInt(2) + 1;
        switch(rand){
            case(1):
                prvostevilo = String.valueOf(random.nextInt(312) + 1);
                drugostevilo = String.valueOf(random.nextInt(312) + 1);
                conv1 = convert(prvostevilo, sb = 10, sk = 2);
                conv2 = convert(drugostevilo, sb = 10, sk = 2);
                resultat = String.valueOf(Integer.parseInt(prvostevilo) + Integer.parseInt(drugostevilo));
                conv3 = convert(resultat, 10, 2);
                rightanswer = conv3;
                koncnbase = 2;
                event.getChannel().sendMessage("Izziv za gospoda "  + event.getAuthor().getAsMention() + ":\n" + "Seštej binarni števili " + conv1 + " in " + conv2).queue();
                break;
            case(2):
                prvostevilo = String.valueOf(random.nextInt(312) + 1);
                drugostevilo = String.valueOf(random.nextInt(69) + 1);
                conv1 = convert(prvostevilo, sb = 10, sk = 2);
                conv2 = convert(drugostevilo, sb = 10, sk = 2);
                resultat = String.valueOf(Integer.parseInt(prvostevilo) - Integer.parseInt(drugostevilo));
                conv3 = convert(resultat, 10, 2);
                rightanswer = conv3;
                koncnbase = 2;
                event.getChannel().sendMessage("Izziv za gospoda "  + event.getAuthor().getAsMention() + ":\n" + "Odštej binarni števili " + conv1 + " in " + conv2).queue();
                break;
        }
    }


}


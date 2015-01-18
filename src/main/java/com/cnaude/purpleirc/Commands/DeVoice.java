package com.cnaude.purpleirc.Commands;

import com.cnaude.purpleirc.PurpleIRC;
import com.cnaude.purpleirc.Utilities.BotsAndChannels;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

/**
 *
 * @author cnaude
 */
public class DeVoice implements IRCCommandInterface {

    private final PurpleIRC plugin;
    private final String usage = "([bot]) ([channel]) [user(s)]";
    private final String desc = "De-voice IRC user(s).";
    private final String name = "devoice";
    private final String fullUsage = ChatColor.WHITE + "Usage: " + ChatColor.GOLD + "/irc " + name + " " + usage;

    /**
     *
     * @param plugin
     */
    public DeVoice(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param sender
     * @param args
     */
    @Override
    public void dispatch(CommandSender sender, String[] args) {
        BotsAndChannels bac;
        int idx;

        if (args.length >= 4) {
            bac = new BotsAndChannels(plugin, sender, args[1], args[2]);
            idx = 3;
        } else if (args.length == 2) {
            bac = new BotsAndChannels(plugin, sender);
            idx = 1;
        } else {
            sender.sendMessage(new TextComponent(fullUsage));
            return;
        }
        if (bac.bot.size() > 0 && bac.channel.size() > 0) {
            for (String botName : bac.bot) {
                for (String channelName : bac.channel) {
                    for (int i = idx; i < args.length; i++) {
                        plugin.ircBots.get(botName).deVoice(channelName, args[i]);
                        sender.sendMessage(new TextComponent("Removing voice status from "
                                + ChatColor.WHITE + args[i]
                                + ChatColor.RESET + " in "
                                + ChatColor.WHITE + channelName));
                    }
                }
            }
        }    
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public String usage() {
        return usage;
    }
}

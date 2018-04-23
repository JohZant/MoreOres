package com.arch.moreores;

import org.bukkit.ChatColor;

public class Logger {

    MoreOres plugin;

    public Logger(MoreOres plugin){
        this.plugin = plugin;
    }

    /*logger for MoreOres*/
    public void log(String message) {
        String logMessage = ChatColor.GOLD + "[MoreOres] " + ChatColor.RESET + message;//adds plugin name to front of log
        /*Sends message to log */
        plugin.getServer().getConsoleSender().sendMessage(logMessage);

    }
}

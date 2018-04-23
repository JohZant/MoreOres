package com.arch.moreores;


public class Logger {

    MoreOres plugin;

    public Logger(MoreOres plugin){
        this.plugin = plugin;
    }

    /*logger for MoreOres*/
    public void log(String message) {
        String logMessage = plugin.config.prefix + message;//adds plugin name to front of log
        /*Sends message to log */
        plugin.getServer().getConsoleSender().sendMessage(logMessage);

    }
}

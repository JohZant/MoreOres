package com.arch.moreores;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class MoreOresCommands implements CommandExecutor {

    private MoreOres plugin;

    public MoreOresCommands(MoreOres plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length == 0){
            //send version
            sender.sendMessage(plugin.config.prefix + ChatColor.GREEN + plugin.getDescription().getVersion());
        }
        else if (args[0].toLowerCase().equals("reload")) {
            //check if sender has permission
            //check for permission
            if (!sender.hasPermission("moreores.admin.reload")) {
                sender.sendMessage(plugin.config.prefix + ChatColor.RED + "You do not have permission to run that command.");
                return true;
            }

            plugin.console.log("Reloading....");
            for (String arg : args) {
                plugin.console.log(arg);
            }

            plugin.ores = new ArrayList<Ore>();
            plugin.reloadConfig();
            plugin.config.loadConfig();
            sender.sendMessage(plugin.config.prefix + ChatColor.GREEN + "Reloaded.");
            return true;
        }
        else if (args[0].toLowerCase().equals("enable")){
            //enable moreores

            //test if user has access to toggle
            if (!sender.hasPermission("moreores.admin.toggle")){
                sender.sendMessage(plugin.config.prefix + ChatColor.RED + "You do not have permission to run that command.");
                return true;
            }

            if (!plugin.config.enabled){
                plugin.config.enabled = true;
            }
            sender.sendMessage(plugin.config.prefix + ChatColor.GREEN + "Enabled");
        }
        else if (args[0].toLowerCase().equals("disable")){
            //disable moreores

            //test if user has access to toggle
            if (!sender.hasPermission("moreores.admin.toggle")){
                sender.sendMessage(plugin.config.prefix + ChatColor.RED + "You do not have permission to run that command.");
                return true;
            }

            if (plugin.config.enabled){
                plugin.config.enabled = false;
            }
            sender.sendMessage(plugin.config.prefix + ChatColor.RED + "Disabled");
        }
        else if(args[0].toLowerCase().equals("debug")){
            //toggles the debugger

            //test that user has permission
            if (!sender.hasPermission("moreores.admin.debug")){
                sender.sendMessage(plugin.config.prefix + ChatColor.RED + "You do not have permission to run that command.");
                return true;
            }

            if (plugin.config.debug){
                //turn debug off
                plugin.config.debug = false;
                sender.sendMessage(plugin.config.prefix + ChatColor.RED + " Debugger Disabled");
                plugin.console.log(ChatColor.RED + "Debugger Disabled");
            }
            else{
                //turn debug on
                plugin.config.debug = true;
                sender.sendMessage(plugin.config.prefix + ChatColor.GREEN + " Debugger Enabled");
                plugin.console.log(ChatColor.GREEN + "Debugger Enabled");
            }
        }
        else{
            //The command is not known so is not handled.
            sender.sendMessage(plugin.config.prefix + ChatColor.RED + "Unknown Command.");
            return true;
        }


        return false;
    }

}

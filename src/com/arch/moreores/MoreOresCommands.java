package com.arch.moreores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MoreOresCommands implements CommandExecutor {

    private MoreOres plugin;

    public MoreOresCommands(MoreOres plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /*Player p = (Player) sender;

        //check for permission
        if (!p.hasPermission("moreores.reload") && !p.isOp()) {
            p.sendMessage(ChatColor.RED + "You do not have permission to run that command.");
            return true;
        }

        //test if there are arguments
        if (args.length == 0) {
            return true;
        }

        if (args[0].toLowerCase().equals("reload")) {

            plugin.console.log("Reloading....");
            for (String arg : args) {
                plugin.console.log(arg);
            }

            plugin.ores = new ArrayList<Ore>();
            plugin.config.loadConfig();
            p.sendMessage(ChatColor.GREEN + "MoreOres Reloaded.");
            return true;
        }*/
        return false;
    }

}

package com.arch.moreores;


import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoreOres extends JavaPlugin {
    Config config;//config file
    Logger console;//logging object
    List<Ore> ores;//
    Random rnd;

    @Override
    public void onEnable() {
        console = new Logger(this);
        ores = new ArrayList<Ore>();
        rnd = new Random();

        final String prefix = ChatColor.GOLD + "[MoreOres]" + ChatColor.RESET;

        /* read config */
        ConfigLoad();

        if(!config.allBlocks) {
            for (Ore ore : ores) {
                console.log("Loaded " + ore.getName());
            }
        }

        /*register Listener */
        getServer().getPluginManager().registerEvents(new MoreOresListener(this), this);
        //setup commands
        //this.getCommand("moreores").setExecutor(new MoreOresCommands(this));
    }

    /*Fired when the server stops and disables plugins*/
    @Override
    public void onDisable() {
        /*  */
    }

    /*Loads Config */
    public void ConfigLoad() {
        //create config file if not already created
        this.saveDefaultConfig();

        //load config
        config = new Config(this);
        config.loadConfig();
    }

}

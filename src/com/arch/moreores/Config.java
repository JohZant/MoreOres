package com.arch.moreores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public class Config {
    private MoreOres plugin;
    public boolean enabled;
    public boolean debug;

    /*Allblocks Vals*/
    public boolean allBlocks;
    public double allBlocks_chance;
    public int allBlocks_multiplier;
    public boolean allBlocks_exp;

    public Config(MoreOres plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        FileConfiguration c = plugin.getConfig();//load up config file

        enabled = c.getBoolean("enabled");
        //write out to console whether or not we are enabled
        if (enabled) {
            plugin.console.log(ChatColor.GREEN + "Enabled");
        } else {
            plugin.console.log(ChatColor.RED + "Disabled");
        }

        //gets debug value
        debug = c.getBoolean("debug");
        //write out to console whether or not we are enabled
        if (debug) {
            plugin.console.log(ChatColor.GREEN + "Debugging is on! Goodluck!");
        } else {
            plugin.console.log(ChatColor.RED + "Debugging is Disabled. (This is a very, very, VERY good thing.)");
        }

        //get allblocks values
        allBlocks = c.getBoolean("allblocks");
        allBlocks_chance = c.getDouble("allblocks_chance");
        allBlocks_multiplier = c.getInt("allblocks_multiplier");
        allBlocks_exp = c.getBoolean("allblocks_exp");

        if (allBlocks){
            plugin.console.log(ChatColor.GREEN + "Monitoring All Blocks");
        }
        else{
            plugin.console.log(ChatColor.GREEN + "Only Monitoring Blocks from Config");
        }

        //set up Ores
        String preKey = "Ores.";
        Set<String> keys = c.getConfigurationSection("Ores").getKeys(false);
        for (String key : keys) {
            Ore tempOre = new Ore();//value to old our new ore and add to Ore list
            Material testMat = Material.matchMaterial(key);//see if it matches at all
            if (testMat != null) {
                /*mat was matched. Add to list*/
                tempOre.setName(key);
                tempOre.setBlock(testMat);
                tempOre.setChance(c.getDouble(preKey + key + ".chance"));
                tempOre.setMultiplier(c.getInt(preKey + key + ".multiplier"));
                tempOre.setExp(c.getBoolean(preKey + key + ".include_experience"));
                //add to list
                plugin.ores.add(tempOre);
            } else {
                plugin.console.log(ChatColor.DARK_RED + "Material unable to be found: "
                        + ChatColor.RESET + key);
            }

        }


    }

}

package com.arch.moreores;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Collection;
import java.util.List;

public class MoreOresListener implements Listener {
    private MoreOres plugin;


    final String placeBlock = "Arch_MoreOres_Placed";

    public MoreOresListener(MoreOres plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDropEvent(BlockBreakEvent event) {
        //test if we have enabled the plugin
        if (!plugin.config.enabled) {
            //plugin is not enabled.. do nothing
            return;
        }

        Player player = event.getPlayer();
        //message for debug
        String debugMSG = "";
        //test if player is in creative
        if (player.getGameMode().equals(GameMode.CREATIVE)){
            //don't drop anything
            if (plugin.config.debug) {
                debugMSG = ChatColor.DARK_GREEN + "[DEBUG]"
                        + ChatColor.YELLOW + "[" + player.getDisplayName() + "]"
                        + ChatColor.RESET;
                plugin.console.log(debugMSG + "Player in Creative Mode. Not Dropping Items.");
            }
            return;
        }

        Block block = event.getBlock();


        String blockLocation = "";
        if (plugin.config.debug) {
            debugMSG = ChatColor.DARK_GREEN + "[DEBUG]"
                    + ChatColor.YELLOW + "[" + player.getDisplayName() + "]"
                    + ChatColor.BLUE + "[" + block.getType().name() + "]"
                    + ChatColor.RESET;
            blockLocation = ChatColor.DARK_AQUA + " X:" + ChatColor.GRAY + block.getLocation().getBlockX()
                    + ChatColor.DARK_AQUA + " Y:" + ChatColor.GRAY + block.getLocation().getBlockY()
                    + ChatColor.DARK_AQUA + " Z:" + ChatColor.GRAY + block.getLocation().getBlockZ();
        }

        //test to see if this block was placed
        List<MetadataValue> blockD = block.getMetadata(placeBlock);
        if (blockD.size() != 0) {
            //block was placed
            // don't drop anything

            //debug
            if (plugin.config.debug) {
                plugin.console.log(debugMSG
                        + " Block Was Placed. No Drops Allowed. "
                        + blockLocation
                );
            }
            return;
        }

        Material brokenBlock = block.getType();//gets the material that was just broken


        //test if we need to check out list of Ores,
        // OR... are we doing it for ALL Blocks
        Ore tempOre = new Ore();//set up tempOre for good measure

        if (plugin.config.allBlocks) {
            //we are looking at all blocks
            // make tempOre equal to the block that we broke
            tempOre.setBlock(brokenBlock);
            tempOre.setExp(plugin.config.allBlocks_exp);
            tempOre.setMultiplier(plugin.config.allBlocks_multiplier);
            tempOre.setChance(plugin.config.allBlocks_chance);
        } else {
            //find a Material in our list that matches
            tempOre = plugin.ores.stream().filter((ore) -> brokenBlock.name().contains(ore.getName())).findFirst().orElse(null);
        }


        if (tempOre == null) {
            //ore was not in our list.
            //stop method

            //debug
            if (plugin.config.debug) {
                plugin.console.log(debugMSG
                        + " Block was not Registered in config. No Drops Allowed. "
                );
            }

            return;
        }

        //find out if we hit the right chance and go from there
        int chance = plugin.rnd.nextInt(100);//get a percentage

        //debug
        if (plugin.config.debug) {
            plugin.console.log(debugMSG
                    + " Chance Needed: " + tempOre.getChance()
                    + " Chance Received: " + chance + " "
                    + blockLocation
            );
        }

        if (chance <= tempOre.getChance()) {
            //success on the chance\

            //debug
            if (plugin.config.debug) {
                plugin.console.log(debugMSG
                        + " Drops Allowed. "
                        + blockLocation
                );
            }

            Collection<ItemStack> droppedItems = event.getBlock().getDrops();//get what ever was dropped
            int initialDrop = 0;//variable to hold how many items were dropped to begin with
            //drop an extra item per drop
            for (ItemStack i : droppedItems) {
                initialDrop = i.getAmount();
                i.setAmount(i.getAmount()*tempOre.getMultiplier() - 1);//set amount to drop

                //debug
                if (plugin.config.debug) {
                    plugin.console.log(debugMSG
                            + " Drop: " + i.getType().name()
                            + " Amount: " + initialDrop
                            + " Multiplier: " + tempOre.getMultiplier()
                            + " Extra Given: " + (i.getAmount())
                            + blockLocation
                    );
                }

                event.getBlock().getWorld().dropItemNaturally(
                        event.getBlock().getLocation(), i
                );
            }

            //find out if we need to give experience
            if (tempOre.isExp()) {

                //debug
                if (plugin.config.debug) {
                    plugin.console.log(debugMSG
                            + " Drop includes XP"
                            + " Amount: " + event.getExpToDrop()
                            + " Multiplier: " + tempOre.getMultiplier()
                            + " Total Given: " + (event.getExpToDrop() * tempOre.getMultiplier())
                            + blockLocation
                    );
                }

                event.setExpToDrop(event.getExpToDrop() * tempOre.getMultiplier());
            }


        }


    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        //test if we have enabled the plugin
        if (!plugin.config.enabled) {
            //plugin is not enabled.. do nothing
            return;
        }

        Block block = event.getBlock();//grab clock that was just broken

        Player player;//to hold player value if we are debugging

        //message for debug
        String debugMSG = "";
        String blockLocation = "";
        if (plugin.config.debug) {
            player = event.getPlayer();
            debugMSG = ChatColor.DARK_GREEN + "[DEBUG]"
                    + ChatColor.YELLOW + "[" + player.getDisplayName() + "]"
                    + ChatColor.BLUE + "[" + block.getType().name() + "]"
                    + ChatColor.RESET;
            blockLocation = ChatColor.DARK_AQUA + " X:" + ChatColor.GRAY + block.getLocation().getBlockX()
                    + ChatColor.DARK_AQUA + " Y:" + ChatColor.GRAY + block.getLocation().getBlockY()
                    + ChatColor.DARK_AQUA + " Z:" + ChatColor.GRAY + block.getLocation().getBlockZ();
        }

        //if block already has been set up as being placed, don't do anything
        List<MetadataValue> blockD = block.getMetadata(placeBlock);
        if (blockD.size() != 0) {
            //block was placed

            //debug message
            if (plugin.config.debug) {
                plugin.console.log(debugMSG
                        + " Already Placed block was Placed again...? "
                        + blockLocation
                );
            }

            return;
        }

        //set up metadata
        MetadataValue meta = new FixedMetadataValue(plugin, true);

        //test if we are monitoring all blocks
        if (plugin.config.allBlocks) {
            //monitoring all blocks
            block.setMetadata(placeBlock, meta);
        } else {
            //check to see if it is a block that we are meant to be monitoring
            Material brokenBlock = event.getBlock().getType();//gets the material that was just broken
            Ore tempOre = plugin.ores.stream().filter((ore) -> brokenBlock.name().contains(ore.getName())).findFirst().orElse(null);
            if (tempOre != null) {
                block.setMetadata(placeBlock, meta);
            }
        }

        //put into console that block ahs been tagged
        if (plugin.config.debug) {
            plugin.console.log(debugMSG
                    + " Block Tagged "
                    + blockLocation
            );
        }

    }
}

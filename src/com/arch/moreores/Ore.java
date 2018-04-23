package com.arch.moreores;

import org.bukkit.Material;

public class Ore {
    private String Name;//Item Key
    private Material Block;//Matieral that we are working with
    private double chance;//chance that there will be double drops
    private int Multiplier;//How many drops
    private boolean exp;//whether or not to also multiple the experience

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public double getChance() {
        return chance;
    }

    public Material getBlock() {
        return Block;
    }

    public void setBlock(Material block) {
        Block = block;
    }

    public int getMultiplier() {
        return Multiplier;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public void setMultiplier(int multiplier) {
        Multiplier = multiplier;
    }

    public boolean isExp() {
        return exp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }
}

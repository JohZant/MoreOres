package com.arch.moreores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {
    private static final String[] COMMANDS = { "reload", "enable", "disable", "debug", "help" };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        final List<String> completions = new ArrayList<String>();
        //convert to list
        StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
        //copy matches of first argument from list (if first arg is 'm' will return just 'minecraft')
        Collections.sort(completions);
        //you want to sort no?
        return completions;
    }
}

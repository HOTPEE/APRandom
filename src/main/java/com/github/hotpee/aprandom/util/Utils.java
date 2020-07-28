package com.github.hotpee.aprandom.util;

import com.github.hotpee.aprandom.APRandom;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class Utils {
    public static void Message(CommandSender p, String s){
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void Help(Player player) {
        if (player.isOp()) {
            Utils.Message(player, "&7&l---------------------------------");
            Utils.Message(player, "&bAPRandom 主命令帮助");
            Utils.Message(player, "&a");
            Utils.Message(player, " &3/apr help  -  &c查看插件所有命令");
            Utils.Message(player, " &3/apr give <Player> <FileName>  -  &c给予一把随机武器");
            Utils.Message(player, " &3/apr reload  -  &c重载插件");
            Utils.Message(player, "&a");
            Utils.Message(player, "&7&l--------------------------------");
        } else {
            Utils.Message(player, "&7&l---------------------------------");
            Utils.Message(player, "&bAPRandom 主命令帮助");
            Utils.Message(player, "&a");
            Utils.Message(player, " &3本插件没有对玩家开放的命令");
            Utils.Message(player, "&a");
            Utils.Message(player, "&7&l--------------------------------");
        }
    }
}

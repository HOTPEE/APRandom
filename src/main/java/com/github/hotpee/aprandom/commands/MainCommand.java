package com.github.hotpee.aprandom.commands;

import com.github.hotpee.aprandom.APRandom;
import com.github.hotpee.aprandom.manager.RandomManager;
import com.github.hotpee.aprandom.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1){
            Player player = (Player) sender;
            Utils.Help(player);
            return false;
        }
        if (args[0].equalsIgnoreCase("help")){
            Player player = (Player) sender;
            Utils.Help(player);
            return false;
        }
        if (args[0].equalsIgnoreCase("give")){
            if (args.length != 3){
                Utils.Message(sender, APRandom.getIns().getConfig().getString("Prefix").replace("&","§") + " &b参数不足! 请检查命令是否正确");
                return false;
            }
            try {
                Player player = Bukkit.getPlayer(args[1]);
                String name = args[2];
                RandomManager.setUp(player, name);
                Utils.Message(sender, APRandom.getIns().getConfig().getString("Prefix").replace("&","§") + " &b已成功给予你 &6" + name);
            } catch (StringIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}

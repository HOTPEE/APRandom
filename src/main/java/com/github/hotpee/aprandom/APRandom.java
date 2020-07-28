package com.github.hotpee.aprandom;

import com.github.hotpee.aprandom.commands.MainCommand;
import com.github.hotpee.aprandom.manager.RandomManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class APRandom extends JavaPlugin {
    private static APRandom ins;
    private List<File> files = new ArrayList<>();

    @Override
    public void onEnable() {
        ins = this;
        getCommand("apr").setExecutor(new MainCommand());
        saveDefaultConfig();
        saveResource("weapon/test.yml", false);
        RandomManager.addFile();

    }

    @Override
    public void onDisable() {
    }

    public List<File> getFiles() {
        return files;
    }

    public static APRandom getIns() {
        return ins;
    }
}

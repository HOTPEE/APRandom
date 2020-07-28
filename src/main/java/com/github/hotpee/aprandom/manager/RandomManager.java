package com.github.hotpee.aprandom.manager;

import com.github.hotpee.aprandom.APRandom;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomManager {
    public static String getFile(String name){
        for (File files : APRandom.getIns().getFiles()) {
            if (name.equalsIgnoreCase(files.getName())){
                return files.getName();
            }
        }
        return null;
    }

    public static void addFile(){
        File weaponfile = new File(APRandom.getIns().getDataFolder(), "/weapon/");
        File[] file = weaponfile.listFiles();
        if (file == null){
            return;
        }
        for (File files : file) {
            APRandom.getIns().getFiles().add(files);
        }
    }

    public static void setUp(Player player, String name) {
        File file = new File(APRandom.getIns().getDataFolder() + "/weapon/", RandomManager.getFile(name) + ".yml");
        FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection item = conf.getConfigurationSection("Item");
        ConfigurationSection randomString = conf.getConfigurationSection("RandomString");
        ConfigurationSection randomAttribute = conf.getConfigurationSection("RandomAttribute");
        player.getInventory().addItem(getItem(item,randomString,randomAttribute));
    }

    public static ItemStack getItem(ConfigurationSection item,ConfigurationSection randomString,ConfigurationSection randomAttribute){
        ItemStack stack = new ItemStack(Material.getMaterial(item.getInt("ID")), 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(item.getBoolean("Unbreakable"));
        for (String flag : item.getStringList("HideFlags")) {
            meta.addItemFlags(ItemFlag.valueOf(flag));
        }
        for (String flag : item.getStringList("Enchants")) {
            List<String> str = Arrays.asList(flag.split(":"));
            meta.addEnchant(Enchantment.getByName(flag), Integer.parseInt(str.get(1)), true);
        }
        meta.setDisplayName(item.getString("Name").replace("<RandomPrefix>", getRandomPrefix(randomString)
                .replace("<Name>", getName(randomString)
                        .replace("<RandomSuffix>",getRandomSuffix(randomString).replaceAll("&","§")))));

        meta.setLore(getLore(item, randomAttribute));
        stack.setItemMeta(meta);
        return stack;
    }

    public static String getName(ConfigurationSection randomString){
        List<String> name = new ArrayList<>();
        name.addAll(randomString.getStringList("Name"));
        Random random = new Random();
        if (name == null){
            return null;
        }
        return name.get(random.nextInt(name.size()));
    }

    public static String getRandomPrefix(ConfigurationSection randomString){
        List<String> prefix = new ArrayList<>();
        prefix.addAll(randomString.getStringList("RandomPrefix"));
        Random random = new Random();
        if (prefix == null){
            return null;
        }
        return prefix.get(random.nextInt(prefix.size()));
    }

    public static String getRandomSuffix(ConfigurationSection randomString){
        List<String> suffix = new ArrayList<>();
        suffix.addAll(randomString.getStringList("RandomSuffix"));
        Random random = new Random();
        if (suffix == null){
            return null;
        }
        return suffix.get(random.nextInt(suffix.size()));
    }


    public static List<String> getLore(ConfigurationSection item, ConfigurationSection randomAttribute){
        List<String> lore = item.getStringList("Lore");
        if (lore == null){
            return null;
        }
        for (String lores : lore){
            for (String s : randomAttribute.getKeys(false)) {
                lores.replace("<RandomAttribute_" + s + "", getAttribute(s, randomAttribute)).replace("&","§");
                lore.clear();
                lore.add(lores);
                return lore;
            }
        }
        return null;
    }

    public static String getAttribute(String s , ConfigurationSection randomAttribute){
        ConfigurationSection section = randomAttribute;
        for(String attribute : randomAttribute.getKeys(false)){
            String name = randomAttribute.getConfigurationSection(attribute).getString("String").replace("&","§");
            Random value = new Random();
            int random = value.nextInt(randomAttribute.getConfigurationSection(attribute).getInt("MinValue") +
                    randomAttribute.getConfigurationSection(attribute).getInt("MaxValue"));
            Random chance = new Random();
            int n = chance.nextInt(100);
            if (n < randomAttribute.getConfigurationSection(attribute).getDouble("MaxValue") * 10){
                return name + ": " +
                        randomAttribute.getConfigurationSection(attribute).getString("Value").replace("&","§") + random;
            }
        }
        return null;
    }
}
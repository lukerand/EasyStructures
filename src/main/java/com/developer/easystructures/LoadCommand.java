package com.developer.easystructures;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadCommand implements CommandExecutor {
    private Main main;
    public ArrayList<Block> blocks = new ArrayList<>();
    public LoadCommand(Main main) {
        this.main = main;
    }
    Player player;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("this command must be executed by a player");
            return false;
        }
        player = (Player) sender;
        if (main.loc1 == null) {
            player.sendMessage(ChatColor.RED + "You must select the position by right clicking with a golden hoe first!");
            return false;
        }
        if (args.length == 1) {
            try {
                load(args[0],main.loc1.getLocation());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.sendMessage(ChatColor.RED + "Invalid arguments. correct usage:\n/load <filename>");
        }
        return false;
    }
    public void load(String filename, Location location) throws IOException {
        filename = filename + ".struct";
        File file = new File(filename);
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;
        int x = 0;
        int y = 0;
        int z = 0;
        while((line = in.readLine())!=null){
            if (line.startsWith("e:")) {
                String tag = line.substring(2);
                if (tag.equals("test")) {
                    Entity as = player.getWorld().spawnEntity(new Location(player.getWorld(),location.getX() + x + 0.5, location.getY() + y, location.getZ() + z + 0.5), EntityType.ARMOR_STAND);
                }
            } else {
                if (line.equals(";")) {
                    x++;
                    z = 0;
                    y = 0;
                } else if (line.equals(",")) {
                    y++;
                    z = 0;
                } else {
                    Block bl = location.getWorld().getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                    if (!Bukkit.createBlockData(line).getMaterial().equals(Material.STRUCTURE_VOID)) {
                        bl.setBlockData(Bukkit.createBlockData(line));
                    }
                    z++;
                }
            }
        }
    }
}

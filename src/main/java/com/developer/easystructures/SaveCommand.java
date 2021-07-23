package com.developer.easystructures;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveCommand implements CommandExecutor{
    private Main main;
    public SaveCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("this command must be executed by a player");
            return false;
        }
        Player player = (Player) sender;
        if (main.loc1 == null || main.loc2 == null) {
            player.sendMessage(ChatColor.RED + "You must select two positions by left and right clicking with a golden hoe first!");
            return false;
        }
        if (args.length == 2) {
            Block[] loc= main.Sort(new Location[]{main.loc1.getLocation(),main.loc2.getLocation()});
            for (int x = loc[0].getX(); x <= loc[1].getX(); x++) {
                for (int y = loc[0].getY(); y <= loc[1].getY(); y++) {
                    for (int z = loc[0].getZ(); z <= loc[1].getZ(); z++) {
                        Block block = main.loc1.getWorld().getBlockAt(x,y,z);
                        if (Boolean.parseBoolean(args[1])) {
                            for (Entity e: block.getWorld().getNearbyEntities(new Location(player.getWorld(),block.getX() + 0.5, block.getY(), block.getZ() + 0.5),0.1,0.1,0.1)) {
                                try {
                                    write("e:" + e.getCustomName() + "\n",args[0]);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        }
                        try {
                            write(block.getBlockData().getAsString(true) + "\n", args[0]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        write(",\n", args[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    write(";\n", args[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Invalid arguments. correct usage:\n/save <filename> <include entities: true|false>");
        }
        return false;
    }
    private void write(String str, String filename) throws IOException {
        filename = filename + ".struct";
        File f = new File(filename);
        if (f.exists()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.append(str);
            writer.close();
        } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(str);
            writer.close();
        }
    }
}

package com.developer.easystructures;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    public Block loc1, loc2;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("save").setExecutor(new SaveCommand(this));
        getCommand("load").setExecutor(new LoadCommand(this));
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().equals(new ItemStack(Material.GOLDEN_HOE)) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            loc1 = e.getClickedBlock();
            e.getPlayer().sendMessage(ChatColor.GREEN + "pos1 has been set to " + loc1.getLocation().toString());
        }
    }
    @EventHandler
    public void onLeftClick(BlockBreakEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().equals(new ItemStack(Material.GOLDEN_HOE))){
            loc2 = e.getBlock();
            e.getPlayer().sendMessage(ChatColor.GREEN + "pos2 has been set to " + loc2.getLocation().toString());
            e.setCancelled(true);
        }
    }
    public Block[] Sort(Location[] loc) {
        double temp;
        if(loc[0].getX() > loc[1].getX()){
            temp = loc[0].getX();
            loc[0].setX(loc[1].getX());
            loc[1].setX(temp);
        }
        if(loc[0].getY() > loc[1].getY()){
            temp = loc[0].getY();
            loc[0].setY(loc[1].getY());
            loc[1].setY(temp);
        }
        if(loc[0].getZ() > loc[1].getZ()){
            temp = loc[0].getZ();
            loc[0].setZ(loc[1].getZ());
            loc[1].setZ(temp);
        }
        return new Block[]{loc[0].getBlock(),loc[1].getBlock()};
    }
}

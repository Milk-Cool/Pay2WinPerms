package com.milk_cool.pay2winperms;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();

    public void onEnable() {
        config.addDefault("message", "You cannot use this feature!\nBuy the feature on our website:\nhttps://example.com");
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("Pay2WinPerms enabled!");
    }
    public void onDisable() {
        getLogger().info("Pay2WinPerms disabled! Thank you for using my plugin!");
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        if (!event.getWhoClicked().hasPermission("pay2winperms.craft")) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(ChatColor.RED + config.getString("message"));
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if ((
                event.getInventory().getType() == InventoryType.FURNACE
                && !event.getPlayer().hasPermission("pay2winperms.furnace")
        ) || (
                event.getInventory().getType() == InventoryType.WORKBENCH
                && !event.getPlayer().hasPermission("pay2winperms.craft")
        ) || (
                (
                        event.getInventory().getType() == InventoryType.CHEST
                                || event.getInventory().getType() == InventoryType.BARREL
                                || event.getInventory().getType() == InventoryType.DISPENSER
                                || event.getInventory().getType() == InventoryType.ENDER_CHEST
                ) && !event.getPlayer().hasPermission("pay2winperms.chest"
        ) || (
                event.getInventory().getType() == InventoryType.ANVIL
                && !event.getPlayer().hasPermission("pay2winperms.anvil")
        ) || (
                event.getInventory().getType() == InventoryType.ENCHANTING
                && !event.getPlayer().hasPermission("pay2winperms.enchant")
        ))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + config.getString("message"));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("pay2winperms.place")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + config.getString("message"));
        }
    }

    @EventHandler void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("pay2winperms.break")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + config.getString("message"));
        }
    }

    @EventHandler void onPlayerFish(PlayerFishEvent event) {
        if (!event.getPlayer().hasPermission("pay2winperms.fish")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + config.getString("message"));
        }
    }

    @EventHandler void onPlayerPortal(PlayerPortalEvent event) {
        if ((
            event.getTo().getWorld().getEnvironment() == World.Environment.NETHER
            && !event.getPlayer().hasPermission("pay2winperms.nether")
        ) || (
            event.getTo().getWorld().getEnvironment() == World.Environment.THE_END
            && !event.getPlayer().hasPermission("pay2winperms.end")
        )) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + config.getString("message"));
        }
    }
}
package de.festival.Bedwars.Utils;

import de.festival.Bedwars.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class WorldProtection implements Listener {
    public ArrayList<Block> existingBlocks = new ArrayList<Block>();
    public ArrayList<Block> placedBlocks = new ArrayList<Block>();
    public boolean enabled = true;

    Main plugin;

    public WorldProtection(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = (Main) plugin;
        plugin.getLogger().info("Preventing blocks from breaking.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.enabled) {
            if (!(event.getBlock().getType() == Material.BED_BLOCK)) {
                if (placedBlocks.indexOf(event.getBlock()) == -1) {
                    event.setCancelled(true);
                } else if (placedBlocks.indexOf(event.getBlock()) != -1) {
                    placedBlocks.remove(placedBlocks.indexOf(event.getBlock()));
                }
            } else {
                this.plugin.bedHandler.destroyBed(event.getBlock(), event.getPlayer(), event.getBlock().getLocation());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (this.enabled && !(event.getBlock().getType() == Material.BED_BLOCK)) {
            placedBlocks.add(event.getBlock());
        }
    }

    public void destroyPlacedBlocks() {
        for (Block block: placedBlocks) {
            block.breakNaturally(null);
        }
        placedBlocks.clear();

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (this.enabled) {
            event.setCancelled(true);
        }
    }
}

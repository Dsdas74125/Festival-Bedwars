package de.festivaldev.bedwars.listeners;

import de.festivaldev.bedwars.Main;
import de.festivaldev.bedwars.utilities.Spawner;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Created by Fabian on 01.04.2016.
 */
public class BlockPlacedListener implements Listener {

	Main plugin;

	public BlockPlacedListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = (Main) plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		ItemStack diy = new ItemStack(Material.QUARTZ_BLOCK, 1, (byte) 1);
		if (event.getBlockPlaced().getType() == diy.getType()) {
			Spawner spawner = new Spawner(new ItemStack(Material.NETHER_STAR, 1), 3, event.getBlockPlaced().getLocation(), plugin);
			plugin.spawners.add(spawner);
			spawner.enabled = plugin.spawners.get(0).enabled;
		}
	}

}

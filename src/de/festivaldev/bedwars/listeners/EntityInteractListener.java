package de.festivaldev.bedwars.listeners;

import de.festivaldev.bedwars.Main;
import de.festivaldev.bedwars.helpers.ShopCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by Fabian on 01.04.2016.
 */
public class EntityInteractListener implements Listener {

	Main plugin;

	public EntityInteractListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = (Main) plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getName().equalsIgnoreCase("Shop")) {
			event.setCancelled(true);
			ShopCreator.openShop(event.getPlayer());
		}
	}

}

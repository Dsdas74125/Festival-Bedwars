package de.festivaldev.bedwars.listeners;

import de.festivaldev.bedwars.Main;
import de.festivaldev.bedwars.helpers.PickupValueCalculator;
import me.clip.actionannouncer.ActionAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by Fabian on 31.03.2016.
 */
public class PlayerPickupItemListener implements Listener {

	private Main plugin;

	public PlayerPickupItemListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = (Main) plugin;
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();

		int value = PickupValueCalculator.getValue(event.getItem().getItemStack());

		if (value > 0) {
			event.setCancelled(true);
			event.getItem().remove();

			ActionAPI.sendPlayerAnnouncement(player, ChatColor.BOLD + "" + ChatColor.GREEN + "+" + value + ChatColor.WHITE + " Ressourcen");
			player.setLevel(player.getLevel() + value);
			player.setExp(0);
		}
	}

}

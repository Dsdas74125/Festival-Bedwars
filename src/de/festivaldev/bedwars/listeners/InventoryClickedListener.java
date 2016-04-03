package de.festivaldev.bedwars.listeners;

import de.festivaldev.bedwars.Main;
import de.festivaldev.bedwars.helpers.ItemHelper;
import de.festivaldev.bedwars.helpers.ShopCreator;
import de.festivaldev.bedwars.values.Itemvalues;
import me.clip.actionannouncer.ActionAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fabian on 01.04.2016.
 */
public class InventoryClickedListener implements Listener {

	Main plugin;

	public InventoryClickedListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = (Main) plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClicked(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack is = event.getCurrentItem();
		int slot = event.getSlot();

		if (inventory.getName().equalsIgnoreCase("Shop")) {

			event.setCancelled(true);
			if (slot >= 0 && slot < 9)
				ShopCreator.openCategory(player, slot);

		} else if (inventory.getName().startsWith("Shop >")) {

			String itemid = "";

			event.setCancelled(true);
			if (slot >= 18 && slot < 27) {
				if (inventory.getItem(slot).getType() != Material.STAINED_GLASS_PANE) {
					int amount = getAmount(event.getClick());
					itemid = inventory.getItem(slot).getItemMeta().getLore().get(1);
					Pattern pat = Pattern.compile("\\d:\\d");
					Matcher m = pat.matcher(itemid);
					while (m.find()) {
						itemid = m.group(0);
					}

					int price = Itemvalues.Prices.get(itemid);
					int totalprice = price * amount;

					if (price < 1000) {
						if (amount == 64 && player.getLevel() < totalprice) {
							int possibleAmount = player.getLevel() / price;
							int r = player.getLevel() - (possibleAmount * price);
							amount = possibleAmount;
							price = player.getLevel() - r;
						}

						if (player.getLevel() >= price) {
							player.setLevel(player.getLevel() - price);
							ActionAPI.sendPlayerAnnouncement(player, ChatColor.BOLD + "" + ChatColor.RED + "-" + price + ChatColor.WHITE + " Ressourcen");

							ItemHelper.giveItem(itemid, amount, player, plugin);

						} else {
							ActionAPI.sendPlayerAnnouncement(player, ChatColor.BOLD + "" + ChatColor.RED + "Du hast nicht genug Ressourcen!");
						}
					} else {
						price = price - 1000;
						int owned = 0;
						for (ItemStack is1 : player.getInventory().all(Material.DIAMOND).values()) {
							owned += is.getAmount();
						}

						if (amount == 64 && owned < totalprice) {
							int possibleAmount = owned / price;
							int r = owned - (possibleAmount * price);
							amount = possibleAmount;
							price = owned - r;
						}

						if (player.getInventory().contains(Material.DIAMOND)) {
							if (player.getInventory().getItem(player.getInventory().first(Material.DIAMOND)).getAmount() >= price) {
								ActionAPI.sendPlayerAnnouncement(player, ChatColor.BOLD + "" + ChatColor.RED + "-" + price + ChatColor.WHITE + " Diamanten");

								int slot1 = player.getInventory().first(Material.DIAMOND);
								if (player.getInventory().getItem(slot1).getAmount() == price) {
									player.getInventory().clear(slot1);
								} else if (player.getInventory().getItem(slot1).getAmount() > price) {
									player.getInventory().getItem(slot1).setAmount(player.getInventory().getItem(slot1).getAmount() - price);
								}

								ItemHelper.giveItem(itemid, amount, player, plugin);

							} else {
								ActionAPI.sendPlayerAnnouncement(player, ChatColor.BOLD + "" + ChatColor.RED + "Du hast nicht genug Diamanten!");
							}
						}
					}
				}
			}

			ShopCreator.openCategory(player, Integer.parseInt(itemid.split(":")[0]) - 1);

		}

	}

	public int getAmount(ClickType type) {
		switch (type) {
			case LEFT: return 1; //Left-click
			case RIGHT: return 16; //Right-click
			case SHIFT_LEFT: //Shift
			case SHIFT_RIGHT: return 64; //Shift
			default: return 1;
		}
	}

}

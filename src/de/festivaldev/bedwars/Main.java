package de.festivaldev.bedwars;

import de.festivaldev.bedwars.helpers.ItemHelper;
import de.festivaldev.bedwars.helpers.ShopCreator;
import de.festivaldev.bedwars.listeners.BlockPlacedListener;
import de.festivaldev.bedwars.listeners.EntityInteractListener;
import de.festivaldev.bedwars.listeners.InventoryClickedListener;
import de.festivaldev.bedwars.listeners.PlayerPickupItemListener;
import de.festivaldev.bedwars.utilities.Spawner;
import de.festivaldev.bedwars.utilities.Team;
import de.festivaldev.bedwars.values.Itemvalues;
import me.clip.actionannouncer.ActionAPI;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Created by Fabian on 31.03.2016.
 */
public class Main extends JavaPlugin {

	public ArrayList<Spawner> spawners = new ArrayList<>();
	public ArrayList<Team> teams = new ArrayList<>();
	public static Team blau;
	public static Team orange;

	public final static String spawnerprefix = ChatColor.DARK_PURPLE + "[Spawner] " + ChatColor.RESET;

	@Override
	public void onEnable() {
		spawn();
		new PlayerPickupItemListener(this);
		new InventoryClickedListener(this);
		new EntityInteractListener(this);
		new BlockPlacedListener(this);
		Itemvalues.initialize();
		blau = new Team("Blau", ChatColor.BLUE, DyeColor.LIGHT_BLUE);
		orange = new Team("Orange", ChatColor.GOLD, DyeColor.ORANGE);
		teams.add(blau);
		teams.add(orange);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player player;
		if (!(sender instanceof Player)) {
			return false;
		}
		player = (Player) sender;

		if (command.getName().equalsIgnoreCase("bedwars.t1")) {
			player.setLevel(0);
			player.getInventory().setItem(player.getInventory().firstEmpty(), ShopCreator.getDiamond());
			return true;
		} else if (command.getName().equalsIgnoreCase("bedwars.t2")) {
			blau.members.add(player.getUniqueId());
			return true;
		} else if (command.getName().equalsIgnoreCase("bedwars.t3")) {
			ItemStack is = new ItemStack(Material.FLOWER_POT_ITEM, 1);
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 30000);
			is = ItemHelper.setName(is, "Blumentopf der Verdammnis", ChatColor.RED);
			player.getInventory().setItem(player.getInventory().firstEmpty(), is);
			ActionAPI.sendPlayerAnnouncement(player, ChatColor.RED + "Muahahahahaha?");
			return true;
		} else if (command.getName().equalsIgnoreCase("createspawner") && args.length == 1) {
			Spawner spawner = new Spawner(player.getItemInHand(), Integer.parseInt(args[0]), player.getLocation(), this);
			spawners.add(spawner);
			player.sendMessage(spawnerprefix + "Der Spawner mit der ID " + highlight(spawner.id, ChatColor.DARK_PURPLE) + " wurde erstellt.");
			return true;
		} else if (command.getName().equalsIgnoreCase("removespawner") && args.length == 1) {
			Spawner desired = null;
			for(Spawner spawner: spawners) {
				if (spawner.id.equalsIgnoreCase(args[0])) {
					desired = spawner;
					desired.enabled = false;
				}
			}
			if (desired != null)
				spawners.remove(desired);
			player.sendMessage(spawnerprefix + "Der Spawner mit der ID " + highlight(desired.id, ChatColor.DARK_PURPLE) + " wurde gelöscht.");
			return true;
		} else if (command.getName().equalsIgnoreCase("disablespawner")) {
			for(Spawner spawner: spawners) {
				spawner.enabled = false;
			}
			player.sendMessage(spawnerprefix + "Alle Spawner wurden deaktiviert.");
			return true;
		} else if (command.getName().equalsIgnoreCase("enablespawner")) {
			for(Spawner spawner: spawners) {
				spawner.enabled = true;
			}
			player.sendMessage(spawnerprefix + "Alle Spawner wurden aktiviert.");
			return true;
		}

		return false;
	}

	public void spawn() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Spawner s: spawners) {
					if (s.done == s.rate) {
						if (s.enabled) {
							s.is.setAmount(1);
							Item dropped = s.location.getWorld().dropItem(s.location, s.is);
							s.location.getWorld().playEffect(s.location, Effect.EXTINGUISH, 0);
							dropped.setVelocity(new Vector(0, 0, 0));
							s.done = 0;
						}
					}
					else {
						s.done++;
					}
				}
			}
		}, 0, 20);
	}

	public static String highlight(String s, ChatColor color) {
		return color + s + ChatColor.RESET;
	}

}

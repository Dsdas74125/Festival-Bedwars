package de.festivaldev.bedwars;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xpath.internal.operations.Bool;
import de.festivaldev.bedwars.helpers.ItemHelper;
import de.festivaldev.bedwars.helpers.ShopCreator;
import de.festivaldev.bedwars.listeners.BlockPlacedListener;
import de.festivaldev.bedwars.listeners.EntityInteractListener;
import de.festivaldev.bedwars.listeners.InventoryClickedListener;
import de.festivaldev.bedwars.listeners.PlayerPickupItemListener;
import de.festivaldev.bedwars.utilities.Spawner;
import de.festivaldev.bedwars.utilities.Team;
import de.festivaldev.bedwars.values.Itemvalues;
import de.festivaldev.bedwars.utilities.ParticleEffect;
import me.clip.actionannouncer.ActionAPI;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.SystemUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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


		// Load exported spawners
		JSONParser parser = new JSONParser();


		try {
			FileReader fReader = new FileReader(getDataFolder().getAbsolutePath()+"/spawners.json");
			Object obj = parser.parse(fReader);

			JSONArray jsonObject = (JSONArray) obj;
			Iterator<JSONObject> iterator = jsonObject.iterator();
			while (iterator.hasNext()) {
				JSONObject test = iterator.next();

				JSONObject loc = (JSONObject)test.get("location");
				World world = getServer().getWorld(test.get("world_name").toString());
				//Location _loc = new Location(World.Environment, Double.parseDouble(loc.get("x").toString()), Double.parseDouble(loc.get("y").toString()), Double.parseDouble(loc.get("z").toString()));
				Location _loc = new Location(world, Double.parseDouble(loc.get("x").toString()), Double.parseDouble(loc.get("y").toString()), Double.parseDouble(loc.get("z").toString()));

				boolean isSpecial = Boolean.parseBoolean(test.get("isSpecial").toString());

				Spawner spawner = new Spawner(Material.getMaterial(test.get("material").toString()), Integer.parseInt(test.get("rate").toString()), _loc, isSpecial);
				spawners.add(spawner);
			}

			System.out.println("[BedWars] "+jsonObject);
			fReader.close();

		} catch (Exception e) {
			e.printStackTrace();
			getLogger().info("Could not load BedWars spawners. Have you exported some before?");
		}
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
		} else if (command.getName().equalsIgnoreCase("createspawner") && args.length >= 1) {
			Spawner spawner;
			if (args.length == 2) {
				spawner = new Spawner(player.getItemInHand().getType(), Integer.parseInt(args[0]), player.getTargetBlock((Set<Material>) null, 10).getLocation(), (args[1] != "") ? Boolean.parseBoolean(args[1]) : false);
			} else {
				spawner = new Spawner(player.getItemInHand().getType(), Integer.parseInt(args[0]), player.getTargetBlock((Set<Material>) null, 10).getLocation(), false);
			}
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
		} else if (command.getName().equalsIgnoreCase("removespawner")) {
			for (Spawner spawner: spawners) {
				spawner.enabled = false;
			}
			spawners.clear();
			player.sendMessage(spawnerprefix + "Alle Spawner wurden deaktiviert und gelöscht.");
			return true;
		} else if (command.getName().equalsIgnoreCase("exportspawner")) {
			JSONArray json = new JSONArray();
			for (Spawner spawner: spawners) {
				json.add(spawner.exportSpawner());
			}
			//System.out.println(json);

			File parent_directory = new File(getDataFolder().getAbsolutePath());
			if (null != parent_directory)
			{
				parent_directory.mkdirs();
			}

			//try {
				File oldFile = new File(getDataFolder().getAbsolutePath() + "/spawners.json");
				oldFile.delete();
			//} catch ()

			try (FileWriter file = new FileWriter(getDataFolder().getAbsolutePath()+"/spawners.json", false)) {
				file.write(json.toJSONString());
				file.close();
				player.sendMessage(spawnerprefix + "Alle Spawner wurden exportiert.");
			} catch (IOException e) {
				e.printStackTrace();
			}
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
							//s.is.setAmount(1);
							ItemStack is = new ItemStack(s.material);
							Item dropped = s.location.getWorld().dropItem(s.location, is);
							//s.location.getWorld().playEffect(s.location, Effect.EXTINGUISH, 0);

							float x = (float)s.location.getX();
							float y = (float)(s.location.getY() - 0.5);
							float z = (float)s.location.getZ();

							if (s.isSpecial) {
								ParticleEffect.SPELL.display(0.5f, 0.0f, 0.5f, 0.0f, 150, s.location, 10);
							}

							dropped.setVelocity(new Vector(0, 0, 0));
							s.done = 1;
						}
					}
					else {
						s.done++;
					}
				}
			}
		}, 0L, 20L);
	}

	public static String highlight(String s, ChatColor color) {
		return color + s + ChatColor.RESET;
	}

}

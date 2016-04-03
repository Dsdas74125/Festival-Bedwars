package de.festivaldev.bedwars.utilities;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

/**
 * Created by Fabian on 31.03.2016.
 */
public class Spawner {

	public ItemStack is;
	public Location location;
	public boolean enabled = false;
	public String id = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
	public int rate;
	public int done = 0;

	public Spawner(ItemStack is, int rate, Location loc, Plugin plugin) {
		this.is = is;
		this.location = loc;
		this.location.setY(this.location.getY() + 0.5);
		this.rate = rate;
	}

}

package de.festivaldev.bedwars.utilities;

import de.festivaldev.bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Fabian on 01.04.2016.
 */
public class Team {

	public ArrayList<UUID> members = new ArrayList<>();
	public ChatColor color;
	public DyeColor dyeColor;
	public String name;

	public Team(String name, ChatColor color, DyeColor dye) {
		this.color = color;
		this.dyeColor = dye;
		this.name = name;
	}

	public static Team getTeam(Player player, Main main) {
		for (Team t: main.teams) {
			if (t.members.contains(player.getUniqueId())) {
				return t;
			}
		}
		return null;
	}

}

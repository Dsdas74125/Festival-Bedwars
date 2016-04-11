package de.festival.Bedwars.GameElements;

import de.festival.Bedwars.Helper.BedPart;
import de.festival.Bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BedHandler implements Listener {
    public Map<Team, Location> bedLocations = new HashMap<>();
    private Main plugin;

    public BedHandler(Main plugin) {
        this.plugin = plugin;

        JSONParser parser = new JSONParser();
        try {
            FileReader fReader = new FileReader(plugin.getDataFolder().getAbsolutePath()+"/beds.json");
            JSONObject obj = (JSONObject)parser.parse(fReader);

            for (World world: plugin.getServer().getWorlds()) {
                if (obj.get(world.getName()) != null) {
                    JSONObject loc = (JSONObject)obj.get(world.getName());

                    Iterator it = plugin.teams.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        Team team = (Team)pair.getValue();

                        JSONArray loc_coords = (JSONArray) loc.get(team.id);

                        bedLocations.put(team, new Location(world, (long)loc_coords.get(0), (long)loc_coords.get(1), (long)loc_coords.get(2)));
                        team.bed = world.getBlockAt(new Location(world, (long)loc_coords.get(0), (long)loc_coords.get(1), (long)loc_coords.get(2)));
                    }
                }
            }

            fReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info("Could not load Bedwars spawners. Have you exported some before?");
        }
    }

    public void destroyBed(Block block, Player player, Location loc) {
        BedPart part = new BedPart(block);
        Team playerTeam = this.plugin.teamMap.get(player.getUniqueId());

        if (!playerTeam.bed.getLocation().equals(part.locateFootPart().getLocation())) {

            Iterator it = plugin.teams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Team _team = (Team)pair.getValue();

                if (_team.bed.getLocation().equals(part.locateFootPart().getLocation())) {
                    for (Player player1: this.plugin.getServer().getOnlinePlayers()) {
                        player1.sendMessage("Das Bett von Team " + _team.teamColor.chatColor + _team.name + ChatColor.RESET + " wurde von " + playerTeam.teamColor.chatColor + player.getDisplayName() + ChatColor.RESET + " zerstört!");
                    }
                    break;
                }
            }


        } else {
            System.out.println("Sending \"Bed Destroyed\" notification to team members");
            playerTeam.sendBedDestroyedNotification();
        }
    }
}

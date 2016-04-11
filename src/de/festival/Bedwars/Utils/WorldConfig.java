package de.festival.Bedwars.Utils;


import de.festival.Bedwars.GameElements.Spawner;
import de.festival.Bedwars.GameElements.Team;
import de.festival.Bedwars.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorldConfig {
    /*ArrayList<Location> beds = new ArrayList<>();
    ArrayList<Location> spawners = new ArrayList<>();
    ArrayList<Location> teamSpawns = new ArrayList<>();
    ArrayList<Location> teamSelector = new ArrayList<>();*/

    public Map<Location, Team> teamSelectors = new HashMap<>();
    public Map<Team, Location> teamSpawns = new HashMap<>();
    public Map<Location, Spawner> spawners = new HashMap<>();
    public Location worldSpawn;

    public WorldConfig(Main plugin, World world, JSONObject setup) {
        JSONArray _worldSpawn = (JSONArray)setup.get("worldspawn");
        this.worldSpawn = new Location(world, Double.parseDouble(_worldSpawn.get(0).toString()), Double.parseDouble(_worldSpawn.get(1).toString()), Double.parseDouble(_worldSpawn.get(2).toString()));

        JSONObject _teamSelector = (JSONObject) setup.get("team_selectors");
        Iterator iterator1 = _teamSelector.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator1.next();
            JSONArray obj = (JSONArray) pair.getValue();

            Team _team = plugin.teams.get(pair.getKey());
            Location _loc = new Location(world, Double.parseDouble(obj.get(0).toString()), Double.parseDouble(obj.get(1).toString()), Double.parseDouble(obj.get(2).toString()));
            _loc.setPitch(Float.parseFloat(obj.get(3).toString()));
            _loc.setYaw(Float.parseFloat(obj.get(4).toString()));

            teamSelectors.put(_loc, _team);
        }

        JSONObject _teamSpawns = (JSONObject) setup.get("team_spawns");
        Iterator iterator2 = _teamSpawns.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator2.next();
            JSONArray obj = (JSONArray) pair.getValue();

            Team _team = plugin.teams.get(pair.getKey());
            Location _loc = new Location(world, Double.parseDouble(obj.get(0).toString()), Double.parseDouble(obj.get(1).toString()), Double.parseDouble(obj.get(2).toString()));
            _loc.setPitch(Float.parseFloat(obj.get(3).toString()));
            _loc.setYaw(Float.parseFloat(obj.get(4).toString()));

            teamSpawns.put(_team, _loc);
        }

        JSONArray _spawners = (JSONArray) setup.get("spawners");

        for (int i=0; i<_spawners.size(); i++) {
            JSONObject obj = (JSONObject)_spawners.get(i);
            JSONArray location = (JSONArray) obj.get("location");
            Location _loc = new Location(world, Double.parseDouble(location.get(0).toString()), Double.parseDouble(location.get(1).toString()), Double.parseDouble(location.get(2).toString()));
            boolean isSpecial = Boolean.parseBoolean(obj.get("isSpecial").toString());

            Spawner spawner = new Spawner(Material.getMaterial(obj.get("material").toString()), Integer.parseInt(obj.get("rate").toString()), _loc, isSpecial);
            plugin.spawners.add(spawner);
        }
    }
}

package de.festival.Bedwars;

import de.festival.Bedwars.GameElements.BedHandler;
import de.festival.Bedwars.GameElements.Shop;
import de.festival.Bedwars.GameElements.Spawner;
import de.festival.Bedwars.GameElements.Team;
import de.festival.Bedwars.Helper.ParticleEffect;
import de.festival.Bedwars.Listener.EntityInteractListener;
import de.festival.Bedwars.Listener.ItemPickupListener;
import de.festival.Bedwars.Utils.ScoreBoardManager;
import de.festival.Bedwars.Utils.WorldProtection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main extends JavaPlugin implements Listener {
    WorldProtection protector;
    public BedHandler bedHandler;

    public Shop shop;
    public ArrayList<Spawner> spawners = new ArrayList<>();
    public ArrayList<Team> teams = new ArrayList<>();
    public Map<UUID, Team> teamMap = new HashMap<UUID, Team>();

    public final static String spawnerprefix = ChatColor.DARK_PURPLE + "[Spawner] " + ChatColor.RESET;
    public ScoreBoardManager board = new ScoreBoardManager(this);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        protector = new WorldProtection(this);
        new EntityInteractListener(this);
        new ItemPickupListener(this);

        shop = new Shop(this);

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
            fReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            getLogger().info("Could not load Bedwars spawners. Have you exported some before?");
        }

        // Make spawners spawn things
        spawn();

        // Setup teams
        int teamSize = this.getConfig().getInt("team-size");
        teams.add(new Team(this, "Rot", "red", Team.TeamColor.RED, teamSize));
        teams.add(new Team(this, "Gelb", "yellow", Team.TeamColor.YELLOW, teamSize));
        teams.add(new Team(this, "Grün", "green", Team.TeamColor.LIME, teamSize));
        teams.add(new Team(this, "Blau", "blue", Team.TeamColor.BLUE, teamSize));


        // Assign online players to random team
        for (Player player: getServer().getOnlinePlayers()) {
            int random = randInt(0, teams.size()-1);
            //int random = 0;

            if (!teams.get(random).isFull()) {
                teams.get(random).addMember(player);
                //teams.get(random).members.add(player.getUniqueId());
                teamMap.put(player.getPlayer().getUniqueId(), teams.get(random));
                player.sendMessage("Du bist in Team " + teams.get(random).teamColor.chatColor + teams.get(random).name);
            } else {
                for (int i=0; i<teams.size(); i++) {
                    if (!teams.get(i).isFull()) {
                        teams.get(i).addMember(player);
                        teamMap.put(player.getPlayer().getUniqueId(), teams.get(i));
                        player.sendMessage("Du bist in Team " + teams.get(i).teamColor.chatColor + teams.get(i).name);
                        break;
                    }
                }
            }

            board.openScoreboard(player);
        }

        bedHandler = new BedHandler(this);
    }

    @Override
    public void onDisable() {
        //protector.destroyPlacedBlocks();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player;
        if (!(sender instanceof Player)) {
            return false;
        }
        player = (Player) sender;

        if (command.getName().equalsIgnoreCase("wp.reset")) {
            protector.destroyPlacedBlocks();
            return true;
        } else if (command.getName().equalsIgnoreCase("wp.enable")) {
            protector.enabled = true;
        } else if (command.getName().equalsIgnoreCase("wp.disable")) {
            protector.enabled = false;
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        int random = randInt(0, teams.size()-1);
        teams.get(random).addMember(event.getPlayer());
        teamMap.put(event.getPlayer().getUniqueId(), teams.get(random));
        event.getPlayer().sendMessage("Du bist in Team "+teams.get(random).teamColor.chatColor+teams.get(random).name);

        Location spawn_tp = new Location(event.getPlayer().getWorld(), 0.5, 54, 0.5);
        event.getPlayer().teleport(spawn_tp);
        board.openScoreboard(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        //System.out.println(randInt(0, teams.size()-1));
        for (int i=0; i<teams.size(); i++) {
            if (teams.get(i).members.indexOf(event.getPlayer().getUniqueId()) != -1) {
                int index = teams.get(i).members.indexOf(event.getPlayer().getUniqueId());
                teams.get(i).members.remove(index);
                teamMap.remove(event.getPlayer().getUniqueId());
            }
        }
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

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}

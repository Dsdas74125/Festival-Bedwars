package de.festival.Bedwars.GameElements;

import de.festival.Bedwars.Helper.BedPart;
import de.festival.Bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.UUID;

public class Team implements Listener {
    public enum TeamColor {
        WHITE(DyeColor.WHITE, ChatColor.WHITE),
        LIME(DyeColor.LIME, ChatColor.GREEN),
        BLUE(DyeColor.BLUE, ChatColor.BLUE),
        RED(DyeColor.RED, ChatColor.RED),
        YELLOW(DyeColor.YELLOW, ChatColor.YELLOW),
        PURPLE(DyeColor.PURPLE, ChatColor.LIGHT_PURPLE),
        ORANGE(DyeColor.ORANGE, ChatColor.GOLD),
        BLACK(DyeColor.BLACK, ChatColor.BLACK);

        public DyeColor color;
        public ChatColor chatColor;

        private TeamColor(DyeColor color, ChatColor chatColor) {
            this.color = color;
            this.chatColor = chatColor;
        }

        public static boolean contains(String test) {

            for (TeamColor c : TeamColor.values()) {
                if (c.name().equals(test)) {
                    return true;
                }
            }

            return false;
        }
    }

    public String name;
    public String id;
    public TeamColor teamColor;
    public ArrayList<UUID> members = new ArrayList<>();
    public Block bed;

    private Main plugin;

    public Team(Main plugin, String name, String id, TeamColor teamColor, int teamSize) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.name = name;
        this.teamColor = teamColor;
        this.id = id;

        for (int i=0; i<teamSize; i++) {
            this.members.add(null);
        }
    }

    public void addMember(Player player) {
        if (!this.isFull()) {
            this.members.set(this.members.indexOf(null), player.getUniqueId());
        }
    }
    public void removeMember(Player player) {
        if (this.members.indexOf(player.getUniqueId()) != -1) {
            this.members.set(this.members.indexOf(player.getUniqueId()), null);
        }
    }

    public boolean isFull() {
        if (this.members.indexOf(null) != -1) {
            return false;
        }

        return true;
    }

    public int getActiveMembers() {
        int _members = 0;

        for (int i=0; i<this.members.size(); i++) {
            if (this.members.get(i) instanceof UUID) {
                _members++;
            }
        }

        return _members;
    }

    public void sendBedDestroyedNotification() {
        for (Player player: this.plugin.getServer().getOnlinePlayers()) {
            if (this.members.indexOf(player.getUniqueId()) != -1) {
                player.sendTitle("", "Dein Bett wurde zerstört!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakOwnBedAttempt(PlayerInteractEvent event) {
        if (this.members.indexOf(event.getPlayer().getUniqueId()) != -1 && event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getType().equals(Material.BED_BLOCK)) {
                BedPart bedPart = new BedPart(event.getClickedBlock());

                if (this.bed.getLocation().equals(bedPart.locateFootPart().getLocation())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (this.members.indexOf(event.getPlayer().getUniqueId()) != -1) {
            event.setRespawnLocation(this.plugin.worldConfigs.get(event.getPlayer().getLocation().getWorld().getName()).teamSpawns.get(this));
        }
    }
}

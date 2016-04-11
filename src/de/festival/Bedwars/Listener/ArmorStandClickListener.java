package de.festival.Bedwars.Listener;

import de.festival.Bedwars.GameElements.Team;
import de.festival.Bedwars.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import java.util.Iterator;
import java.util.Map;

public class ArmorStandClickListener implements Listener {
    Main plugin;

    public ArmorStandClickListener(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerClickedArmorStand(PlayerArmorStandManipulateEvent event) {
        Iterator iterator = this.plugin.worldConfigs.get(event.getPlayer().getLocation().getWorld().getName()).teamSelectors.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();

            boolean isLocationEqual = pair.getKey().equals(event.getRightClicked().getLocation());
            if (isLocationEqual) {
                Team _team = (Team)pair.getValue();
                boolean allowJoinTeam = true;

                if (plugin.teamMap.get(event.getPlayer().getUniqueId()) != null && plugin.teamMap.get(event.getPlayer().getUniqueId()).equals(_team)) {
                    event.getPlayer().sendMessage("Du bist bereits in Team " + _team.teamColor.chatColor + _team.name);
                    event.getPlayer().teleport(this.plugin.worldConfigs.get(event.getPlayer().getLocation().getWorld().getName()).teamSpawns.get(_team));
                    allowJoinTeam = false;
                } else if (plugin.teamMap.get(event.getPlayer().getUniqueId()) != null) {
                    event.getPlayer().sendMessage("Du kannst während einer Runde nicht das Team wechseln!");
                    allowJoinTeam = false;
                }
                if (allowJoinTeam) {
                    _team.addMember(event.getPlayer());
                    plugin.teamMap.put(event.getPlayer().getUniqueId(), _team);
                    event.getPlayer().sendMessage("Du bist in Team " + _team.teamColor.chatColor + _team.name);

                    event.getPlayer().teleport(this.plugin.worldConfigs.get(event.getPlayer().getLocation().getWorld().getName()).teamSpawns.get(_team));
                    plugin.board.updateScoreboard();
                }
            }
        }
        event.setCancelled(true);
    }

    /*@EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockHangingBreak(EntityDamageEvent event) {
        event.setCancelled(true);
    }*/
}

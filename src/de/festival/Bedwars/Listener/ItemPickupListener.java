package de.festival.Bedwars.Listener;

import de.festival.Bedwars.Main;
import me.clip.actionannouncer.ActionAPI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemPickupListener implements Listener {
    private Main plugin;

    public enum ItemValue {
        NETHER_BRICK_ITEM(3),
        CLAY_BRICK(10),
        IRON_INGOT(20);
        private int value;

        private ItemValue(int value) {
            this.value = value;
        }

        public static boolean contains(String test) {

            for (ItemValue c : ItemValue.values()) {
                if (c.name().equals(test)) {
                    return true;
                }
            }

            return false;
        }
    }

    public ItemPickupListener(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = (Main) plugin;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if (ItemValue.contains(event.getItem().getItemStack().getType().toString())) {
            int value = ItemValue.valueOf(event.getItem().getItemStack().getType().toString()).value;
            int multiplier = event.getItem().getItemStack().getAmount();

            if (value > 0) {
                event.setCancelled(true);
                event.getItem().remove();
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 1);

                ActionAPI.sendPlayerAnnouncement(player, ChatColor.BOLD + "" + ChatColor.GREEN + "+" + (value*multiplier) + ChatColor.WHITE + " Ressourcen");
                player.setLevel(player.getLevel() + (value*multiplier));
                player.setExp(0);
            }
        }

        /*if (value > 0) {
            event.setCancelled(true);
            event.getItem().remove();

            ActionAPI.sendPlayerAnnouncement(player, ChatColor.BOLD + "" + ChatColor.GREEN + "+" + value + ChatColor.WHITE + " Ressourcen");
            player.setLevel(player.getLevel() + value);
            player.setExp(0);
        }*/
    }
}
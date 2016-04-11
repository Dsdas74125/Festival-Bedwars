package de.festival.Bedwars.Helper;

import de.festival.Bedwars.GameElements.Team;
import de.festival.Bedwars.Main;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Dye;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class ShopItem {
    public Material itemMaterial;
    public int itemPrice = -1;
    public int itemPriceDiamonds = -1;
    public boolean isSpecial;
    public boolean isTeamColored = false;
    public int potionID = -1;
    private Team team;

    public String name;
    public ArrayList<String> description;

    private ItemStack itemStack;

    public ShopItem(String name, Player player, Main plugin, ArrayList<String> description, Material material, int price, boolean isSpecial, boolean isTeamColored, int potionID) {
        this.name = name;
        this.description = description;
        this.itemMaterial = material;
        this.isTeamColored = isTeamColored;
        this.team = plugin.teamMap.get(player.getUniqueId());

        this.itemStack = new ItemStack(this.itemMaterial, 1, (short) 0, (byte) 0);
        if (this.isTeamColored && this.team != null) {
            if (this.itemStack.getItemMeta() instanceof LeatherArmorMeta) {
                LeatherArmorMeta meta = (LeatherArmorMeta)this.itemStack.getItemMeta();

                meta.setColor(this.team.teamColor.color.getColor());
                this.itemStack.setItemMeta(meta);
            } else {
                this.itemStack = new ItemStack(this.itemMaterial, 1, (short) 0, this.team.teamColor.color.getData());
            }
        }

        if (this.itemMaterial == Material.POTION) {
            this.itemStack = new ItemStack(this.itemMaterial, 1, (short) 0, (byte) potionID);
        }

        ItemMeta meta = this.itemStack.getItemMeta();

        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + this.name);

        if (description != null) {
            this.description = description;
        }

        this.isSpecial = isSpecial;
        if (!isSpecial) {
            this.itemPrice = price;
            this.description.add("" + ChatColor.GOLD + itemPrice + " " + ChatColor.WHITE + "Ressourcen");
        } else {
            this.itemPriceDiamonds = price;
            this.description.add("" + ChatColor.AQUA + itemPriceDiamonds + " " + ChatColor.WHITE + ((itemPriceDiamonds > 1 ) ? "Diamanten" : "Diamant"));
        }
        meta.setLore(this.description);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.itemStack.setItemMeta(meta);
    }

    public ItemStack getItemStack() {

        return this.itemStack;
    }

    public ItemStack playerCanPurchase(Player player) {
        if (!this.isSpecial) {
            if (player.getLevel() >= itemPrice) {
                return new ColoredGlass().getColoredGlass(DyeColor.LIME.getData(), ChatColor.GREEN + "Verfügbar");
            } else {
                return new ColoredGlass().getColoredGlass(DyeColor.RED.getData(), ChatColor.RED + "Nicht verfügbar");
            }
        } else {
            if (player.getInventory().contains(Material.DIAMOND)) {
                if (player.getInventory().getItem(player.getInventory().first(Material.DIAMOND)).getAmount() >= itemPriceDiamonds) {
                    return new ColoredGlass().getColoredGlass(DyeColor.LIME.getData(), ChatColor.GREEN + "Verfügbar");
                } else {
                    return new ColoredGlass().getColoredGlass(DyeColor.RED.getData(), ChatColor.RED + "Nicht verfügbar");
                }
            } else {
                return new ColoredGlass().getColoredGlass(DyeColor.RED.getData(), ChatColor.RED + "Nicht verfügbar");
            }
        }
    }

    public boolean purchase(Player player) {
        if (!this.isSpecial) {
            if (player.getLevel() < this.itemPrice) {

                return false;
            } else {
                ItemStack purchasedItem = this.getItemStack();
                purchasedItem.setAmount(1);

                if (this.isTeamColored) {
                    if (this.itemStack.getItemMeta() instanceof LeatherArmorMeta) {
                        LeatherArmorMeta meta = (LeatherArmorMeta) this.itemStack.getItemMeta();

                        meta.setColor(this.team.teamColor.color.getColor());
                        this.itemStack.setItemMeta(meta);
                    } else {
                    }
                }

                if (!player.getInventory().contains(purchasedItem.getType())) {
                    player.getInventory().setItem(player.getInventory().firstEmpty(), purchasedItem);
                } else {
                    int slot = player.getInventory().first(purchasedItem.getType());
                    int oldamount = player.getInventory().getItem(slot).getAmount();
                    purchasedItem.setAmount(oldamount + 1);
                    player.getInventory().setItem(slot, purchasedItem);
                }
                player.setLevel(player.getLevel() - this.itemPrice);

                return true;
            }
        } else {
            if (player.getInventory().contains(Material.DIAMOND)) {
                if (player.getInventory().getItem(player.getInventory().first(Material.DIAMOND)).getAmount() >= this.itemPriceDiamonds) {
                    ItemStack purchasedItem = this.getItemStack();
                    purchasedItem.setAmount(1);
                    if (!player.getInventory().contains(purchasedItem.getType())) {
                        player.getInventory().setItem(player.getInventory().firstEmpty(), purchasedItem);
                    } else {
                        int slot = player.getInventory().first(purchasedItem.getType());
                        int oldamount = player.getInventory().getItem(slot).getAmount();
                        purchasedItem.setAmount(oldamount + 1);
                        player.getInventory().setItem(slot, purchasedItem);
                    }
                    int slot1 = player.getInventory().first(Material.DIAMOND);
                    player.getInventory().getItem(slot1).setAmount(player.getInventory().getItem(slot1).getAmount() - this.itemPriceDiamonds);

                    return true;
                }
                return false;
            }
            return false;
        }
    }
}

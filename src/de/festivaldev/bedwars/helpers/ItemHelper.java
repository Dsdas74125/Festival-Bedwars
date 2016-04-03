package de.festivaldev.bedwars.helpers;

import de.festivaldev.bedwars.Main;
import de.festivaldev.bedwars.utilities.Team;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

/**
 * Created by Fabian on 01.04.2016.
 */
public class ItemHelper {

	public static ItemStack getItem(String id) {
		ItemStack is = null;

		switch (id) {
			case "1:1":
				is = setName(new ItemStack(Material.STAINED_CLAY, 1), "Teamfarbener Lehm");
				break;
			case "1:2":
				is = setName(new ItemStack(Material.SANDSTONE, 1), "Anonymer Sandstein");
				break;
			case "1:3":
				is = setName(new ItemStack(Material.SANDSTONE_STAIRS, 1), "Sandsteinstufen");
				break;
			case "1:4":
				is = setName(new ItemStack(Material.STAINED_GLASS, 1), "Teamfarbenes Glas");
				//color
				break;
			case "1:5":
				is = setName(new ItemStack(Material.ENDER_STONE, 1), "Endstein");
				break;
			case "1:6":
				is = setName(new ItemStack(Material.STONE, 1), "Stein");
				break;
			case "1:7":
				is = setName(new ItemStack(Material.IRON_BLOCK, 1), "Eisenblock");
				break;
			case "2:1":
				is = setName(new ItemStack(Material.WOOD_PICKAXE, 1), "Spitzhacke I");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				break;
			case "2:2":
				is = setName(new ItemStack(Material.WOOD_PICKAXE, 1), "Spitzhacke II");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				is.addEnchantment(Enchantment.DIG_SPEED, 1);
				break;
			case "2:3":
				is = setName(new ItemStack(Material.WOOD_PICKAXE, 1), "Spitzhacke III");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				is.addEnchantment(Enchantment.DIG_SPEED, 2);
				is.addEnchantment(Enchantment.DURABILITY, 1);
				break;
			case "2:4":
				is = setName(new ItemStack(Material.IRON_PICKAXE, 1), "Get Rid Of 'Em");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				is.addEnchantment(Enchantment.DIG_SPEED, 5);
				break;
			case "3:1":
				is = setName(new ItemStack(Material.STICK, 1), "Knockbackstick I");
				is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
				break;
			case "3:2":
				is = setName(new ItemStack(Material.BLAZE_ROD, 1), "Knockbackstick II");
				is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
				break;
			case "3:3":
				is = setName(new ItemStack(Material.WOOD_SWORD, 1), "Schwert I");
				is.addEnchantment(Enchantment.DURABILITY, 1);
				is.addEnchantment(Enchantment.DAMAGE_ALL, 1);
				break;
			case "3:4":
				is = setName(new ItemStack(Material.WOOD_SWORD, 1), "Schwert II");
				is.addEnchantment(Enchantment.DURABILITY, 1);
				is.addEnchantment(Enchantment.DAMAGE_ALL, 2);
				break;
			case "3:5":
				is = setName(new ItemStack(Material.IRON_SWORD, 1), "Die Letzte Rettung");
				is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
				is.addEnchantment(Enchantment.FIRE_ASPECT, 2);
				break;
			case "4:1":
				is = setName(new ItemStack(Material.BOW, 1), "Bogen I");
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
				break;
			case "4:2":
				is = setName(new ItemStack(Material.BOW, 1), "Bogen II");
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
				break;
			case "4:3":
				is = setName(new ItemStack(Material.BOW, 1), "Auf die Schnelle");
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
				is.addEnchantment(Enchantment.ARROW_FIRE, 1);
				is.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
				is.addEnchantment(Enchantment.ARROW_INFINITE, 1);
				break;
			case "4:4":
				is = setName(new ItemStack(Material.ARROW, 1), "Pfeil");
				break;
			case "5:1":
				is = setName(new ItemStack(Material.LEATHER_HELMET, 1), "Helm");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:2":
				is = setName(new ItemStack(Material.LEATHER_CHESTPLATE, 1), "Harnisch I");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:3":
				is = setName(new ItemStack(Material.LEATHER_LEGGINGS, 1), "Hose");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:4":
				is = setName(new ItemStack(Material.LEATHER_BOOTS, 1), "Stiefel");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:5":
				is = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "Harnisch II");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				break;
			case "5:6":
				is = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "Harnisch III");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				is.addEnchantment(Enchantment.THORNS, 1);
				break;
			case "5:7":
				is = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "Harnisch IV");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				is.addEnchantment(Enchantment.THORNS, 2);
				is.addEnchantment(Enchantment.DURABILITY, 1);
				break;
			case "5:8":
				is = setName(new ItemStack(Material.LEATHER_BOOTS, 1), "Get Down!");
				is.addEnchantment(Enchantment.PROTECTION_FALL, 3);
				break;
			case "6:1":
				is = setName(new ItemStack(Material.BAKED_POTATO, 1), "Backkartoffel");
				break;
			case "6:2":
				is = setName(new ItemStack(Material.GRILLED_PORK, 1), "Gegrilltes Schweinefleisch");
				break;
			case "6:3":
				is = setName(new ItemStack(Material.CAKE, 1), "Teamnahrung");
				break;
			case "6:4":
				is = setName(new ItemStack(Material.GOLDEN_APPLE, 1), "Goldapfel");
				break;
			case "7:1":
				is = setName(new ItemStack(Material.CHEST, 1), "Kiste");
				break;
			case "7:2":
				is = setName(new ItemStack(Material.ENDER_CHEST, 1), "Teamkiste");
				break;
			case "7:3":
				is = setName(new ItemStack(Material.JUKEBOX, 1), "Gutscheinmaschine");
				break;
			case "8:1":
				Potion potion = new Potion(PotionType.INSTANT_HEAL, 2);
				is = setName(new ItemStack(Material.POTION, 1), "Trank der Heilung");
				potion.apply(is);
				break;
			case "8:2":
				Potion potion1 = new Potion(PotionType.SPEED, 1);
				is = setName(new ItemStack(Material.POTION, 1), "Trank der Schnelligkeit");
				potion1.apply(is);
				break;
			case "8:3":
				Potion potion2 = new Potion(PotionType.SLOWNESS, 1);
				potion2.setSplash(true);
				is = setName(new ItemStack(Material.POTION, 1), "Werfbarer Trank der Langsamkeit");
				potion2.apply(is);
				break;
			case "8:4":
				Potion potion3 = new Potion(PotionType.STRENGTH, 1);
				is = setName(new ItemStack(Material.POTION, 1), "Trank der Stärke");
				potion3.apply(is);
				break;
			case "8:5":
				Potion potion4 = new Potion(PotionType.WEAKNESS, 1);
				potion4.setSplash(true);
				is = setName(new ItemStack(Material.POTION, 1), "Werfbarer Trank der Schwäche");
				potion4.apply(is);
				break;
			case "9:1":
				is = setName(new ItemStack(Material.LADDER, 1), "Leiter");
				break;
			case "9:2":
				is = setName(new ItemStack(Material.ENDER_PEARL, 1), "Enderperle");
				break;
			case "9:3":
				is = setName(new ItemStack(Material.FIREWORK, 1), "Beam Me Up, Scotty!");
				break;
			case "9:4":
				is = setName(new ItemStack(Material.FLINT_AND_STEEL, 1), "Feuerzeug");
				break;
			case "9:5":
				is = setName(new ItemStack(Material.TNT, 1), "TNT");
				break;
			case "9:6":
				is = setName(new ItemStack(Material.QUARTZ_BLOCK, 1, (byte) 1), "DIY Spawner");
		}

		return is;
	}

	@SuppressWarnings("deprecation")
	public static void giveItem(String id, int amount, Player player, Main main) {
		ItemStack is = null;

		switch (id) {
			case "1:1":
				is = setName(new ItemStack(Material.STAINED_CLAY, amount, Team.getTeam(player, main).dyeColor.getData()), "Teamfarbener Lehm");
				break;
			case "1:2":
				is = setName(new ItemStack(Material.SANDSTONE, amount), "Anonymer Sandstein");
				break;
			case "1:3":
				is = setName(new ItemStack(Material.SANDSTONE_STAIRS, amount), "Sandsteinstufen");
				break;
			case "1:4":
				is = setName(new ItemStack(Material.STAINED_GLASS, amount, Team.getTeam(player, main).dyeColor.getData()), "Teamfarbenes Glas");
				break;
			case "1:5":
				is = setName(new ItemStack(Material.ENDER_STONE, amount), "Endstein");
				break;
			case "1:6":
				is = setName(new ItemStack(Material.STONE, amount), "Stein");
				break;
			case "1:7":
				is = setName(new ItemStack(Material.IRON_BLOCK, amount), "Eisenblock");
				break;
			case "2:1":
				is = setName(new ItemStack(Material.WOOD_PICKAXE, amount), "Spitzhacke I");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				break;
			case "2:2":
				is = setName(new ItemStack(Material.WOOD_PICKAXE, amount), "Spitzhacke II");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				is.addEnchantment(Enchantment.DIG_SPEED, 1);
				break;
			case "2:3":
				is = setName(new ItemStack(Material.WOOD_PICKAXE, amount), "Spitzhacke III");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				is.addEnchantment(Enchantment.DIG_SPEED, 2);
				is.addEnchantment(Enchantment.DURABILITY, 1);
				break;
			case "2:4":
				is = setName(new ItemStack(Material.IRON_PICKAXE, amount), "Get Rid Of 'Em");
				is.addEnchantment(Enchantment.SILK_TOUCH, 1);
				is.addEnchantment(Enchantment.DIG_SPEED, 5);
				break;
			case "3:1":
				is = setName(new ItemStack(Material.STICK, amount), "Knockbackstick I");
				is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
				break;
			case "3:2":
				is = setName(new ItemStack(Material.BLAZE_ROD, amount), "Knockbackstick II");
				is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
				break;
			case "3:3":
				is = setName(new ItemStack(Material.WOOD_SWORD, amount), "Schwert I");
				is.addEnchantment(Enchantment.DURABILITY, 1);
				is.addEnchantment(Enchantment.DAMAGE_ALL, 1);
				break;
			case "3:4":
				is = setName(new ItemStack(Material.WOOD_SWORD, amount), "Schwert II");
				is.addEnchantment(Enchantment.DURABILITY, 1);
				is.addEnchantment(Enchantment.DAMAGE_ALL, 2);
				break;
			case "3:5":
				is = setName(new ItemStack(Material.IRON_SWORD, amount), "Die Letzte Rettung");
				is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
				is.addEnchantment(Enchantment.FIRE_ASPECT, 2);
				break;
			case "4:1":
				is = setName(new ItemStack(Material.BOW, amount), "Bogen I");
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
				break;
			case "4:2":
				is = setName(new ItemStack(Material.BOW, amount), "Bogen II");
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
				break;
			case "4:3":
				is = setName(new ItemStack(Material.BOW, amount), "Auf die Schnelle");
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
				is.addEnchantment(Enchantment.ARROW_FIRE, 1);
				is.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
				is.addEnchantment(Enchantment.ARROW_INFINITE, 1);
				break;
			case "4:4":
				is = setName(new ItemStack(Material.ARROW, amount), "Pfeil");
				break;
			case "5:1":
				is = setName(new ItemStack(Material.LEATHER_HELMET, amount), "Helm");
				LeatherArmorMeta lam1 = (LeatherArmorMeta) is.getItemMeta();
				lam1.setColor(Team.getTeam(player, main).dyeColor.getColor());
				is.setItemMeta(lam1);
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:2":
				is = setName(new ItemStack(Material.LEATHER_CHESTPLATE, amount), "Harnisch I");
				LeatherArmorMeta lam2 = (LeatherArmorMeta) is.getItemMeta();
				lam2.setColor(Team.getTeam(player, main).dyeColor.getColor());
				is.setItemMeta(lam2);
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:3":
				is = setName(new ItemStack(Material.LEATHER_LEGGINGS, amount), "Hose");
				LeatherArmorMeta lam3 = (LeatherArmorMeta) is.getItemMeta();
				lam3.setColor(Team.getTeam(player, main).dyeColor.getColor());
				is.setItemMeta(lam3);
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:4":
				is = setName(new ItemStack(Material.LEATHER_BOOTS, amount), "Stiefel");
				LeatherArmorMeta lam4 = (LeatherArmorMeta) is.getItemMeta();
				lam4.setColor(Team.getTeam(player, main).dyeColor.getColor());
				is.setItemMeta(lam4);
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				break;
			case "5:5":
				is = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE, amount), "Harnisch II");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				break;
			case "5:6":
				is = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE, amount), "Harnisch III");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				is.addEnchantment(Enchantment.THORNS, 1);
				break;
			case "5:7":
				is = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE, amount), "Harnisch IV");
				is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				is.addEnchantment(Enchantment.THORNS, 2);
				is.addEnchantment(Enchantment.DURABILITY, 1);
				break;
			case "5:8":
				is = setName(new ItemStack(Material.LEATHER_BOOTS, amount), "Get Down!");
				LeatherArmorMeta lam5 = (LeatherArmorMeta) is.getItemMeta();
				lam5.setColor(Team.getTeam(player, main).dyeColor.getColor());
				is.setItemMeta(lam5);
				is.addEnchantment(Enchantment.PROTECTION_FALL, 3);
				break;
			case "6:1":
				is = setName(new ItemStack(Material.BAKED_POTATO, amount), "Backkartoffel");
				break;
			case "6:2":
				is = setName(new ItemStack(Material.GRILLED_PORK, amount), "Gegrilltes Schweinefleisch");
				break;
			case "6:3":
				is = setName(new ItemStack(Material.CAKE, amount), "Teamnahrung");
				break;
			case "6:4":
				is = setName(new ItemStack(Material.GOLDEN_APPLE, amount), "Goldapfel");
				break;
			case "7:1":
				is = setName(new ItemStack(Material.CHEST, amount), "Kiste");
				break;
			case "7:2":
				is = setName(new ItemStack(Material.ENDER_CHEST, amount), "Teamkiste");
				break;
			case "7:3":
				is = setName(new ItemStack(Material.JUKEBOX, amount), "Gutscheinmaschine");
				break;
			case "8:1":
				Potion potion = new Potion(PotionType.INSTANT_HEAL, 2);
				is = setName(new ItemStack(Material.POTION, amount), "Trank der Heilung");
				potion.apply(is);
				break;
			case "8:2":
				Potion potion1 = new Potion(PotionType.SPEED, 1);
				is = setName(new ItemStack(Material.POTION, amount), "Trank der Schnelligkeit");
				potion1.apply(is);
				break;
			case "8:3":
				Potion potion2 = new Potion(PotionType.SLOWNESS, 1);
				potion2.setSplash(true);
				is = setName(new ItemStack(Material.POTION, amount), "Werfbarer Trank der Langsamkeit");
				potion2.apply(is);
				break;
			case "8:4":
				Potion potion3 = new Potion(PotionType.STRENGTH, 1);
				is = setName(new ItemStack(Material.POTION, amount), "Trank der Stärke");
				potion3.apply(is);
				break;
			case "8:5":
				Potion potion4 = new Potion(PotionType.WEAKNESS, 1);
				potion4.setSplash(true);
				is = setName(new ItemStack(Material.POTION, amount), "Werfbarer Trank der Schwäche");
				potion4.apply(is);
				break;
			case "9:1":
				is = setName(new ItemStack(Material.LADDER, amount), "Leiter");
				break;
			case "9:2":
				is = setName(new ItemStack(Material.ENDER_PEARL, amount), "Enderperle");
				break;
			case "9:3":
				is = setName(new ItemStack(Material.FIREWORK, amount), "Beam Me Up, Scotty!");
				break;
			case "9:4":
				is = setName(new ItemStack(Material.FLINT_AND_STEEL, amount), "Feuerzeug");
				break;
			case "9:5":
				is = setName(new ItemStack(Material.TNT, amount), "TNT");
				break;
			case "9:6":
				is = setName(new ItemStack(Material.QUARTZ_BLOCK, amount, (byte) 1), "DIY Spawner");
		}
		if (!player.getInventory().contains(is.getType())) {
			player.getInventory().setItem(player.getInventory().firstEmpty(), is);
		} else {
			int slot = player.getInventory().first(is.getType());
			int oldamount = player.getInventory().getItem(slot).getAmount();
			is.setAmount(oldamount + amount);
			player.getInventory().setItem(slot, is);
		}
	}

	public static ItemStack setName(ItemStack is, String name) {
		return setName(is, name, ChatColor.AQUA);
	}

	public static ItemStack setName(ItemStack is, String name, ChatColor color) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "" + color + name);
		is.setItemMeta(im);
		return is;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createItem(Material material, int amount, short damage, byte data, String name, ArrayList<String> lore) {
		ItemStack is = new ItemStack(material, amount, damage, data);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack createBuyItem(int price, String itemid) {
		ItemStack is = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Kaufen");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GOLD + "Preis: " + ChatColor.RESET + price + ChatColor.GOLD + " Ressourcen");
		lore.add(ChatColor.DARK_GRAY + itemid);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack createOptionalBuyItem(int price, String itemid) {
		ItemStack is = new ItemStack(Material.DIAMOND, 1);
		is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + "Kaufen");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.AQUA + "Preis: " + ChatColor.RESET + (price - 1000) + ChatColor.AQUA + " Diamant(en)");
		lore.add(ChatColor.DARK_GRAY + itemid);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

}

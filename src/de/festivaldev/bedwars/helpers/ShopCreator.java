package de.festivaldev.bedwars.helpers;

import de.festivaldev.bedwars.values.Itemvalues;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Fabian on 01.04.2016.
 */
public class ShopCreator {

	private static String[] categories = {"Blöcke", "Spitzhacken", "Waffen", "Bögen", "Kleidung", "Essen", "Kisten und Maschinen", "Tränke", "Spezial"};

	private static String[] cat1 = {"1:1", "1:2", "1:3", "1:4", "1:5", "1:6", "1:7"};
	private static String[] cat2 = {"2:1", "2:2", "2:3", "2:4"};
	private static String[] cat3 = {"3:1", "3:2", "3:3", "3:4", "3:5"};
	private static String[] cat4 = {"4:1", "4:2", "4:3", "4:4"};
	private static String[] cat5 = {"5:1", "5:2", "5:3", "5:4", "5:5", "5:6", "5:7", "5:8"};
	private static String[] cat6 = {"6:1", "6:2", "6:3", "6:4"};
	private static String[] cat7 = {"7:1", "7:2", "7:3"};
	private static String[] cat8 = {"8:1", "8:2", "8:3", "8:4", "8:5"};
	private static String[] cat9 = {"9:1", "9:2", "9:3", "9:4", "9:5", "9:6"};

	public static void openShop(Player player) {

		Inventory inventory = Bukkit.createInventory(player, 9, "Shop");

		ItemStack[] cat = {
			ItemHelper.createItem(Material.SANDSTONE, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Blöcke", null),
			ItemHelper.createItem(Material.IRON_PICKAXE, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Spitzhacken", null),
			ItemHelper.createItem(Material.IRON_SWORD, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Waffen", null),
			ItemHelper.createItem(Material.BOW, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Bögen", null),
			ItemHelper.createItem(Material.CHAINMAIL_CHESTPLATE, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Kleidung", null),
			ItemHelper.createItem(Material.GRILLED_PORK, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Essen", null),
			ItemHelper.createItem(Material.CHEST, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD + "Kisten und Maschinen", null),
			ItemHelper.createItem(Material.POTION, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Tränke", null),
			ItemHelper.createItem(Material.NETHER_STAR, 1, (short) 0, (byte) 0, ChatColor.RESET + "" + ChatColor.GOLD +"Spezial", null)
		};

		for (int i = 0; i < cat.length; i++) {
			inventory.setItem(i, cat[i]);
		}

		player.openInventory(inventory);

	}

	@SuppressWarnings("deprecation")
	public static void openCategory(Player player, int cat) {

		Inventory inventory = Bukkit.createInventory(player, 27, "Shop > " + categories[cat]);

		ItemStack blackPane = ItemHelper.setName(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()), "/", ChatColor.BLACK);
		ItemStack redPane = ItemHelper.setName(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Du kannst diesen Artikel nicht kaufen", ChatColor.RED);
		ItemStack greenPane = ItemHelper.setName(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData()), "Du kannst diesen Artikel kaufen", ChatColor.GREEN);

		switch (cat + 1) {
			case 1:
				inventory.setItem(0, ItemHelper.getItem(cat1[0]));
				inventory.setItem(1, ItemHelper.getItem(cat1[1]));
				inventory.setItem(2, ItemHelper.getItem(cat1[2]));
				inventory.setItem(3, ItemHelper.getItem(cat1[3]));
				inventory.setItem(4, blackPane);
				inventory.setItem(5, ItemHelper.getItem(cat1[4]));
				inventory.setItem(6, ItemHelper.getItem(cat1[5]));
				inventory.setItem(7, blackPane);
				inventory.setItem(8, ItemHelper.getItem(cat1[6]));

				ArrayList<Integer> prices1 = getPrices(cat1);
				ArrayList<ItemStack> priceinfo1 = new ArrayList<>();
				for (int p: prices1) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo1.add(greenPane);
						} else {
							priceinfo1.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo1.add(greenPane);
						} else {
							priceinfo1.add(redPane);
						}
					}
				}

				inventory.setItem(9, priceinfo1.get(0));
				inventory.setItem(10, priceinfo1.get(1));
				inventory.setItem(11, priceinfo1.get(2));
				inventory.setItem(12, priceinfo1.get(3));
				inventory.setItem(13, blackPane);
				inventory.setItem(14, priceinfo1.get(4));
				inventory.setItem(15, priceinfo1.get(5));
				inventory.setItem(16, blackPane);
				inventory.setItem(17, priceinfo1.get(6));

				inventory.setItem(18, ItemHelper.createBuyItem(getPrice(cat1[0]), cat1[0]));
				inventory.setItem(19, ItemHelper.createBuyItem(getPrice(cat1[1]), cat1[1]));
				inventory.setItem(20, ItemHelper.createBuyItem(getPrice(cat1[2]), cat1[2]));
				inventory.setItem(21, ItemHelper.createBuyItem(getPrice(cat1[3]), cat1[3]));
				inventory.setItem(22, blackPane);
				inventory.setItem(23, ItemHelper.createBuyItem(getPrice(cat1[4]), cat1[4]));
				inventory.setItem(24, ItemHelper.createBuyItem(getPrice(cat1[5]), cat1[5]));
				inventory.setItem(25, blackPane);
				inventory.setItem(26, ItemHelper.createBuyItem(getPrice(cat1[6]), cat1[6]));

				break;
			case 2:
				inventory.setItem(0, blackPane);
				inventory.setItem(1, ItemHelper.getItem(cat2[0]));
				inventory.setItem(2, blackPane);
				inventory.setItem(3, ItemHelper.getItem(cat2[1]));
				inventory.setItem(4, blackPane);
				inventory.setItem(5, ItemHelper.getItem(cat2[2]));
				inventory.setItem(6, blackPane);
				inventory.setItem(7, ItemHelper.getItem(cat2[3]));
				inventory.setItem(8, blackPane);

				ArrayList<Integer> prices2 = getPrices(cat2);
				ArrayList<ItemStack> priceinfo2 = new ArrayList<>();
				for (int p: prices2) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo2.add(greenPane);
						} else {
							priceinfo2.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo2.add(greenPane);
						} else {
							priceinfo2.add(redPane);
						}
					}
				}

				inventory.setItem(9, blackPane);
				inventory.setItem(10, priceinfo2.get(0));
				inventory.setItem(11, blackPane);
				inventory.setItem(12, priceinfo2.get(1));
				inventory.setItem(13, blackPane);
				inventory.setItem(14, priceinfo2.get(2));
				inventory.setItem(15, blackPane);
				inventory.setItem(16, priceinfo2.get(3));
				inventory.setItem(17, blackPane);

				inventory.setItem(18, blackPane);
				inventory.setItem(19, ItemHelper.createBuyItem(getPrice(cat2[0]), cat2[0]));
				inventory.setItem(20, blackPane);
				inventory.setItem(21, ItemHelper.createBuyItem(getPrice(cat2[1]), cat2[1]));
				inventory.setItem(22, blackPane);
				inventory.setItem(23, ItemHelper.createBuyItem(getPrice(cat2[2]), cat2[2]));
				inventory.setItem(24, blackPane);
				inventory.setItem(25, ItemHelper.createOptionalBuyItem(getPrice(cat2[3]), cat2[3]));
				inventory.setItem(26, blackPane);

				break;
			case 3:
				inventory.setItem(0, ItemHelper.getItem(cat3[0]));
				inventory.setItem(1, blackPane);
				inventory.setItem(2, ItemHelper.getItem(cat3[1]));
				inventory.setItem(3, blackPane);
				inventory.setItem(4, ItemHelper.getItem(cat3[2]));
				inventory.setItem(5, blackPane);
				inventory.setItem(6, ItemHelper.getItem(cat3[3]));
				inventory.setItem(7, blackPane);
				inventory.setItem(8, ItemHelper.getItem(cat3[4]));

				ArrayList<Integer> prices3 = getPrices(cat3);
				ArrayList<ItemStack> priceinfo3 = new ArrayList<>();
				for (int p: prices3) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo3.add(greenPane);
						} else {
							priceinfo3.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo3.add(greenPane);
						} else {
							priceinfo3.add(redPane);
						}
					}
				}

				inventory.setItem(9, priceinfo3.get(0));
				inventory.setItem(10, blackPane);
				inventory.setItem(11, priceinfo3.get(1));
				inventory.setItem(12, blackPane);
				inventory.setItem(13, priceinfo3.get(2));
				inventory.setItem(14, blackPane);
				inventory.setItem(15, priceinfo3.get(3));
				inventory.setItem(16, blackPane);
				inventory.setItem(17, priceinfo3.get(4));

				inventory.setItem(18, ItemHelper.createBuyItem(getPrice(cat3[0]), cat3[0]));
				inventory.setItem(19, blackPane);
				inventory.setItem(20, ItemHelper.createBuyItem(getPrice(cat3[1]), cat3[1]));
				inventory.setItem(21, blackPane);
				inventory.setItem(22, ItemHelper.createBuyItem(getPrice(cat3[2]), cat3[2]));
				inventory.setItem(23, blackPane);
				inventory.setItem(24, ItemHelper.createBuyItem(getPrice(cat3[3]), cat3[3]));
				inventory.setItem(25, blackPane);
				inventory.setItem(26, ItemHelper.createOptionalBuyItem(getPrice(cat3[4]), cat3[4]));

				break;
			case 4:
				inventory.setItem(0, blackPane);
				inventory.setItem(1, ItemHelper.getItem(cat4[0]));
				inventory.setItem(2, blackPane);
				inventory.setItem(3, ItemHelper.getItem(cat4[1]));
				inventory.setItem(4, blackPane);
				inventory.setItem(5, ItemHelper.getItem(cat4[2]));
				inventory.setItem(6, blackPane);
				inventory.setItem(7, ItemHelper.getItem(cat4[3]));
				inventory.setItem(8, blackPane);

				ArrayList<Integer> prices4 = getPrices(cat4);
				ArrayList<ItemStack> priceinfo4 = new ArrayList<>();
				for (int p: prices4) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo4.add(greenPane);
						} else {
							priceinfo4.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo4.add(greenPane);
						} else {
							priceinfo4.add(redPane);
						}
					}
				}

				inventory.setItem(9, blackPane);
				inventory.setItem(10, priceinfo4.get(0));
				inventory.setItem(11, blackPane);
				inventory.setItem(12, priceinfo4.get(1));
				inventory.setItem(13, blackPane);
				inventory.setItem(14, priceinfo4.get(2));
				inventory.setItem(15, blackPane);
				inventory.setItem(16, priceinfo4.get(3));
				inventory.setItem(17, blackPane);

				inventory.setItem(18, blackPane);
				inventory.setItem(19, ItemHelper.createBuyItem(getPrice(cat4[0]), cat4[0]));
				inventory.setItem(20, blackPane);
				inventory.setItem(21, ItemHelper.createBuyItem(getPrice(cat4[1]), cat4[1]));
				inventory.setItem(22, blackPane);
				inventory.setItem(23, ItemHelper.createOptionalBuyItem(getPrice(cat4[2]), cat4[2]));
				inventory.setItem(24, blackPane);
				inventory.setItem(25, ItemHelper.createBuyItem(getPrice(cat4[3]), cat4[3]));
				inventory.setItem(26, blackPane);

				break;
			case 5:
				inventory.setItem(0, ItemHelper.getItem(cat5[0]));
				inventory.setItem(1, ItemHelper.getItem(cat5[1]));
				inventory.setItem(2, ItemHelper.getItem(cat5[2]));
				inventory.setItem(3, ItemHelper.getItem(cat5[3]));
				inventory.setItem(4, blackPane);
				inventory.setItem(5, ItemHelper.getItem(cat5[4]));
				inventory.setItem(6, ItemHelper.getItem(cat5[5]));
				inventory.setItem(7, ItemHelper.getItem(cat5[6]));
				inventory.setItem(8, ItemHelper.getItem(cat5[7]));

				ArrayList<Integer> prices5 = getPrices(cat5);
				ArrayList<ItemStack> priceinfo5 = new ArrayList<>();
				for (int p: prices5) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo5.add(greenPane);
						} else {
							priceinfo5.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo5.add(greenPane);
						} else {
							priceinfo5.add(redPane);
						}
					}
				}

				inventory.setItem(9, priceinfo5.get(0));
				inventory.setItem(10, priceinfo5.get(1));
				inventory.setItem(11, priceinfo5.get(2));
				inventory.setItem(12, priceinfo5.get(3));
				inventory.setItem(13, blackPane);
				inventory.setItem(14, priceinfo5.get(4));
				inventory.setItem(15, priceinfo5.get(5));
				inventory.setItem(16, priceinfo5.get(6));
				inventory.setItem(17, priceinfo5.get(7));

				inventory.setItem(18, ItemHelper.createBuyItem(getPrice(cat5[0]), cat5[0]));
				inventory.setItem(19, ItemHelper.createBuyItem(getPrice(cat5[1]), cat5[1]));
				inventory.setItem(20, ItemHelper.createBuyItem(getPrice(cat5[2]), cat5[2]));
				inventory.setItem(21, ItemHelper.createBuyItem(getPrice(cat5[3]), cat5[3]));
				inventory.setItem(22, blackPane);
				inventory.setItem(23, ItemHelper.createBuyItem(getPrice(cat5[4]), cat5[4]));
				inventory.setItem(24, ItemHelper.createBuyItem(getPrice(cat5[5]), cat5[5]));
				inventory.setItem(25, ItemHelper.createBuyItem(getPrice(cat5[6]), cat5[6]));
				inventory.setItem(26, ItemHelper.createOptionalBuyItem(getPrice(cat5[7]), cat5[7]));

				break;
			case 6:
				inventory.setItem(0, blackPane);
				inventory.setItem(1, ItemHelper.getItem(cat6[0]));
				inventory.setItem(2, blackPane);
				inventory.setItem(3, ItemHelper.getItem(cat6[1]));
				inventory.setItem(4, blackPane);
				inventory.setItem(5, ItemHelper.getItem(cat6[2]));
				inventory.setItem(6, blackPane);
				inventory.setItem(7, ItemHelper.getItem(cat6[3]));
				inventory.setItem(8, blackPane);

				ArrayList<Integer> prices6 = getPrices(cat6);
				ArrayList<ItemStack> priceinfo6 = new ArrayList<>();
				for (int p: prices6) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo6.add(greenPane);
						} else {
							priceinfo6.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo6.add(greenPane);
						} else {
							priceinfo6.add(redPane);
						}
					}
				}

				inventory.setItem(9, blackPane);
				inventory.setItem(10, priceinfo6.get(0));
				inventory.setItem(11, blackPane);
				inventory.setItem(12, priceinfo6.get(1));
				inventory.setItem(13, blackPane);
				inventory.setItem(14, priceinfo6.get(2));
				inventory.setItem(15, blackPane);
				inventory.setItem(16, priceinfo6.get(3));
				inventory.setItem(17, blackPane);

				inventory.setItem(18, blackPane);
				inventory.setItem(19, ItemHelper.createBuyItem(getPrice(cat6[0]), cat6[0]));
				inventory.setItem(20, blackPane);
				inventory.setItem(21, ItemHelper.createBuyItem(getPrice(cat6[1]), cat6[1]));
				inventory.setItem(22, blackPane);
				inventory.setItem(23, ItemHelper.createBuyItem(getPrice(cat6[2]), cat6[2]));
				inventory.setItem(24, blackPane);
				inventory.setItem(25, ItemHelper.createBuyItem(getPrice(cat6[3]), cat6[3]));
				inventory.setItem(26, blackPane);

				break;
			case 7:
				inventory.setItem(0, blackPane);
				inventory.setItem(1, blackPane);
				inventory.setItem(2, ItemHelper.getItem(cat7[0]));
				inventory.setItem(3, blackPane);
				inventory.setItem(4, ItemHelper.getItem(cat7[1]));
				inventory.setItem(5, blackPane);
				inventory.setItem(6, ItemHelper.getItem(cat7[2]));
				inventory.setItem(7, blackPane);
				inventory.setItem(8, blackPane);

				ArrayList<Integer> prices7 = getPrices(cat7);
				ArrayList<ItemStack> priceinfo7 = new ArrayList<>();
				for (int p: prices7) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo7.add(greenPane);
						} else {
							priceinfo7.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo7.add(greenPane);
						} else {
							priceinfo7.add(redPane);
						}
					}
				}

				inventory.setItem(9, blackPane);
				inventory.setItem(10, blackPane);
				inventory.setItem(11, priceinfo7.get(0));
				inventory.setItem(12, blackPane);
				inventory.setItem(13, priceinfo7.get(1));
				inventory.setItem(14, blackPane);
				inventory.setItem(15, priceinfo7.get(2));
				inventory.setItem(16, blackPane);
				inventory.setItem(17, blackPane);

				inventory.setItem(18, blackPane);
				inventory.setItem(19, blackPane);
				inventory.setItem(20, ItemHelper.createBuyItem(getPrice(cat7[0]), cat7[0]));
				inventory.setItem(21, blackPane);
				inventory.setItem(22, ItemHelper.createBuyItem(getPrice(cat7[1]), cat7[1]));
				inventory.setItem(23, blackPane);
				inventory.setItem(24, ItemHelper.createOptionalBuyItem(getPrice(cat7[2]), cat7[2]));
				inventory.setItem(25, blackPane);
				inventory.setItem(26, blackPane);

				break;
			case 8:
				inventory.setItem(0, blackPane);
				inventory.setItem(1, ItemHelper.getItem(cat8[0]));
				inventory.setItem(2, blackPane);
				inventory.setItem(3, ItemHelper.getItem(cat8[1]));
				inventory.setItem(4, ItemHelper.getItem(cat8[2]));
				inventory.setItem(5, blackPane);
				inventory.setItem(6, ItemHelper.getItem(cat8[3]));
				inventory.setItem(7, ItemHelper.getItem(cat8[4]));
				inventory.setItem(8, blackPane);

				ArrayList<Integer> prices8 = getPrices(cat8);
				ArrayList<ItemStack> priceinfo8 = new ArrayList<>();
				for (int p: prices8) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo8.add(greenPane);
						} else {
							priceinfo8.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo8.add(greenPane);
						} else {
							priceinfo8.add(redPane);
						}
					}
				}

				inventory.setItem(9, blackPane);
				inventory.setItem(10, priceinfo8.get(0));
				inventory.setItem(11, blackPane);
				inventory.setItem(12, priceinfo8.get(1));
				inventory.setItem(13, priceinfo8.get(2));
				inventory.setItem(14, blackPane);
				inventory.setItem(15, priceinfo8.get(3));
				inventory.setItem(16, priceinfo8.get(4));
				inventory.setItem(17, blackPane);

				inventory.setItem(18, blackPane);
				inventory.setItem(19, ItemHelper.createBuyItem(getPrice(cat8[0]), cat8[0]));
				inventory.setItem(20, blackPane);
				inventory.setItem(21, ItemHelper.createBuyItem(getPrice(cat8[1]), cat8[1]));
				inventory.setItem(22, ItemHelper.createOptionalBuyItem(getPrice(cat8[2]), cat8[2]));
				inventory.setItem(23, blackPane);
				inventory.setItem(24, ItemHelper.createBuyItem(getPrice(cat8[3]), cat8[3]));
				inventory.setItem(25, ItemHelper.createOptionalBuyItem(getPrice(cat8[4]), cat8[4]));
				inventory.setItem(26, blackPane);

				break;
			case 9:
				inventory.setItem(0, ItemHelper.getItem(cat9[0]));
				inventory.setItem(1, blackPane);
				inventory.setItem(2, ItemHelper.getItem(cat9[1]));
				inventory.setItem(3, ItemHelper.getItem(cat9[2]));
				inventory.setItem(4, blackPane);
				inventory.setItem(5, ItemHelper.getItem(cat9[3]));
				inventory.setItem(6, ItemHelper.getItem(cat9[4]));
				inventory.setItem(7, blackPane);
				inventory.setItem(8, ItemHelper.getItem(cat9[5]));

				ArrayList<Integer> prices9 = getPrices(cat9);
				ArrayList<ItemStack> priceinfo9 = new ArrayList<>();
				for (int p: prices9) {
					if (p < 1000) {
						if (player.getLevel() >= p) {
							priceinfo9.add(greenPane);
						} else {
							priceinfo9.add(redPane);
						}
					} else {
						if (player.getInventory().containsAtLeast(getDiamond(), p - 1000)) {
							priceinfo9.add(greenPane);
						} else {
							priceinfo9.add(redPane);
						}
					}
				}

				inventory.setItem(9, priceinfo9.get(0));
				inventory.setItem(10, blackPane);
				inventory.setItem(11, priceinfo9.get(1));
				inventory.setItem(12, priceinfo9.get(2));
				inventory.setItem(13, blackPane);
				inventory.setItem(14, priceinfo9.get(3));
				inventory.setItem(15, priceinfo9.get(4));
				inventory.setItem(16, blackPane);
				inventory.setItem(17, priceinfo9.get(5));

				inventory.setItem(18, ItemHelper.createBuyItem(getPrice(cat9[0]), cat9[0]));
				inventory.setItem(19, blackPane);
				inventory.setItem(20, ItemHelper.createOptionalBuyItem(getPrice(cat9[1]), cat9[1]));
				inventory.setItem(21, ItemHelper.createBuyItem(getPrice(cat9[2]), cat9[2]));
				inventory.setItem(22, blackPane);
				inventory.setItem(23, ItemHelper.createBuyItem(getPrice(cat9[3]), cat9[3]));
				inventory.setItem(24, ItemHelper.createBuyItem(getPrice(cat9[4]), cat9[4]));
				inventory.setItem(25, blackPane);
				inventory.setItem(26, ItemHelper.createOptionalBuyItem(getPrice(cat9[5]), cat9[5]));

				break;
		}

		player.openInventory(inventory);

	}

	public static int getPrice(String itemid) {
		return Itemvalues.Prices.get(itemid);
	}

	public static ArrayList<Integer> getPrices(String[] cat) {
		ArrayList<Integer> prices = new ArrayList<>();
		for (String aCat : cat) {
			prices.add(getPrice(aCat));
		}
		return prices;
	}

	public static ItemStack getDiamond() {
		ItemStack is = new ItemStack(Material.DIAMOND, 1);
		is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		is.setItemMeta(im);
		return is;
	}

}

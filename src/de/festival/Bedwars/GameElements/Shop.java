package de.festival.Bedwars.GameElements;

import de.festival.Bedwars.Helper.ColoredGlass;
import de.festival.Bedwars.Helper.ShopItem;
import de.festival.Bedwars.Main;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("deprecation")
public class Shop implements Listener {
    public Inventory mainInv;
    public JSONObject shopData;
    private ArrayList<Inventory> inventories = new ArrayList<>();
    private ArrayList<ArrayList> shopItemsList = new ArrayList<>();

    private Main plugin;

    private int currentCategory = -1;

    public Shop(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        JSONParser parser = new JSONParser();
        try {
            //FileReader fReader = new FileReader(plugin.getDataFolder().getAbsolutePath()+"/shop.json");
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(plugin.getDataFolder().getAbsolutePath()+"/shop.json"), StandardCharsets.UTF_8);
            Object obj = parser.parse(fReader);

            JSONObject jsonObject = (JSONObject) obj;
            this.shopData = (jsonObject);
            //this.makeCategories();

            fReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info("Could not load Bedwars shop. Probably the shop.json is missing or something");
        }
    }

    private void makeCategories(Player player) {
        inventories.clear();
        JSONArray categories = (JSONArray)this.shopData.get("categories");

        for (int i=0; i<categories.size(); i++) {
            JSONObject currentCategory = (JSONObject)this.shopData.get(categories.get(i));
            JSONArray itemArray = (JSONArray)currentCategory.get("items");

            Inventory inventory = Bukkit.createInventory(null, 27, "Shop > " + currentCategory.get("title").toString());
            ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();

            for (int j=0; j<itemArray.size(); j++) {

                if (((JSONObject)itemArray.get(j)).get("id") != null) {
                    String itemObject = (String) ((JSONObject) itemArray.get(j)).get("id");
                    String itemName = (String) ((JSONObject) itemArray.get(j)).get("name");

                    boolean teamColored = false;
                    if (((JSONObject) itemArray.get(j)).get("team_color") != null) {
                        teamColored = (boolean)((JSONObject) itemArray.get(j)).get("team_color");
                    }

                    Material itemMaterial = Material.getMaterial(itemObject.toUpperCase());

                    ArrayList<String> lore = new ArrayList<String>();

                    if (((JSONObject) itemArray.get(j)).get("description") != null) {
                        lore.add(ChatColor.GRAY + ((JSONObject) itemArray.get(j)).get("description").toString());
                    }

                    lore.add("");

                    boolean isSpecial = false;
                    int itemPrice = 0;
                    if (((JSONObject) itemArray.get(j)).get("price") != null) {
                        itemPrice = Integer.parseInt(((JSONObject) itemArray.get(j)).get("price").toString());
                        isSpecial = false;
                    }

                    int itemPriceDiamonds = 0;
                    if (((JSONObject) itemArray.get(j)).get("price_diamond") != null) {
                        itemPriceDiamonds = Integer.parseInt(((JSONObject) itemArray.get(j)).get("price_diamond").toString());
                        isSpecial = true;
                    }

                    int potionID = 0;
                    if (((JSONObject) itemArray.get(j)).get("potion_data") != null) {
                        potionID = Integer.parseInt(((JSONObject) itemArray.get(j)).get("potion_data").toString());
                    }

                    ShopItem shopItem = new ShopItem(itemName, player, this.plugin, lore, itemMaterial, (!isSpecial ? itemPrice : itemPriceDiamonds), isSpecial, teamColored, potionID);
                    shopItems.add(shopItem);

                    inventory.setItem(j, shopItem.getItemStack());
                    inventory.setItem(j+9, shopItem.playerCanPurchase(player));

                    /*int itemPrice = 0;
                    if (((JSONObject) itemArray.get(j)).get("price") != null) {
                        itemPrice = Integer.parseInt(((JSONObject) itemArray.get(j)).get("price").toString());
                    }

                    int itemPriceDiamonds = 0;
                    if (((JSONObject) itemArray.get(j)).get("price_diamond") != null) {
                        itemPriceDiamonds = Integer.parseInt(((JSONObject) itemArray.get(j)).get("price_diamond").toString());
                    }

                    Material itemMaterial = Material.getMaterial(itemObject.toUpperCase());
                    ItemStack is = new ItemStack(itemMaterial, 1, (short) 0, (byte) 0);
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + itemName);

                    List<String> lore = new ArrayList<String>();

                    if (((JSONObject) itemArray.get(j)).get("description") != null) {
                        lore.add(ChatColor.GRAY + ((JSONObject) itemArray.get(j)).get("description").toString());
                    }

                    lore.add("");

                    if (itemPrice > 0) {
                        lore.add("" + ChatColor.GOLD + itemPrice + " " + ChatColor.WHITE + "Ressourcen");

                        if (player.getLevel() >= itemPrice) {
                            inventory.setItem(j+9, new ColoredGlass().getColoredGlass(DyeColor.GREEN.getData(), ChatColor.GREEN + "Verfügbar"));
                        } else {
                            inventory.setItem(j+9, new ColoredGlass().getColoredGlass(DyeColor.RED.getData(), ChatColor.RED + "Nicht verfügbar"));
                        }
                    } else if (itemPriceDiamonds > 0) {
                        lore.add("" + ChatColor.AQUA + itemPriceDiamonds + " " + ChatColor.WHITE + ((itemPriceDiamonds > 1 ) ? "Diamanten" : "Diamant"));

                        if (player.getInventory().contains(Material.DIAMOND)) {
                            if (player.getInventory().getItem(player.getInventory().first(Material.DIAMOND)).getAmount() >= itemPriceDiamonds) {
                                inventory.setItem(j+9, new ColoredGlass().getColoredGlass(DyeColor.GREEN.getData(), ChatColor.GREEN + "Verfügbar"));
                            } else {
                                inventory.setItem(j+9, new ColoredGlass().getColoredGlass(DyeColor.RED.getData(), ChatColor.RED + "Nicht verfügbar"));
                            }
                        } else {
                            inventory.setItem(j+9, new ColoredGlass().getColoredGlass(DyeColor.RED.getData(), ChatColor.RED + "Nicht verfügbar"));
                        }
                    }



                    im.setLore(lore);
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                    is.setItemMeta(im);

                    inventory.setItem(j, is);*/
                } else {
                    inventory.setItem(j, new ColoredGlass().getColoredGlass(DyeColor.BLACK.getData(), " "));
                    inventory.setItem(j+9, new ColoredGlass().getColoredGlass(DyeColor.BLACK.getData(), " "));
                    shopItems.add(null);
                }
            }
            shopItemsList.add(shopItems);

            // Create back button

            for (int k=0; k<4; k++) {
                inventory.setItem(k+18, new ColoredGlass().getColoredGlass(DyeColor.BLACK.getData(), " "));
            }
            Material backButton = Material.BARRIER;
            ItemStack stack = new ItemStack(backButton, 1, (short) 0);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.RESET + "" + ChatColor.RED + "Zurück");
            stack.setItemMeta(meta);
            inventory.setItem(22, stack);
            for (int k=0; k<4; k++) {
                inventory.setItem(k+23, new ColoredGlass().getColoredGlass(DyeColor.BLACK.getData(), " "));
            }

            inventories.add(inventory);
        }
    }

    public void openShop(Player player) {
        JSONArray categories = (JSONArray)this.shopData.get("categories");
        this.currentCategory = -1;

        mainInv = Bukkit.createInventory(player, 9, "Shop");
        for (int i=0; i<categories.size(); i++) {
            JSONObject currentCategory = (JSONObject)this.shopData.get(categories.get(i));

            Material displayMaterial = Material.getMaterial(currentCategory.get("display_item").toString().toUpperCase());

            ItemStack is = new ItemStack(displayMaterial, 1, (short)0, (byte)0);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + currentCategory.get("title").toString());
            im.setLore(null);
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            is.setItemMeta(im);

            mainInv.setItem(i, is);
        }

        player.openInventory(mainInv);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClicked(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (event.getInventory().getName().startsWith("Shop")) {


            if (event.getCurrentItem() != null && !(event.getClickedInventory() instanceof CraftInventoryPlayer)) {
                event.setCancelled(true);
                if (this.currentCategory < 0) {
                    this.makeCategories(player);
                    player.openInventory(this.inventories.get(event.getRawSlot()));
                    this.currentCategory = event.getRawSlot();
                } else if (currentCategory >= 0 && event.getCurrentItem().getType() != Material.BARRIER) {
                    ArrayList<ShopItem> itemsToBuy = shopItemsList.get(this.currentCategory);
                    if (event.getRawSlot() >= 0 && event.getRawSlot() < itemsToBuy.size() && itemsToBuy.get(event.getRawSlot()) != null) {
                        ShopItem item = (ShopItem)shopItemsList.get(this.currentCategory).get(event.getRawSlot());
                        if (item.purchase(player)) {
                            this.makeCategories(player);
                            player.openInventory(this.inventories.get(this.currentCategory));
                            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
                        } else {
                            player.sendMessage(ChatColor.RED + "Du hast nicht genug Ressourcen");
                            player.playNote(player.getLocation(), Instrument.BASS_GUITAR, Note.flat(0, Note.Tone.C));
                        }

                    }
                } else if (currentCategory >= 0 && event.getCurrentItem().getType() == Material.BARRIER) {
                    player.openInventory(mainInv);
                    this.currentCategory = -1;
                }
            }
        }
    }
}

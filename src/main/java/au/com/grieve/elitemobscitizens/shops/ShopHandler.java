package au.com.grieve.elitemobscitizens.shops;


import com.magmaguy.elitemobs.commands.shops.SharedShopElements;
import com.magmaguy.elitemobs.config.ConfigValues;
import com.magmaguy.elitemobs.config.EconomySettingsConfig;
import com.magmaguy.elitemobs.config.MobCombatSettingsConfig;
import com.magmaguy.elitemobs.items.ItemWorthCalculator;
import com.magmaguy.elitemobs.items.itemconstructor.ItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates a shop based upon a provided seed.
 * Items are generated between min-max tier
 * Items are ordered by cost
 */

public class ShopHandler implements Listener {

    private Inventory shopInventory;
    private String name;
    private int size;
    private int minTier;
    private int maxTier;
    private JavaPlugin plugin;
    private List<ItemStack> items = new ArrayList<>();

    public ShopHandler(String name, int size, int minTier, int maxTier, JavaPlugin plugin) {
        this.name = name;
        this.size = size;
        this.minTier = minTier;
        this.maxTier = maxTier;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        populateShop();
    }

    public void open(Player player) {
        shopInventory = Bukkit.createInventory(player, size, name);
        for(int i =0; i < size; i++) {
            shopInventory.setItem(i, items.get(i));
        }
        player.openInventory(shopInventory);
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
        plugin = null;
    }

    private void populateShop() {
        Random random = new Random();

        // Generate Items
        for(int i = 0; i < size; i++) {
            int itemTier = random.nextInt(maxTier-minTier) + minTier;

            items.add(ItemConstructor.constructItem(itemTier, null));
        }

        // Order by Cost
        items.sort(Comparator.comparingDouble(ItemWorthCalculator::determineItemWorth));
    }

    // Handle clicks in Shop
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().getName().equals(name)) {
            return;
        }

        if (!SharedShopElements.inventoryNullPointerPreventer(event)) {
            return;
        }

        event.setCancelled(true);
    }

    // Handle Close
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!event.getInventory().getName().equals(name)) {
            return;
        }
    }



}

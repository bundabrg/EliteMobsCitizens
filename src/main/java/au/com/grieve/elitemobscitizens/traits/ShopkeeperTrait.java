package au.com.grieve.elitemobscitizens.traits;

import au.com.grieve.elitemobscitizens.EliteMobsCitizens;
import au.com.grieve.elitemobscitizens.shops.ShopHandler;
import com.magmaguy.elitemobs.config.ConfigValues;
import com.magmaguy.elitemobs.config.ItemsDropSettingsConfig;
import lombok.Getter;
import lombok.Setter;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public class ShopkeeperTrait extends Trait {

    final private EliteMobsCitizens plugin;
    private ShopHandler shop;
    private int taskId;

    // Settings

    @Getter @Setter private int minSize;
    @Getter @Setter private int maxSize;
    @Getter @Setter private long updateTicks;
    @Getter @Setter private int minTier;
    @Getter @Setter private int maxTier;
    @Getter @Setter private boolean enabled;
    @Getter @Setter private String shopName = "Shop";

    public ShopkeeperTrait() {
        super("emcs");
        this.plugin = JavaPlugin.getPlugin(EliteMobsCitizens.class);
    }

    public void destroyShop() {
        if (shop != null) {
            shop.destroy();
        }
        shop = null;
    }

    public void refreshShop() {
        destroyShop();
        shop = new ShopHandler(shopName, Math.max(1, ThreadLocalRandom.current().nextInt(Math.max(1,maxSize - minSize)) + minSize), minTier, maxTier, plugin);

    }

    public void load(DataKey key) {
        shopName = key.getString("name", "Shop");
        minSize = key.getInt("minSize", 54);
        maxSize = key.getInt("maxSize", 54);
        updateTicks = key.getLong("updateTicks", 6000);
        minTier = key.getInt("minTier", 0);
        maxTier = key.getInt("maxTier", (int) Math.round(ConfigValues.itemsDropSettingsConfig.getDouble(ItemsDropSettingsConfig.MAXIMUM_LOOT_TIER)));
        enabled  = key.getBoolean("enabled", true);
    }

    public void save(DataKey key) {
        key.setString("name", shopName);
        key.setInt("minSize", minSize);
        key.setInt("maxSize", maxSize);
        key.setLong("updateTicks", updateTicks);
        key.setInt("minTier", minTier);
        key.setInt("maxTier", maxTier);
        key.setBoolean("enabled", enabled);
    }

    // Run when trait attached to NPC
    @Override
    public void onAttach() {
    }

    // Run when despawned
    @Override
    public void onDespawn() {
        plugin.getServer().getScheduler().cancelTask(taskId);
        destroyShop();
    }

    // When when spawned
    @Override
    public void onSpawn() {
        refreshShop();

        // Update Shop every 6000 ticks
        long delay = getNPC().getStoredLocation().getWorld().getTime() - 24000;
        taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, this::refreshShop, delay, updateTicks);
    }

    // When when NPC is removed
    @Override
    public void onRemove() {
    }

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if (event.getNPC() != this.getNPC()) {
            return;
        }

        // Shop Enabled?
        if (!enabled) {
            return;
        }

        // Open Shop
        shop.open(event.getClicker());
    }


}

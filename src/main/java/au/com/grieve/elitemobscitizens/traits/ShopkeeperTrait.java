package au.com.grieve.elitemobscitizens.traits;

import au.com.grieve.elitemobscitizens.EliteMobsCitizens;
import au.com.grieve.elitemobscitizens.shops.ShopHandler;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopkeeperTrait extends Trait {

    final private EliteMobsCitizens plugin;
    private ShopHandler shop;
    private int taskId;

    public ShopkeeperTrait() {
        super("emshopkeeper");
        this.plugin = JavaPlugin.getPlugin(EliteMobsCitizens.class);
    }

    public void refreshShop() {
        shop = new ShopHandler("SuperShop", 54, 0, 100, plugin);
    }

    public void load(DataKey key) {
    }

    public void save(DataKey key) {
    }

    // Run when trait attached to NPC
    @Override
    public void onAttach() {
    }

    // Run when despawned
    @Override
    public void onDespawn() {
        plugin.getServer().getScheduler().cancelTask(taskId);
        if (shop != null) {
            shop.destroy();
            shop = null;
        }
    }

    // When when spawned
    @Override
    public void onSpawn() {
        refreshShop();

        // Update Shop every 6000 ticks
        long delay = getNPC().getStoredLocation().getWorld().getTime() - 24000;
        taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, this::refreshShop, delay, 6000);
    }

    // When when NPC is removed
    @Override
    public void onRemove() {
    }

    @EventHandler
    public void onClick(NPCLeftClickEvent event) {
        if (event.getNPC() != this.getNPC()) {
            return;
        }
        event.getClicker().sendMessage("You clicked me with the left button");
    }

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if (event.getNPC() != this.getNPC()) {
            return;
        }
        event.getClicker().sendMessage("You clicked me with the right button");

        // Open Shop
        shop.open(event.getClicker());

    }

}

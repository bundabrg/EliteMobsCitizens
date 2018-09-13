package au.com.grieve.elitemobscitizens;

import au.com.grieve.elitemobscitizens.commands.EMCSCommand;
import au.com.grieve.elitemobscitizens.traits.ShopkeeperTrait;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class EliteMobsCitizens extends JavaPlugin {

    // Variables
    @Getter
    private PaperCommandManager manager;

    @Override
    public void onEnable() {

        // Register with Citizens
        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
//        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(ShopkeeperTrait.class));

        // Register your trait with Citizens.
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(ShopkeeperTrait.class).withName("emshopkeeper"));

        // Register Listeners
//        getServer().getPluginManager().registerEvents(new ShopkeeperListener(this), this);

        // Register Commands
        registerCommands();

        // Register Events


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("help");


        manager.registerCommand(new EMCSCommand());

    }
}

package au.com.grieve.elitemobscitizens;

import au.com.grieve.elitemobscitizens.commands.EMCSCommand;
import au.com.grieve.elitemobscitizens.thirdparty.npcdestinations.EMC_Plugin;
import au.com.grieve.elitemobscitizens.traits.ShopkeeperTrait;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.livecar.nuttyworks.npc_destinations.DestinationsPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public final class EliteMobsCitizens extends JavaPlugin {

    // Variables
    @Getter
    private PaperCommandManager manager;
    private EMC_Plugin emcPlugin;

    @Override
    public void onEnable() {

        // Register with Citizens
        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register your trait with Citizens.
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(ShopkeeperTrait.class).withName("emcs"));

        // Register with NPCDestinations
        if (getServer().getPluginManager().getPlugin("NPC_Destinations") != null) {
            getLogger().log(Level.INFO, "NPC_Destinations found.");
            this.emcPlugin = new EMC_Plugin(this);

            // Export Language File
            try {
                Path target = DestinationsPlugin.Instance.languagePath.toPath().resolve("en_def-npcdestlang.yml");
                if (Files.exists(target)) {
                    Files.delete(target);
                }
                Files.copy(getResource("en_def-npcdestlang.yml"), target);
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }

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

package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import au.com.grieve.elitemobscitizens.EliteMobsCitizens;
import au.com.grieve.elitemobscitizens.traits.ShopkeeperTrait;
import lombok.Getter;
import net.citizensnpcs.api.npc.NPC;
import net.livecar.nuttyworks.npc_destinations.DestinationsPlugin;

import java.util.Date;

public class EMC_Plugin {
    @Getter
    private final EliteMobsCitizens plugin;
    private final EMC_Addon addon;

    public EMC_Plugin(EliteMobsCitizens plugin) {
        this.plugin = plugin;
        this.addon = new EMC_Addon(this);
        DestinationsPlugin.Instance.getPluginManager.registerPlugin(addon);
        DestinationsPlugin.Instance.getCommandManager.registerCommandClass(EMC_Commands.class);
    }

    public EMC_LocationSettings getCurrentSettings(NPC npc) {
        if (!npc.hasTrait(ShopkeeperTrait.class)) {
            return null;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);
        EMC_LocationSettings currentSettings = new EMC_LocationSettings();

        currentSettings.setLastSet(new Date());
        currentSettings.setMinSize(shopTrait.getMinSize());
        currentSettings.setMaxSize(shopTrait.getMaxSize());
        currentSettings.setMinTier(shopTrait.getMinTier());
        currentSettings.setMaxTier(shopTrait.getMaxTier());
        currentSettings.setUpdateTicks(shopTrait.getUpdateTicks());
        currentSettings.setEnabled(shopTrait.isEnabled());

        return currentSettings;
    }

    public void setCurrentSettings(NPC npc, EMC_LocationSettings settings) {
        if (!npc.hasTrait(ShopkeeperTrait.class)) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);
        shopTrait.setMinSize(settings.getMinSize());
        shopTrait.setMaxSize(settings.getMaxSize());
        shopTrait.setMinTier(settings.getMinTier());
        shopTrait.setMaxTier(settings.getMaxTier());
        shopTrait.setUpdateTicks(settings.getUpdateTicks());
        shopTrait.setEnabled(settings.isEnabled());
    }
}

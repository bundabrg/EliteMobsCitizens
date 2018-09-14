package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import lombok.Getter;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.DataKey;
import net.livecar.nuttyworks.npc_destinations.api.Destination_Setting;
import net.livecar.nuttyworks.npc_destinations.citizens.NPCDestinationsTrait;
import net.livecar.nuttyworks.npc_destinations.plugins.DestinationsAddon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EMC_Addon extends DestinationsAddon {
    @Getter
    private final EMC_Plugin pluginReference;

    @Getter
    private Map<Integer, EMC_NPCSettings> npcSettings = new HashMap<>();

    public EMC_Addon(EMC_Plugin instanceRef) {
        this.pluginReference = instanceRef;
    }

    @Override
    public String getPluginIcon() {
        return "ṁ";
    }

    @Override
    public String getActionName() {
        return "emcs";
    }

    @Override
    public String getQuickDescription() {
        return new TextComponent(new ComponentBuilder("EliteMobsCitizens Plugin\n").color(ChatColor.GOLD)
                .append("Usage").color(ChatColor.YELLOW).append(":\n").color(ChatColor.DARK_AQUA)
                .append("  /nd locemcs <loc#> <set/clear> ").color(ChatColor.WHITE).append("--npc #\n").color(ChatColor.GOLD)
                .append("Description").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                .append("  Set/Clear EMC Shopkeeper settings per location\n").color(ChatColor.WHITE)
                .append("    set ").color(ChatColor.GRAY).append("- Sets the current EMCS settings for this location\n").color(ChatColor.WHITE)
                .append("    clear ").color(ChatColor.GRAY).append("- Clears the settings for this location\n").color(ChatColor.WHITE)
                .append("Permission").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                .append("  npcdestinations.edit<own/all>.locemcs\n").color(ChatColor.WHITE).create()).toLegacyText();
    }

    @Override
    public String getDestinationHelp(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location) {
        String locationActive = "Not Active";
        String size = "";
        String tier = "";
        String enabled = "";

        if (npcSettings.containsKey(npc.getId()) && npcSettings.get(npc.getId()).getLocations().containsKey(location.LocationIdent)) {
            EMC_LocationSettings locSettings = npcSettings.get(npc.getId()).getLocations().get(location.LocationIdent);
            if (locSettings.getLastSet().getTime() == 0) {
                locationActive = "No Settings";
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                locationActive = format.format(locSettings.getLastSet());
                size = locSettings.getMinSize() + " - " + locSettings.getMaxSize();
                tier = locSettings.getMinTier() + " - " + locSettings.getMaxTier();
                enabled = locSettings.isEnabled()?"Enabled":"Disabled";
            }
        }

        return "," + ComponentSerializer.toString(new ComponentBuilder(getPluginIcon()).color(ChatColor.GREEN)
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/npcdest locemcs --npc " + npc.getId() + " " + npcTrait.NPCLocations.indexOf(location) + " "))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("                EMCS Settings\n").color(ChatColor.GOLD)
                                .append("Current Settings\n").color(ChatColor.YELLOW)
                                .append("  Active: ").color(ChatColor.GRAY).append(locationActive + "\n").color(ChatColor.WHITE)
                                .append("  Shop Enabled: ").color(ChatColor.GRAY).append(enabled + "\n").color(ChatColor.WHITE)
                                .append("  Shop Size: ").color(ChatColor.GRAY).append(size + "\n").color(ChatColor.WHITE)
                                .append("  Shop Tier: ").color(ChatColor.GRAY).append(tier + "\n").color(ChatColor.WHITE)
                                .append("Usage").color(ChatColor.YELLOW).append(":\n").color(ChatColor.DARK_AQUA)
                                .append("  /nd locemcs <loc#> <set/clear> ").color(ChatColor.WHITE).append("--npc #\n").color(ChatColor.GOLD)
                                .append("Description").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                                .append("  Set/Clear EMC Shopkeeper settings per location\n").color(ChatColor.WHITE)
                                .append("    set ").color(ChatColor.GRAY).append("- Sets the current EMCS settings for this location\n")
                                .append("    clear ").color(ChatColor.GRAY).append("- Clears the settings for this location\n")
                                .append("Permission").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                                .append("  npcdestinations.edit<own/all>.locemcs\n").color(ChatColor.WHITE).create()))
                .create());
    }

    @Override
    public String parseLanguageLine(String message, NPCDestinationsTrait npcTrait, Destination_Setting locationSetting, Material blockMaterial, NPC npc, int ident) {
        if (locationSetting != null) {
            if (!npcSettings.containsKey(npc.getId())) {
                message = message.replaceAll("<location\\.emcs>", "");
                return message;
            }

            if (npcSettings.get(npc.getId()).getLocations().containsKey((locationSetting.LocationIdent))) {
                EMC_LocationSettings locSettings = npcSettings.get(npc.getId()).getLocations().get(locationSetting.LocationIdent);

                if (message.toLowerCase().contains("<location.emcs>")) {
                    if (locSettings.getLastSet().getTime() == 0) {
                        message = message.replaceAll("<location\\.emcs>","No settings");
                    } else {
                        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                        message = message.replaceAll("<location\\.emcs>", format.format(locSettings.getLastSet()));
                    }
                }
            } else {
                message = message.replaceAll("<location\\.emcs>", "");
            }
        }
        return message;
    }

    @Override
    public boolean isDestinationEnabled(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location) {
        return true;
    }

    @Override
    public void onLocationLoading(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location, DataKey storageKey) {
        if (!storageKey.keyExists("emcs")) {
            return;
        }

        EMC_NPCSettings settings;
        if (!npcSettings.containsKey(npc.getId())) {
            settings = new EMC_NPCSettings(npc);
            npcSettings.put(npc.getId(), settings);
        } else {
            settings = npcSettings.get(npc.getId());
        }

        EMC_LocationSettings oLoc = new EMC_LocationSettings();
        oLoc.setEnabled(storageKey.getBoolean("emcs.enabled", oLoc.isEnabled()));
        oLoc.setMinSize(storageKey.getInt("emcs.minSize", oLoc.getMinSize()));
        oLoc.setMaxSize(storageKey.getInt("emcs.maxSize", oLoc.getMaxSize()));
        oLoc.setMinTier(storageKey.getInt("emcs.minTier", oLoc.getMinTier()));
        oLoc.setMaxTier(storageKey.getInt("emcs.maxTier", oLoc.getMaxTier()));
        oLoc.setUpdateTicks(storageKey.getLong("emcs.updateTicks", oLoc.getUpdateTicks()));

        oLoc.setLocationId(location.LocationIdent);
        settings.getLocations().put(location.LocationIdent, oLoc);
    }

    @Override
    public void onLocationSaving(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location, DataKey storageKey) {
        if (!npcSettings.containsKey(npc.getId())) {
            return;
        }

        if (!npcSettings.get(npc.getId()).getLocations().containsKey(location.LocationIdent)) {
            return;
        }

        EMC_LocationSettings oLoc = npcSettings.get(npc.getId()).getLocations().get(location.LocationIdent);

        if (oLoc.getLocationId() != null) {
            storageKey.setBoolean("emcs.enabled", oLoc.isEnabled());
            storageKey.setInt("emcs.minSize", oLoc.getMinSize());
            storageKey.setInt("emcs.maxSize", oLoc.getMaxSize());
            storageKey.setInt("emcs.minTier", oLoc.getMinTier());
            storageKey.setInt("emcs.maxTier", oLoc.getMaxTier());
            storageKey.setLong("emcs.updateTicks", oLoc.getUpdateTicks());
        }
    }

    @Override
    public boolean onNavigationReached(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location) {
        if (!npcSettings.containsKey(npc.getId())) {
            return false;
        }

        if (!npcSettings.get(npc.getId()).getLocations().containsKey(location.LocationIdent)) {
            return false;
        }

        pluginReference.setCurrentSettings(npc, npcSettings.get(npc.getId()).getLocations().get(location.LocationIdent));

        return false;
    }

    @Override
    public boolean onNewDestination(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location) {
        return false;
    }

    @Override
    public void onEnableChanged(NPC npc, NPCDestinationsTrait npcTrait, boolean enabled) {
    }
}

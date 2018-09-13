package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import au.com.grieve.elitemobscitizens.EliteMobsCitizens;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.ai.speech.Chat;
import net.livecar.nuttyworks.npc_destinations.api.Destination_Setting;
import net.livecar.nuttyworks.npc_destinations.citizens.NPCDestinationsTrait;
import net.livecar.nuttyworks.npc_destinations.plugins.DestinationsAddon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EMC_Addon extends DestinationsAddon {
    private final EMC_Plugin pluginReference;

    public EMC_Addon(EMC_Plugin instanceRef) {
        this.pluginReference = instanceRef;
    }

    @Override
    public String getPluginIcon() {
        return "ṁ";
    }

    @Override
    public String getActionName() {
        return "EMCS";
    }

    @Override
    public String getQuickDescription() {
        return Arrays.stream(new ComponentBuilder("EliteMobsCitizens Plugin\n").color(ChatColor.GOLD)
                .append("Usage").color(ChatColor.YELLOW).append(":\n").color(ChatColor.DARK_AQUA)
                .append("  /nd locemcs <loc#> <enabled> <min_size> <max_size> <min_tier> <max_tier> ").color(ChatColor.WHITE).append("--npc #").color(ChatColor.GOLD)
                .append("Description").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                .append("  Sets EMC Shopkeeper settings per location").color(ChatColor.WHITE)
                .append("Permission").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                .append("  npcdestinations.edit<own/all>.locemcs").color(ChatColor.WHITE).create())
                .map(BaseComponent::toLegacyText)
                .collect( Collectors.joining(""));
    }

    @Override
    public String getDestinationHelp(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location) {
        return Arrays.stream(new ComponentBuilder(getPluginIcon()).color(ChatColor.GREEN)
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/npcdest locemcs --npc <npc.id> <location.id>"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("                EMCS Settings\n").color(ChatColor.GOLD)
                                .append("Current Settings\n").color(ChatColor.YELLOW)
                                .append("  Active: ").color(ChatColor.GRAY).append("<location.emcs>\n").color(ChatColor.WHITE)
                                .append("Usage").color(ChatColor.YELLOW).append(":\n").color(ChatColor.DARK_AQUA)
                                .append("  /nd locemcs <loc#> <enabled> <min_size> <max_size> <min_tier> <max_tier> ").color(ChatColor.WHITE).append("--npc #").color(ChatColor.GOLD)
                                .append("Description").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                                .append("  Sets EMC Shopkeeper settings per location").color(ChatColor.WHITE)
                                .append("Permission").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                                .append("  npcdestinations.edit<own/all>.locemcs").color(ChatColor.WHITE).create())).create())
                .map(BaseComponent::toLegacyText)
                .collect( Collectors.joining(""));
    }
}

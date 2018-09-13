package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import au.com.grieve.elitemobscitizens.EliteMobsCitizens;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.ai.speech.Chat;
import net.livecar.nuttyworks.npc_destinations.api.Destination_Setting;
import net.livecar.nuttyworks.npc_destinations.citizens.NPCDestinationsTrait;
import net.livecar.nuttyworks.npc_destinations.plugins.DestinationsAddon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

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
        return new ComponentBuilder("EliteMobsCitizens Plugin\n").color(ChatColor.GOLD)
                .append("Usage").color(ChatColor.YELLOW).append(":\n").color(ChatColor.DARK_AQUA)
                .append("  /nd locemcs <loc#> <enabled> <minSize> <maxSize> <minTier> <maxTier>\n").color(ChatColor.WHITE)
                .append("Description").color(ChatColor.GOLD).append(":\n").color(ChatColor.DARK_AQUA)
                .append("")
        return "EliteMobsCitizens Addon";
    }

    @Override
    public String getDestinationHelp(NPC npc, NPCDestinationsTrait npcTrait, Destination_Setting location) {
        return ""
    }
}

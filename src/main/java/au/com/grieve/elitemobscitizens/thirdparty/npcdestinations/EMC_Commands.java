package au.com.grieve.elitemobscitizens.thirdparty.npcdestinations;

import au.com.grieve.elitemobscitizens.traits.ShopkeeperTrait;
import net.citizensnpcs.api.npc.NPC;
import net.livecar.nuttyworks.npc_destinations.DestinationsPlugin;
import net.livecar.nuttyworks.npc_destinations.citizens.NPCDestinationsTrait;
import net.livecar.nuttyworks.npc_destinations.listeners.commands.CommandInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;

public class EMC_Commands {

    @CommandInfo(
            name = "locemcs",
            group = "External Plugin Commands",
            languageFile = "npcdestlang",
            helpMessage = "command_locemcs_help",
            arguments = {
                    "set|clear"
            },
            permission = {
                    "npcdestinations.editall.locemcs",
                    "npcdestinations.editown.locemcs"
            },
            allowConsole = true,
            minArguments = 2,
            maxArguments = 2
    )
    public boolean cmdLocEMCS(DestinationsPlugin destRef, CommandSender sender, NPC npc, String[] inargs, boolean isOwner, NPCDestinationsTrait destTrait) {
        if (!sender.hasPermission("npcdestinations.editall.locemcs") && !sender.hasPermission("npcdestinations.editown.locemcs")) {
            sender.spigot().sendMessage(new ComponentBuilder("No permission").color(ChatColor.RED).create());
            return true;
        }

        if (npc == null) {
            sender.spigot().sendMessage(new ComponentBuilder("Invalid NPC").color(ChatColor.RED).create());
            return true;
        }

        if (!npc.hasTrait(ShopkeeperTrait.class)) {
            sender.spigot().sendMessage(new ComponentBuilder("NPC does not have the emsc trait").color(ChatColor.RED).create());
            return true;
        }

        if (inargs.length < 3) {
            return false;
        }

        int locIndex = Integer.parseInt(inargs[1]);
        if (locIndex > destTrait.NPCLocations.size() -1) {
            sender.spigot().sendMessage(new ComponentBuilder("Invalid NPC Location").color(ChatColor.RED).create());
            return true;
        }

        if (!destTrait.NPCLocations.get(locIndex).managed_Location.equals("")) {
            destRef.getMessageManager.sendMessage("destinations", sender, "messages.commands_managed", destTrait, destTrait.NPCLocations.get(locIndex));
            return true;
        }

        // Get the Location
        EMC_LocationSettings locSetting = null;
        EMC_Addon addon = (EMC_Addon) destRef.getPluginManager.getPluginByName("emcs");

        if (!addon.getNpcSettings().containsKey(npc.getId())) {
            addon.getNpcSettings().put(npc.getId(), new EMC_NPCSettings(npc));
        }

        if (addon.getNpcSettings().get(npc.getId()).getLocations().containsKey(destTrait.NPCLocations.get(locIndex).LocationIdent)) {
            locSetting = addon.getNpcSettings().get(npc.getId()).getLocations().get(destTrait.NPCLocations.get(locIndex).LocationIdent);
        }

        if (inargs[2].equalsIgnoreCase("clear")) {
            if (locSetting != null) {
                addon.getNpcSettings().get(npc.getId()).getLocations().remove(destTrait.NPCLocations.get(locIndex).LocationIdent);
            }
            destRef.getCommandManager.onCommand(sender, new String[] { "info", "--npc", Integer.toString(npc.getId()) });
            return true;
        }

        if (inargs[2].equalsIgnoreCase("set")) {
            addon.getNpcSettings().get(npc.getId()).getLocations().remove(destTrait.NPCLocations.get(locIndex).LocationIdent);
            locSetting = addon.getPluginReference().getCurrentSettings(npc);
            locSetting.setLocationId(destTrait.NPCLocations.get(locIndex).LocationIdent);
            addon.getNpcSettings().get(npc.getId()).getLocations().put(destTrait.NPCLocations.get(locIndex).LocationIdent, locSetting);
            destRef.getCommandManager.onCommand(sender, new String[] { "info", "--npc", Integer.toString(npc.getId()) });
            return true;
        }
        return false;
    }

}

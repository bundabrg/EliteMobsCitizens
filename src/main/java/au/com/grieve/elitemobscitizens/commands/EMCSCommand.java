package au.com.grieve.elitemobscitizens.commands;

import au.com.grieve.elitemobscitizens.traits.ShopkeeperTrait;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.magmaguy.elitemobs.config.ConfigValues;
import com.magmaguy.elitemobs.config.ItemsDropSettingsConfig;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandAlias("emcs")
public class EMCSCommand extends BaseCommand {

    @Dependency
    private Plugin plugin;

    public NPC getNPC(CommandSender sender) {
        // See if NPC is selected
        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);

        if (npc == null) {
            sender.sendMessage(ChatColor.RED + "You must have an NPC selected");
            return null;
        }

        // Does it have our trait?
        if (!npc.hasTrait(ShopkeeperTrait.class)) {
            sender.sendMessage(ChatColor.RED + "NPC lacks the 'emcs' trait.");
            return null;
        }

        return npc;
    }

    @HelpCommand
    @Subcommand("help")
    public void onHelp(CommandSender sender) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        sender.spigot().sendMessage(new ComponentBuilder("=== [ EMC Shopkeeper Help ] ===").color(ChatColor.AQUA).create());
        sender.spigot().sendMessage(new ComponentBuilder("/emcs info").color(ChatColor.DARK_AQUA)
                .append(" - Show info about Shopkeeper").color(ChatColor.GRAY).create());
        sender.spigot().sendMessage(new ComponentBuilder("/emcs size <min> <max>").color(ChatColor.DARK_AQUA)
                .append(" - Set minimum and maximum size for shop").color(ChatColor.GRAY).create());
        sender.spigot().sendMessage(new ComponentBuilder("/emcs tier <min> <max>").color(ChatColor.DARK_AQUA)
                .append(" - Show minimum and maximum tier for offered items").color(ChatColor.GRAY).create());
        sender.spigot().sendMessage(new ComponentBuilder("/emcs enabled {true|false}").color(ChatColor.DARK_AQUA)
                .append(" - Enable or Disable Shop").color(ChatColor.GRAY).create());
        sender.spigot().sendMessage(new ComponentBuilder("/emcs vault {true|false}").color(ChatColor.DARK_AQUA)
                .append(" - Enable or Disable Vault Integration").color(ChatColor.GRAY).create());
    }

    @Subcommand("info")
    public void onTest1(CommandSender sender) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        sender.spigot().sendMessage(new ComponentBuilder("=== [ EMC Shopkeeper Info ] ===").color(ChatColor.AQUA).create());
        sender.spigot().sendMessage(new ComponentBuilder("Shop Name: ").color(ChatColor.DARK_AQUA)
                .append(shopTrait.getShopName()).color(ChatColor.GREEN).create());
        sender.spigot().sendMessage(new ComponentBuilder("Shop Size: ").color(ChatColor.DARK_AQUA)
                .append(String.valueOf(shopTrait.getMinSize()) + " - " + String.valueOf(shopTrait.getMaxSize())).color(ChatColor.GREEN).create());
        sender.spigot().sendMessage(new ComponentBuilder("Tier Range: ").color(ChatColor.DARK_AQUA)
                .append(String.valueOf(shopTrait.getMinTier()) + " - " + String.valueOf(shopTrait.getMaxTier())).color(ChatColor.GREEN).create());
        sender.spigot().sendMessage(new ComponentBuilder("Update Interval (ticks): ").color(ChatColor.DARK_AQUA)
                .append(String.valueOf(shopTrait.getUpdateTicks())).color(ChatColor.GREEN).create());
        sender.spigot().sendMessage(new ComponentBuilder("Enabled: ").color(ChatColor.DARK_AQUA)
                .append(shopTrait.isEnabled()?"True":"False").color(shopTrait.isEnabled()?ChatColor.GREEN:ChatColor.RED).create());

    }

    @Subcommand("size")
    public void onSize(CommandSender sender, int min, int max) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        shopTrait.setMinSize(Math.max(1,min));
        shopTrait.setMaxSize(Math.min(54, max));
        shopTrait.refreshShop();

        sender.spigot().sendMessage(new ComponentBuilder("Updated Shop Size").color(ChatColor.GREEN).create());
    }

    @Subcommand("tier")
    public void onTier(CommandSender sender, int min, int max) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        shopTrait.setMinTier(Math.max(0,min));
        shopTrait.setMaxTier(Math.min((int) Math.round(ConfigValues.itemsDropSettingsConfig.getDouble(ItemsDropSettingsConfig.MAXIMUM_LOOT_TIER)), max));
        shopTrait.refreshShop();

        sender.spigot().sendMessage(new ComponentBuilder("Updated Item Tiers").color(ChatColor.GREEN).create());
    }

    @Subcommand("update_ticks")
    public void onUpdateInterval(CommandSender sender, int ticks) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        shopTrait.setUpdateTicks(Math.max(10, ticks));
        sender.spigot().sendMessage(new ComponentBuilder("Updated Update Interval. Respawn NPC to see effect.").color(ChatColor.GREEN).create());
    }

    @Subcommand("enabled")
    public void onUpdateInterval(CommandSender sender, boolean enabled) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        shopTrait.setEnabled(enabled);
        shopTrait.refreshShop();

        sender.spigot().sendMessage(new ComponentBuilder("Updated Shop.").create());
    }

    @Subcommand("vault")
    public void onVault(CommandSender sender, boolean enabled) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        shopTrait.setVaultEnabled(enabled);
        shopTrait.refreshShop();

        sender.spigot().sendMessage(new ComponentBuilder("Updated Shop.").create());
    }

    @Subcommand("name")
    public void onName(CommandSender sender, String name) {
        NPC npc = getNPC(sender);
        if (npc == null) {
            return;
        }

        ShopkeeperTrait shopTrait = npc.getTrait(ShopkeeperTrait.class);

        shopTrait.setShopName(name);
        shopTrait.refreshShop();

        sender.spigot().sendMessage(new ComponentBuilder("Updated Shop.").create());
    }

}


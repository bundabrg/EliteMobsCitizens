package au.com.grieve.elitemobscitizens.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandAlias("emsk")
public class EMSKCommand extends BaseCommand {

    @Dependency
    private Plugin plugin;

    @Subcommand("test1")
    public void onTest1(CommandSender sender) {
        sender.sendMessage("test1");
    }

    @Subcommand("test2")
    public void onTest2(CommandSender sender, Player player) {
        sender.sendMessage("test2" + player.getAddress().toString());
    }

}

package dev.kyro.wiji.prisonbridge.commands;

import dev.kyro.wiji.prisonbridge.misc.AMisc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!(sender instanceof Player) || !sender.isOp()) return false;
		Player player = (Player) sender;

		AMisc.sendMessage(player, "This is a test command");

		return false;
	}
}

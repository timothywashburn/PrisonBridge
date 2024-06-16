package dev.kyro.wiji.prisonbridge.commands;

import dev.kyro.wiji.prisonbridge.commands.subcommands.TestSubCommand;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor {
	public List<SubCommand> subCommands = new ArrayList<>();

	public BaseCommand() {
		subCommands.add(new TestSubCommand());
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!sender.hasPermission("prisonbridge.admin")) return false;



		return false;
	}
}

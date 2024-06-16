package dev.kyro.wiji.prisonbridge.commands;

import dev.kyro.wiji.prisonbridge.commands.subcommands.AdminBlocksCommand;
import dev.kyro.wiji.prisonbridge.commands.subcommands.TestSubCommand;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {
	public List<SubCommand> subCommands = new ArrayList<>();

	public BaseCommand() {
		subCommands.add(new AdminBlocksCommand());
//		subCommands.add(new SetPrestigeCommand());
//		subCommands.add(new SetRankCommand());
		subCommands.add(new TestSubCommand());
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!sender.hasPermission("prisonbridge.admin")) return false;

		if(args.length == 0) {
			sendHelpMessage(sender, label);
			return false;
		}

		String subCommandString = args[0].toLowerCase();
		for(SubCommand subCommand : subCommands) {
			if(!subCommand.executor.equals(subCommandString) && !subCommand.aliases.contains(subCommandString)) continue;
			List<String> argsList = new ArrayList<>(List.of(args));
			argsList.remove(0);
			subCommand.execute(sender, command, label, argsList);
		}

		return false;
	}

	public void sendHelpMessage(CommandSender sender, String label) {
		AMisc.sendMessage(sender, "&8&m--------------------------------------------------");
		for(SubCommand subCommand : subCommands) {
			AMisc.sendMessage(sender, " &9&l>&f /" + label + " " + subCommand.executor + " &7" + subCommand.getUsageMessage());
		}
		AMisc.sendMessage(sender, "&8&m--------------------------------------------------");
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> tabComplete = new ArrayList<>();
		if(!sender.hasPermission("prisonbridge.admin")) return tabComplete;

		if(args.length == 1) {
			String tabCompleteStart = args[0].toLowerCase();
			for(SubCommand subCommand : subCommands) {
				if(subCommand.executor.startsWith(tabCompleteStart)) tabComplete.add(subCommand.executor);
			}
			return tabComplete;
		}

		String subCommandString = args[0].toLowerCase();
		for(SubCommand subCommand : subCommands) {
			if(!subCommand.executor.equals(subCommandString) && !subCommand.aliases.contains(subCommandString)) continue;
			List<String> argsList = new ArrayList<>(List.of(args));
			argsList.remove(0);
			return subCommand.getTabComplete(sender, command, label, argsList);
		}

		return null;
	}
}

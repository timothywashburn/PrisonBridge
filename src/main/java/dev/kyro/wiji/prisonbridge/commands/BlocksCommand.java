package dev.kyro.wiji.prisonbridge.commands;

import dev.kyro.wiji.prisonbridge.commands.blockscommand.BalanceCommand;
import dev.kyro.wiji.prisonbridge.commands.blockscommand.PrestigeCommand;
import dev.kyro.wiji.prisonbridge.commands.blockscommand.RankupCommand;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlocksCommand implements CommandExecutor, TabCompleter {
	public List<SubCommand> subCommands = new ArrayList<>();

	public BlocksCommand() {
		subCommands.add(new BalanceCommand());
		subCommands.add(new PrestigeCommand());
		subCommands.add(new RankupCommand());
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!(sender instanceof Player)) return false;

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

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> tabComplete = new ArrayList<>();

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

	public void sendHelpMessage(CommandSender sender, String label) {
		AMisc.sendConfigurableMessage(sender, "commands.blocks.generic.divider");
		for(SubCommand subCommand : subCommands) {
			AMisc.sendConfigurableMessage(sender, "commands.blocks.generic.commandhelp",
					m -> m.replace("{command}", label).replace("{subcommand}", subCommand.executor)
							.replace("{usage}", subCommand.getUsageMessage()));
		}
		AMisc.sendConfigurableMessage(sender, "commands.blocks.generic.divider");
	}
}

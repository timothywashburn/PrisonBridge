package dev.kyro.wiji.prisonbridge.commands.subcommands;

import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminBlocksCommand extends SubCommand {
	public AdminBlocksCommand() {
		super("blocks");
	}

	@Override
	public String getDescription() {
		return "Modifies a player's blocks";
	}

	@Override
	public String getUsageMessage() {
		return "<add|remove|set> <target> <amount>";
	}

	@Override
	public void execute(CommandSender sender, Command command, String label, List<String> args) {
		if(args.size() < 1) {
			displayHelpMessage(sender, label);
			return;
		}

		String subCommand = args.get(0).toLowerCase();
		if(subCommand.equals("add")) {
			if(args.size() < 3) {
				AMisc.sendMessage(sender, "&cUsage: /" + label + " add <target> <amount>");
				return;
			}

			Player target = getTarget(args.get(1));
			if(target == null) {
				AMisc.sendConfigurableMessage(sender, "commands.admin.blocks.playernotfound", m -> m.replace("{player}", args.get(1)));
				return;
			}

			long blocks;
			try {
				blocks = Long.parseLong(args.get(2));
				if(blocks < 0) throw new NumberFormatException();
			} catch(NumberFormatException e) {
				AMisc.sendMessage(sender, "&c&lERROR!&7 Invalid amount of blocks: " + args.get(2));
				return;
			}

			PrisonPlayer prisonTarget = PlayerManager.getPrisonPlayer(target);
			prisonTarget.blocks += blocks;
			AMisc.sendConfigurableMessage(sender, "commands.admin.blocks.added",
					m -> m.replace("{target}", target.getName()).replace("{blocks}", AMisc.formatBlocks(blocks)));
			AMisc.sendConfigurableMessage(target, "commands.admin.blocks.received",
					m -> m.replace("{blocks}", AMisc.formatBlocks(blocks)));
		} else if(subCommand.equals("remove")) {
			if(args.size() < 3) {
				AMisc.sendMessage(sender, "&cUsage: /" + label + " remove <target> <amount>");
				return;
			}

			Player target = getTarget(args.get(1));
			if(target == null) {
				AMisc.sendConfigurableMessage(sender, "commands.admin.blocks.playernotfound", m -> m.replace("{player}", args.get(1)));
				return;
			}

			long blocks;
			try {
				blocks = Long.parseLong(args.get(2));
				if(blocks < 0) throw new NumberFormatException();
			} catch(NumberFormatException e) {
				AMisc.sendMessage(sender, "&c&lERROR!&7 Invalid amount of blocks: " + args.get(2));
				return;
			}

			PrisonPlayer prisonTarget = PlayerManager.getPrisonPlayer(target);
			if(prisonTarget.blocks < blocks) {
				AMisc.sendConfigurableMessage(sender, "commands.admin.blocks.notenough",
						m -> m.replace("{target}", target.getName()).replace("{blocks}", prisonTarget.getBlocksFormatted()));
				return;
			}

			prisonTarget.blocks -= blocks;
			AMisc.sendConfigurableMessage(sender, "commands.admin.blocks.removed",
					m -> m.replace("{target}", target.getName()).replace("{blocks}", AMisc.formatBlocks(blocks)));
		} else if(subCommand.equals("set")) {
			if(args.size() < 3) {
				AMisc.sendMessage(sender, "&cUsage: /" + label + " set <target> <amount>");
				return;
			}

			Player target = getTarget(args.get(1));
			if(target == null) {
				AMisc.sendConfigurableMessage(sender, "commands.admin.blocks.playernotfound", m -> m.replace("{player}", args.get(1)));
				return;
			}

			long blocks;
			try {
				blocks = Long.parseLong(args.get(2));
				if(blocks < 0) throw new NumberFormatException();
			} catch(NumberFormatException e) {
				AMisc.sendMessage(sender, "&c&lERROR!&7 Invalid amount of blocks: " + args.get(2));
				return;
			}

			PrisonPlayer prisonTarget = PlayerManager.getPrisonPlayer(target);
			prisonTarget.blocks = blocks;
			AMisc.sendConfigurableMessage(sender, "commands.admin.blocks.set",
					m -> m.replace("{target}", target.getName()).replace("{blocks}", AMisc.formatBlocks(blocks)));
		} else {
			displayHelpMessage(sender, label);
		}
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, Command command, String label, List<String> args) {
		List<String> tabComplete = new ArrayList<>();
		if(args.size() == 0) return tabComplete;

		String subCommand = args.get(0).toLowerCase();
		if(args.size() == 1) {
			List<String> subCommands = new ArrayList<>();
			subCommands.add("add");
			subCommands.add("remove");
			subCommands.add("set");
			for(String subCommandTest : subCommands) {
				if(subCommandTest.startsWith(subCommand)) tabComplete.add(subCommandTest);
			}
			return tabComplete;
		}

		if(subCommand.equals("add") || subCommand.equals("remove") || subCommand.equals("set")) {
			if(args.size() == 2) return null;
			return tabComplete;
		}

		return null;
	}

	public Player getTarget(String targetName) {
		Player target = null;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getName().equalsIgnoreCase(targetName)) {
				target = player;
				break;
			}
		}
		return target;
	}
}

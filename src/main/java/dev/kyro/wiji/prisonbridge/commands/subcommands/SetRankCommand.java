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

public class SetRankCommand extends SubCommand {
	public SetRankCommand() {
		super("setrank");
	}

	@Override
	public String getUsageMessage() {
		return "<target> <rank>";
	}

	@Override
	public void execute(CommandSender sender, Command command, String label, List<String> args) {
		if(args.size() < 2) {
			displayHelpMessage(sender, label);
			return;
		}

		Player target = Bukkit.getPlayer(args.get(0));
		if(target == null) {
			AMisc.sendConfigurableMessage(sender, "commands.generic.notarget", m -> m.replace("{player}", args.get(0)));
			return;
		}

		char rank;
		try {
			rank = args.get(1).toUpperCase().charAt(0);
			if(args.get(1).length() != 1 || (int) rank < 65 || (int) rank > 90)	throw new Exception();
		} catch(Exception e) {
			AMisc.sendMessage(sender, "&c&lERROR!&7 Invalid rank: " + args.get(1));
			return;
		}

		PrisonPlayer prisonTarget = PlayerManager.getPrisonPlayer(target);
		prisonTarget.rank = rank - 65;
		AMisc.sendConfigurableMessage(sender, "commands.admin.rank.set",
				m -> m.replace("{target}", target.getName()).replace("{rank}", String.valueOf(rank)));
		AMisc.sendConfigurableMessage(target, "commands.admin.rank.setself",
				m -> m.replace("{rank}", String.valueOf(rank)));
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, Command command, String label, List<String> args) {
		List<String> tabComplete = new ArrayList<>();
		if(args.size() == 1) return null;
		return tabComplete;
	}
}

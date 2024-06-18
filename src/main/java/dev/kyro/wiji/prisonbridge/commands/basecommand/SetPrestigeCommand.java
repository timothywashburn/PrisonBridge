package dev.kyro.wiji.prisonbridge.commands.basecommand;

import dev.kyro.wiji.prisonbridge.controllers.LevelManager;
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

public class SetPrestigeCommand extends SubCommand {
	public SetPrestigeCommand() {
		super("setprestige");
	}

	@Override
	public String getUsageMessage() {
		return "<target> <prestige>";
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

		int prestige;
		try {
			prestige = Integer.parseInt(args.get(1));
			if(prestige < 0 || prestige > LevelManager.totalPrestiges) throw new NumberFormatException();
		} catch(NumberFormatException e) {
			AMisc.sendMessage(sender, "&c&lERROR!&7 Invalid prestige: " + args.get(1));
			return;
		}

		PrisonPlayer prisonTarget = PlayerManager.getPrisonPlayer(target);
		prisonTarget.prestige = prestige;
		AMisc.sendConfigurableMessage(sender, "commands.admin.prestige.set",
				m -> m.replace("{target}", target.getName()).replace("{prestige}", String.valueOf(prestige)));
		AMisc.sendConfigurableMessage(target, "commands.admin.prestige.setself",
				m -> m.replace("{prestige}", String.valueOf(prestige)));
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, Command command, String label, List<String> args) {
		List<String> tabComplete = new ArrayList<>();
		if(args.size() == 1) return null;
		return tabComplete;
	}
}

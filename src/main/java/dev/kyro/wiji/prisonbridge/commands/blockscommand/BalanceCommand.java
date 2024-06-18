package dev.kyro.wiji.prisonbridge.commands.blockscommand;

import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BalanceCommand extends SubCommand {
	public BalanceCommand() {
		super("balance");
	}

	@Override
	public String getUsageMessage() {
		return "";
	}

	@Override
	public void execute(CommandSender sender, Command command, String label, List<String> args) {
		Player player = (Player) sender;
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player);

		if (args.isEmpty()) {
			AMisc.sendConfigurableMessage(player, "commands.blocks.self",
					m -> m.replace("{blocks}", prisonPlayer.getBlocksFormatted()));
			return;
		}

		Player target = null;
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if(onlinePlayer.getName().equals(args.get(0))) {
				target = onlinePlayer;
				break;
			}
		}
		if (target == null) {
			AMisc.sendConfigurableMessage(player, "commands.blocks.notarget", m -> m.replace("{target}", args.get(0)));
			return;
		}

		Player finalTarget = target;
		PrisonPlayer prisonTarget = PlayerManager.getPrisonPlayer(target);
		AMisc.sendConfigurableMessage(player, "commands.blocks.other",
				m -> m.replace("{target}", finalTarget.getName()).replace("{blocks}", prisonTarget.getBlocksFormatted()));
	}
}

package dev.kyro.wiji.prisonbridge.commands.blockscommand;

import dev.kyro.wiji.prisonbridge.controllers.LevelManager;
import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PrestigeCommand extends SubCommand {
	public PrestigeCommand() {
		super("prestige");
	}

	@Override
	public String getUsageMessage() {
		return "";
	}

	@Override
	public void execute(CommandSender sender, Command command, String label, List<String> args) {
		Player player = (Player) sender;
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player);

		if (prisonPlayer.prestige == LevelManager.totalPrestiges) {
			AMisc.sendConfigurableMessage(player, "commands.rankup.maxrank");
			return;
		}

		if (prisonPlayer.rank != 25) {
			AMisc.sendConfigurableMessage(player, "commands.rankup.notmaxrank");
			return;
		}

//		TODO: Call console command with placeholder for player name
		prisonPlayer.prestige++;
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, Command command, String label, List<String> args) {
		return new ArrayList<>();
	}
}
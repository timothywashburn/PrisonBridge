package dev.kyro.wiji.prisonbridge.commands.blockscommand;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankupCommand extends SubCommand {
	public RankupCommand() {
		super("rankup");
	}

	@Override
	public String getUsageMessage() {
		return "";
	}

	@Override
	public void execute(CommandSender sender, Command command, String label, List<String> args) {
		Player player = (Player) sender;
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player);

		if (prisonPlayer.rank == 25) {
			AMisc.sendConfigurableMessage(player, "commands.prestige.maxprestige");
			return;
		}

		if (!prisonPlayer.canRankup()) {
			AMisc.sendConfigurableMessage(player, "commands.prestige.notenoughblocks",
					m -> m.replace("{blocks}", AMisc.formatBlocks(prisonPlayer.getRemainingBlocksToRankup())));
			return;
		}

//		TODO: Call console command with placeholder for player name
		prisonPlayer.rank++;

		List<String> onRankupCommands = PrisonBridge.getConfiguration().getStringList("on-rankup-commands");
		for(String onRankupCommand : onRankupCommands) {
			onRankupCommand.replace("{player}", player.getName());
			onRankupCommand.replace("{rank}", prisonPlayer.getRankFormatted());

			ConsoleCommandSender console = PrisonBridge.INSTANCE.getServer().getConsoleSender();
			PrisonBridge.INSTANCE.getServer().dispatchCommand(console, onRankupCommand);
		}
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, Command command, String label, List<String> args) {
		return new ArrayList<>();
	}
}

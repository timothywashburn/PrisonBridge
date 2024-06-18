package dev.kyro.wiji.prisonbridge.commands.blockscommand;

import dev.kyro.wiji.prisonbridge.controllers.LevelManager;
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
			AMisc.sendConfigurableMessage(player, "commands.blocks.rankup.maxrank");
			return;
		}

		if (!prisonPlayer.canRankup()) {
			AMisc.sendConfigurableMessage(player, "commands.blocks.rankup.notenoughblocks",
					m -> m.replace("{blocks}", AMisc.formatBlocks(prisonPlayer.getRemainingBlocksToRankup())));
			return;
		}

		prisonPlayer.blocks -= LevelManager.getBlocksForRank(prisonPlayer.prestige, prisonPlayer.rank);
		prisonPlayer.rank++;
		AMisc.sendConfigurableMessage(player, "commands.blocks.rankup.rankup",
				m -> m.replace("{rank}", prisonPlayer.getRankFormatted()));

		List<String> onRankupCommands = PrisonBridge.getConfiguration().getStringList("on-rankup-commands");
		for(String onRankupCommand : onRankupCommands) {

			ConsoleCommandSender console = PrisonBridge.INSTANCE.getServer().getConsoleSender();
			PrisonBridge.INSTANCE.getServer().dispatchCommand(console, onRankupCommand.replace("{player}",
					player.getName()).replace("{rank}", prisonPlayer.getRankFormatted()));
		}
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, Command command, String label, List<String> args) {
		return new ArrayList<>();
	}
}

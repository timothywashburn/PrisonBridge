package dev.kyro.wiji.prisonbridge.commands;

import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankupCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player);

		if(prisonPlayer.rank == 25) {
			AMisc.sendConfigurableMessage(player, "commands.rankup.maxrank");
			return false;
		}

		if(!prisonPlayer.canRankup()) {
			AMisc.sendConfigurableMessage(player, "commands.rankup.notenoughblocks",
					m -> m.replace("%1", AMisc.formatBlocks(prisonPlayer.getRemainingBlocksToRankup())));
			return false;
		}

//		TODO: Call console command with placeholder for player name
		prisonPlayer.rank++;
		return false;
	}
}
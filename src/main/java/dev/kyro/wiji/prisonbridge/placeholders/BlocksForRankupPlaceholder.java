package dev.kyro.wiji.prisonbridge.placeholders;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import dev.kyro.wiji.prisonbridge.controllers.LevelManager;
import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PAPIPlaceholder;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public class BlocksForRankupPlaceholder implements PAPIPlaceholder {
	@Override
	public String getIdentifier() {
		return "blocksforrankup";
	}

	@Override
	public String getValue(Player player, List<String> parameters) {
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player.getUniqueId());
		assert prisonPlayer != null;

		if(parameters.isEmpty()) {
			if(prisonPlayer.rank == 25) return PrisonBridge.getLang("misc.maxrank");
			return AMisc.formatBlocks(prisonPlayer.getRemainingBlocksToRankup(), true);
		}

		char rank = parameters.get(0).toUpperCase().charAt(0);

		int rankDigit = rank - 65;

		if(rankDigit < 0 || rankDigit > 25) return PrisonBridge.getLang("placeholders.invalidrank");

		return String.valueOf(LevelManager.getBlocksForRank(prisonPlayer.prestige, (rank - 65)));
	}
}

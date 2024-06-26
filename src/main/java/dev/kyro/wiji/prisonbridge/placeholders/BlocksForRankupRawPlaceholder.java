package dev.kyro.wiji.prisonbridge.placeholders;

import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PAPIPlaceholder;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class BlocksForRankupRawPlaceholder implements PAPIPlaceholder {
	@Override
	public String getIdentifier() {
		return "blocksforrankupraw";
	}

	@Override
	public String getValue(Player player, List<String> parameters) {
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player.getUniqueId());
		assert prisonPlayer != null;

		return String.valueOf(prisonPlayer.getRemainingBlocksToRankup());
	}
}

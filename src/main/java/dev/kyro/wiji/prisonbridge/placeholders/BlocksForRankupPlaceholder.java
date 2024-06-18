package dev.kyro.wiji.prisonbridge.placeholders;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PAPIPlaceholder;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import org.bukkit.entity.Player;

public class BlocksForRankupPlaceholder implements PAPIPlaceholder {
	@Override
	public String getIdentifier() {
		return "blocksforrankup";
	}

	@Override
	public String getValue(Player player) {
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player.getUniqueId());
		assert prisonPlayer != null;

		if(prisonPlayer.rank == 25) return PrisonBridge.getLang("misc.maxrank");
		return AMisc.formatBlocks(prisonPlayer.getRemainingBlocksToRankup(), true);
	}
}

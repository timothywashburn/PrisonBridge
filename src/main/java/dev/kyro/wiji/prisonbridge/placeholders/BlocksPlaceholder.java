package dev.kyro.wiji.prisonbridge.placeholders;

import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.objects.PAPIPlaceholder;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class BlocksPlaceholder implements PAPIPlaceholder {

	@Override
	public String getIdentifier() {
		return "blocks";
	}

	@Override
	public String getValue(Player player, List<String> parameters) {
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player.getUniqueId());
		assert prisonPlayer != null;

		return prisonPlayer.getBlocksFormatted(true);
	}
}

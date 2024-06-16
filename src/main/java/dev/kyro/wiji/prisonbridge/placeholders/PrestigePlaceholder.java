package dev.kyro.wiji.prisonbridge.placeholders;

import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.objects.Placeholder;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import org.bukkit.entity.Player;

public class PrestigePlaceholder implements Placeholder {
	@Override
	public String getIdentifier() {
		return "prestige";
	}

	@Override
	public String getValue(Player player) {
		PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player.getUniqueId());
		assert prisonPlayer != null;

		return prisonPlayer.getPrestigeFormatted();
	}
}

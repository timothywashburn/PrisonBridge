package dev.kyro.wiji.prisonbridge.objects;

import org.bukkit.entity.Player;

public interface PAPIPlaceholder {
	String getIdentifier();
	String getValue(Player player);
}

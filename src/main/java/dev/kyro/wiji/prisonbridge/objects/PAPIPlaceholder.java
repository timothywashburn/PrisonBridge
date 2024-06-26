package dev.kyro.wiji.prisonbridge.objects;

import org.bukkit.entity.Player;

import java.util.List;

public interface PAPIPlaceholder {
	String getIdentifier();
	String getValue(Player player, List<String> parameters);
}

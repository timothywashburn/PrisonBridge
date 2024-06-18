package dev.kyro.wiji.prisonbridge.controllers;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class LevelManager implements Listener {
	private static final Map<Integer, Long> rankBlocksMap = new HashMap<>();
	private static final Map<Integer, Double> prestigeMultiplierMap = new HashMap<>();

	public static int totalPrestiges = 0;

	public LevelManager() {
		for (int i = 0; i < 26; i++) {
			String rank = Character.toString((char) (i + 65));
			rankBlocksMap.put(i, PrisonBridge.getConfiguration().getLong("ranks." + rank));
		}

		int prestige = 0;
		while (PrisonBridge.getConfiguration().contains("prestiges." + prestige)) {
			prestigeMultiplierMap.put(prestige, PrisonBridge.getConfiguration().getDouble("prestiges." + prestige));
			totalPrestiges = prestige;
			prestige++;
		}
	}

	public static long getBlocksForRank(int prestige, int rank) {
		return (long) Math.ceil(prestigeMultiplierMap.get(prestige) * rankBlocksMap.get(rank));
	}
}

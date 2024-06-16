package dev.kyro.wiji.prisonbridge.controllers;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import tech.mcprison.prison.Prison;

import java.util.HashMap;
import java.util.Map;

public class LevelManager {
	private static final Map<Integer, Long> rankBlocksMap = new HashMap<>();
	private static final Map<Integer, Double> prestigeMultiplierMap = new HashMap<>();

	public LevelManager() {
		for (int i = 0; i < 26; i++) {
			String rank = Character.toString((char) (i + 65));
			rankBlocksMap.put(i, PrisonBridge.getConfiguration().getLong("ranks." + rank));
		}

		int prestige = 0;
		while (PrisonBridge.getConfiguration().contains("prestiges." + prestige)) {
			prestigeMultiplierMap.put(prestige, PrisonBridge.getConfiguration().getDouble(prestige + ""));
			prestige++;
		}
	}

	public static long getBlocksForRank(int prestige, int rank) {
		return (long) Math.ceil(prestigeMultiplierMap.get(prestige) * rankBlocksMap.get(rank));
	}
}

package dev.kyro.wiji.prisonbridge.controllers;

import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import me.revils.revenchants.events.MineBlockEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager implements Listener {
	public static List<PrisonPlayer> prisonPlayerList = new ArrayList<>();

	@EventHandler
	public void onBlockMine(MineBlockEvent event) {

	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		PrisonPlayer prisonPlayer = getPrisonPlayer(event.getPlayer().getUniqueId());
		assert prisonPlayer != null;

		prisonPlayer.save();
	}

	public static PrisonPlayer getPrisonPlayer(Player player) {
		return getPrisonPlayer(player.getUniqueId());
	}

	public static PrisonPlayer getPrisonPlayer(UUID uuid) {
		for(PrisonPlayer prisonPlayer : prisonPlayerList) if(prisonPlayer.uuid.equals(uuid)) return prisonPlayer;
		return null;
	}
}

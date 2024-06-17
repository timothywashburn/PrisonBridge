package dev.kyro.wiji.prisonbridge.controllers;

import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import me.revils.revenchants.events.MineBlockEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tech.mcprison.prison.util.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager implements Listener {
	public static List<PrisonPlayer> prisonPlayerList = new ArrayList<>();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockMine(MineBlockEvent event) {
		Player player = event.getPlayer();

//		TODO: Remove after confirmed functional
		AMisc.sendMessage(player, "You have broken " + event.getMinedBlockAmount() + " block" + (event.getMinedBlockAmount() == 1 ? "" : "s"));

		PrisonPlayer prisonPlayer = getPrisonPlayer(player);
		prisonPlayer.blocks += event.getMinedBlockAmount();
	}

	@EventHandler
	public void onJoin(AsyncPlayerPreLoginEvent event) {
		for(PrisonPlayer prisonPlayer : prisonPlayerList) {
			if(event.getUniqueId().equals(prisonPlayer.uuid)) {
				event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.empty());
				event.setKickMessage(ChatColor.RED + "Duplicate playerdata found!");
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		PrisonPlayer prisonPlayer = new PrisonPlayer(event.getPlayer().getUniqueId());
		prisonPlayerList.add(prisonPlayer);
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

package dev.kyro.wiji.prisonbridge.commands;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import jogamp.opengl.awt.AWTUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tech.mcprison.prison.Prison;

public class BlocksCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;

		if (args.length == 0) {
			PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(player);
			AMisc.sendConfigurableMessage(player, "commands.blocks.self",
					m -> m.replace("%1", prisonPlayer.getBlocksFormatted()));
			return false;
		}

		Player target = null;
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if(onlinePlayer.getName().equals(args[0])) {
				target = onlinePlayer;
				break;
			}
		}
		if (target == null) {
			AMisc.sendConfigurableMessage(player, "commands.blocks.notarget", m -> m.replace("%1", args[0]));
			return false;
		}

		Player finalTarget = target;
		PrisonPlayer prisonTarget = PlayerManager.getPrisonPlayer(target);
		AMisc.sendConfigurableMessage(player, "commands.blocks.other",
				m -> m.replace("%1", finalTarget.getName()).replace("%2", prisonTarget.getBlocksFormatted()));

		return false;
	}
}
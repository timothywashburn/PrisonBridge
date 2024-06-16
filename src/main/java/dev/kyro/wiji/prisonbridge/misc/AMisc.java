package dev.kyro.wiji.prisonbridge.misc;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.mcprison.prison.util.ChatColor;

import java.text.DecimalFormat;

public class AMisc {
	public static DecimalFormat blocksFormat = new DecimalFormat("#,##0");

	public static void sendConfigurableMessage(CommandSender sender, String path) {
		sendConfigurableMessage(sender, path, m -> m);
	}

	public static void sendConfigurableMessage(CommandSender sender, String path, ConsumerSupplier<String, String> regex) {
		String message = PrisonBridge.getLang(path);
		sendMessage(sender, regex.consumerSupplier(message));
	}

	public static void sendMessage(CommandSender sender, String message) {
		if(message == null) return;
		if(!(sender instanceof Player player)) {
			System.out.println(message);
			return;
		}

		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public static void broadcastMessage(String message) {
		if(message == null) return;
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public static String formatBlocks(long blocks) {
		return blocksFormat.format(blocks) + " block" + (blocks == 1 ? "" : "s");
	}
}

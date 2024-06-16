package dev.kyro.wiji.prisonbridge.objects;

import dev.kyro.wiji.prisonbridge.misc.AMisc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {
	public String executor;
	public List<String> aliases = new ArrayList<>();

	public SubCommand(String executor) {
		this.executor = executor;
	}

	public abstract String getUsageMessage();
	public abstract void execute(CommandSender sender, Command command, String label, List<String> args);

	public List<String> getTabComplete(CommandSender sender, Command command, String label, List<String> args) {
		return null;
	}

	public void displayHelpMessage(CommandSender sender, String label) {
		AMisc.sendMessage(sender, "&c&lERROR!&7 /" + label + " " + executor + " " + getUsageMessage());
	}
}

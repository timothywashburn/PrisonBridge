package dev.kyro.wiji.prisonbridge.objects;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {
	public String executor;
	public List<String> aliases;
	public String help;

	public abstract void execute(Player player, List<String> args);
}

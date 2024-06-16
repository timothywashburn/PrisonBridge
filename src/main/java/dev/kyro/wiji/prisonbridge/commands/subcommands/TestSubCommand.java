package dev.kyro.wiji.prisonbridge.commands.subcommands;

import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TestSubCommand extends SubCommand {
	public TestSubCommand() {
		super("test");
	}

	public void execute(Player player, List<String> args) {
		AMisc.sendMessage(player, String.join("", args));
	}

	@Override
	public String getDescription() {
		return "test command";
	}

	@Override
	public String getUsageMessage() {
		return "";
	}

	@Override
	public void execute(CommandSender sender, Command command, String label, List<String> args) {

	}
}

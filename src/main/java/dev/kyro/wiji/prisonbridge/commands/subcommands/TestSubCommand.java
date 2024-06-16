package dev.kyro.wiji.prisonbridge.commands.subcommands;

import dev.kyro.wiji.prisonbridge.commands.BaseCommand;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.objects.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class TestSubCommand extends SubCommand {
	public TestSubCommand() {

	}

	public void execute(Player player, List<String> args) {
		AMisc.sendMessage(player, String.join("", args));
	}
}

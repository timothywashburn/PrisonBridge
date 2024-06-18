package dev.kyro.wiji.prisonbridge;

import com.google.common.base.Charsets;
import dev.kyro.wiji.prisonbridge.commands.*;
import dev.kyro.wiji.prisonbridge.controllers.LevelManager;
import dev.kyro.wiji.prisonbridge.controllers.PAPIExtension;
import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import dev.kyro.wiji.prisonbridge.placeholders.*;
import dev.kyro.wiji.prisonbridge.sql.TableManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class PrisonBridge extends JavaPlugin {
	public static PrisonBridge INSTANCE;

	@Override
	public void onEnable() {
		INSTANCE = this;

		TableManager.registerTables(this);

		new PAPIExtension("prisonbridge").register();

		initConfig();

		registerCommands();
		registerListeners();
		registerPlaceholders();

		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			PrisonPlayer prisonPlayer = new PrisonPlayer(onlinePlayer.getUniqueId());
			PlayerManager.prisonPlayerList.add(prisonPlayer);
		}
	}

	@Override
	public void onDisable() {
//		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//			PrisonPlayer prisonPlayer = PlayerManager.getPrisonPlayer(onlinePlayer.getUniqueId());
//			assert prisonPlayer != null;
//			prisonPlayer.save();
//		}
	}

	public void registerCommands() {
		BaseCommand baseCommand = new BaseCommand();
		Objects.requireNonNull(getCommand("prisonbridge")).setExecutor(baseCommand);
		Objects.requireNonNull(getCommand("prisonbridge")).setTabCompleter(baseCommand);

		BlocksCommand blocksCommand = new BlocksCommand();
		Objects.requireNonNull(getCommand("blocks")).setExecutor(blocksCommand);
		Objects.requireNonNull(getCommand("blocks")).setTabCompleter(blocksCommand);

		Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand());
	}

	public void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerManager(), this);
		Bukkit.getPluginManager().registerEvents(new LevelManager(), this);
	}

	public void registerPlaceholders() {
		new PAPIExtension("prisonbridge").register();
		PAPIExtension.registerPlaceholder(new BlocksForRankupPlaceholder());
		PAPIExtension.registerPlaceholder(new BlocksPlaceholder());
		PAPIExtension.registerPlaceholder(new PrestigePlaceholder());
		PAPIExtension.registerPlaceholder(new RankPlaceholder());

		PAPIExtension.registerPlaceholder(new BlocksForRankupRawPlaceholder());
		PAPIExtension.registerPlaceholder(new BlocksRawPlaceholder());
		PAPIExtension.registerPlaceholder(new RankRawPlaceholder());
	}

	public void initConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();

		final InputStream defConfigStream = getResource("lang.yml");
		if (defConfigStream == null) {
			return;
		}

		YamlConfiguration lang = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
		lang.options().copyDefaults(true);

		try {
			File file = new File(getDataFolder() + "/lang.yml");
			if(!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
				lang.save(file);
			}
		} catch(IOException e) { throw new RuntimeException(e); }
	}

	public static FileConfiguration getConfiguration() {
		return INSTANCE.getConfig();
	}

	public static String getLang(String path) {
		File file = new File(INSTANCE.getDataFolder() + "/lang.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (!config.contains(path)) new RuntimeException("WARNING! Config missing path: " + path).printStackTrace();
		return config.getString(path);
	}
}
package dev.kyro.wiji.prisonbridge;

import com.google.common.base.Charsets;
import dev.kyro.wiji.prisonbridge.commands.BaseCommand;
import dev.kyro.wiji.prisonbridge.controllers.PAPIExtension;
import dev.kyro.wiji.prisonbridge.sql.TableManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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
	}

	@Override
	public void onDisable() {

	}

	public void registerCommands() {
		BaseCommand baseCommand = new BaseCommand();
		Objects.requireNonNull(getCommand("prisonbridge")).setExecutor(baseCommand);
		Objects.requireNonNull(getCommand("prisonbridge")).setTabCompleter(baseCommand);
	}

	public void registerListeners() {

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
	}

	public static FileConfiguration getConfiguration() {
		return INSTANCE.getConfig();
	}

	public static String getLang(String path) {
		File file = new File(INSTANCE.getServer().getPluginsFolder().getPath() + "/lang.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		return config.getString(path);
	}
}
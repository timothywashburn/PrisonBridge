package dev.kyro.wiji.prisonbridge.sql;

import dev.kyro.wiji.prisonbridge.objects.PrisonPlayer;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TableManager {
	public static Plugin plugin;
	private static final List<SQLTable> tables = new ArrayList<>();

	public static void registerTables(Plugin instance) {
		plugin = instance;

		List<TableColumn> prisonPlayerFields = new ArrayList<>();

		int validFields = 0;

		for(Field field : PrisonPlayer.class.getDeclaredFields()) {
			if(Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) continue;

			Class<?> type = field.getType();

			try {
				TableStructure.Clazz.valueOf(type.getSimpleName());
			} catch(IllegalArgumentException e) {
				type = String.class;
			}

			prisonPlayerFields.add(new TableColumn(type, field.getName(), false, validFields == 0));
			validFields++;
		}

		new SQLTable(ConnectionInfo.PRISON_BRIDGE, "PlayerData",
				new TableStructure(prisonPlayerFields.toArray(new TableColumn[0]))
		);
	}

	protected static void registerTable(SQLTable table) {
		tables.add(table);
	}

	public static SQLTable getTable(String tableName) {
		for(SQLTable table : tables) {
			if(table.tableName.equals(tableName)) return table;
		}
		return null;
	}
}

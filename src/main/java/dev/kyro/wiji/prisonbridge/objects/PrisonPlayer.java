package dev.kyro.wiji.prisonbridge.objects;

import dev.kyro.wiji.prisonbridge.controllers.LevelManager;
import dev.kyro.wiji.prisonbridge.controllers.PlayerManager;
import dev.kyro.wiji.prisonbridge.misc.AMisc;
import dev.kyro.wiji.prisonbridge.sql.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrisonPlayer {
	public UUID uuid;
	public Long blocks;
	public Integer prestige;
	public Integer rank;

	public PrisonPlayer(UUID uuid) {
		this.uuid = uuid;

		load();

		PlayerManager.prisonPlayerList.add(this);
	}

	private long getRequiredBlocksForRankup() {
		return LevelManager.getBlocksForRank(prestige, rank);
	}

	public long getRemainingBlocksToRankup() {
		return Math.max(getRequiredBlocksForRankup() - blocks, 0);
	}

	public boolean canRankup() {
		return blocks >= getRequiredBlocksForRankup();
	}

	public String getBlocksFormatted() {
		return AMisc.formatBlocks(blocks);
	}

	public String getRankFormatted() {
		return String.valueOf(rank);
	}

	public String getPrestigeFormatted() {
		return String.valueOf(prestige);
	}

	public void load() {
		SQLTable table = TableManager.getTable("PlayerData");
		if(table == null) throw new RuntimeException("PlayerData table failed to register!");

		ResultSet rs = table.selectRow(new Constraint("uuid", uuid.toString()));

		try {
			if(!rs.next()) return;
		} catch(SQLException e) { throw new RuntimeException(e); }

		for(Field field : getClass().getDeclaredFields()) {
			if(Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) continue;

			Class<?> type = field.getType();
			String name = field.getName();
			boolean toDeserialize = false;

			Method method;
			TableStructure.Clazz clazz;

			try {
				clazz = TableStructure.Clazz.valueOf(type.getSimpleName());
			} catch(IllegalArgumentException e) {
				clazz = TableStructure.Clazz.String;
				toDeserialize = true;
			}

			try {
				method = ResultSet.class.getMethod("get" + (clazz.name().equals("Integer") ? "Int" : clazz.name()), String.class);
			} catch(NoSuchMethodException e) { throw new RuntimeException(e); }

			try {
				Object value = method.invoke(rs, name);
				if(toDeserialize) {
					Method fromString = type.getMethod("fromString", String.class);
					value = fromString.invoke(null, (String) value);
				}
				field.set(this, value);
			} catch(Exception e) { throw new RuntimeException(e); }

		}

		try {
			rs.close();
		} catch(SQLException e) { throw new RuntimeException(e); }
	}

	public void save() {
		SQLTable table = TableManager.getTable("PlayerData");
		if(table == null) throw new RuntimeException("PlayerData table failed to register!");

		List<QueryStorage> queries = new ArrayList<>();
		queries.add(new Constraint("uuid", uuid.toString()));

		for(Field field : getClass().getDeclaredFields()) {
			if(Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) continue;
			if(field.getName().equals("uuid")) continue;

			Class<?> type = field.getType();
			Object value;

			try {
				value = field.get(this);
			} catch(IllegalAccessException ex) { throw new RuntimeException(ex); }

			try {
				TableStructure.Clazz.valueOf(type.getSimpleName());
			} catch(IllegalArgumentException e) {
				if(!Serializable.class.isAssignableFrom(type)) throw new RuntimeException("Field " + field.getName() + " is not serializable");
				value = value.toString();
			}

			queries.add(new Value(field.getName(), value));
		}

		table.updateRow(queries.toArray(new QueryStorage[0]));
	}
}

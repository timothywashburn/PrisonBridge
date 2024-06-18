package dev.kyro.wiji.prisonbridge.controllers;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import dev.kyro.wiji.prisonbridge.objects.PAPIPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PAPIExtension extends PlaceholderExpansion {
	public static List<PAPIPlaceholder> registeredPlaceholders = new ArrayList<>();

	private final String identifier;

	public PAPIExtension(@NotNull String identifier) {
		this.identifier = identifier;
	}

	@Override
	public boolean persist(){
		return true;
	}

	@Override
	public boolean canRegister(){
		return true;
	}

	@Override
	public @NotNull String getAuthor(){
		return "Kyro & Wiji";
	}

	@Override
	public @NotNull String getIdentifier(){
		return identifier;
	}

	@Override
	public @NotNull String getVersion(){
		return PrisonBridge.INSTANCE.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, @NotNull String identifier){

		if(player == null) return "";

		for(PAPIPlaceholder registeredPlaceholder : registeredPlaceholders) {

			if(!registeredPlaceholder.getIdentifier().equalsIgnoreCase(identifier)) continue;

			return registeredPlaceholder.getValue(player);
		}

		return null;
	}

	public static void registerPlaceholder(PAPIPlaceholder placeholder) {
		registeredPlaceholders.add(placeholder);
	}
}

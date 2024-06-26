package dev.kyro.wiji.prisonbridge.controllers;

import dev.kyro.wiji.prisonbridge.PrisonBridge;
import dev.kyro.wiji.prisonbridge.objects.PAPIPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


			FunctionDetails functionDetails = parseFunction(identifier);

			if(!registeredPlaceholder.getIdentifier().equalsIgnoreCase(
					functionDetails != null ? functionDetails.getFunctionName() : identifier)) continue;

			return registeredPlaceholder.getValue(player,
					functionDetails == null ? new ArrayList<>() : functionDetails.getParameters());
		}

		return null;
	}

	public static void registerPlaceholder(PAPIPlaceholder placeholder) {
		registeredPlaceholders.add(placeholder);
	}

	public static FunctionDetails parseFunction(String input) {
		String regex = "(\\w+)\\(([^)]*)\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		if(matcher.find()) {
			String functionName = matcher.group(1);
			String paramsString = matcher.group(2);
			String[] paramsArray = paramsString.split("\\s*,\\s*");

			List<String> paramsList = new ArrayList<>(Arrays.asList(paramsArray));

			return new FunctionDetails(functionName, paramsList);
		}

		return null;
	}
}

class FunctionDetails {
	private final String functionName;
	private final List<String> parameters;

	public FunctionDetails(String functionName, List<String> parameters) {
		this.functionName = functionName;
		this.parameters = parameters;
	}

	public String getFunctionName() {
		return functionName;
	}

	public List<String> getParameters() {
		return parameters;
	}
}

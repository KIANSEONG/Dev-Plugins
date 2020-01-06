package com.kodestash.welcomemessage.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.kodestash.welcomemessage.WelcomeMessage;

public class Utils {
	
	private FileConfiguration config;
	
	public Utils(WelcomeMessage wm) {
		config = wm.getConfig();
	}
	
	public void sendTitle(Player player, String typeOfPlayer) {
		player.sendTitle(colourise(replaceVariables(config.getString(typeOfPlayer + ".title"), player)),
				colourise(replaceVariables(config.getString(typeOfPlayer + ".subtitle"), player)),
				 config.getInt(typeOfPlayer + ".fadein-time"),
				 config.getInt(typeOfPlayer + ".stay-time"),
				 config.getInt(typeOfPlayer + ".fadeout-time"));
	}
	
	private String colourise(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	private String replaceVariables(String s, Player player) {
		return s.replaceAll("%player%", player.getName());
	}
	
	public String format(Player player, String message) {
		return colourise(replaceVariables(message, player));
	}

}

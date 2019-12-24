package me.WelcomeMessage;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	private final Logger logger = this.getLogger();
	private Configuration config;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		config = getConfig();
		logger.info("WelcomeMessage enabled.");
	}
	
	@Override
	public void onDisable() { }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (player.hasPlayedBefore()) {
			if (config.getBoolean("returning-player.haveTitle")) {
				sendTitle(player, "returning-player.title-settings");
			} else {
				player.sendMessage(config.getString("returning-player.message"));
			}
		} else {
			if (config.getBoolean("new-player.haveTitle")) {
				sendTitle(player, "new-palyer.title-settings");
			} else {
				player.sendMessage(config.getString("new-palyer.message"));
			}
		}
		
	}
	
	private void sendTitle(Player player, String typeOfPlayer) {
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
		return s.replaceAll("\\{player\\}", player.getName());
	}

}

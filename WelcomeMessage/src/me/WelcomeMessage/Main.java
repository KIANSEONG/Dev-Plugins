package me.WelcomeMessage;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
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
		String playerName = player.getName();
		String typeOfPlayer = "";
		
		if (player.hasPlayedBefore()) {
			if (config.getBoolean("returning-player.haveTitle")) {
				typeOfPlayer = "returning-player.title-settings";
				player.sendTitle(config.getString(typeOfPlayer + ".title").replaceAll("\\{player\\}", playerName),
								 config.getString(typeOfPlayer + ".subtitle").replaceAll("\\{player\\}", playerName),
								 config.getInt(typeOfPlayer + ".fadein-time"),
								 config.getInt(typeOfPlayer + ".stay-time"),
								 config.getInt(typeOfPlayer + ".fadeout-time"));
			} else {
				player.sendMessage(config.getString(typeOfPlayer + ".message"));
			}
		} else {
			if (config.getBoolean(typeOfPlayer + ".haveTitle")) {
				typeOfPlayer = "new-player.title-settings";
				player.sendTitle(config.getString(typeOfPlayer + ".title").replaceAll("\\{player\\}", playerName),
								 config.getString(typeOfPlayer + ".subtitle").replaceAll("\\{player\\}", playerName),
								 config.getInt(typeOfPlayer + ".fadein-time"),
								 config.getInt(typeOfPlayer + ".stay-time"),
								 config.getInt(typeOfPlayer + ".fadeout-time"));
			} else {
				player.sendMessage(config.getString(typeOfPlayer + ".message"));
			}
		}
		
	}

}

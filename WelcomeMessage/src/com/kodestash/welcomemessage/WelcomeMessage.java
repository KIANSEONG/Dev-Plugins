package com.kodestash.welcomemessage;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.kodestash.welcomemessage.utils.Utils;

public class WelcomeMessage extends JavaPlugin implements Listener {
	
	// TODO - Organise code into different classes
	// TODO - Welcome message that consists of multiple lines
	// TODO - Cleanup plugin.yml
	// TODO - Update logger
	
	private final Logger LOGGER = this.getLogger();
	private Configuration config;
	private Utils utils = new Utils(this);
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		// Save configuration defaults
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		// Initialise config variable
		config = getConfig();
		
		LOGGER.info("Enabled Version " + this.getDescription().getVersion());
	}
	
	@Override
	public void onDisable() {
		utils = null;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		
		if (player.hasPlayedBefore()) {
			if (config.getBoolean("returning-player.haveTitle")) {
				utils.sendTitle(player, "returning-player.title-settings");
			} else {
				List<String> list = config.getStringList("returning-player.message");
				for (String s : list) {
					player.sendMessage(utils.format(player, s));
				}
			}
		} else {
			if (config.getBoolean("new-player.haveTitle")) {
				utils.sendTitle(player, "new-palyer.title-settings");
			} else {
				player.sendMessage(config.getString("new-palyer.message"));
			}
		}
		
	}
	
	

}

package me.exrider;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {
	
	private CombatTagManager tagman;
	
	private static List<String> blocked_commands = Arrays.asList("tp",  "tpa", "tpaccept", "spawn", "home", "ehome", "espawn", "etpa", "warp", "back", "fhome", "fwarp");
	
	public CommandEvent(CombatTagManager tagman) {
		this.tagman = tagman;
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		
		String command = event.getMessage();
		command = command.replace("/", "");
		if(command.startsWith("f")) command = command.replace(" ", "");
		else command = command.split(" ")[0];
		
		
		if(tagman.isCombatTagged(player)) {
			if(contains(blocked_commands, command)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.DARK_AQUA + "[PVP]" + ChatColor.AQUA + " >> " + ChatColor.RED + "You cannot use this command while combat tagged!");
			}
		}
	}
	
	
	
	private boolean contains(List<String> list, String str) {
		for(String lstr : list) {
			if(lstr.equals(str)) return true;
		}
		return false;
	}
	
}

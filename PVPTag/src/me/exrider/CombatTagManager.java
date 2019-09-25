package me.exrider;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatTagManager {

	private TagMain main;
	public CombatTagManager(TagMain flymain) {
		main = flymain;
	}
	
	
	
	public ConcurrentHashMap<UUID, Integer> combatTags = new ConcurrentHashMap<UUID, Integer>();
	
	
	
	public boolean isCombatTagged(Player player) {
		return combatTags.containsKey(player.getUniqueId());
	}
	
	
	public Integer getCombatTag(Player player) {
		if(!combatTags.containsKey(player.getUniqueId())) return 0;
		return combatTags.get(player.getUniqueId());
	}
	
	
	
	
	
	public void removeCombatTag(Player player) {
		if(!combatTags.containsKey(player.getUniqueId())) return;
		combatTags.remove(player.getUniqueId());
		
		player.sendMessage(ChatColor.DARK_AQUA + "[PVP]" + ChatColor.AQUA + " >> " + ChatColor.RED + "You are no longer in combat.");
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 1.2);
	}
	
	
	
	public void setCombatTag(Player player, Integer seconds) {
		if(!combatTags.containsKey(player.getUniqueId())) { 
			player.sendMessage(ChatColor.DARK_AQUA + "[PVP]" + ChatColor.AQUA + " >> " + ChatColor.RED + "You have been combat tagged for " + ChatColor.GRAY + seconds + ChatColor.RED + " seconds");
		}
		
		combatTags.put(player.getUniqueId(), seconds);
	}
	
	
	
	
	
	public void tagTimer() {
		new BukkitRunnable() {
			public void run() {
				if(combatTags.isEmpty()) return;
				
				for(UUID uuid : combatTags.keySet()) {
					Integer time = combatTags.get(uuid);
					
					if(combatTags.get(uuid) > 0) combatTags.put(uuid, time - 1);
										
					try { if(time <= 0) removeCombatTag(Bukkit.getPlayer(uuid)); }      //TODO: The plugin errors when a player's combat tag expires, combatTags.remove() raises error
					catch (Exception e) { }
				}			
			}
		}.runTaskTimer(main, 60, 20);
	}
	
	
	
}

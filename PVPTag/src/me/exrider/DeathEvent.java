package me.exrider;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
	
	private CombatTagManager tagman;
	
	public DeathEvent(CombatTagManager tagman) {
		this.tagman = tagman;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		if(tagman.isCombatTagged(player)) tagman.removeCombatTag(player);
	}
	
}

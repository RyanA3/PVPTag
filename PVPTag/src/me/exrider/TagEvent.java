package me.exrider;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TagEvent implements Listener {

	TagMain main;
	private CombatTagManager tagmanager;
	
	public TagEvent(TagMain main, CombatTagManager tagmanager) {
		this.main = main;
		this.tagmanager = tagmanager;
	}
	
	
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void combatEvent(EntityDamageByEntityEvent event) {
		if(event.isCancelled()) return;
		
		Entity entityDamager = getDamagingEntity(event);
		
		if(!(entityDamager instanceof Player)) return;
		if(!(event.getEntity() instanceof Player)) return;
		
		Player damager = (Player) entityDamager;
		Player victim = (Player) event.getEntity();
		
		if(victim.getGameMode() == GameMode.CREATIVE || victim.getGameMode() == GameMode.SPECTATOR) return;
		if(damager.getGameMode() == GameMode.CREATIVE || damager.getGameMode() == GameMode.SPECTATOR) return;
		
		//if(!EngineCanCombatHappen.get().canCombatDamageHappen(event, false)) return;
		
		if(tagmanager.combatTags.containsKey(damager.getUniqueId()) && tagmanager.getCombatTag(damager) < 120) tagmanager.setCombatTag(damager, tagmanager.getCombatTag(damager) + 2);
		else tagmanager.setCombatTag(damager, 15);
		if(tagmanager.combatTags.containsKey(victim.getUniqueId()) && tagmanager.getCombatTag(damager) < 120) tagmanager.setCombatTag(victim, tagmanager.getCombatTag(victim) + 2);
		else tagmanager.setCombatTag(victim, 15);
	}

	


	private Entity getDamagingEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		if(entity instanceof Projectile) {
			Projectile projectile = (Projectile) entity;
			if(projectile.getShooter() instanceof Entity) entity = (Entity) projectile.getShooter();
		}
		
		return entity;
	}	
}

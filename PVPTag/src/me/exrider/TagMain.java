package me.exrider;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TagMain extends JavaPlugin {
	
	public CombatTagManager tagmanager;
    
	public void onEnable() {
		tagmanager = new CombatTagManager(this);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new TagEvent(this, tagmanager), this);
		pm.registerEvents(new CommandEvent(tagmanager), this);
		pm.registerEvents(new DeathEvent(tagmanager), this);
		
		tagmanager.tagTimer();
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[PVP]" + ChatColor.AQUA + " >> " + ChatColor.GREEN + "Successfully started the PVPTag plugin.");
	}
	
	public void onDisable() {
		
	}
}

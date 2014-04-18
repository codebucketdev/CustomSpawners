package de.codebucket.customspawners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.codebucket.customspawners.spawner.CustomSpawner;

public class CustomSpawners extends JavaPlugin implements Listener
{
	private ConfigData data;
	public static CustomSpawners instance;
	
	@Override
	public void onEnable() 
	{
		Bukkit.getScheduler().runTask(this, new Runnable()
		{
			@Override
			public void run() 
			{
				enablePlugin();
			}
		});
	}
	
	private void enablePlugin()
	{
		//LOAD CONFIG
		instance = this;
		this.data = new ConfigData(this);
		data.loadConfig();
		
		//INIT COMMANDS AND EVENTS
		getCommand("customspawners").setExecutor(new Commands(this));
		getServer().getPluginManager().registerEvents(this, this);
		for(CustomSpawner spawner : data.getSpawners())
		{
			spawner.start();
		}
		
		//PLUGIN INFO
		String v = getDescription().getVersion();
		String au = getDescription().getAuthors().get(0);
		getLogger().info("Version " + v + " by " + au + ".");
	}
	
	@Override
	public void onDisable()
	{
		disablePlugin();
	}
	
	private void disablePlugin()
	{
		//STOP SPAWNERS
		for(CustomSpawner spawner : data.getSpawners())
		{
			spawner.stop();
		}
		
		//UNLOAD CONFIG FROM RAM
		data.unloadConfig();
		
		//PLUGIN FAREWELL
		String pn = getDescription().getName();
		getLogger().info(pn + " disabled!");
		getLogger().info("Thank you for using " + pn + "!");
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) 
	{
	   if (!event.isCancelled()) 
	    {
	        String msg = event.getMessage().toLowerCase();
	        if (msg.startsWith("/cs")) 
	        {
	        	String cmd = msg.substring(3);
	            getServer().dispatchCommand(event.getPlayer(), "customspawners" + cmd);
	            event.setCancelled(true);
	        }
	        if (msg.startsWith("/cspawners"))
	        {
	        	String cmd = msg.substring(10);
	            getServer().dispatchCommand(event.getPlayer(), "customspawners" + cmd);
	            event.setCancelled(true);
	        }
	    }
	}
	
	public static CustomSpawners getInstance()
	{
		return instance;
	}
	
	public ConfigData getData()
	{
		return data;
	}
}

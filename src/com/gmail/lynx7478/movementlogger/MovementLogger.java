package com.gmail.lynx7478.movementlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MovementLogger extends JavaPlugin implements Listener {
	
	private HashMap<String, File> files;
	
	@Override
	public void onEnable()
	{
		this.files = new HashMap<String, File>();
		this.getDataFolder().mkdirs();
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		String name = e.getPlayer().getName();
		if(this.files.containsKey(name))
		{
			return;
		}
		File f = new File(this.getDataFolder(), name+".mlog");
		if(!f.exists())
		{
			try {
				f.createNewFile();
				this.write(f, "---- Log started for " + name + " on "+System.currentTimeMillis()+" ----");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		this.files.put(name, f);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		String name = e.getPlayer().getName();
		try {
			this.write(this.files.get(name), "Moved from X: "+e.getFrom().getX()
					+", Y: "+e.getFrom().getY()+", Z: "+e.getFrom().getZ()
					+" to X: "+e.getTo().getX()+", Y: "+e.getTo().getY() +", Z: "+e.getTo().getZ());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void write(File f, String text) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		writer.write(text);
		writer.newLine();
		writer.flush();
	}

}

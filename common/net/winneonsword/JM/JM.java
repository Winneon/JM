package net.winneonsword.JM;

import java.util.List;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.winneonsword.JM.utils.ConfigUtils;
import net.winneonsword.JM.utils.DataUtils;
import net.winneonsword.JM.utils.LogUtils;

public class JM extends JavaPlugin {
	
	private PluginManager pm;
	
	private LogUtils logUtils;
	private ConfigUtils configUtils;
	private DataUtils dataUtils;
	
	private String[] configs;
	
	@Override
	public void onEnable(){
		
		pm = getServer().getPluginManager();
		new Utils();
		
		logUtils = new LogUtils(this);
		configUtils = new ConfigUtils(this);
		dataUtils = new DataUtils(this);
		
		this.configs = new String[] {
				
				"config"
				
		};
		
		for (String c : configs){
			
			getData().registerFile(c);
			
		}
		
		getCommand("jm").setExecutor(new CommandJm(this));
		pm.registerEvents(new JoinListener(this), this);
		
		List<String> users = getData().getConfig("config").getStringList("users");
		getUtils().setUsers(users);
		
		getLogging().info('e', "Loading " + users.size() + " users.");
		
		for (String n : getUtils().getUsers()){
			
			getUtils().setupPlayer(n);
			
		}
		
		getUtils().setMessageColour(getData().getConfig("config").getString("colours.message"));
		getUtils().setPlayerColour(getData().getConfig("config").getString("colours.playername"));
		
		getLogging().info("JoinMessages has been enabled.");
		
	}
	
	@Override
	public void onDisable(){
		
		List<String> users = getUtils().getUsers();
		
		for (String n : users){
			
			getUtils().savePlayer(n);
			
		}
		
		getData().getConfig("config").set("users", users);
		
		for (String c : configs){
			
			getData().saveConfig(c);
			getLogging().info('e', "Saved the config '" + c + "'.");
			
		}
		
		getLogging().info('c', "JoinMessages has been disabled.");
		
	}
	
	public PluginManager getPM(){
		
		return pm;
		
	}
	
	public ConfigUtils getData(){
		
		return configUtils;
		
	}
	
	public LogUtils getLogging(){
		
		return logUtils;
		
	}
	
	public DataUtils getUtils(){
		
		return dataUtils;
		
	}
	
}

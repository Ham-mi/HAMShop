package Main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import Commands.CmdShop;
import Events.EvtItemSet;
import Events.EvtOpenShop;
import Events.EvtPriceSet;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{
	public static HashMap<String,Integer> HamData = new HashMap<String, Integer>();
	
	public static String HAMShop = "[ Shop ]";
	
	public static Economy econ = null;
	
	public static Main instance;
	
	public static Plugin plugin;
	
	@Override
	public void onEnable() {
		getLogger().info("msg");
		getPlugin();
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			
			public void run() {
				if(!setupEconomy()) {
					getLogger().warning("Vault 플러그인의 setup에 실패. 플러그인 비활성화 진행");
					getServer().getPluginManager().disablePlugin(plugin);
					return;
				}
				
			}
		});

		instance = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		getServer().getPluginManager().registerEvents((Listener)new EvtItemSet(), plugin);
		getServer().getPluginManager().registerEvents((Listener)new EvtOpenShop(), plugin);
		getServer().getPluginManager().registerEvents((Listener)new EvtPriceSet(), plugin);
		getCommand("상점").setExecutor((CommandExecutor)new CmdShop());
		
	}
	
	public void getPlugin() {
		plugin = (Plugin)this;
	}
	
	public boolean setupEconomy() {
		if(getServer().getPluginManager().getPlugin("Vault")==null) return false;
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp == null) return false;
		econ = (Economy)rsp.getProvider();
		return (econ!=null) ;
	}
	
	public static Economy getEconomy() {
		return econ;
	}
	
	public static Main getInstance() {
		return instance;
	}
}

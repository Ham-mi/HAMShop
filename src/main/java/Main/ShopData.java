package Main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ShopData {
	public File file;
	public FileConfiguration customFile;
	public String name;
	public String path;
	
	public ShopData(String name) {
		this.name = name;
		this.path = String.valueOf(Bukkit.getServer().getPluginManager().getPlugin("HAMShop").getDataFolder().getPath())+"/"+name+".yml";
		this.file = new File(this.path.replace("/'", File.separator));
		if(this.file.exists())
			this.customFile = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
	}
	public void create() {
		if(!file.exists()) {
			try {
				file.createNewFile();
			}catch (IOException e) {}
		}
		this.customFile = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
	}
	
	public void delete() {
		if(file.exists()) file.delete();
	}
	
	public FileConfiguration get() {
		return this.customFile;
	}
	
	public void save() {
		try {
			customFile.save(file);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reload() {
		customFile = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
	}
	
	public boolean isExist() {
		if(file.exists()) return true;
		return false;
	}
}

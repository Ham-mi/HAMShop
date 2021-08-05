package Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Main;
import Main.ShopData;
import net.md_5.bungee.api.ChatColor;

public class CmdShop implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0 ) {
		      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 열기 <이름> : 상점을 엽니다." );
		      if(!sender.hasPermission("hamshop.admin") && sender instanceof Player) return true;
		      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 제작 <이름> : 상점을 제작합니다." );
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 삭제 <이름> : 상점을 삭제합니다." );
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 목록 : 상점 목록을 확인합니다." );
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 줄 <이름> <줄 수> : 상점의 줄 수를 설정합니다." );
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 설정 <이름> : 상점을 설정합니다." );
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 판매가격 <이름> <가격> : 상점 판매가격을 설정합니다.");
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 구매가격 <이름> <가격> : 상점 구매가격을 설정합니다." );
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 가격초기화 <이름> : 상점 가격을 초기화 합니다. ");
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + " /상점 리로드 : config.yml 을 리로드 합니다.");
    	      sender.sendMessage(String.valueOf(Main.HAMShop) + "가격 설정시에 가격에  '0'을 적을시 가격을 삭제합니다." );
    	      return true;
		}
		if(args[0].equals("열기") && args.length == 2) {
			if(!(sender instanceof Player)) return false;
			Player player = (Player)sender;
			ShopData shop = new ShopData(args[1]);
			if( !shop.isExist()) {
				sender.sendMessage(String.valueOf(Main.HAMShop)+"존재하지 않는 상점입니다.");
				return false;
			}
			int size = 9 * shop.get().getInt("line");
			Inventory inv = Bukkit.createInventory((InventoryHolder)player, size,"Shop-"+args[1]);
			ItemStack[] item = new ItemStack[size];
			List<Integer> buy_price = shop.get().getIntegerList("buy-price");
			List<Integer> sell_price = shop.get().getIntegerList("sell-price");
			List<String> lore_config = Main.getInstance().getConfig().getStringList("lores");
			for(int i =0; i<size;i++) {
				if(item[i] instanceof ItemStack) {
					ItemMeta meta = item[i].getItemMeta();
					List<String> lore = new ArrayList();
					if(item[i].getItemMeta().hasLore()) {
						lore = item[i].getItemMeta().getLore();
					}
					if(!((Integer)sell_price.get(i)).equals(Integer.valueOf(0)) || !((Integer)buy_price.get(i)).equals(Integer.valueOf(0))) {
						lore.add("");
						for(String data_lore:lore_config) {
							String str = data_lore;
							if(str.contains("<SELL_PRICE>") && ((Integer)sell_price.get(i)).equals(Integer.valueOf(0))) continue;
							if(str.contains("<BUY_PRICE>") && ((Integer)buy_price.get(i)).equals(Integer.valueOf(0)) ) continue;
							str = str.replace("<SELL_PRICE>", ((Integer)sell_price.get(i)).toString());
							str = str.replace("<BUY_PRICE>", ((Integer)sell_price.get(i)).toString());
							lore.add(ChatColor.translateAlternateColorCodes('&', str));
						}
					}
					meta.setLore(lore);
					item[i].setItemMeta(meta);
				}
			}
			inv.setContents(item);
			player.openInventory(inv);
			return true;
			
		}
		sender.sendMessage(String.valueOf(Main.HAMShop) + " 올바르지 않은 명령어 입니다." );
		return false;
	}
	

}

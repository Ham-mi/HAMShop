package Events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Main.Main;
import Main.ShopData;
public class EvtItemSet implements Listener{
	@EventHandler
	public void OnInventoryClose(InventoryCloseEvent event) {
//		if(event.getInventory().getViewers().isEmpty()) return;
//		HumanEntity entity = (HumanEntity)event.getInventory().getViewers().get(0);
		if(event.getInventory().getTitle().startsWith("Shop.Set-")) {
			String title = event.getInventory().getTitle().replace("Shop.Set-", "");
			Inventory inv = event.getInventory();
			ShopData shop = new ShopData(title);
			if(!shop.isExist()) return;
			
			int i = 0;
			byte b;
			int j;
			ItemStack[] arrayOfItemStack= inv.getContents();
			for(j= arrayOfItemStack.length , b=0 ; b<j; b++) {
				ItemStack item = arrayOfItemStack[b];
				shop.get().set("item."+i,item);
				i++;
			}
			shop.save();
			event.getPlayer().sendMessage(String.valueOf(Main.HAMShop)+" " + title + " 상점을 설정했습니다.");
			
		}
	}
}

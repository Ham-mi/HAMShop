package Events;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import Main.Main;
import Main.ShopData;

public class EvtPriceSet implements Listener{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
//		if(event.getInventory().getViewers().isEmpty()) return;
//		HumanEntity entity = (HumanEntity)event.getInventory().getViewers().get(0);
		if(event.getInventory().getTitle().startsWith("Shop.Buy-")) {
			String title = event.getInventory().getTitle().replace("Shop.Buy-", "");
			ShopData shop = new ShopData(title);
			if(!shop.isExist()) return;
			if(event.getSlot()<0) return;
			if(event.getSlot() != event.getRawSlot())return;
			if(event.getCurrentItem()== null) return;
			event.setCancelled(true);
			List<Integer> price = shop.get().getIntegerList("buy-price");
			int slot = event.getSlot();
			int setprice = ((Integer)Main.HamData.get("price"+event.getWhoClicked())).intValue();
			price.set(slot, Integer.valueOf(setprice));
			shop.get().set("buy-price",price);
			shop.save();
			event.getWhoClicked().sendMessage(String.valueOf(Main.HAMShop)+title+" 상점의 " +slot + " 슬롯의 구매가격을 " + setprice + " 원으로 설정했습니다.");
		}else if(event.getInventory().getTitle().startsWith("Shop.Sell-")) {
			String title = event.getInventory().getTitle().replace("Shop.Sell-", "");
			ShopData shop = new ShopData(title);
			if(!shop.isExist()) return;
			if(event.getSlot()<0) return;
			if(event.getSlot() != event.getRawSlot())return;
			if(event.getCurrentItem()== null) return;
			event.setCancelled(true);
			List<Integer> price = shop.get().getIntegerList("sell-price");
			int slot = event.getSlot();
			int setprice = ((Integer)Main.HamData.get("price"+event.getWhoClicked())).intValue();
			price.set(slot, Integer.valueOf(setprice));
			shop.get().set("sell-price",price);
			shop.save();
			event.getWhoClicked().sendMessage(String.valueOf(Main.HAMShop+title+" 상점의 " + slot + " 슬롯의 판매가격을 " + setprice + " 원으로 설정했습니다."));
		}
	}

}

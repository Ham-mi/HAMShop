package Events;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Main.Main;
import Main.ShopData;
import net.milkbowl.vault.economy.Economy;

public class EvtOpenShop implements Listener{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
//		if(event.getInventory().getViewers().isEmpty()) return;
//		HumanEntity entity = (HumanEntity)event.getInventory().getViewers().get(0);
		if(event.getInventory().getTitle().startsWith("Shop-")) {
			event.setCancelled(true);
			String title = event.getInventory().getTitle().replace("Shop-","");
			ShopData shop = new ShopData(title);
			if(!shop.isExist()) return;
			if(event.getSlot()<0) return;
			if(event.getSlot() != event.getRawSlot()) return;
			if(event.getCurrentItem() == null) return;
			Player player = (Player)event.getWhoClicked();
			int slot = event.getSlot();
			int size = 9 * shop.get().getInt("line");
			ItemStack[] item = new ItemStack[size];
			
			for( int i=0; i<size; i++) {
				item[i] = shop.get().getItemStack("item." + i);
			}
			Economy eco = Main.getEconomy();
			if(event.getClick().isRightClick()) {
				List<Integer> sell_price = shop.get().getIntegerList("sell-price");
				if(((Integer)sell_price.get(slot)).equals(Integer.valueOf(0))) return;
				if(!player.getInventory().containsAtLeast(item[slot], 1)) {
					player.sendMessage(String.valueOf(Main.HAMShop)+"판매할 아이템을 가지고 있지 않습니다.");
					return;
				}
				int amount = event.getCurrentItem().getAmount();
				int p_amount = getAmount(player, item[slot]);
				if(p_amount < 1) {
					player.sendMessage(String.valueOf(Main.HAMShop)+"판매할 아이템을 가지고 있지 않습니다.");
					return;
				}
				if(!event.getClick().isShiftClick() && p_amount > amount) p_amount = amount;
				double sell = ((Integer)sell_price.get(slot)).doubleValue() / amount * p_amount;
				ItemStack last_item = item[slot];
				
			}
		}
	}
	
	public int getAmount(Player player, ItemStack item) {
		int amount = 0;
		for( int i = 0; i < 36; i++) {
			ItemStack itemslot = player.getInventory().getItem(i);
			if(itemslot != null && itemslot.isSimilar(item)) {
				amount += itemslot.getAmount();
			}
		}
		return amount;
	}
	
	public boolean isCanHold(Player player , ItemStack item) {
		Inventory inv = Bukkit.createInventory(null, 36);
		for(int i=0; i<36; i++) {
			inv.setItem(i, player.getInventory().getItem(i));
		}
		Map<Integer,ItemStack> map = inv.addItem(new ItemStack[] {item});
		if(!map.isEmpty()) return false;
		return true;
	}
}

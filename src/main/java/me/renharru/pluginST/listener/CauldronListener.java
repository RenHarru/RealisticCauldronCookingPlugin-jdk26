package me.renharru.pluginST.listener;

import org.bukkit.inventory.EquipmentSlot;
import me.renharru.pluginST.service.SoupService;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CauldronListener implements Listener {

    private final SoupService soupService;

    public CauldronListener(SoupService soupService) {
        this.soupService = soupService;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getClickedBlock() == null) return;
        System.out.println("Тип блока: " + e.getClickedBlock().getType());

        Block block = e.getClickedBlock();
        if (block.getType() != Material.CAULDRON
                && block.getType() != Material.WATER_CAULDRON) return;

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        soupService.handleInteraction(player, block, item);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        soupService.removeCauldron(e.getBlock().getLocation());
    }
}
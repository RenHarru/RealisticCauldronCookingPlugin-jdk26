package me.renharru.pluginST.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class HeatChecker {

    public static boolean isHeated(Block cauldron) {
        Material below = cauldron.getRelative(BlockFace.DOWN).getType();

        return below == Material.CAMPFIRE
                || below == Material.SOUL_CAMPFIRE
                || below == Material.LAVA
                || below == Material.FIRE;
    }
}
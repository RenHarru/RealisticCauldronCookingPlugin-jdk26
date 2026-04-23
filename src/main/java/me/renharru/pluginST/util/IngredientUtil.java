package me.renharru.pluginST.util;

import org.bukkit.Material;

public class IngredientUtil {

    public static boolean isIngredient(Material mat) {
        return mat == Material.RED_MUSHROOM
                || mat == Material.BROWN_MUSHROOM
                || mat == Material.RABBIT
                || mat == Material.CARROT
                || mat == Material.BAKED_POTATO;
    }
}
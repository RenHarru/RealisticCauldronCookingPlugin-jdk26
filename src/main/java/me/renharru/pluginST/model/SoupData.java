package me.renharru.pluginST.model;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public class SoupData {

    private final Set<Material> ingredients = new HashSet<>();
    private int servings = 0;
    private Material result;

    public int getServings() {
        return servings;
    }

    public void addIngredient(Material mat) {
        ingredients.add(mat);
    }

    public boolean has(Material mat) {
        return ingredients.contains(mat);
    }

    public boolean isCooked() {
        return servings > 0;
    }

    public void finish(Material result) {
        this.result = result;
        this.servings = 3;
        ingredients.clear();
    }

    public boolean hasSoup() {
        return servings > 0;
    }

    public void takeServing() {
        servings --;
    }

    public boolean isEmpty() {
        return servings <= 0;
    }

    public Material getResult() {
        return result;
    }
}
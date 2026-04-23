package me.renharru.pluginST.service;

import org.bukkit.block.data.Levelled;
import me.renharru.pluginST.model.SoupData;
import me.renharru.pluginST.util.HeatChecker;
import me.renharru.pluginST.util.IngredientUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SoupService {

    private final Map<Location, SoupData> cauldrons = new HashMap<>();

    private void syncWater(Block block, int servings) {

        if (servings <= 0) {
            block.setType(Material.CAULDRON);
            return;
        }

        if (servings > 3) {
            servings = 3;
        }

        Levelled level = (Levelled) block.getBlockData();
        level.setLevel(servings);
        block.setBlockData(level);
    }

    public void handleInteraction(Player player, Block block, ItemStack item) {

        if (!HeatChecker.isHeated(block)) {
            player.sendMessage("§cКотёл должен стоять на источнике тепла!");
            return;
        }

        if (!(block.getBlockData() instanceof Levelled) || block.getType() != Material.WATER_CAULDRON) {
            player.sendMessage("§cВ котле нет воды!");
            return;
        }

        Location loc = block.getLocation();
        SoupData data = cauldrons.getOrDefault(loc, new SoupData());

        // Добавление ингредиентов
        if (IngredientUtil.isIngredient(item.getType())) {


            Levelled level = (Levelled) block.getBlockData();
            if (level.getLevel() < 3) {
                player.sendMessage("§cКотёл должен быть полностью заполнен!");
                return;
            }

            if (data.isCooked()) {
                player.sendMessage("§cСуп уже готов!");
                return;
            }

            data.addIngredient(item.getType());
            item.setAmount(item.getAmount() - 1);

            cauldrons.put(loc, data);
            player.sendMessage("§aДобавлен ингредиент");

            // Проверка рецептов
            checkRecipe(player, loc, data);
            return;
        }

        // Забор супа
        if (item.getType() == Material.BOWL && data.hasSoup()) {

            item.setAmount(item.getAmount() - 1);
            player.getInventory().addItem(new ItemStack(data.getResult()));
            data.takeServing();

            syncWater(block, data.getServings());

            if (data.isEmpty()) {
                cauldrons.remove(loc);
            }

            player.sendMessage("§eТы набрал суп");

        }
    }

    private void checkRecipe(Player player, Location loc, SoupData data) {

        // Грибной суп
        if (data.has(Material.RED_MUSHROOM) && data.has(Material.BROWN_MUSHROOM)) {
            data.finish(Material.MUSHROOM_STEW);
            player.sendMessage("§6Грибной суп готов!");
        }

        // Кроличий суп
        if (data.has(Material.RABBIT) && data.has(Material.CARROT) && data.has(Material.BAKED_POTATO)) {
            data.finish(Material.RABBIT_STEW);
            player.sendMessage("§6Кроличий суп готов!");
        }

        cauldrons.put(loc, data);
    }

    public void removeCauldron(Location loc) {
        cauldrons.remove(loc);
    }
}
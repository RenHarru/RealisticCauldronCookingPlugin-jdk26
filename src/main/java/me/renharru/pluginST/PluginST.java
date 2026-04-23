package me.renharru.pluginST;

import me.renharru.pluginST.listener.CauldronListener;
import me.renharru.pluginST.service.SoupService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class RealisticCauldronCookingPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        SoupService soupService = new SoupService();

        Bukkit.getPluginManager().registerEvents(
                new CauldronListener(soupService),
                this
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package com.redmancometh.tilelevels;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.redmancometh.redcore.config.ConfigManager;
import com.redmancometh.tilelevels.config.MessageConfig;
import com.redmancometh.tilelevels.config.TierEntry;
import com.redmancometh.tilelevels.config.TileConfig;
import com.redmancometh.tilelevels.listeners.TileListeners;
import com.redmancometh.tilelevels.mediators.TierManager;

import co.q64.tile.extra.UpgradableTile;
import lombok.Getter;

public class Tiles extends JavaPlugin
{
    private ConfigManager<TileConfig> configManager;
    @Getter
    private TileManager tiles;
    @Getter
    private TierManager tiers;

    @Override
    public void onEnable()
    {
        this.tiers = new TierManager();
        configManager = new ConfigManager("tiles.json", TileConfig.class);
        configManager.init(this);
        tiles = new TileManager();
        tiles.init();
        tiers.init();
        Bukkit.getPluginManager().registerEvents(new TileListeners(), this);
    }

    @Override
    public void onDisable()
    {
        tiles.disable();
    }

    /*
    public static List<Upgrade> upgrades(Class<? extends UpgradableTile> tileClass)
    {
        return instance().upgrades.upgrades(tileClass).getUpgrades();
    }
    */

    public static MessageConfig msg()
    {
        return cfg().getMessages();
    }

    public static TierEntry tier(Class<? extends UpgradableTile> tileClass, int tier)
    {
        return instance().tiers.getTier(tileClass, tier);
    }

    public static void reload()
    {
        instance().configManager.reload();
        instance().tiles = new TileManager();
        instance().tiles.init();
    }

    public static TileManager tiles()
    {
        return instance().tiles;
    }

    public static TileConfig cfg()
    {
        return instance().configManager.getCurrentConfig();
    }

    public static Tiles instance()
    {
        return JavaPlugin.getPlugin(Tiles.class);
    }
}

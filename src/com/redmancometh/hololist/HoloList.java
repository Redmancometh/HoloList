package com.redmancometh.hololist;

import org.bukkit.plugin.java.JavaPlugin;

import com.redmancometh.hololist.config.ConfigManager;
import com.redmancometh.hololist.config.HoloListConfig;
import com.redmancometh.hololist.factories.HologramFactory;
import com.redmancometh.hololist.mediators.HologramManager;

public class HoloList extends JavaPlugin
{
    private HologramManager holoMan;
    private ConfigManager<HoloListConfig> config;
    private HologramFactory factory;

    public void onEnable()
    {
        factory = new HologramFactory();
        config = new ConfigManager("holos.json", HoloListConfig.class);
        config.init(this);
        holoMan = new HologramManager();
        holoMan.init();
    }

    public static HologramFactory factory()
    {
        return instance().factory;
    }

    public static HoloListConfig config()
    {
        return instance().config.getCurrentConfig();
    }

    public static HologramManager holoManager()
    {
        return instance().holoMan;
    }

    public static HoloList instance()
    {
        return JavaPlugin.getPlugin(HoloList.class);
    }
}

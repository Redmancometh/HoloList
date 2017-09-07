package com.redmancometh.hololist;

import org.bukkit.plugin.java.JavaPlugin;

import com.redmancometh.hololist.config.ConfigManager;
import com.redmancometh.hololist.config.HoloListConfig;

public class HoloList extends JavaPlugin
{
    private HologramManager holoMan;
    private ConfigManager<HoloListConfig> config;

    public void onEnable()
    {
        config = new ConfigManager("holo.json", HoloListConfig.class);
        config.init(this);
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

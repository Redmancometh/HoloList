package com.redmancometh.hololist;

import org.bukkit.plugin.java.JavaPlugin;

public class HoloList extends JavaPlugin
{
    public void onEnable()
    {
        
    }

    public static HoloList instance()
    {
        return JavaPlugin.getPlugin(HoloList.class);
    }
}

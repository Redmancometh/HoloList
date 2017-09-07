package com.redmancometh.hololist.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;

import com.redmancometh.hololist.RankedHologram;
import com.redmancometh.hololist.config.HologramEntry;

public class HologramFactory
{
    private Map<String, Class<? extends RankedHologram>> classMap = new ConcurrentHashMap();
    private Map<String, Constructor> constructorMap = new ConcurrentHashMap();

    public void addHologramType(String name, Class<? extends RankedHologram> type)
    {
        this.classMap.put(name, type);
        Constructor construct;
        try
        {
            construct = type.getDeclaredConstructor(Location.class, int.class);
            construct.setAccessible(true);
            constructorMap.put(name, construct);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
        }
    }

    public RankedHologram buildHoloList(HologramEntry entry)
    {
        try
        {
            RankedHologram holo = (RankedHologram) constructorMap.get(entry.getName()).newInstance(entry.getLoc(), entry.getPageLength());
            holo.setLoc(entry.getLoc());
            return holo;
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

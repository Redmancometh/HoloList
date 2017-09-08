package com.redmancometh.hololist.mediators;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.bukkit.Bukkit;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.redmancometh.hololist.HoloList;
import com.redmancometh.hololist.RankedHologram;
import com.redmancometh.hololist.config.HoloListConfig;
import com.redmancometh.hololist.tasks.PlayerExaminerTask;

import lombok.Data;

@Data
public class HologramManager implements Iterable<RankedHologram>
{
    private Multimap<String, RankedHologram> holoMap = HashMultimap.create();
    private PlayerExaminerTask task;

    public void init()
    {
        registerClassTypes();
        task = new PlayerExaminerTask();
        HoloListConfig cfg = HoloList.config();
        task.runTaskTimer(HoloList.instance(), 20, cfg.getHoloCheckRate());
        cfg.getHolograms().forEach((holo) ->
        {
            RankedHologram hologram = HoloList.factory().buildHoloList(holo);
            holoMap.put(holo.getName(), hologram);
        });
    }

    public void registerClassTypes()
    {
        HoloList.config().getHoloClasses().forEach((holoClass) ->
        {
            String className = holoClass.getClassName();
            String internalName = holoClass.getInternalName();
            Class holoType;
            try
            {
                Bukkit.getLogger().log(Level.INFO, "\n[Holo-List] Registering type: " + internalName + "\n\tClass: " + className);
                holoType = Class.forName(className);
                //  Any time this type is null an exception will be thrown..I'm pretty sure.
                HoloList.factory().addHologramType(internalName, holoType);
            }
            catch (ClassNotFoundException e)
            {
                Bukkit.getLogger().log(Level.SEVERE, "\nThe class name: " + className + " was not found loaded in the JVM!");
                Bukkit.getLogger().log(Level.SEVERE, "\nUnable to instantiate holograms with internal name: " + internalName + "!");
            }
        });
    }

    public void addHologram(String name, RankedHologram h)
    {
        holoMap.put(name, h);
    }

    public void removeHologram(String name)
    {
        holoMap.removeAll(name);
    }

    public Stream<RankedHologram> stream()
    {
        return holoMap.values().stream();
    }

    @Override
    public Iterator<RankedHologram> iterator()
    {
        return holoMap.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super RankedHologram> action)
    {
        holoMap.values().forEach(action);
    }
}

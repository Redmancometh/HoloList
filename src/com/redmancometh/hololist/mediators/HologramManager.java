package com.redmancometh.hololist.mediators;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.redmancometh.hololist.ExampleRankedHologram;
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
        HoloList.factory().addHologramType("exampleranked", ExampleRankedHologram.class);
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

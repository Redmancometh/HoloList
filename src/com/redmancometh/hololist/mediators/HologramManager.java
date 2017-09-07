package com.redmancometh.hololist.mediators;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.redmancometh.hololist.HoloList;
import com.redmancometh.hololist.RankedHologram;
import com.redmancometh.hololist.tasks.PlayerExaminerTask;

import lombok.Data;

@Data
public class HologramManager implements Iterable<RankedHologram>
{
    private Map<String, RankedHologram> holoMap;
    private PlayerExaminerTask task;

    public void init()
    {
        task = new PlayerExaminerTask();
        task.runTaskTimer(HoloList.instance(), 20, HoloList.config().getHoloCheckRate());
    }

    public void addHologram(RankedHologram h)
    {
        holoMap.put(h.getName(), h);
    }

    public void removeHologram(String name)
    {
        holoMap.remove(name);
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

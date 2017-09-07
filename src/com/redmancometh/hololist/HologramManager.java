package com.redmancometh.hololist;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.Data;

@Data
public class HologramManager implements Iterable<RankedHologram>
{
    private Map<String, RankedHologram> holoMap;

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

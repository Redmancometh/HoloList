package com.redmancometh.hololist;

import java.util.Map;

import lombok.Data;

@Data
public class HologramManager
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
}

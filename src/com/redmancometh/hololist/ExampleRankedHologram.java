package com.redmancometh.hololist;

import org.bukkit.Location;
import com.redmancometh.hololist.hooking.SimpleHookTest;

public class ExampleRankedHologram extends RankedHologram
{

    public ExampleRankedHologram(Location loc, int pageLength)
    {
        super(loc, pageLength, new SimpleHookTest());
    }

}

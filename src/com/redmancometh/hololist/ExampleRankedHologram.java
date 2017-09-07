package com.redmancometh.hololist;

import org.bukkit.Location;
import com.redmancometh.hololist.hooking.SimpleHookTest;

public class ExampleRankedHologram extends RankedHologram
{
    private String format = "&b&l[&a&l#%r &e&l%l&b&l]";

    public ExampleRankedHologram(Location loc, int pageLength)
    {
        super(loc, pageLength, new SimpleHookTest());
    }

    @Override
    public void setFormat(String format)
    {
        this.format = format;
    }

    @Override
    public String getFormat()
    {
        return format;
    }

}

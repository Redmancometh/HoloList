package com.redmancometh.hololist.holograms;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import com.gmail.filoghost.holographicdisplays.object.PluginHologram;
import com.gmail.filoghost.holographicdisplays.object.line.CraftItemLine;
import com.gmail.filoghost.holographicdisplays.object.line.CraftTextLine;

public class RedmanHologram extends PluginHologram
{

    public RedmanHologram(Location source, Plugin plugin)
    {
        super(source, plugin);
    }

    
    
    @Override
    public CraftItemLine appendItemLine(ItemStack itemStack)
    {
        throw new NotImplementedException();
    }

    @Override
    public CraftTextLine appendTextLine(String text)
    {
        throw new NotImplementedException();
    }

}

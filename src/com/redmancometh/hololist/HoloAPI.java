package com.redmancometh.hololist;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.object.PluginHologramManager;
import com.redmancometh.hololist.holograms.RedmanHologram;

public class HoloAPI
{
    public static RedmanHologram create(Location loc)
    {
        RedmanHologram hologram = new RedmanHologram(loc, HoloList.instance());
        PluginHologramManager.addHologram(hologram);
        return hologram;
    }

    public static RedmanHologram createIndividualHologram(Location loc, Player p)
    {
        RedmanHologram hologram = create(loc);
        hologram.getVisibilityManager().setVisibleByDefault(false);
        hologram.getVisibilityManager().showTo(p);
        return hologram;
    }

}

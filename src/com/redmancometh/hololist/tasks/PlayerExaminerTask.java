package com.redmancometh.hololist.tasks;

import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.redmancometh.hololist.HoloList;
import com.redmancometh.hololist.RankedHologram;

public class PlayerExaminerTask extends BukkitRunnable
{
    Consumer<Player> iterator = (p) ->
    {
        HoloList.holoManager().stream().filter((holo) -> isInWorld(p, holo)).forEach((holo) ->
        {
            UUID uuid = p.getUniqueId();
            if (holo.isViewing(uuid) && !isClose(p, holo))
                holo.removeViewer(uuid);
            else if (!holo.isViewing(uuid) && isClose(p, holo)) holo.addViewer(p);
        });
    };

    @Override
    public void run()
    {
        Bukkit.getOnlinePlayers().forEach(iterator);
    }

    public boolean isClose(Player p, RankedHologram holo)
    {
        return holo.getLoc().distanceSquared(p.getLocation()) < HoloList.config().getHoloDestroyDistance();
    }

    public boolean isInWorld(Player p, RankedHologram holo)
    {
        System.out.println(holo);
        System.out.println(holo.getLoc());
        System.out.println(holo.getLoc().getWorld());
        return holo.getLoc().getWorld().getName().equals(p.getWorld().getName());
    }

}

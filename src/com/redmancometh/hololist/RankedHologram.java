package com.redmancometh.hololist;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import com.redmancometh.hololist.hooking.Hook;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class RankedHologram<T>
{
    private Location loc;
    private int length;
    private String name;
    private Hook<T> dataHook;
    //Final so it will be excluded from @AllArgsConstructor
    private final Map<UUID, Hologram> viewers;
    private static TouchHandler prevPage = new TouchHandler();
    private static TouchHandler nextPage = new TouchHandler();

    public void update()
    {
        setCache(dataHook.getCacheUpdater().get());
    }

    public void addViewer(Player p)
    {
        Hologram h = HolographicDisplaysAPI.createIndividualHologram(HoloList.instance(), loc, p, "lines");
    }

    public void removeViewer(UUID uuid)
    {
        Hologram h = viewers.get(uuid);
        h.delete();
        if (h == null) throw new IllegalStateException("removeViewer was called on an");
    }

    public boolean isViewing(UUID uuid)
    {
        return viewers.containsKey(uuid);
    }

    public Hologram spawnHologramFor(Player p)
    {
        return HolographicDisplaysAPI.createIndividualHologram(HoloList.instance(), loc, p, "testing", "hologram");
    }

    /**
     * Update the board using the cache. Will never fetch from db.
     */
    public abstract void updateBoard();

    /**
     * 
     * @param cache
     */
    public abstract void setCache(Collection<T> cache);

    /**
     * 
     * @return returns a SpecialFuture representing the completion of the task to update the cache.
     * Use .thenAccept and .thenApply for non-blocking, async logic.
     */
    public SpecialFuture<Collection<T>> getCache()
    {
        return SpecialFuture.supplyAsync(() -> dataHook.getCacheUpdater().get());
    }

    /**
     * Sets the collection field equal to the supplied parameter
     * @param c
     */
    public abstract void setRanks(Collection c);

}

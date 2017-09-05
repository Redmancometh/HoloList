package com.redmancometh.hololist;

import java.util.Collection;

import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
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
    private Hologram h;

    public void update()
    {

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

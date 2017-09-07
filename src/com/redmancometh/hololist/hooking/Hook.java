package com.redmancometh.hololist.hooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.redmancometh.hololist.SpecialFuture;
import lombok.Data;

@Data
public class Hook<T>
{
    private Supplier<List<T>> cacheUpdater;
    private List<T> cache = new ArrayList();

    public Hook(Supplier<List<T>> cacheUpdater)
    {
        this.cacheUpdater = cacheUpdater;
    }

    /**
     * 
     * @return returns a SpecialFuture representing the completion of the task to update the cache.
     * Use .thenAccept and .thenApply for non-blocking, async logic.
     * 
     * The SpecialFuture itself returns the new cache data.
     */
    public SpecialFuture<Collection<T>> updateCache()
    {
        return SpecialFuture.supplyAsync(() -> cacheUpdater.get());
    }

}

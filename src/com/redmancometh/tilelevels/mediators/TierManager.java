package com.redmancometh.tilelevels.mediators;

import java.util.HashMap;
import java.util.Map;

import com.redmancometh.tilelevels.Tiles;
import com.redmancometh.tilelevels.config.TierConfig;
import com.redmancometh.tilelevels.config.TierEntry;

import co.q64.tile.extra.UpgradableTile;

public class TierManager
{
    private Map<Class<? extends UpgradableTile>, TierConfig> tierMap;

    public void init()
    {
        tierMap = new HashMap();
        Tiles.cfg().getTierConfigs().forEach((tierConfig) ->
        {
            tierMap.put(tierConfig.getTierClass(), tierConfig);
        });
    }

    public int getHighest(Class<? extends UpgradableTile> tileClass)
    {
        return tierMap.get(tileClass).getTiers().size() - 1;
    }

    public TierConfig cfg(Class<? extends UpgradableTile> tileClass)
    {
        return tierMap.get(tileClass);
    }

    public TierEntry getTier(Class<? extends UpgradableTile> tileClass, int tier)
    {
        if (Tiles.cfg().isDebugMode()) System.out.println("Trying to get for class: " + tileClass + " at level: " + tier);
        return tiers(tileClass).getTierAt(tier);
    }

    public TierConfig tiers(Class<? extends UpgradableTile> tileClass)
    {
        return tierMap.get(tileClass);
    }

}

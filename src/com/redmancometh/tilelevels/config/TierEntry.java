package com.redmancometh.tilelevels.config;

import com.redmancometh.tilelevels.Tiles;

import co.q64.tile.extra.UpgradableTile;
import lombok.Data;

@Data
public class TierEntry
{
    private long cost;
    private int tier;

    public long costToTier(int toTier, Class<? extends UpgradableTile> tileClass)
    {
        if (Tiles.cfg().isDebugMode()) System.out.println("To Tier: " + toTier);
        TierConfig cfg = Tiles.instance().getTiers().cfg(tileClass);
        long costTotal = 0;
        for (int x = tier; x < Math.min(cfg.getTiers().size(), toTier); x++)
        {
            if (Tiles.cfg().isDebugMode()) System.out.println("X: " + x);
            if (Tiles.cfg().isDebugMode()) System.out.println("costTotal+=" + cfg.getTierAt(x).getCost());
            costTotal += cfg.getTierAt(x).getCost();
        }
        return costTotal;
    }

    public long costToMax(Class<? extends UpgradableTile> tileClass)
    {
        int levels = levelsToMax(tileClass);
        return costToTier(levels, tileClass);
    }

    public int levelsToMax(Class<? extends UpgradableTile> tileClass)
    {
        TierConfig cfg = Tiles.instance().getTiers().cfg(tileClass);
        int levels = 0;
        for (int x = tier; x < cfg.getTiers().size(); x++)
            levels += 1;
        return levels;
    }
}

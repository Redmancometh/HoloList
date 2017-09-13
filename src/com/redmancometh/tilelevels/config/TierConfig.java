package com.redmancometh.tilelevels.config;

import java.util.List;

import co.q64.tile.extra.UpgradableTile;
import lombok.Data;

@Data
public class TierConfig
{
    private List<TierEntry> tiers;
    private Class<? extends UpgradableTile> tierClass;

    public int getHighestTier()
    {
        return tiers.size() - 1;
    }

    public TierEntry getTierAt(int tier)
    {
        return tiers.get(tier);
    }

}

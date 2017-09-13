package com.redmancometh.tilelevels.mediators;

import java.util.Map;

import com.redmancometh.tilelevels.config.UpgradeConfig;

import co.q64.tile.extra.UpgradableTile;

public class UpgradeManager<T extends UpgradableTile>
{
    private Map<Class<? extends UpgradableTile>, UpgradeConfig> upgradeMap;

    public UpgradeConfig upgrades(Class<? extends UpgradableTile> tileClass)
    {
        return upgradeMap.get(tileClass);
    }

    public void init()
    {

    }
}

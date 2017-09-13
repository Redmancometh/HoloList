package com.redmancometh.tilelevels.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.redmancometh.redcore.config.MenuTemplate;
import com.redmancometh.redmenus.config.IndexedIcon;
import com.redmancometh.redmenus.config.ReloadableMenuConfig;
import com.redmancometh.redmenus.config.TypedIndexIcon;

import co.q64.tile.extra.UpgradableTile;
import lombok.Data;

@Data
public class TileConfig implements ReloadableMenuConfig
{
    private String upgradeMenuTitle, upgradeName;
    private TypedIndexIcon<UpgradableTile> statusIcon, upgradeOneIcon, upgradeFiveIcon, upgradeFullIcon, breakIcon;
    private MenuTemplate upgradeMenuTemplate;
    private List<TierConfig> tierConfigs;
    private List<UpgradeConfig> upgradeConfigs;
    private MessageConfig messages;
    private boolean debugMode;

    @Override
    public List<IndexedIcon> getIndexedIcons()
    {
        return new ArrayList();
    }

    @Override
    public List<TypedIndexIcon> getTypedIcons()
    {
        TypedIndexIcon[] typedIcons =
        { statusIcon, upgradeOneIcon, upgradeFiveIcon, upgradeFullIcon, breakIcon };
        return new ArrayList(Arrays.asList(typedIcons));
    }
}

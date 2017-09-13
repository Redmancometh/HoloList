package com.redmancometh.tilelevels.menus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.bukkit.entity.Player;
import com.redmancometh.orbs.Orbs;
import com.redmancometh.redmenus.absraction.CloseCallbackMenu;
import com.redmancometh.redmenus.absraction.TypedMenu;
import com.redmancometh.redmenus.config.TypedIndexIcon;
import com.redmancometh.redmenus.menus.TypedSelector;
import com.redmancometh.tilelevels.Tiles;
import com.redmancometh.tilelevels.config.MessageConfig;
import com.redmancometh.tilelevels.config.TierEntry;
import com.redmancometh.tilelevels.config.TileConfig;
import com.redmancometh.tilelevels.mediators.TierManager;
import co.q64.tile.extra.UpgradableTile;

public class TileGUI extends TypedMenu<UpgradableTile> implements CloseCallbackMenu
{
    private static TypedSelector<UpgradableTile> selector = new TypedSelector();
    private static Map<String, BiFunction<Player, UpgradableTile, String>> placeholders = new HashMap();

    public TileGUI()
    {
        super(Tiles.cfg().getUpgradeMenuTitle(), Tiles.cfg().getUpgradeMenuTemplate(), 54);
        addPlaceholder("%cl", (p, upgradable) ->
        {
            return upgradable.getTier() + "";
        });
        TierManager tiers = Tiles.instance().getTiers();
        addPlaceholder("%c1", (p, tile) ->
        {
            if (Tiles.cfg().isDebugMode()) System.out.println("Highest c1: " + tiers.getHighest(tile.getClass()));
            int levelTo = Math.min(tile.getTier() + 1, tiers.getHighest(tile.getClass()));
            return tiers.getTier(tile.getClass(), tile.getTier()).costToTier(levelTo, tile.getClass()) + "";
        });
        addPlaceholder("%1l", (p, tile) -> Math.min(tile.getTier() + 1, tiers.getHighest(tile.getClass())) - tile.getTier() + "");
        addPlaceholder("%c5", (p, tile) ->
        {
            int toTier = Math.min(tile.getTier() + 5, tiers.getHighest(tile.getClass()));
            if (Tiles.cfg().isDebugMode()) System.out.println("toTier: " + toTier);
            if (Tiles.cfg().isDebugMode()) System.out.println("cost 5: " + tiers.getTier(tile.getClass(), tile.getTier()).costToTier(toTier, tile.getClass()));
            return tiers.getTier(tile.getClass(), tile.getTier()).costToTier(toTier, tile.getClass()) + "";
        });
        addPlaceholder("%l5", (p, tile) ->
        {
            int levelTo = Math.min(tile.getTier() + 5, tiers.getHighest(tile.getClass()));
            return levelTo - tile.getTier() + "";
        });
        addPlaceholder("%lmax", (p, tile) -> (tiers.getHighest(tile.getClass()) - tile.getTier()) + "");
        TierEntry tier;
        addPlaceholder("%cmax", (p, tile) ->
        {
            TierEntry entry = tiers.getTier(tile.getClass(), tile.getTier());
            return entry.costToTier(tiers.getHighest(tile.getClass()), tile.getClass()) + "";
        });
        initButtons();
    }

    public void initButtons()
    {
        TierManager tiers = Tiles.instance().getTiers();
        TileConfig cfg = Tiles.cfg();
        TypedIndexIcon<UpgradableTile> status = cfg.getStatusIcon();
        TypedIndexIcon<UpgradableTile> upgradeOne = cfg.getUpgradeOneIcon();
        TypedIndexIcon<UpgradableTile> upgradeFive = cfg.getUpgradeFiveIcon();
        TypedIndexIcon<UpgradableTile> upgradeFull = cfg.getUpgradeFullIcon();
        TypedIndexIcon<UpgradableTile> breakIcon = cfg.getBreakIcon();
        setButton(status.getSlot(), status.typeButton((clickType, tile, p) ->
        {

        }, this));
        setButton(upgradeOne.getSlot(), upgradeOne.typeButton((clickType, tile, p) ->
        {
            Class c = tile.getClass();
            int maxLevel = tiers.getHighest(c);
            if (maxLevel == tile.getTier()) return;
            int upgradeTo = Math.min(tile.getTier() + 1, maxLevel);
            tryUpgrade(tiers.getTier(c, tile.getTier()).costToTier(upgradeTo, c), tile, upgradeTo, p);
        }, this));
        setButton(upgradeFive.getSlot(), upgradeFive.typeButton((clickType, tile, p) ->
        {
            Class c = tile.getClass();
            int maxLevel = tiers.getHighest(c);
            if (maxLevel == tile.getTier()) return;
            int upgradeTo = Math.min(tile.getTier() + 5, maxLevel);
            tryUpgrade(tiers.getTier(c, tile.getTier()).costToTier(upgradeTo, c), tile, upgradeTo, p);
        }, this));
        setButton(upgradeFull.getSlot(), upgradeFull.typeButton((clickType, tile, p) ->
        {
            Class c = tile.getClass();
            int maxLevel = tiers.getHighest(c);
            if (maxLevel == tile.getTier()) return;
            tryUpgrade(tiers.getTier(c, tile.getTier()).costToTier(maxLevel, c), tile, maxLevel, p);
        }, this));
        setButton(breakIcon.getSlot(), breakIcon.typeButton((clickType, tile, p) ->
        {

        }, this));
    }

    public void tryUpgrade(long cost, UpgradableTile tile, int levels, Player p)
    {
        MessageConfig msg = Tiles.msg();
        Orbs.orbs().getRecord(p.getUniqueId()).thenAccept((orbPlayer) ->
        {
            if (!orbPlayer.hasEnough(cost))
            {
                String message = msg.getPrefix() + msg.getNotEnoughOrbs();
                message = message.replace("%co", orbPlayer.getOrbs() + "").replace("%on", cost + "");
                p.sendMessage(message);
                p.closeInventory();
                return;
            }
            orbPlayer.removeOrbs(cost);
            String message = msg.getUpgradedSuccessfully().replace("%c", cost + "").replace("%co", orbPlayer.getOrbs() + "");
            p.sendMessage(message);
            System.out.println("Tile Currently level: " + tile.getTier());
            System.out.println("Upgrading to: " + levels + " for: " + cost);
            tile.upgradeTo(levels);
            System.out.println("Now level: " + tile.getTier());
            p.closeInventory();
        });
    }

    @Override
    public Supplier<TypedSelector> getSelector()
    {
        return () -> selector;
    }

    @Override
    public void onClose(Player p)
    {
        getSelector().get().deSelect(p.getUniqueId());
    }

    @Override
    public Supplier<Map<String, BiFunction<Player, UpgradableTile, String>>> getTypedPlaceholders()
    {
        return () -> placeholders;
    }

}

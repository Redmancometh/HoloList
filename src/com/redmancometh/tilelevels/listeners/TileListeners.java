package com.redmancometh.tilelevels.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import com.redmancometh.tilelevels.menus.TileGUI;

import co.q64.tile.extra.UpgradableTile;
import co.q64.tile.nms.tiles.CustomTileEntity;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntity;

public class TileListeners implements Listener
{
    @EventHandler
    public void openMenu(PlayerInteractEvent e)
    {
        Block b = e.getClickedBlock();
        if (!e.getPlayer().isSneaking()) return;
        if (b != null && b.getType() != Material.AIR)
        {
            Location loc = b.getLocation();
            BlockPosition pos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            TileEntity tile = ((CraftWorld) b.getWorld()).getHandle().getTileEntity(pos);
            if (tile != null && (tile instanceof UpgradableTile && tile instanceof CustomTileEntity))
            {
                e.setCancelled(true);
                UpgradableTile upgradable = (UpgradableTile) tile;
                Player p = e.getPlayer();
                TileGUI gui = new TileGUI();
                gui.getSelector().get().select(p.getUniqueId(), upgradable);
                p.openInventory(gui.getConstructInventory().apply(p, upgradable));
            }
        }
    }
}

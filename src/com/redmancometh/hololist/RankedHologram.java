package com.redmancometh.hololist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redmancometh.hololist.config.HoloListConfig;
import com.redmancometh.hololist.hooking.Hook;

import lombok.Data;

@Data
public abstract class RankedHologram<T>
{
    private Location loc;

    private int pageLength;
    private Hook<T> dataHook;
    //Final so it will be excluded from @AllArgsConstructor
    private Map<UUID, Hologram> viewers;
    private Map<UUID, Integer> pages;

    /**
     * This will change from the default format.
     */
    public abstract void setFormat(String format);

    public abstract String getFormat();

    /**
     * 
     * @param loc The location the hologram will be located at
     * @param pageLength How many items per page
     * @param dataHook The data hook that fetches the rank, caches it, etc.
     * 
     * ALWAYS PROVIDE THE DATA HOOK IN THE derivative class's superconstructor call!
     * The implementation *cannot* have any other argument.
     * 
     * Any implementation must always have one constructor of Class(Location loc, int pageLength) in that order.
     */
    public RankedHologram(Location loc, int pageLength, Hook<T> dataHook)
    {
        this.loc = loc;
        this.pageLength = pageLength;
        this.dataHook = dataHook;
        viewers = new HashMap();
        pages = new HashMap();
        dataHook.updateCache().get();
        scheduleCacheFetch();
    }

    public void scheduleCacheFetch()
    {
        HoloListConfig cfg = HoloList.config();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HoloList.instance(), () -> dataHook.updateCache(), cfg.getHoloUpdateRate(), cfg.getHoloUpdateRate());
    }

    public void addViewer(Player p)
    {
        UUID uuid = p.getUniqueId();
        Hologram h = makeHolo(p);
        viewers.put(p.getUniqueId(), h);
        int page = pages.getOrDefault(uuid, 1);
        turnPage(page, h, uuid);
    }

    /**
     * 
     * @param page
     * @param cache
     * @param h This should be a specific player's individual hologram
     */
    public void turnPage(int page, Hologram h, UUID uuid)
    {
        List<T> cache = dataHook.getCache();
        int startIndex = (page - 1) * pageLength;
        int endIndex = Math.min((page * pageLength), cache.size());
        h.clearLines();
        h.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&a&lPage: &b&l" + page));
        for (int x = startIndex; x < endIndex; x++)
        {
            T line = cache.get(x);
            
            h.appendTextLine(ChatColor.translateAlternateColorCodes('&', getFormat().replace("%r", "" + x).replace("%l", line.toString())));
        }
        attachTouchButtons(page, h, uuid);
    }

    private void attachTouchButtons(int page, Hologram h, UUID uuid)
    {

        if (page > 1)
        {
            //h.appendTextLine("<--- Previous").setTouchHandler((p) -> turnPage(page + 1, h, uuid));
            h.appendItemLine(new ItemStack(Material.REDSTONE)).setTouchHandler((p) -> turnPage(page - 1, h, uuid));
        }
        int maxPage = dataHook.getCache().size() / pageLength;
        if (page < maxPage) //h.appendTextLine("Next --->").setTouchHandler((p) -> turnPage(page + 1, h, uuid));
            h.appendItemLine(new ItemStack(Material.EMERALD)).setTouchHandler((p) -> turnPage(page + 1, h, uuid));
    }

    private Hologram makeHolo(Player p)
    {
        Hologram h = HologramsAPI.createHologram(HoloList.instance(), loc);
        VisibilityManager visiblility = h.getVisibilityManager();
        visiblility.setVisibleByDefault(false);
        visiblility.showTo(p);
        return h;
    }

    public void removeViewer(UUID uuid)
    {
        Hologram h = viewers.get(uuid);
        if (h == null) throw new IllegalStateException("removeViewer was called on an");
        h.delete();
        viewers.remove(uuid);
    }

    public boolean isViewing(UUID uuid)
    {
        return viewers.containsKey(uuid);
    }

}

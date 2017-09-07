package com.redmancometh.hololist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redmancometh.hololist.config.HoloListConfig;
import com.redmancometh.hololist.hooking.Hook;

import lombok.Data;
import net.md_5.bungee.api.ChatColor;

@Data
public abstract class RankedHologram<T>
{
    private Location loc;
    private int length;
    private Hook<T> dataHook;
    //Final so it will be excluded from @AllArgsConstructor
    private Map<UUID, Hologram> viewers;
    private Map<UUID, Integer> pages;
    private String format = "&b&l[&e&l#%r %l&b&l]";

    /**
     * This will change from the default format.
     */
    public void setFormat(String format)
    {
        this.format = format;
    }

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
        this.length = pageLength;
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
        Hologram h = makeHolo();
        viewers.put(p.getUniqueId(), h);
        dataHook.getCache().forEach((item) -> System.out.println("ITEM: " + item));
        List<T> cache = dataHook.getCache();
        int page = pages.get(uuid);
        for (int x = 0; x < cache.size(); x++)
        {
            T line = cache.get(x);
            h.appendTextLine(ChatColor.translateAlternateColorCodes('&', format.replace("%r", "" + x).replace("%l", line.toString())));
        }
    }

    private void attachTouchButtons()
    {
        
    }

    private Hologram makeHolo()
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

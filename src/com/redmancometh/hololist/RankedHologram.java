package com.redmancometh.hololist;

import java.util.HashMap;
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

@Data
public abstract class RankedHologram<T>
{
    private Location loc;
    private int length;
    private Hook<T> dataHook;
    //Final so it will be excluded from @AllArgsConstructor
    private Map<UUID, Hologram> viewers;

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
        scheduleCacheFetch();
    }

    public void scheduleCacheFetch()
    {
        HoloListConfig cfg = HoloList.config();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HoloList.instance(), () -> update(), cfg.getHoloUpdateRate() * 20, cfg.getHoloUpdateRate() * 20);
    }

    public void update()
    {
        dataHook.updateCache().thenAccept((rankList) ->
        {
            this.viewers.forEach((uuid, holo) ->
            {
                System.out.println(rankList.size() + " SIZE");
                holo.clearLines();
                rankList.forEach((line) -> holo.appendTextLine(line.toString()));
            });
        });
    }

    public void addViewer(Player p)
    {
        Hologram h = HologramsAPI.createHologram(HoloList.instance(), loc);
        VisibilityManager visiblility = h.getVisibilityManager();
        visiblility.setVisibleByDefault(false);
        visiblility.showTo(p);
        viewers.put(p.getUniqueId(), h);
        this.dataHook.getCache().forEach((item) ->
        {
            h.appendTextLine(item.toString());
        });
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

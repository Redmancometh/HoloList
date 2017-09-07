package com.redmancometh.hololist.config;

import org.bukkit.Location;

import lombok.Data;

@Data
public class HologramEntry
{
    private String name;
    private Location loc;
    private int pageLength;
}

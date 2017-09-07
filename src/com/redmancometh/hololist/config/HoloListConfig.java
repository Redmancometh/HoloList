package com.redmancometh.hololist.config;

import java.util.List;

import lombok.Data;

@Data
public class HoloListConfig
{
    private long holoUpdateRate;
    private long holoCheckRate;
    private long holoDestroyDistance;
    private List<HologramEntry> holograms;
}

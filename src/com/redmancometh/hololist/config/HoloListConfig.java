package com.redmancometh.hololist.config;

import lombok.Data;

@Data
public class HoloListConfig
{
    private long holoUpdateRate;
    private long holoCheckRate;
    private long holoDestroyDistance;
}

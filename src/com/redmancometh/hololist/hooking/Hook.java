package com.redmancometh.hololist.hooking;

import java.util.Collection;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Hook<T>
{
    private Supplier<Collection<T>> cacheUpdater;

}

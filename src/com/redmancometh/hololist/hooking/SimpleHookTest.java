package com.redmancometh.hololist.hooking;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SimpleHookTest extends Hook<String>
{
    private List<String> cache;

    public SimpleHookTest(Supplier<Collection<String>> cacheUpdater)
    {
        super(cacheUpdater);
    }

}

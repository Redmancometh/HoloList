package com.redmancometh.hololist.hooking;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleHookTest extends Hook<String>
{
    private List<String> cache = new ArrayList();

    public SimpleHookTest()
    {
        super(() ->
        {
            List<String> testList = new ArrayList();
            String testString = "a";
            for (int x = 0; x <= 30; x++)
            {
                testString += "a";
                testList.add(testString);
            }
            return new ArrayList();
        });
    }

}

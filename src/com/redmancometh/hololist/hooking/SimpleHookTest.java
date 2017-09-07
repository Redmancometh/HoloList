package com.redmancometh.hololist.hooking;

import java.util.ArrayList;
import java.util.List;

public class SimpleHookTest extends Hook<String>
{
    public SimpleHookTest()
    {
        super(() ->
        {
            List<String> testList = new ArrayList();
            String testString = "Redmancometh";
            for (int x = 0; x <= 60; x++)
            {
                testList.add(testString + x);
            }
            System.out.println("Returning " + testList.size());
            return testList;
        });
    }
}

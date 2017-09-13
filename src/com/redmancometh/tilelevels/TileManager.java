package com.redmancometh.tilelevels;

import com.redmancometh.redmenus.Menus;
import com.redmancometh.tilelevels.menus.TileGUI;

import lombok.Data;

@Data
public class TileManager
{
    private TileGUI titleGUI;

    public void init()
    {
        initMenus();
    }

    public void disable()
    {
        unregisterMenus();
    }

    public void unregisterMenus()
    {
        Menus.getInstance().getMenuManager().unregisterTyped(titleGUI);
    }

    public void initMenus()
    {
        this.titleGUI = new TileGUI();
        Menus.getInstance().getMenuManager().addTypedMenu(titleGUI);
    }
}

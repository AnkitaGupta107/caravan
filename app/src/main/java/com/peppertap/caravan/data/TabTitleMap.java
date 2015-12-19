package com.peppertap.caravan.data;

import com.peppertap.caravan.activities.TabbedActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samvedana on 20/11/15.
 */
public class TabTitleMap {
    private static HashMap<TabbedActivity.ActivityType, List<String>> tabTitleMap = null;

    private static void initTabTitleMap() {
        tabTitleMap = new HashMap<>();

        ArrayList<String> home_tiles = new ArrayList<>();
        home_tiles.add("Dashboard");
        home_tiles.add("Shop");

        tabTitleMap.put(TabbedActivity.ActivityType.HOME, home_tiles);

        ArrayList<String> cart_tiles = new ArrayList<>();
        cart_tiles.add("Perishables");
        cart_tiles.add("Non Perishables");

        tabTitleMap.put(TabbedActivity.ActivityType.CART, cart_tiles);
    }

    public static HashMap<TabbedActivity.ActivityType, List<String>> getTabTitleMap() {
        if (tabTitleMap == null) {
            initTabTitleMap();
        }
        return tabTitleMap;
    }
}

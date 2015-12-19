package com.peppertap.caravan.events;

/**
 * Created by samvedana on 24/11/15.
 */
public class DataEvents {

    public static class FetchProductFragmentData {
        public enum FetchDataType {
            NONE, TOP_100_WEEKLY, TOP_100_MONTHLY, OUT_OF_STOCK
        }
        FetchDataType fetchDataType;
        boolean clearCacheBeforeRequest;

        public FetchProductFragmentData(FetchDataType type) {
            fetchDataType = type;
            clearCacheBeforeRequest = false;
        }

        public FetchProductFragmentData(FetchDataType type, boolean shouldClearCache) {
            fetchDataType = type;
            clearCacheBeforeRequest = shouldClearCache;
        }

        public FetchDataType getDataTypeToFetch() {
            return fetchDataType;
        }

        public boolean shouldClearCacheBeforeRequest() {
            return clearCacheBeforeRequest;
        }
    }

    public static class refreshEvent {
        int currentTab;

        public refreshEvent(int tabPos) {
            currentTab = tabPos;
        }

        public int getCurrentTabPosition() {
            return currentTab;
        }
    }

}

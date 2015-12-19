package com.peppertap.caravan.events;

import java.util.ArrayList;

/**
 * Created by anshul on 24/11/15.
 */
public class UIEvents {

    public static class FabButtonClickEvent {
        int selectedTabPosition;

        public FabButtonClickEvent(int currentSelectedPosition) {
            this.selectedTabPosition = currentSelectedPosition;
        }

        public int getSelectedTabPosition() {
            return selectedTabPosition;
        }

    }

    public static class UpdateFabButtonEvent {
        int selectedTabPosition;

        public UpdateFabButtonEvent(int currentSelectedPosition) {
            this.selectedTabPosition = currentSelectedPosition;
        }

        public int getSelectedTabPosition() {
            return selectedTabPosition;
        }

    }

    public static class FabButtonVisibility {
        boolean visibility;

        public FabButtonVisibility(boolean show) {
            this.visibility = show;
        }

        public boolean fabButtonVisibility() {
            return visibility;
        }

    }

    public static class ShowProductUpdateSnackBar {
        boolean refresh;
        ArrayList<String> messages = new ArrayList<>();
        Source source;

        public enum Source {
            UPDATE_PRODUCT, OUT_OF_STOCK_CLICK
        }

        public ShowProductUpdateSnackBar(Source src, boolean refresh, String p_msg, String q_msg, String s_msg) {
            if (p_msg != null) messages.add(p_msg);
            if (q_msg != null) messages.add(q_msg);
            if (s_msg != null) messages.add(s_msg);
            this.refresh = refresh;
            source = src;
        }

        public ShowProductUpdateSnackBar(Source src, boolean refresh, String e_msg) {
            if (e_msg != null) messages.add(e_msg);
            this.refresh = refresh;
            source = src;
        }

        public ArrayList<String> getMessages() {
            return messages;
        }

        public Source getSource() {
            return source;
        }

        public boolean refreshNeeded() {
            return refresh;
        }
    }
}

package com.peppertap.caravan.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.peppertap.caravan.R;
import com.peppertap.caravan.events.DataEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by samvedana on 19/12/15.
 */
public class HomeActivity extends TabbedActivity {

    @Override
    protected void onFabButtonClick() {
        startActivity(new Intent(this, CheckoutActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                handleRefresh();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleRefresh() {
        EventBus.getDefault().post(new DataEvents.refreshEvent(selectedTabPosition));
    }
}

package com.peppertap.caravan.viewHolders;

import android.content.res.Resources;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;
import com.peppertap.caravan.activities.HomeActivity;
import com.peppertap.caravan.cartHelpers.PrimaryCartHelper;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.events.PrimaryCartDbEvents;
import com.peppertap.caravan.network.Urls;
import com.peppertap.caravan.utils.MoneyHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by samvedana on 19/12/15.
 */
public class ProductHolder extends RecyclerView.ViewHolder {
    Resources resources;

    @InjectView(R.id.line_item_title)
    TextView title;
    @InjectView(R.id.line_item_wt)
    TextView attribute;
    @InjectView(R.id.mrp)
    TextView price;
    @InjectView(R.id.total_mrp)
    TextView total;
    @InjectView(R.id.quantity)
    EditText quantity;
    @InjectView(R.id.line_item_image)
    ImageView image;

    int num_qt;

    ProductHelper.ProductGroup data;
    ProductHelper.ProductItem item;
    CaravanApp globalApplication;
    HomeActivity activity;

    public ProductHolder(View itemView, CaravanApp app, HomeActivity activity) {
        super(itemView);
        globalApplication = app;
        this.activity = activity;
        ButterKnife.inject(this, itemView);
        resources = itemView.getResources();

        TextWatcher editTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable qt) {
                int num = 0;
                try {
                    num = Integer.parseInt(qt.toString());
                    if (num > 0) {
                        num_qt = num;
                        calculateAndDisplayTotal();
                    }
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    Timber.e("Empty quantity or invalid int");
                }
            }
        };

        quantity.addTextChangedListener(editTextWatcher);
    }

    public void bindData(ProductHelper.ProductGroup group) {
        data = group;
        item = group.getSelected();

        String url = Urls.getAppendedMediaUrl(item);
        Ion.with(image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .smartSize(true)
                .load(url);
        title.setText(item.getTitle());
        price.setText("\u20B9"+ item.getSale_price());
        attribute.setText(item.getDisplay_attribute());
        new UpdateQuantityFromCart(this).execute();
    }

    private class UpdateQuantityFromCart extends AsyncTask<Void, Void, Integer> {
        private ProductHolder adapterHolder;

        public UpdateQuantityFromCart(ProductHolder adapterHolder){
            this.adapterHolder = adapterHolder;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return PrimaryCartHelper.getInstance(globalApplication)
                    .getQuantityInCart(item.getProduct_id());
        }

        protected void onProgressUpdate(Integer... progress) {}

        protected void onPostExecute(Integer result) {
            num_qt = result;
            adapterHolder.quantity.setText(num_qt + "");
            adapterHolder.calculateAndDisplayTotal();
        }
    }

    private void calculateAndDisplayTotal() {
        float sale_total = num_qt * Float.valueOf(item.getSale_price());
        total.setText("\u20B9"+ MoneyHelper.toMoneyTwoDecimals(sale_total));
    }

    @OnClick(R.id.addToCartBtn)
    public void addToCart() {
        if (num_qt > 0) {
            PrimaryCartDbEvents.ProductAddEvent event = new PrimaryCartDbEvents.ProductAddEvent(item, "Shop Page");
            event.productsToBeAddedProgramaticallyWithQt(num_qt);
            activity.showToastMsg(item.getTitle() + " added to cart (" + num_qt + " units)");
            EventBus.getDefault().post(event);
        }
    }
}

package com.peppertap.caravan.viewHolders;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;
import com.peppertap.caravan.cartHelpers.PrimaryCartHelper;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.events.PrimaryCartDbEvents;
import com.peppertap.caravan.network.Urls;
import com.peppertap.caravan.utils.MoneyHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import pepperTap.LineItem;
import timber.log.Timber;

/**
 * Created by samvedana on 19/12/15.
 */
public class LineItemHolder extends RecyclerView.ViewHolder {
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
    @InjectView(R.id.addToCartBtn)
    Button addToCartButton;

    int num_qt;

    LineItem item;
    CaravanApp globalApplication;

    public LineItemHolder(View itemView, CaravanApp app) {
        super(itemView);
        globalApplication = app;
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
                        updateLineItem();
                    }
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    Timber.e("Empty quantity or invalid int");
                }
            }
        };

        quantity.addTextChangedListener(editTextWatcher);
    }

    public void bindData(LineItem lineItem) {
        item = lineItem;
        addToCartButton.setVisibility(View.GONE);

        String url = Urls.getAppendedMediaUrl(item.getImage_url());
        Ion.with(image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .smartSize(true)
                .load(url);
        title.setText(item.getTitle());
        price.setText("\u20B9"+ item.getSale_price());
        attribute.setText(item.getDisplay_attribute());
        num_qt = item.getQuantity();
        calculateAndDisplayTotal();
    }

    private void calculateAndDisplayTotal() {
        float sale_total = num_qt * Float.valueOf(item.getSale_price());
        total.setText("\u20B9"+ MoneyHelper.toMoneyTwoDecimals(sale_total));
    }

    public void updateLineItem() {
        if (num_qt != item.getQuantity()) {
            if (num_qt > 0) {
                PrimaryCartDbEvents.ProductAddEvent event = new PrimaryCartDbEvents.ProductAddEvent(item, "Cart Page");
                event.productsToBeAddedProgramaticallyWithQt(num_qt);
                EventBus.getDefault().post(event);
            }
            else {
                PrimaryCartDbEvents.ProductReduceEvent event = new PrimaryCartDbEvents.ProductReduceEvent(item, "Cart Page");
                event.productToBeRemoved(true);
                EventBus.getDefault().post(event);
            }
        }
    }
}

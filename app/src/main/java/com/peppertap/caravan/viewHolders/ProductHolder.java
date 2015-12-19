package com.peppertap.caravan.viewHolders;

import android.content.res.Resources;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;
import com.peppertap.caravan.cartHelpers.PrimaryCartHelper;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.network.Urls;
import com.peppertap.caravan.utils.MoneyHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

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

    ProductHelper.ProductGroup data;
    ProductHelper.ProductItem item;
    CaravanApp globalApplication;

    public ProductHolder(View itemView, CaravanApp app) {
        super(itemView);
        globalApplication = app;
        ButterKnife.inject(this, itemView);
        resources = itemView.getResources();
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
            adapterHolder.quantity.setText(result + "");
        }
    }
}

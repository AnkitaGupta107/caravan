package com.peppertap.caravan.DAOHelpers;

import android.util.Log;

import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.network.Urls;

import java.util.List;

import pepperTap.Cart;
import pepperTap.LineItem;

/**
 * Created by subodh on 1/10/14.
 */
public class LineItemRepository {

    private static final String TAG = "LINE_ITEM_REPOSITORY";

    private static LineItemRepository instance = null;

    DbWrapper wrapper;

    private LineItemRepository(CaravanApp app){
        wrapper = app.getDbWrapper();
    }

    public static LineItemRepository getInstance(CaravanApp app){
        if(instance == null){
            instance = new LineItemRepository(app);
        }
        return instance;
    }

    public void addLineItem( LineItem item){
        Log.d(TAG, "In add line item method");
        insertOrUpdate(item);
    }

    public void reduceLineItem(LineItem item){
        LineItem temp = getLineItemForId(item.getId());
        if(temp == null){
            //do nothing
            return;
        }
        else{
            int quantity = item.getQuantity();
            if(quantity > 0){
                insertOrUpdate(item);
            }
            else if(quantity == 0){
                deleteLineItemWithId(temp.getId());
            }
            else{
                Log.e(TAG, "Something went wrong while reducing lineItem from db");
            }
        }
    }

/*
    public Boolean insertFromProductItem(ProductHelper.ProductItem productItem){
        Long product_id = new Long(Long.parseLong(productItem.getProduct_id()));
        LineItem item;
        Boolean inserted = false;
        try {
            item = getLineItemForId(product_id);
            int quantity = item.getQuantity() + 1;
            item.setQuantity(quantity);
            item.setTotal(item.getSale_price()*quantity);
        } catch(android.database.sqlite.SQLiteException e){
            item = fromProductItem(productItem);
            inserted = true;
        }
        insertOrUpdate(item);
        return inserted;
    }
*/
    public LineItem fromProductItem(ProductHelper.ProductItem productItem){
        String productId = productItem.getProduct_id();
        LineItem item = new LineItem();
        item.setCartID(new Long(1));
        item.setTitle(productItem.getTitle());
        item.setProduct_code(productId);
        item.setDisplay_attribute(productItem.getDisplay_attribute());
        item.setQuantity(1);
        item.setMax_limit(productItem.getMax_limit());
        item.setImage_url(Urls.getAppendedMediaUrl(productItem));
        item.setMrp(Float.parseFloat(productItem.getMrp()));
        item.setSale_price(Float.parseFloat(productItem.getSale_price()));
        item.setTotal(item.getSale_price());
        item.setClassification_id(productItem.getClassification_id());
        item.setDiscount_string(productItem.getDiscount_string());
        item.setClassifications(productItem.getProduct_classification().toString());
        return item;
    }

    public ProductHelper.ProductGaClassification getProductGaClassification(LineItem lineItem) {
        String classification_str = lineItem.getClassifications();
        return ProductHelper.ProductGaClassification.fromStringToObject(classification_str);
    }

    public void addTileInfoToLineItem(LineItem lineItem, String tile) {
        ProductHelper.ProductGaClassification classification = getProductGaClassification(lineItem);
        classification.addTileInfo(tile);
        lineItem.setClassifications(classification.toString());
    }

    public void insertOrUpdate(LineItem lineItem) {
        wrapper.getLineItemDao().insertOrReplace(lineItem);
    }

    public void clearLineItems( ) {
        wrapper.getLineItemDao().deleteAll();
    }

    public void deleteLineItemWithId(long id) {
        wrapper.getLineItemDao().delete(getLineItemForId(id));
    }

    public LineItem getLineItemForId(long id) {
        return wrapper.getLineItemDao().load(id);
    }

    public List<LineItem> getAllLineItems( ) {
        return wrapper.getLineItemDao().loadAll();
    }

    public List<LineItem> getLineItemsForCart( long cartId) {
        return wrapper.getLineItemDao()._queryCart_LineItemList(cartId);
    }

    public void deleteLineItemsForCart(long cartId) {
        List<LineItem> listLineItems = getLineItemsForCart(cartId);
        for (LineItem x: listLineItems) {
            wrapper.getLineItemDao().delete(x);
        }
    }

    public LineItem getLineItemFromGivenCart(String product_code) {
        List<LineItem> lineItems = getAllLineItems();
        for (LineItem x : lineItems) {
            if (x.getProduct_code().equals(product_code)) {
                return x;
            }
        }
        return null;
    }

    public LineItem getLineItemFromGivenCart(Cart cart, String product_code) {
        List<LineItem> lineItems = getLineItemsForCart(cart.getId());
        for (LineItem x : lineItems) {
            if (x.getProduct_code().equals(product_code)) {
                return x;
            }
        }
        return null;
    }
}

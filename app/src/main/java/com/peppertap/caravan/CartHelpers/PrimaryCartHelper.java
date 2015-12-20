package com.peppertap.caravan.cartHelpers;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.DAOHelpers.CartRepository;
import com.peppertap.caravan.DAOHelpers.LineItemRepository;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.events.CartEvents;
import com.peppertap.caravan.events.PrimaryCartDbEvents;
import com.peppertap.caravan.utils.MoneyHelper;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import pepperTap.Cart;
import pepperTap.LineItem;

public class PrimaryCartHelper extends BaseCartHelper {

    /* Singleton instance */
    private PrimaryCartHelper(){}
    private static PrimaryCartHelper instance = null;

    private static final String TAG = "PRIMARY_CART_HELPER";

    public static PrimaryCartHelper getInstance(Context context){
        if(instance == null){
            instance = new PrimaryCartHelper();
            instance.instanceContext = context;
            instance.globalApplication = (CaravanApp) context.getApplicationContext();
            //fetch cart from the database
            instance.getCart();

            //update properties of CartHelper
            instance.updateValuesFromCart();

            //Register with EventBus
            EventBus.getDefault().register(instance);
        }
        return instance;
    }

    public static void updateInstance(Context context){
        Log.d(TAG, "in PrimaryCart update instance");
        if(instance != null){
            instance.cart = null;
            instance.getCart();
            instance.updateValuesFromCart();
        }
    }

    public Cart getCart(){
        if(this.cart == null){
            this.cart = CartRepository.getInstance(globalApplication).getUpdatedCart();
        }
        return this.cart;
    }

    public static Boolean getIsEmpty(Context context){
        return getInstance(context).getIsEmpty();
    }

    public static Float getSavings(Context context) {
        return getInstance(context).savings;
    }

    public static int getNoItems(Context context) {
        return getInstance(context).noItems;
    }

    private void updateValuesFromCart(){
        Cart temp = getCart();
        is_empty = temp.getIs_empty();
        noItems = temp.getNo_items();
        savings = temp.getSavings();
        total = temp.getTotal();
        sub_total = temp.getSub_total();
        classificationQuantities.clear();
        for(LineItem x: temp.getLineItemList()){
            this.lineItemHelpers.put(x.getProduct_code(), x);
            insertOrUpdateClassificationQuantities(x);
        }
    }

    private void insertOrUpdateClassificationQuantities(LineItem item){
        if(item.getClassification_id() != ProductHelper.DEFAULT_CLASSIFICATION_ID) {
            if (classificationQuantities.containsKey(item.getClassification_id())) {
                classificationQuantities.put(item.getClassification_id(), classificationQuantities.get(item.getClassification_id()) + item.getQuantity());
            } else {
                classificationQuantities.put(item.getClassification_id(), item.getQuantity());
            }
        }
    }

    public static void emptyCart(){
        if(instance != null){
            instance.emptyCartItems();
        }
    }


    /*
       WARNING:
       The following method assumes that database deletion has been taken care of. Never call this method directly.
       Always trigger an event ApplicationEvents.ClearCart. This event is guaranteed to be
       intercepted in the application CaravanApp.java
    */
    private void emptyCartItems(){
        noItems = 0;
        sub_total = zero;
        is_empty = true;
        savings = zero;
        total = zero;
        cart = null;
        lineItemHelpers = new HashMap<String, LineItem>();
        classificationQuantities.clear();
        EventBus.getDefault().post(new CartEvents.ShowCartFragment());
    }

    public Integer getQuantityInCart(String product_id){
        if(lineItemHelpers.containsKey(product_id)){
            return lineItemHelpers.get(product_id).getQuantity();
        }
        else{
            return 0;
        }
    }

    public Integer getClassificationQuantityInCart(Long classification_id){
        int quantity = 0;
        if(classificationQuantities.containsKey(classification_id)){
            quantity = classificationQuantities.get(classification_id);
        }
        return quantity;
    }

    public void onEventBackgroundThread(PrimaryCartDbEvents.ProductAddEvent e){
        String prod_code = null;

        ProductHelper.ProductGaClassification classification;
        String ga_string = "";
        LineItem lineItem = null;

        boolean skipDbSave = e.wereProductsToBeAddedProgramatically() && e.getQt() > 1;

        if(e.getLineItem() != null){
            lineItem = e.getLineItem();
            addProduct(lineItem);
            prod_code = lineItem.getProduct_code();
            classification = LineItemRepository.getInstance(globalApplication).getProductGaClassification(lineItem);
            ga_string = classification.toGaString();
        }
        else if(e.getProductItem() != null){
            ProductHelper.ProductItem item = e.getProductItem();
            if (!skipDbSave) {
                addProduct(item);
            }
            else {
                lineItem = LineItemRepository.getInstance(globalApplication).fromProductItem(item);
                saveLineItemToCart(lineItem, e.getQt());
            }
            prod_code = e.getProductItem().getProduct_id();
            classification = item.getProduct_classification();
            ga_string = classification.toGaString();
        }
        else{
            Log.e(TAG, "Something went wrong with adding product");
        }
    }

    public void onEventBackgroundThread(PrimaryCartDbEvents.ProductReduceEvent e){
        String prod_code = null;
        ProductHelper.ProductGaClassification classification;
        String ga_string = "";
        if(e.getLineItem() != null){
            LineItem lineItem = e.getLineItem();
            reduceProduct(lineItem);
            prod_code = lineItem.getProduct_code();
            classification = LineItemRepository.getInstance(globalApplication).getProductGaClassification(lineItem);
            ga_string = classification.toGaString();
        }
        else if(e.getProductItem() != null){
            ProductHelper.ProductItem item = e.getProductItem();
            reduceProduct(item);
            prod_code = e.getProductItem().getProduct_id();
            classification = item.getProduct_classification();
            ga_string = classification.toGaString();
        }
        else{
            Log.e(TAG, "Something went wrong with adding product");
        }
    }

    public void onEventAsync(PrimaryCartDbEvents.ProductAddHitQtLimit e){
        String prod_code = null;
        if(e.getLineItem() != null){
            prod_code = e.getLineItem().getProduct_code();
        }
        else if(e.getProductItem() != null){
            prod_code = e.getProductItem().getProduct_id();
        }
        else{
            Log.e(TAG, "Something went wrong with ProductAddHitQtLimit");
        }
    }

    public void onEventBackgroundThread(PrimaryCartDbEvents.AddLineItemEvent e){
        LineItem item = e.getLineItem();
        item.setCartID(new Long(1));
        LineItemRepository.getInstance(globalApplication).addLineItem(item);
    }

    public void onEventBackgroundThread(PrimaryCartDbEvents.ReduceLineItemEvent e){
        LineItem item = e.getLineItem();
        item.setCartID(new Long(1));
        LineItemRepository.getInstance(globalApplication).reduceLineItem(item);
    }

    public static long getCartValue(Context context) {
        return Long.parseLong(MoneyHelper.toMoneyWithoutDecimals(getInstance(context).getTotal()));
    }

    public LineItem getLineItemClassificationForProductId(String product_id) {
        if (lineItemHelpers.containsKey(product_id)) {
            return lineItemHelpers.get(product_id);
        }
        else {
            return null;
        }
    }
}
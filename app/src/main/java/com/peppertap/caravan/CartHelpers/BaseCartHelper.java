package com.peppertap.caravan.cartHelpers;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.DAOHelpers.LineItemRepository;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.events.CartEvents;
import com.peppertap.caravan.events.PrimaryCartDbEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import de.greenrobot.event.EventBus;
import pepperTap.Cart;
import pepperTap.LineItem;

/**
 * Created by samvedana on 16/1/15.
 */
public class BaseCartHelper {

    private static final String TAG = "CART_HELPER";

    protected Context instanceContext = null;
    protected CaravanApp globalApplication = null;

    protected int noItems = 0;
    protected static final Float zero = new Float(0);
    protected Float sub_total = zero;
    protected Boolean is_empty = true;
    protected Float savings = zero;
    protected Float total = zero;
    protected Cart cart = null;
    protected HashMap<String, LineItem> lineItemHelpers = new HashMap<String, LineItem>();
    protected HashMap<Long, Integer> classificationQuantities = new HashMap<>();

    public Boolean getIsEmpty(){
        return this.is_empty;
    }

    public Float getSubTotal(){
        return sub_total;
    }

    public Float getTotal(){
        return total;
    }

    public JsonObject toJson(){
        JsonObject temp = new JsonObject();
        return temp;
    }

    public ArrayList<LineItem> getLineItems(){
        ArrayList<LineItem> temp = new ArrayList<LineItem>();
        temp.addAll(lineItemHelpers.values());
        return temp;
    }

    public void addProduct(ProductHelper.ProductItem item){
        String addedId = item.getProduct_id();
        LineItem lineItem;
        if(lineItemHelpers.containsKey(addedId)){
            lineItem = lineItemHelpers.get(addedId);
            lineItem.setQuantity(lineItem.getQuantity() + 1);

            if(item.getClassification_id() == ProductHelper.DEFAULT_CLASSIFICATION_ID){
                //being added from search
                if(lineItem.getClassification_id() == ProductHelper.DEFAULT_CLASSIFICATION_ID){
                    //was originally added from search do nothing
                }else{
                    //was added from tile
                    //increment the classification quantity
                    addToClassificationQuantity(lineItem.getClassification_id());
                }
            }else{
                //being added from tile
                if(lineItem.getClassification_id() == ProductHelper.DEFAULT_CLASSIFICATION_ID){
                    //was originally added from search do nothing
                    //change its classification_id
                    //increment the classification quantity
                    lineItem.setClassification_id(item.getClassification_id());
                    for(int i = 0; i < lineItem.getQuantity(); i++){
                        addToClassificationQuantity(lineItem.getClassification_id());
                    }
                }else{
                    //was originally added from tile
                    //increment the classification quantity
                }
                addToClassificationQuantity(lineItem.getClassification_id());
            }
            //merge classification tile info from item to lineItem
            ProductHelper.ProductGaClassification lineItemClassification = LineItemRepository.getInstance(globalApplication).getProductGaClassification(lineItem);
            lineItemClassification.mergeTileInfo(item.getProduct_classification().getTiles());
            lineItem.setClassifications(lineItemClassification.toString());
        }
        else{
            noItems += 1;
            lineItem = LineItemRepository.getInstance(globalApplication).fromProductItem(item);
            if(lineItem.getClassification_id() != ProductHelper.DEFAULT_CLASSIFICATION_ID){
                //is being added from tile
                //increment the classification quantity
                addToClassificationQuantity(lineItem.getClassification_id());
            }
        }
        lineItemHelpers.put(item.getProduct_id(), lineItem);
        total += lineItem.getSale_price();
        sub_total += lineItem.getMrp();
        savings = sub_total - total;
        is_empty = false;
        Log.d(TAG, "successful add operation");

        EventBus.getDefault().post(new CartEvents.ShowCartFragment());
        EventBus.getDefault().post(new PrimaryCartDbEvents.AddLineItemEvent(lineItem));
    }

    public void reduceProduct(ProductHelper.ProductItem item){
        String reducedId = item.getProduct_id();
        LineItem lineItem;
        if(lineItemHelpers.containsKey(reducedId)){
            lineItem = lineItemHelpers.get(reducedId);
            int new_quantity = lineItem.getQuantity() - 1;
            lineItem.setQuantity(new_quantity);
            if(item.getClassification_id() == ProductHelper.DEFAULT_CLASSIFICATION_ID){
                //being reduced from search
                if(lineItem.getClassification_id() == ProductHelper.DEFAULT_CLASSIFICATION_ID){
                    //was originally added from search do nothing
                }else{
                    //was added from tile
                    //decrement the classification quantity
                    reduceFromClassificationQuantity(lineItem.getClassification_id());
                }
            }else{
                //being reduced from tile
                if(lineItem.getClassification_id() == ProductHelper.DEFAULT_CLASSIFICATION_ID){
                    //was originally added from search
                    //change its classification_id
                    //decrement the classification quantity
                    lineItem.setClassification_id(item.getClassification_id());
                    for(int i = 0; i <= new_quantity; i++){
                        addToClassificationQuantity(lineItem.getClassification_id());
                    }
                }else{
                    //was originally added from tile
                    //decrement the classification quantity
                }
                reduceFromClassificationQuantity(lineItem.getClassification_id());
            }
            if (new_quantity == 0){
                noItems -= 1;
                lineItemHelpers.remove(reducedId);
                if(noItems == 0){
                    is_empty = true;
                }
            }
            else {
                //merge classification tile info from item to lineItem
                ProductHelper.ProductGaClassification lineItemClassification = LineItemRepository.getInstance(globalApplication).getProductGaClassification(lineItem);
                lineItemClassification.mergeTileInfo(item.getProduct_classification().getTiles());
                lineItem.setClassifications(lineItemClassification.toString());
            }
            total -= lineItem.getSale_price();
            sub_total -= lineItem.getMrp();
            savings = sub_total - total;
            Log.d(TAG, "successful reduce operation");
            EventBus.getDefault().post(new CartEvents.ShowCartFragment());
            EventBus.getDefault().post(new PrimaryCartDbEvents.ReduceLineItemEvent(lineItem));
        }
    }

    private void reduceFromClassificationQuantity(Long classification_id){
        if(classification_id != ProductHelper.DEFAULT_CLASSIFICATION_ID && classificationQuantities.containsKey(classification_id)) {
            if (classificationQuantities.get(classification_id) == 1) {
                classificationQuantities.remove(classification_id);
            } else {
                classificationQuantities.put(classification_id, classificationQuantities.get(classification_id) - 1);
            }
        }
    }

    private void addToClassificationQuantity(Long classification_id){
        if(classification_id != ProductHelper.DEFAULT_CLASSIFICATION_ID) {
            if (classificationQuantities.containsKey(classification_id)) {
                classificationQuantities.put(classification_id, classificationQuantities.get(classification_id) + 1);
            } else {
                classificationQuantities.put(classification_id, 1);
            }
        }
    }

    public void saveLineItemToCart(LineItem item, int additional_quantity) {
        //just meant to save lineItem obj as it is to db (no qt summation/increment etc)
        String addedId = item.getProduct_code();
        LineItem lineItem;
        if(lineItemHelpers.containsKey(addedId)){
            lineItem = lineItemHelpers.get(addedId);
            lineItem.setQuantity(lineItem.getQuantity() + additional_quantity);
        }
        else{
            noItems += 1;
            lineItem = item;
            lineItem.setQuantity(additional_quantity);
        }
        lineItemHelpers.put(addedId, lineItem);
        total += lineItem.getSale_price() * additional_quantity;
        sub_total += lineItem.getMrp() * additional_quantity;
        savings = sub_total - total;
        is_empty = false;
        Log.d(TAG, "successful add operation");
        EventBus.getDefault().post(new CartEvents.ShowCartFragment());
        EventBus.getDefault().post(new PrimaryCartDbEvents.AddLineItemEvent(lineItem));
    }

    public void addProduct(LineItem item){
        String addedId = item.getProduct_code();
        LineItem lineItem;
        if(lineItemHelpers.containsKey(addedId)){
            lineItem = lineItemHelpers.get(addedId);
            lineItem.setQuantity(lineItem.getQuantity()+1);
        }
        else{
            noItems += 1;
            lineItem = item;
        }
        lineItemHelpers.put(item.getProduct_code(), lineItem);
        addToClassificationQuantity(lineItem.getClassification_id());
        total += lineItem.getSale_price();
        sub_total += lineItem.getMrp();
        savings = sub_total - total;
        is_empty = false;
        Log.d(TAG, "successful add operation");
        EventBus.getDefault().post(new CartEvents.ShowCartFragment());
        EventBus.getDefault().post(new PrimaryCartDbEvents.AddLineItemEvent(lineItem));
    }

    public void reduceProduct(LineItem item){
        String reducedId = item.getProduct_code();
        LineItem lineItem;
        if(lineItemHelpers.containsKey(reducedId)){
            lineItem = lineItemHelpers.get(reducedId);
            int new_quantity = lineItem.getQuantity() - 1;
            if(!(new_quantity < 0)){
                lineItem.setQuantity(new_quantity);
            }
            reduceFromClassificationQuantity(lineItem.getClassification_id());
            if (new_quantity == 0){
                noItems -= 1;
                lineItemHelpers.remove(reducedId);
                if(noItems == 0){
                    is_empty = true;
                }
            }
            total -= lineItem.getSale_price();
            sub_total -= lineItem.getMrp();
            savings = sub_total - total;
            Log.d(TAG, "successful reduce operation");
            EventBus.getDefault().post(new CartEvents.ShowCartFragment());
            EventBus.getDefault().post(new PrimaryCartDbEvents.ReduceLineItemEvent(lineItem));
        }
    }

    public JsonObject getAppCartAsJsonObject(Context context) {
        JsonObject cartJson = new JsonObject();
        JsonArray products = new JsonArray();
        List<LineItem> items = getLineItems();
        ListIterator<LineItem> it = items.listIterator();
        while (it.hasNext()) {
            JsonObject entryJson = getLineItemAsJsonObject(it.next());
            products.add(entryJson);
        }
        cartJson.add("products", products);
        return cartJson;
    }

    public JsonObject getLineItemAsJsonObject(LineItem item) {
        JsonObject itemJson = new JsonObject();
        itemJson.addProperty("product_id", item.getProduct_code());
        itemJson.addProperty("qt", item.getQuantity());
        itemJson.addProperty("sale_price", item.getSale_price());
        return itemJson;
    }

}

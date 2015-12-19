package com.peppertap.caravan.DAOHelpers;

import android.util.Log;

import com.peppertap.caravan.CaravanApp;

import java.util.List;

import pepperTap.Cart;
import pepperTap.LineItem;

/**
 * Created by subodh on 1/10/14.
 */
public class CartRepository {
    private static Long cartId = null;
    private static final String TAG = "CART_REPOSITORY";

    private static CartRepository instance = null;
    private CaravanApp globalApplication;

    DbWrapper wrapper;

    private CartRepository(CaravanApp app){
        wrapper = app.getDbWrapper();
        globalApplication = app;
    }

    public static CartRepository getInstance(CaravanApp app){
        if(instance == null){
            instance = new CartRepository(app);
        }
        return instance;
    }

    private Cart getAppCart(){
        Cart temp = getCartForId(new Long(1));
        if(temp == null){
            temp = new Cart();
            temp.setId(new Long(1));
            insertOrUpdate(temp);
        }
        return temp;
    }

    public Cart getUpdatedCart(){
        return updateCart(getAppCart());
    }

    private Cart updateCart(Cart temp){
        Float zero = new Float(0);
        if(temp ==null){
            Log.e(TAG, "No Cart!!!!!");
            return new Cart();
        }
        temp.setDelivery_charge(zero);
        temp.setTotal(zero);
        temp.setSavings(zero);
        temp.setSub_total(zero);
        temp.setNo_items(0);
        int lineItems = 0;
        Float sub_total = zero;
        Float total = zero;
        for(LineItem x: temp.getLineItemList()){
            lineItems ++;
            int quantity = x.getQuantity();
            sub_total += x.getMrp()*quantity;
            total += x.getSale_price()*quantity;
        }
        if(lineItems == 0){
            temp.setIs_empty(true);
        }
        else{
            temp.setIs_empty(false);
        }
        temp.setNo_items(lineItems);
        temp.setTotal(total);
        temp.setSub_total(sub_total);
        temp.setSavings(sub_total - total);
        insertOrUpdate(temp);
        return temp;
    }

    public void insertOrUpdate(Cart cart) {
        wrapper.getCartDao().insertOrReplace(cart);
    }

    public void clearCarts() {
        wrapper.getCartDao().deleteAll();
        LineItemRepository.getInstance(globalApplication).clearLineItems();
    }

    public void deleteCartWithId(long id) {
        wrapper.getCartDao().delete(getCartForId(id));
    }

    public Cart getCartForId(long id) {
        return wrapper.getCartDao().load(id);
    }

    public List<Cart> getAllCarts() {
        return wrapper.getCartDao().loadAll();
    }

}
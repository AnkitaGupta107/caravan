package com.peppertap.caravan.events;

import com.google.gson.JsonObject;
import com.peppertap.caravan.data.ProductHelper;

import pepperTap.LineItem;

/**
 * Created by samvedana on 20/1/15.
 */
public class PrimaryCartDbEvents {

    public static class ProductAddEvent{
        ProductHelper.ProductItem productItem = null;
        LineItem lineItem = null;
        String source = null;
        JsonObject searchData = null;
        int qt = 0;
        boolean products_to_be_added_programatically = false;
        public ProductAddEvent(ProductHelper.ProductItem item, String source){
            this.productItem = item;
            this.source = source;
        }

        public ProductAddEvent(LineItem item, String source){
            this.lineItem = item;
            this.source = source;
        }
        public ProductHelper.ProductItem getProductItem(){
            return this.productItem;
        }
        public LineItem getLineItem(){
            return this.lineItem;
        }
        public String getSource() {
            return source;
        }
        public void setSearchData(JsonObject data) {
            searchData = data;
        }
        public JsonObject getSearchData() {
            return searchData;
        }
        public boolean wereProductsToBeAddedProgramatically() {
            return products_to_be_added_programatically;
        }
        public int getQt() {
            return qt;
        }

        public void productsToBeAddedProgramaticallyWithQt(int q) {
            products_to_be_added_programatically = true;
            qt = q;
        }
    }

    public static class ProductReduceEvent{
        ProductHelper.ProductItem productItem = null;
        LineItem lineItem = null;
        String source = null;
        JsonObject searchData = null;
        public ProductReduceEvent(ProductHelper.ProductItem item, String source){
            this.productItem = item;
            this.source = source;
        }
        public ProductReduceEvent(LineItem item, String source){
            this.lineItem = item;
            this.source = source;
        }
        public ProductHelper.ProductItem getProductItem() {
            return this.productItem;
        }
        public LineItem getLineItem(){
            return this.lineItem;
        }
        public String getSource() {
            return source;
        }
        public void setSearchData(JsonObject data) {
            searchData = data;
        }
        public JsonObject getSearchData() {
            return searchData;
        }
    }

    public static class ProductAddHitQtLimit {
        ProductHelper.ProductItem productItem = null;
        LineItem lineItem = null;
        public ProductAddHitQtLimit(ProductHelper.ProductItem item){
            this.productItem = item;
        }
        public ProductAddHitQtLimit(LineItem item){
            this.lineItem = item;
        }
        public ProductHelper.ProductItem getProductItem() {
            return this.productItem;
        }
        public LineItem getLineItem(){
            return this.lineItem;
        }
    }

    public static class AddLineItemEvent{
        private LineItem lineItem;
        private ProductHelper.ProductItem productItem;
        public AddLineItemEvent(LineItem item){
            this.lineItem = item;
        }
        public AddLineItemEvent(ProductHelper.ProductItem item){
            this.productItem = item;
        }
        public LineItem getLineItem(){
            return this.lineItem;
        }
        public ProductHelper.ProductItem getProductItem(){
            return this.productItem;
        }
    }

    public static class ReduceLineItemEvent{
        private LineItem lineItem;
        private ProductHelper.ProductItem productItem;
        public ReduceLineItemEvent(LineItem item){
            this.lineItem = item;
        }
        public ReduceLineItemEvent(ProductHelper.ProductItem item){
            this.productItem = item;
        }
        public LineItem getLineItem(){
            return this.lineItem;
        }
        public ProductHelper.ProductItem getProductItem(){
            return this.productItem;
        }
    }
}

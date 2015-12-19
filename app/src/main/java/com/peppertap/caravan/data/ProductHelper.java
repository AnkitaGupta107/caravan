package com.peppertap.caravan.data;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.peppertap.caravan.utils.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ProductHelper {

    ArrayList<ProductGroup> product_list = new ArrayList<ProductGroup>();
    private ArrayList<String> uniqueProductTypes = null;
    public static final Long DEFAULT_CLASSIFICATION_ID = new Long(-1);
    public static final String CLASSIFICATION_ID_KEY = "classification_id";

    public ArrayList<ProductGroup> getProductGroupsForType(String product_type){
        return new ArrayList<ProductGroup>();
    }

    public ArrayList<ProductGroup> getAllProductGroups(){
        return product_list;
    }

    public static ProductHelper fromJson(JsonObject product_list_json){
        ProductHelper helper = new ProductHelper();
        helper.uniqueProductTypes = new ArrayList<String>();
        for(JsonElement x: product_list_json.getAsJsonArray("types")){
            helper.uniqueProductTypes.add(x.getAsString());
        }

        String classification;
        Long classification_id;
        //todo - confirm that this info is reqd
        if (product_list_json.has("classification")) {
            //came here via some tile => ProductFragment : loadProducts
            classification = product_list_json.get("classification").getAsString();
        }
        else {
            //came here via search => NewSearchFragment : performSearch
            classification = "search";
        }
        if (product_list_json.has(CLASSIFICATION_ID_KEY) && !product_list_json.get(CLASSIFICATION_ID_KEY).isJsonNull()) {
            //came here via some tile => ProductFragment : loadProducts
            classification_id = product_list_json.get(CLASSIFICATION_ID_KEY).getAsLong();
        }
        else {
            //came here via search => NewSearchFragment : performSearch
            classification_id = DEFAULT_CLASSIFICATION_ID ;
        }
        for(JsonElement y: product_list_json.getAsJsonArray("pl")){
            helper.product_list.add(ProductGroup.fromJson(y.getAsJsonObject(), classification, classification_id));
        }
        return helper;
    }

    public ArrayList<String> getTitleArray(){
        return this.uniqueProductTypes;
    }

    public static class ProductGroup implements Serializable {
        String title;
        ProductGaClassification gpClassification;
        ArrayList<String> product_types = new ArrayList<String>();
        ArrayList<ProductItem> products = new ArrayList<ProductItem>();
        private boolean hasVariants = false;
        private int variants = 1;

        public static ProductGroup fromJson(JsonObject product_array, String source_tile, Long classification_id){
            ProductGroup group = new ProductGroup();
            group.title = product_array.get("tle").getAsString();
            JsonArray product_types_json_array = product_array.get("typ").getAsJsonArray();
            JsonArray products_json_array = product_array.get("ps").getAsJsonArray();
            for(JsonElement x: product_types_json_array){
                group.product_types.add(x.getAsString());
            }
            ProductItem temp;
            group.gpClassification = ProductGaClassification.createClassification(product_types_json_array);
            if(product_array.has("cid") && !product_array.get("cid").isJsonNull()){
                classification_id = product_array.get("cid").getAsLong();
            }
            if (source_tile != null && !source_tile.equals("")) {
                group.gpClassification.addTileInfo(source_tile);
            }
            for(JsonElement y: products_json_array){
                temp = ProductItem.fromJson(y.getAsJsonObject(), group.gpClassification);
                temp.title = group.title;
                temp.setClassification_id(classification_id);
                group.products.add(temp);
            }
            if(group.products.size() > 1){
                group.hasVariants = true;
                group.variants = group.products.size();
            }
            temp = group.getSelected();
            if(temp == null){
                temp = group.setAndGetFirstSelected();
            }else{
                Collections.swap(group.products, group.products.indexOf(temp), 0);
            }
            return group;
        }

        public void swapSelectedToFirst(){
            Collections.swap(products, products.indexOf(getSelected()), 0);
        }

        public boolean hasVariants(){
            return hasVariants;
        }

        public int getVariantNumber(){
            return variants;
        }

        public String getTitle() {
            return title;
        }

        public ArrayList<ProductItem> getProducts(){
            return this.products;
        }

        public ArrayList<String> getProductTypes(){
            return this.product_types;
        }

        //TODO Find a better way to get the selected product
        public ProductItem getSelected(){
            ProductItem temp = null;
            for(ProductItem x: this.products){
                if(x.getIs_selected()){
                    temp = x;
                    break;
                }
                else if(x.getIs_default()){
                    x.unsetDefault();
                    x.setIs_selected();
                    temp = x;
                    break;
                }
            }
            return temp;
        }

        public ProductItem setAndGetFirstSelected(){
            this.products.get(0).setIs_selected();
            return this.products.get(0);
        }

        public ProductGaClassification getGpClassification() {
            return gpClassification;
        }
    }

    public static class ProductItem implements Serializable {
        String product_id;
        String title;
        String mrp;
        String sale_price;
        String description = "";
        public String image_url;
        String display_attribute;
        Boolean is_default;
        Boolean is_selected = false;
        String uid;
        String discount_string;
        private int max_limit = -1;  //default of -1 implies no maximum limit
        private Long classification_id = DEFAULT_CLASSIFICATION_ID;
        ProductGaClassification product_classification;

        public static ProductItem fromJson(JsonObject product, ProductGaClassification classification_data){

            ProductItem temp = new ProductItem();
            temp.product_id = JsonHelper.getValueOrNone(product, "id");
            temp.mrp = JsonHelper.getValueOrNone(product, "mrp");
            temp.sale_price = JsonHelper.getValueOrNone(product, "sp");
            temp.image_url = "NA"; //JsonHelper.getValueOrNone(product, "img");
            temp.display_attribute = JsonHelper.getValueOrNone(product, "da");
            temp.uid = JsonHelper.getValueOrNone(product, "uid");
            temp.is_default = product.get("dft").getAsBoolean();
            if(product.has("dsc") && !product.get("dsc").isJsonNull()){
                temp.description = product.get("dsc").getAsString();
            }
            if (product.has("mxq") && !product.get("mxq").isJsonNull()) {
                temp.setMax_limit(product.get("mxq").getAsInt());
            }else{
                temp.setMax_limit(-1);
            }
            if (product.has("sst") && !product.get("sst").isJsonNull()) {
                temp.discount_string = product.get("sst").getAsString();
            }else {
                temp.discount_string = null;
            }
            temp.product_classification = classification_data;
            return temp;
        }

        public static ProductItem fromTrackOrderLineJson(JsonObject lineJson) {
            ProductItem temp = new ProductItem();
            temp.product_id = JsonHelper.getValueOrNone(lineJson, "foe_product");
            temp.title = JsonHelper.getValueOrNone(lineJson, "title");
            temp.mrp = JsonHelper.getValueOrNone(lineJson, "mrp");
            temp.sale_price = JsonHelper.getValueOrNone(lineJson, "sale_price");
            temp.image_url = "NA"; //JsonHelper.getValueOrNone(product, "img");
            temp.display_attribute = JsonHelper.getValueOrNone(lineJson, "display_attributes");
            temp.uid = JsonHelper.getValueOrNone(lineJson, "uid");
            temp.is_default = true;
            //leave description empty
            if (lineJson.has("discount_string") && !lineJson.get("discount_string").isJsonNull()) {
                temp.discount_string = lineJson.get("discount_string").getAsString();
            }
            else {
                temp.discount_string = null;
            }
            if (lineJson.has("mxq") && !lineJson.get("mxq").isJsonNull()) {
                temp.setMax_limit(lineJson.get("mxq").getAsInt());
            }else{
                temp.setMax_limit(-1);
            }

            if (lineJson.has("cid") && !lineJson.get("cid").isJsonNull()) {
                temp.setClassification_id(lineJson.get("cid").getAsLong());
            }else{
                temp.setClassification_id(ProductHelper.DEFAULT_CLASSIFICATION_ID);
            }
            JsonArray groups;
            if (lineJson.has("classification_groups") && !lineJson.get("classification_groups").isJsonNull()) {
                groups = lineJson.get("classification_groups").getAsJsonArray();
            }
            else {
                groups = new JsonArray();
            }
            temp.product_classification = ProductGaClassification.createClassification(groups);
            return temp;
        }

        public String getUid() {
            return uid;
        }

        public String getMrp(){
            return this.mrp;
        }

        public String getTitle(){
            return this.title;
        }

        public String getSale_price(){
            return this.sale_price;
        }

        public String getDisplay_attribute(){
            return this.display_attribute;
        }

        public String getImage_url(){
            return this.image_url;
        }

        public Boolean getIs_default(){
            return this.is_default;
        }

        public String getProduct_id(){
            return this.product_id;
        }

        public Boolean getIs_selected(){
            return this.is_selected;
        }

        public void setIs_selected(){
            this.is_selected = true;
        }

        public String getDescription(){
            return this.description;
        }

        public void unsetDefault(){
            this.is_default = false;
        }

        public void setIs_selected(boolean selected){
            this.is_selected = selected;
        }

        public String getDiscount_string() {
            return this.discount_string;
        }

        public ProductGaClassification getProduct_classification() {
            return product_classification;
        }

        public void addTileToClassificationInfo(String tile_name) {
            product_classification.addTileInfo(tile_name);
        }

        public int getMax_limit() {
            return max_limit;
        }

        public void setMax_limit(int max_limit) {
            this.max_limit = max_limit;
        }

        public Long getClassification_id() {
            return classification_id;
        }

        public void setClassification_id(Long classification_id) {
            this.classification_id = classification_id;
        }
    }

    public static class ProductGaClassification implements Serializable {
        ArrayList<String> tiles;
        ArrayList<String> types; //ex ["Soft Drinks"]

        private static ProductGaClassification createClassification() {
            ProductGaClassification temp = new ProductGaClassification();
            temp.types = new ArrayList<>();
            temp.tiles = new ArrayList<>();
            return temp;
        }

        public static ProductGaClassification createClassification(JsonArray types) {
            ProductGaClassification temp = new ProductGaClassification();
            temp.types = JsonHelper.convertJsonArrayToArrayListString(types);
            temp.tiles = new ArrayList<>();
            Collections.sort(temp.types, String.CASE_INSENSITIVE_ORDER);
            return temp;
        }

        public void addTileInfo(String tile) {
            for (String word : tiles) {
                if (word.compareToIgnoreCase(tile) == 0) {
                    //do nothing, tile already in list
                    return;
                }
            }
            tiles.add(tile.toLowerCase());
            Collections.sort(tiles);
        }

        public void mergeTileInfo(ArrayList<String> newTiles) {
            newTiles.removeAll(tiles); //remove from newTiles all elements also contained in tiles
            if (newTiles.size() == 0) {
                return;
            }
            tiles.addAll(newTiles); //combine both into tiles
            Collections.sort(tiles);
        }

        public ArrayList<String> getTiles() {
            return tiles;
        }

        public String toGaString() {
            return "Tiles : " + tiles.toString() + " | Types : " + types.toString();
        }

        @Override
        public String toString() {
            String result = "";
            result += tiles == null ? "null" : tiles.toString();
            result += ":";
            result += types == null ? "null" : types.toString();
            return result;
        }

        public static ProductGaClassification fromStringToObject(String classification) {
            String[] tokens = TextUtils.split(classification, ":");
            if (tokens.length != 2) {
                Log.e("ProductGaClassification", "messed up tokens!");
                return createClassification();
            }
            ProductGaClassification temp = new ProductGaClassification();
            temp.tiles = arrayListStringToArrayList(tokens[0]);
            temp.types = arrayListStringToArrayList(tokens[1]);
            return temp;
        }

        private static String stripBrackets(String str) {
            return str.substring(1, str.length()-1);
        }

        private static ArrayList<String> arrayListStringToArrayList(String arrayListRepr) {
            if (arrayListRepr == null || arrayListRepr.equals("null") || arrayListRepr.equals("[]")) {
                return new ArrayList<>();
            }
            else if (Character.toString(arrayListRepr.charAt(0)).equals("[") && Character.toString(arrayListRepr.charAt(arrayListRepr.length() - 1)).equals("]")) {
                String data = stripBrackets(arrayListRepr);
                return new ArrayList<>(Arrays.asList(data.split(",")));
            }
            else {
                //error
                Log.e("ProductGaClassification", "messed up tokens!");
                return new ArrayList<>();
            }
        }

    }
}
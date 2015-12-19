package pl.surecase.eu;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Created by subodh on 28/10/15.
 */
public class Version1 extends SchemaVersion {

    public Version1(boolean current) {
        super(current);

        Schema schema = getSchema();
        addEntities(schema);
    }

    @Override
    public int getVersionNumber() {
        return 1;
    }

    private static void addEntities(Schema schema) {
        Entity cart = schema.addEntity("Cart");
        cart.addIdProperty();
        cart.addBooleanProperty("is_empty");
        cart.addFloatProperty("sub_total");
        cart.addFloatProperty("total");
        cart.addFloatProperty("discounts");
        cart.addFloatProperty("savings");
        cart.addFloatProperty("delivery_charge");
        cart.addIntProperty("no_items");
        cart.addStringProperty("cart_code_on_server");
        //in case this cart has been sent to server and saved, set cart_id appropriately

        Entity lineItem = schema.addEntity("LineItem");
        lineItem.addIdProperty();
        lineItem.addStringProperty("product_code");
        lineItem.addStringProperty("title");
        lineItem.addFloatProperty("mrp");
        lineItem.addFloatProperty("sale_price");
        lineItem.addIntProperty("quantity");
        lineItem.addIntProperty("max_limit");
        lineItem.addLongProperty("classification_id");
        lineItem.addStringProperty("offer");
        lineItem.addFloatProperty("discount");
        lineItem.addStringProperty("image_url");
        lineItem.addFloatProperty("total");
        lineItem.addStringProperty("display_attribute");
        lineItem.addStringProperty("discount_string");
        lineItem.addStringProperty("classifications");
        Property cartID = lineItem.addLongProperty("cartID").getProperty();
        ToMany lineItems = cart.addToMany(lineItem, cartID);
    }
}

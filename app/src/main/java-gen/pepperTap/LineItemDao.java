package pepperTap;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import pepperTap.LineItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table LINE_ITEM.
*/
public class LineItemDao extends AbstractDao<LineItem, Long> {

    public static final String TABLENAME = "LINE_ITEM";

    /**
     * Properties of entity LineItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Product_code = new Property(1, String.class, "product_code", false, "PRODUCT_CODE");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Mrp = new Property(3, Float.class, "mrp", false, "MRP");
        public final static Property Sale_price = new Property(4, Float.class, "sale_price", false, "SALE_PRICE");
        public final static Property Quantity = new Property(5, Integer.class, "quantity", false, "QUANTITY");
        public final static Property Max_limit = new Property(6, Integer.class, "max_limit", false, "MAX_LIMIT");
        public final static Property Classification_id = new Property(7, Long.class, "classification_id", false, "CLASSIFICATION_ID");
        public final static Property Offer = new Property(8, String.class, "offer", false, "OFFER");
        public final static Property Discount = new Property(9, Float.class, "discount", false, "DISCOUNT");
        public final static Property Image_url = new Property(10, String.class, "image_url", false, "IMAGE_URL");
        public final static Property Total = new Property(11, Float.class, "total", false, "TOTAL");
        public final static Property Display_attribute = new Property(12, String.class, "display_attribute", false, "DISPLAY_ATTRIBUTE");
        public final static Property Discount_string = new Property(13, String.class, "discount_string", false, "DISCOUNT_STRING");
        public final static Property Classifications = new Property(14, String.class, "classifications", false, "CLASSIFICATIONS");
        public final static Property CartID = new Property(15, Long.class, "cartID", false, "CART_ID");
    };

    private Query<LineItem> cart_LineItemListQuery;

    public LineItemDao(DaoConfig config) {
        super(config);
    }
    
    public LineItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'LINE_ITEM' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'PRODUCT_CODE' TEXT," + // 1: product_code
                "'TITLE' TEXT," + // 2: title
                "'MRP' REAL," + // 3: mrp
                "'SALE_PRICE' REAL," + // 4: sale_price
                "'QUANTITY' INTEGER," + // 5: quantity
                "'MAX_LIMIT' INTEGER," + // 6: max_limit
                "'CLASSIFICATION_ID' INTEGER," + // 7: classification_id
                "'OFFER' TEXT," + // 8: offer
                "'DISCOUNT' REAL," + // 9: discount
                "'IMAGE_URL' TEXT," + // 10: image_url
                "'TOTAL' REAL," + // 11: total
                "'DISPLAY_ATTRIBUTE' TEXT," + // 12: display_attribute
                "'DISCOUNT_STRING' TEXT," + // 13: discount_string
                "'CLASSIFICATIONS' TEXT," + // 14: classifications
                "'CART_ID' INTEGER);"); // 15: cartID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'LINE_ITEM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, LineItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String product_code = entity.getProduct_code();
        if (product_code != null) {
            stmt.bindString(2, product_code);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        Float mrp = entity.getMrp();
        if (mrp != null) {
            stmt.bindDouble(4, mrp);
        }
 
        Float sale_price = entity.getSale_price();
        if (sale_price != null) {
            stmt.bindDouble(5, sale_price);
        }
 
        Integer quantity = entity.getQuantity();
        if (quantity != null) {
            stmt.bindLong(6, quantity);
        }
 
        Integer max_limit = entity.getMax_limit();
        if (max_limit != null) {
            stmt.bindLong(7, max_limit);
        }
 
        Long classification_id = entity.getClassification_id();
        if (classification_id != null) {
            stmt.bindLong(8, classification_id);
        }
 
        String offer = entity.getOffer();
        if (offer != null) {
            stmt.bindString(9, offer);
        }
 
        Float discount = entity.getDiscount();
        if (discount != null) {
            stmt.bindDouble(10, discount);
        }
 
        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(11, image_url);
        }
 
        Float total = entity.getTotal();
        if (total != null) {
            stmt.bindDouble(12, total);
        }
 
        String display_attribute = entity.getDisplay_attribute();
        if (display_attribute != null) {
            stmt.bindString(13, display_attribute);
        }
 
        String discount_string = entity.getDiscount_string();
        if (discount_string != null) {
            stmt.bindString(14, discount_string);
        }
 
        String classifications = entity.getClassifications();
        if (classifications != null) {
            stmt.bindString(15, classifications);
        }
 
        Long cartID = entity.getCartID();
        if (cartID != null) {
            stmt.bindLong(16, cartID);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public LineItem readEntity(Cursor cursor, int offset) {
        LineItem entity = new LineItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // product_code
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3), // mrp
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // sale_price
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // quantity
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // max_limit
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // classification_id
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // offer
            cursor.isNull(offset + 9) ? null : cursor.getFloat(offset + 9), // discount
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // image_url
            cursor.isNull(offset + 11) ? null : cursor.getFloat(offset + 11), // total
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // display_attribute
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // discount_string
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // classifications
            cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15) // cartID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, LineItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProduct_code(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMrp(cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3));
        entity.setSale_price(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
        entity.setQuantity(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setMax_limit(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setClassification_id(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setOffer(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDiscount(cursor.isNull(offset + 9) ? null : cursor.getFloat(offset + 9));
        entity.setImage_url(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTotal(cursor.isNull(offset + 11) ? null : cursor.getFloat(offset + 11));
        entity.setDisplay_attribute(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setDiscount_string(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setClassifications(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCartID(cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(LineItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(LineItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "lineItemList" to-many relationship of Cart. */
    public List<LineItem> _queryCart_LineItemList(Long cartID) {
        synchronized (this) {
            if (cart_LineItemListQuery == null) {
                QueryBuilder<LineItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CartID.eq(null));
                cart_LineItemListQuery = queryBuilder.build();
            }
        }
        Query<LineItem> query = cart_LineItemListQuery.forCurrentThread();
        query.setParameter(0, cartID);
        return query.list();
    }

}

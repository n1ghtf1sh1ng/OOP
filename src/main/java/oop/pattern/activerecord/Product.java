package oop.pattern.activerecord;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Product { //Active Record
    private int id;
    private String name;
    private long price;
    private int stock;
    private String manufacturer;

    /// setters and getters

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public Product setPrice(long price) {
        this.price = price;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Product setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Product setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }

    //// DB

    private static File dbFile = new File("src/main/resources/shopping.db");
    private static SqlJetDb db;

    private static String tableName = "sold_products";

    private static ISqlJetTable table;

    private static ISqlJetTable getTable() {
        if (table == null) {
            getDb();
            try {
                db.runWriteTransaction(d -> {
                    Product.setTable();
                    return null;
                });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return table;
    }

    private static SqlJetDb getDb() {
        if (db == null) {
            try {
                db = SqlJetDb.open(dbFile, true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return db;
    }

    private static void setTable() throws SqlJetException {
        try {
            table = db.getTable(tableName);
        } catch (SqlJetException ex) {
            db.createTable("CREATE TABLE " + tableName + " (" +
                    "product_id INT NOT NULL PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "price INT NOT NULL, " +
                    "stock INT NOT NULL, " +
                    "manufacturer TEXT NOT NULL)");
            table = db.getTable(tableName);
            writeExampleData();
        }
    }

    private static void writeExampleData() throws SqlJetException {
        new Product().setId(1).setName("rice").setPrice(1500).setStock(1).setManufacturer("Niigata").insert();
        new Product().setId(2).setName("carrot").setPrice(150).setStock(3).setManufacturer("Chiba").insert();
        new Product().setId(3).setName("onion").setPrice(300).setStock(3).setManufacturer("Saga").insert();
        new Product().setId(4).setName("beef").setPrice(300).setStock(1).setManufacturer("Australia").insert();
        new Product().setId(5).setName("carry powder").setPrice(800).setStock(1).setManufacturer("India").insert();
        new Product().setId(6).setName("butter").setPrice(300).setStock(1).setManufacturer("Hokkaido").insert();
        new Product().setId(7).setName("flour").setPrice(300).setStock(1).setManufacturer("US").insert();
        new Product().setId(8).setName("olive oil").setPrice(500).setStock(1).setManufacturer("Italy").insert();
    }

    public void insert() {
        try {
            Product.getDb().runWriteTransaction(db -> {
                Product.getTable().insert(this.id, this.name, this.price, this.stock, this.manufacturer);
                return null;
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update() {
        try {
            Product.getDb().runWriteTransaction(db -> {

                Product.getTable().lookup(Product.getTable().getPrimaryKeyIndexName(), this.id)
                        .update(this.id, this.name, this.price, this.stock, this.manufacturer);
                return null;
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete() {
        try {
            Product.getDb().runWriteTransaction(db -> {
                Product.getTable().lookup(Product.getTable().getPrimaryKeyIndexName(), this.id)
                        .delete();
                return null;
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<Product> getSoldProducts() {
        try {
            ISqlJetTable table = Product.getTable();
            List<Product> sold = new ArrayList<>();
            getDb().runReadTransaction(db -> {
                ISqlJetCursor cursor = table.order(table.getPrimaryKeyIndexName());

                if (!cursor.eof()) {
                    do {
                        Product p = new Product();
                        p.setId((int) cursor.getInteger("product_id"));
                        p.setName(cursor.getString("name"));
                        p.setPrice(cursor.getInteger("price"));
                        p.setStock((int) cursor.getInteger("stock"));
                        p.setManufacturer(cursor.getString("manufacturer"));
                        sold.add(p);
                    } while (cursor.next());
                }
                return null;
            });
            return sold;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

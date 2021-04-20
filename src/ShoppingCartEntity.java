public class ShoppingCartEntity {

    private Catalog catalog;
    private Item item;
    private int quantity;

    public ShoppingCartEntity(Catalog catalog, Item item, int quantity) {
        this.quantity = quantity;
        this.catalog = catalog;
        this.item = item;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

}

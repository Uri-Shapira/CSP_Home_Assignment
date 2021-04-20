import java.util.*;

public class Catalog {

    private String name;
    private Set<Item> items;
    // defines item specific discounts (in percent off of original price)
    private Map<Item, Double> itemDiscounts;

    public Catalog(String name){
        this.name = name;
        items = new HashSet<>();
        itemDiscounts = new HashMap<>();
    }

    public Catalog(String name, Set<Item> items){
        this.name = name;
        this.items = items;
        itemDiscounts = new HashMap<>();
    }

    public Item getItemByName(String itemName){
        for(Item item : items){
            if(item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Set<Item> getItems(){
        return items;
    }

    public Map<Item, Double> getItemDiscounts() {
        return itemDiscounts;
    }

    public void setItemDiscounts(Map<Item, Double> itemDiscounts) {
        this.itemDiscounts = itemDiscounts;
    }

    public double getItemDiscount(Item item){
        double discount = 0;
        if(itemDiscounts.containsKey(item)){
            discount = itemDiscounts.get(item);
        }
        return discount;
    }
}

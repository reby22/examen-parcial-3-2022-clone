package edu.uaslp.objetos.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Item> items = new ArrayList<>();

    public BigDecimal getTotalCost() {
        if(isEmpty()){
            throw new EmptyShoppingCartException();
        }
        BigDecimal totalCost = BigDecimal.ZERO;

        for(Item item: items){
            totalCost = totalCost.add(item.getUnitCost().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return totalCost;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean isEmpty() {
        if(items.size()==0){
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean getItemsCount() {
        return false;
    }

    public List<Item> getItems() {
        return items;
    }

    public void removeItem(String itemCode2) {
        items.remove(itemCode2);
    }
}

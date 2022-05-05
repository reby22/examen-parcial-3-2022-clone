package edu.uaslp.objetos.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Item> items = new ArrayList<>();
    private int cont;

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
        for(Item itemAd: items){
            if(itemAd.getCode() == item.getCode() && itemAd.getUnitCost().compareTo(item.getUnitCost())==0){
                itemAd.setQuantity(itemAd.getQuantity()+item.getQuantity());
                return;
            }
        }
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

    public int getItemsCount() {

        for(int i=0; i<items.size(); i++){
            cont= cont + items.get(i).getQuantity();
        }
        return cont;
    }

    public List<Item> getItems() {
        return items;
    }

    public void removeItem(String itemCode) {
        for(Item itemremove: items){
            if(itemremove.getCode()==itemCode){
                itemremove.setQuantity(itemremove.getQuantity()-1);
                if(itemremove.getQuantity()==0)
                    items.remove(itemremove);
                return;
            }
        }
    }
}
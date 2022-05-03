package edu.uaslp.objetos.shoppingcart;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ShoppingCartTest {

    @Test
    public void givenANewShoppingCart_whenIsEmpty_thenTrueIsReturned() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();

        // When:
        boolean isEmpty = shoppingCart.isEmpty();

        // Then:
        assertThat(isEmpty).isTrue();
    }

    @Test
    public void givenANewShoppingCart_whenGetTotalCost_thenExceptionIsThrown() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();

        // When:
        assertThatThrownBy(() -> shoppingCart.getTotalCost())
                .isInstanceOf(EmptyShoppingCartException.class)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void givenANewShoppingCart_whenAddItem_thenItemIsAdded() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode = "98234291";
        String providerCode = "8483920";

        Item item = new Item();

        item.setCode(itemCode);
        item.setProviderCode(providerCode);
        item.setQuantity(1);
        item.setUnitCost(new BigDecimal("10.34"));

        // When:
        shoppingCart.addItem(item);

        // Then:
        assertThat(shoppingCart.isEmpty()).isFalse();
        assertThat(shoppingCart.getItemsCount()).isEqualTo(1);

        List<Item> items = shoppingCart.getItems();
        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0).getCode()).isEqualTo(itemCode);
        assertThat(items.get(0).getProviderCode()).isEqualTo(providerCode);
        assertThat(items.get(0).getQuantity()).isEqualTo(1);
        assertThat(items.get(0).getUnitCost()).isEqualByComparingTo(new BigDecimal("10.34"));
    }


    @Test
    public void givenAShoppingCart_whenAddItemWithNullCode_thenExceptionIsThrown() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = null;
        String providerCode1 = "8483920";

        // When / Then:
        assertThatThrownBy(() -> shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1)))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Null or empty string not allowed for item code");

        assertThat(shoppingCart.isEmpty()).isTrue();
    }

    @Test
    public void givenAShoppingCart_whenAddItemWithProviderAsEmptyString_thenExceptionIsThrown() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "31312";
        String providerCode1 = "";

        // When / Then:
        assertThatThrownBy(() -> shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1)))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Null or empty string not allowed for provider code");

        assertThat(shoppingCart.isEmpty()).isTrue();
    }

    @Test
    public void givenAShoppingCart_whenAddItemWithNonPositiveCost_thenExceptionIsThrown() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "31312";
        String providerCode1 = "23421";

        // When / Then:
        assertThatThrownBy(() -> shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("-21.65"), 1)))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Cost must be greater than zero");

        assertThat(shoppingCart.isEmpty()).isTrue();
    }

    @Test
    public void givenAShoppingCart_whenAddItemWithQuantityNotBetween1And5_thenExceptionIsThrown() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "31312";
        String providerCode1 = "23421";

        // When / Then:
        assertThatThrownBy(() -> shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 10)))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Quantity must be greater than zero and lower than 5");

        assertThat(shoppingCart.isEmpty()).isTrue();
    }

    @Test
    public void givenAShoppingCartWithItems_whenAddItem_thenItemIsAdded() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "98234291";
        String providerCode1 = "8483920";

        String itemCode2 = "5421431";
        String providerCode2 = "3284212343";

        shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1));

        // When:
        shoppingCart.addItem(new Item(itemCode2, providerCode2, new BigDecimal("46.2"), 3));

        // Then:
        assertThat(shoppingCart.isEmpty()).isFalse();
        assertThat(shoppingCart.getItemsCount()).isEqualTo(4);
        assertThat(shoppingCart.getTotalCost()).isEqualByComparingTo(new BigDecimal("160.25"));

        List<Item> items = shoppingCart.getItems();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).getCode()).isEqualTo(itemCode1);
        assertThat(items.get(0).getProviderCode()).isEqualTo(providerCode1);
        assertThat(items.get(0).getQuantity()).isEqualTo(1);
        assertThat(items.get(0).getUnitCost()).isEqualByComparingTo(new BigDecimal("21.65"));

        assertThat(items.get(1).getCode()).isEqualTo(itemCode2);
        assertThat(items.get(1).getProviderCode()).isEqualTo(providerCode2);
        assertThat(items.get(1).getQuantity()).isEqualTo(3);
        assertThat(items.get(1).getUnitCost()).isEqualByComparingTo(new BigDecimal("46.2"));
    }

    @Test
    public void givenAShoppingCartWithItems_whenAddAnExistentItem_thenQuantityIsIncreased() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "98234291";
        String providerCode1 = "8483920";

        String itemCode2 = "5421431";
        String providerCode2 = "3284212343";

        shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1));
        shoppingCart.addItem(new Item(itemCode2, providerCode2, new BigDecimal("46.2"), 3));

        // When:
        shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 2));

        // Then:
        assertThat(shoppingCart.isEmpty()).isFalse();
        assertThat(shoppingCart.getItemsCount()).isEqualTo(6);
        assertThat(shoppingCart.getTotalCost()).isEqualByComparingTo(new BigDecimal("203.55"));

        List<Item> items = shoppingCart.getItems();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).getCode()).isEqualTo(itemCode1);
        assertThat(items.get(0).getProviderCode()).isEqualTo(providerCode1);
        assertThat(items.get(0).getQuantity()).isEqualTo(3);
        assertThat(items.get(0).getUnitCost()).isEqualByComparingTo(new BigDecimal("21.65"));

        assertThat(items.get(1).getCode()).isEqualTo(itemCode2);
        assertThat(items.get(1).getProviderCode()).isEqualTo(providerCode2);
        assertThat(items.get(1).getQuantity()).isEqualTo(3);
        assertThat(items.get(1).getUnitCost()).isEqualByComparingTo(new BigDecimal("46.2"));
    }

    @Test
    public void givenAShoppingCartWithItems_whenAddAnExistentItemWithDifferentCost_thenItemIsAdded() {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "98234291";
        String providerCode1 = "8483920";

        String itemCode2 = "5421431";
        String providerCode2 = "3284212343";

        shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1));
        shoppingCart.addItem(new Item(itemCode2, providerCode2, new BigDecimal("46.2"), 3));
        shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1));

        // When:
        shoppingCart.addItem(new Item(itemCode2, providerCode2, new BigDecimal("44.5"), 1));

        // Then:
        assertThat(shoppingCart.isEmpty()).isFalse();
        assertThat(shoppingCart.getItemsCount()).isEqualTo(6);
        assertThat(shoppingCart.getTotalCost()).isEqualByComparingTo(new BigDecimal("226.40"));

        List<Item> items = shoppingCart.getItems();
        assertThat(items.size()).isEqualTo(3);
        assertThat(items.get(0).getCode()).isEqualTo(itemCode1);
        assertThat(items.get(0).getProviderCode()).isEqualTo(providerCode1);
        assertThat(items.get(0).getQuantity()).isEqualTo(2);
        assertThat(items.get(0).getUnitCost()).isEqualByComparingTo(new BigDecimal("21.65"));

        assertThat(items.get(1).getCode()).isEqualTo(itemCode2);
        assertThat(items.get(1).getProviderCode()).isEqualTo(providerCode2);
        assertThat(items.get(1).getQuantity()).isEqualTo(3);
        assertThat(items.get(1).getUnitCost()).isEqualByComparingTo(new BigDecimal("46.2"));

        assertThat(items.get(2).getCode()).isEqualTo(itemCode2);
        assertThat(items.get(2).getProviderCode()).isEqualTo(providerCode2);
        assertThat(items.get(2).getQuantity()).isEqualTo(1);
        assertThat(items.get(2).getUnitCost()).isEqualByComparingTo(new BigDecimal("44.5"));
    }

    @Test
    public void givenAShoppingCartWithItems_whenRemoveItem_thenItemIsRemoved() throws ItemNotFoundException {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "98234291";
        String providerCode1 = "8483920";

        String itemCode2 = "5421431";
        String providerCode2 = "3284212343";

        shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1));
        shoppingCart.addItem(new Item(itemCode2, providerCode2, new BigDecimal("46.2"), 3));

        // When:
        shoppingCart.removeItem(itemCode2);

        // Then:
        assertThat(shoppingCart.isEmpty()).isFalse();
        assertThat(shoppingCart.getItemsCount()).isEqualTo(3);
        assertThat(shoppingCart.getTotalCost()).isEqualByComparingTo(new BigDecimal("114.05"));

        List<Item> items = shoppingCart.getItems();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).getCode()).isEqualTo(itemCode1);
        assertThat(items.get(0).getProviderCode()).isEqualTo(providerCode1);
        assertThat(items.get(0).getQuantity()).isEqualTo(1);
        assertThat(items.get(0).getUnitCost()).isEqualByComparingTo(new BigDecimal("21.65"));

        assertThat(items.get(1).getCode()).isEqualTo(itemCode2);
        assertThat(items.get(1).getProviderCode()).isEqualTo(providerCode2);
        assertThat(items.get(1).getQuantity()).isEqualTo(2);
        assertThat(items.get(1).getUnitCost()).isEqualByComparingTo(new BigDecimal("46.2"));

    }

    @Test
    public void givenAShoppingCartWithItems_whenRemoveItemWithQuantity1_thenItemIsRemoved() throws ItemNotFoundException {
        // Given:
        ShoppingCart shoppingCart = new ShoppingCart();
        String itemCode1 = "98234291";
        String providerCode1 = "8483920";

        String itemCode2 = "5421431";
        String providerCode2 = "3284212343";

        shoppingCart.addItem(new Item(itemCode1, providerCode1, new BigDecimal("21.65"), 1));
        shoppingCart.addItem(new Item(itemCode2, providerCode2, new BigDecimal("46.2"), 3));

        // When:
        shoppingCart.removeItem(itemCode1);

        // Then:
        assertThat(shoppingCart.isEmpty()).isFalse();
        assertThat(shoppingCart.getItemsCount()).isEqualTo(3);
        assertThat(shoppingCart.getTotalCost()).isEqualByComparingTo(new BigDecimal("138.6"));

        List<Item> items = shoppingCart.getItems();
        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0).getCode()).isEqualTo(itemCode2);
        assertThat(items.get(0).getProviderCode()).isEqualTo(providerCode2);
        assertThat(items.get(0).getQuantity()).isEqualTo(3);
        assertThat(items.get(0).getUnitCost()).isEqualByComparingTo(new BigDecimal("46.2"));

    }
}

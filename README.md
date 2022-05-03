# Carrito de compras

Se está implementando el carrito de compras de un sistema.

Los requisitos son los siguientes:

- Se pueden añadir al carrito uno o más items
- Un item lleva:
  - Código
  - Precio unitario
  - Cantidad
  - Código de proveedor
- Si se añade un item ya existente en el carrito, solo debe aumentarse la cantidad del item A MENOS QUE el precio sea diferente al que ya existe
- Se debe poder obtener el costo total del carrito
- Se debe poder eliminar items por código
  - Si se quiere eliminar un item que existe en el carrito con diferentes costos se debe eliminar primero el del costo más alto
- Si se intenta obtener el costo del carrito vacío, se debe lanzar una excepción EmptyShoppingCartException
- Se debe validar que el código del item y del proveedor no sean null ni cadena vacía. 
- El precio debe ser mayor que cero
- La cantidad debe ser entre 1 y 5

El siguiente código puede ser usado para calcular el costo total del carrito: 
```
BigDecimal totalCost = BigDecimal.ZERO;

for(Item item: items){
    totalCost = totalCost.add(item.getUnitCost().multiply(BigDecimal.valueOf(item.getQuantity())));
}

return totalCost;
```
Para comparar objetos BigDecimal se debe utilizar "compareTo" ejemplo: 
```
if(item.getUnitCost().compareTo(newItem.getUnitCost()) == 0) --> Si es igual a cero significa que son iguales 
```

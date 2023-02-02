### Add Item Cart
API to add item to a user's cart.
```agsl
POST /api/cart
```
#### Request Schema
```json
{
  "productId" : 1,
  "cartQuantity" : 1
}
```

#### Response Schema
```json
{
  "username": "prkharg",
  "cartItems": [
    {
      "productId": 1,
      "productName": "Tea",
      "cartQuantity": 1,
      "fulfillable": true
    }
  ]
}
```

### Delete Item From Cart
Delete Item from Cart
```agsl
POST /api/cart/delete
```
#### Request Schema
```json
{
  "productId" : 2
}
```

#### Response Schema

```json
{
  "username": "prkharg",
  "cartItems": [
    {
      "productId": 1,
      "productName": "Tea",
      "cartQuantity": 1,
      "fulfillable": true
    }
  ]
} // New Cart After deletion
```


### Fetch Cart
API to fetch currently logged in user's cart.
```agsl
GET /api/cart
```


#### Response Schema
```json
{
  "username": "prkharg",
  "cartItems": [
    {
      "productId": 1,
      "productName": "Tea",
      "cartQuantity": 1,
      "fulfillable": true
    }
  ]
}
```


### Fetch All Carts
Fetch All carts present in the system.
```agsl
GET /api/cart/all
```


#### Response Schema
```json
[
  {
    "username": "prkharg",
    "cartItems": [
      {
        "productId": 1,
        "productName": "Tea",
        "cartQuantity": 1,
        "fulfillable": true
      }
    ]
  }
]
```



## Product APIs

### List All Available Products
List all in-stock Items

```agsl
GET /api/product
```

#### Response Schema
```json
[
  {
    "productId": 2,
    "productName": "Brush",
    "productPrice": 124
  },
  {
    "productId": 3,
    "productName": "Table",
    "productPrice": 124
  }
]
```



### Create  Inventory
To create or update inventory
```agsl
POST /api/product
```
#### Request Schema
```json
{
  "productName" : "Brush",
  "price" : 124,
  "quantity" : 19
}
```
#### Response Schema
```json
{
  "productId": 1, 
  "productName": "Table",
  "quantity": 19,
  "price": 124
}
```


### Fetch All  Inventory
API to fetch all `available` as well as `out of stock` inventory.
```agsl
GET /api/product/all
```

#### Response Schema
```json
[
  {
    "productId": 1,
    "productName": "Tea",
    "quantity": 0,
    "price": 122
  },
  {
    "productId": 2,
    "productName": "Brush",
    "quantity": 19,
    "price": 124
  },
  {
    "productId": 3,
    "productName": "Table",
    "quantity": 19,
    "price": 124
  }
]
```

### Delete Inventory
API to delete inventory based on ``Product ID``
```agsl
POST /api/product/delete
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
  "productId": 2,
  "productName": null,
  "quantity": null,
  "price": null
}
```
---





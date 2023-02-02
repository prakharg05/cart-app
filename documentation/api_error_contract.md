


### API Error

Apart from authorization errors, all API failures responses have a `errorCode` indicating the cause of error.


| Error Code | ERROR                           | Reason                                       |
|------------|---------------------------------|----------------------------------------------|
| 5001       | `ERROR_INTERNAL_SERVER_FAILURE` | Something went wrong on server side          |
| 4001       | `ERROR_INVALID_INPUT`           | Invalid, malformed, incomplete input request |
| 4002       | `ERROR_INVALID_PRODUCT_ID`      | Invalid Product ID                           |
| 4003       | `ERROR_NOT_ENOUGH_INVENTORY`    | Too much inventory added to cart             |
| 4004       | `ERROR_CART_ITEM_MISSING`       | Trying to delete an invalid or missing item  |


---


## Error Response Schema for all endpoints
```json
{
  "errorCode": 5001,
  "errorMessage": "Something went wrong"
}
```


```agsl
{
    "errorCode": 4002,
    "errorMessage": "Invalid product id 6 "
}
```
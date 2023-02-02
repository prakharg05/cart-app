


### API Error

Apart from authorization errors, all API failures responses have a `errorCode` indicating the cause of error.


| Error Code | ERROR                           |     |
|------------|---------------------------------|-----|
| 5001       | `ERROR_INTERNAL_SERVER_FAILURE` |     |
| 4001       | `ERROR_INVALID_INPUT`           |     |
| 4002       | `ERROR_INVALID_PRODUCT_ID`      |     |
| 4003       | `ERROR_NOT_ENOUGH_INVENTORY`    |     |
| 4004       | `ERROR_CART_ITEM_MISSING`       |     |


---


#### Error Response Schema
```json
{
  "errorCode": 5001,
  "errorMessage": "Something went wrong"
}
```
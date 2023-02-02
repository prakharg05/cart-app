
## User Account APIs

### Register Account
```agsl
POST /api/account/register
```
#### Request Schema
```json
{
	"username" : "prkharg",
	"password" : "*********",
       "email" : "prkharg@shopper.com"
 }
```
#### Response Schema
```json
{
    "userId": 1, // number
    "username": "prkharg", // string
    "email": "prkharg@shopper.com", // string
    "suspended": false, // boolean
    "roles": [
        {
            "id": 4, // number
            "name": "ADMIN", // string
            "description": "Admin role" //string
        },
        {
            "id": 5,
            "name": "USER",
            "description": "User role"
        }
    ]
}
```



### Authenticate User
```agsl
POST /api/account/authenticate
```
#### Request Schema
```json
{
	"username" : "prkharg",
	"password" : "*********"
}
```
#### Response Schema
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcmtoYXJnIiwicm9sZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImlhdCI6MTY3NTI4OTM3MSwiZXhwIjoxNjc1MzA3MzcxfQ.m-awnFe6e04ShFbxjQ9SdGwCy_j5t7TnLbhLj_eSKOo"
}
```

#### All subsequent API requests must be authenticated using the Auth Token provided by Authenicate API. 

The Auth Token must be passed as an Authorization header as follows

```agsl
Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcmtoYXJnIiwicm9sZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImlhdCI6MTY3NTI4OTM3MSwiZXhwIjoxNjc1MzA3MzcxfQ.m-awnFe6e04ShFbxjQ9SdGwCy_j5t7TnLbhLj_eSKOo
```

The rest of the document assumes, to work with an Authenticated user ```prkharg```, with role ```ADMIN```


### Fetch User Account
Fetch the current user account.

```agsl
GET /api/account
```

#### Response Schema
```json
{
  "userId": 1,
  "username": "prkharg",
  "email": "prkharg@shopper.com",
  "suspended": false,
  "roles": [
    {
      "id": 4,
      "name": "ADMIN",
      "description": "Admin role"
    },
    {
      "id": 5,
      "name": "USER",
      "description": "User role"
    }
  ]
}
```

### Suspend User Account
Suspend a user account.
Only Users with ```ADMIN``` role can call the API

```agsl
POST /api/account/suspend
```

#### Request Schema
```json
{
	"username" : "prkharg"
}
```

#### Response Schema
```json
{
  "userId": 1,
  "username": "prkharg",
  "email": "prkharg@shopper.com",
  "suspended": true,
  "roles": [
    {
      "id": 4,
      "name": "ADMIN",
      "description": "Admin role"
    },
    {
      "id": 5,
      "name": "USER",
      "description": "User role"
    }
  ]
}
```


### Reinstate User Account
Reinstate a user account, by setting suspended status as ```false``` .

```agsl
POST /api/account/reinstate
```

#### Request Schema
```json
{
	"username" : "prkharg"
}
```

#### Response Schema
```json
{
  "userId": 1,
  "username": "prkharg",
  "email": "prkharg@shopper.com",
  "suspended": false,
  "roles": [
    {
      "id": 4,
      "name": "ADMIN",
      "description": "Admin role"
    },
    {
      "id": 5,
      "name": "USER",
      "description": "User role"
    }
  ]
}
```
### Fetch All Accounts
Fetch all accounts in the system. 

Only Users with ```ADMIN``` role can call the API

```agsl
GET /api/account/all
```

#### Response Schema
```json
[
  {
    "userId": 1,
    "username": "prkharg",
    "email": "prkharg@shopper.com",
    "suspended": true,
    "roles": [
      {
        "id": 4,
        "name": "ADMIN",
        "description": "Admin role"
      },
      {
        "id": 5,
        "name": "USER",
        "description": "User role"
      }
    ]
  },
  {
    "userId": 2,
    "username": "bob",
    "email": "bob@shopper.com",
    "suspended": null,
    "roles": [
      {
        "id": 4,
        "name": "ADMIN",
        "description": "Admin role"
      },
      {
        "id": 5,
        "name": "USER",
        "description": "User role"
      }
    ]
  }
]
```

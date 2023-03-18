# Simple REST API with JWT Authentication


## Endpoints


`/api/v1/auth/register`

Register with such a JSON in the body :

```json
{
    "firstname": "Bruce",
    "lastname": "Wayne",
    "email": "bruce@wayne.com",
    "password" : "1234"
}
```

You'll get a JWT token in the response body.

`/api/v1/auth/authenticate`

Login with such a JSON in the request's body :
```json
{
    "email": "bruce@wayne.com",
    "password" : "1234"
}
```

You'll receive a JWT token in the response's body


`/api/v1/demo`

You'll get a simple `Hello World!` plain text reponse if you are authenticated.
To be authenticated you need to have a Bearer token header with the JWT token receive via login or signup.

Example :

```http request
Authorization: Bearer MY.JWT.TOKEN
```


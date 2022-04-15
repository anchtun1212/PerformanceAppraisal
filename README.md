# Run the project

To run the project (I am using gradle version 7.2):

```sh
cd product-management

gradle bootJar

gradle bootRun
```

### Access server side:

```sh
http://localhost:8099
```

You can see the product list: 

```sh
http://localhost:8099/api/user/products
```
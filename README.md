# EdcKoko

This is an E-Commerce project using Spring Boot for my final assignment. It's still in developing state and is being updated with new technology.
Feel free to contact me for further questions.

## Development

Update your local database connection and your prefer port in `application.yml` or create your own `application-local.yml` file to override
settings for development.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options".

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing.

## Build

The application can be built using the following command:

```
mvnw clean package
```

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/edc-koko-0.0.1-SNAPSHOT.jar
```


## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
* [Thymeleaf docs](https://www.thymeleaf.org/documentation.html)
* [Bootstrap docs](https://getbootstrap.com/docs/5.3/getting-started/introduction/)
* [Htmx in a nutshell](https://htmx.org/docs/)
* [Learn Spring Boot with Thymeleaf](https://www.wimdeblauwe.com/books/taming-thymeleaf/)  

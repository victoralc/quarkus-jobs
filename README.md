# jobs

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/jobs-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- Flyway ([guide](https://quarkus.io/guides/flyway)): Handle your database schema migrations
- Qute Web ([guide](https://quarkiverse.github.io/quarkiverse-docs/quarkus-qute-web/dev/index.html)): Serves Qute templates directly over HTTP.
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, Jakarta Persistence)
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- Web Bundler ([guide](https://docs.quarkiverse.io/quarkus-web-bundler/dev/)): Creating full-stack Web Apps is fast and simple with this extension. Zero config bundling for your web-app scripts (js, jsx, ts, tsx), dependencies (jquery, react, htmx, ...) and styles (css, scss, sass).
- JobRunr ([guide](https://www.jobrunr.io/en/documentation/configuration/quarkus/)): Integrates JobRunr, a distributed background job scheduler
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### Qute Web

Qute templates like `some-page.html` served via HTTP automatically by Quarkus from the `src/main/resource/templates/pub` directory. No controllers needed. Once the quarkus app is started visit the generated page at http://localhost:8080/some-page?name=World

[Related guide section...](https://docs.quarkiverse.io/quarkus-qute-web/dev/index.html)

### Web Bundler

This is a tiny app `web-bundler.html` to get started with the Web Bundler. Once the quarkus app is started visit the generated page at http://localhost:8080/web-bundler.html

[Related guide section...](https://docs.quarkiverse.io/quarkus-web-bundler/dev/)


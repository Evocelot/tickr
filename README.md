# TickR

The `TickR` is a robust and scalable solution built using Spring Boot, designed to handle scheduled tasks efficiently. Initially focused on sending HTTP requests, the platform is engineered to support additional use cases in the future. By leveraging `Quartz` for task scheduling, TickR ensures precise and reliable execution of tasks. Designed as a standalone microservice, it can be seamlessly integrated into larger systems, offering flexibility and ease of use for managing scheduled operations.

The starter project: `springboot - microbase` is an open-source starter project for quickly building `scalable` and `maintainable` Spring Boot-based microservices. For more details: [Evocelot/springboot-microbase](https://github.com/Evocelot/springboot-microbase).

## Technologies used

- Java 21
- SpringBoot 3.4.1
- Docker / Podman
- Make
- Elasticsearch
- Logstash
- Kibana
- Jaeger
- Prometheus
- Grafana
- Quartz

## Job types

The TickR application currently supports two distinct job types, tailored to meet different operational needs. Each job type is configured via the application's YAML or properties file and scheduled with Quartz.

### 1. HTTP jobs

HTTP jobs are designed to perform scheduled HTTP requests, making them ideal for tasks like invoking APIs or triggering webhooks. These jobs include details such as the HTTP method, URL, and body.

Example Configuration:
```yml
scheduler:
  tasks:
    - name: sendHttpRequestTask
      cron: "0 0/5 * * * ?"
      http:
        method: POST
        url: http://example.com/api
        body: '{"key": "value"}'
```

When this job is triggered:

- The configured HTTP request (e.g., a POST to http://example.com/api) is executed.
- The job logs the request details and the response for debugging and monitoring purposes.

### 2. Log Jobs

Log jobs are simpler and serve primarily as scheduled logging tasks. These jobs are useful for periodic logging of specific messages, often for diagnostic or informational purposes.

Example Configuration:
```yml
scheduler:
  tasks:
    - name: printTestLogTask
      cron: "0 0 * * * ?"
      log:
        message: "Hourly log message for system status."
```
When this job is triggered:

The specified message (e.g., "Hourly log message for system status.") is logged using the application's logging framework.

## How to use:

The project includes a `Makefile` that simplifies starting the application. Each target in the Makefile can be executed independently.

To run the application in a local container, execute the following command:

```bash
make start-local-container
```

> **_NOTE:_** If you want to run only the application itself (without ELK and observability tools), set the Spring profile to `dev`. This can be done inside the `Makefile`.

By default, the application runs on port `8080`.

The swagger UI can be accessed at: http://localhost:8080/swagger-ui/index.html

## Configuration

### Configuring via environment variables

When running the application in container, the following environment variables can be specified:

Environment variable | Sample value | Description |
--- | --- | --- |
SPRING_PROFILES_ACTIVE | dev | The profile to run with the application. Can be `dev` and/or `logstash`. |
LOGSTASH_HOST | logstash | The name of the logstash container to push the logs from the springboot app. It is only required when using the `logstash` profile. |
LOGSTASH_PORT | 5000 | The port of the logstash container. It is only required when using the `logstash` profile. |
TRACING_URL | http://jaeger:4318/v1/traces | The url of the jaeger instance for sending tracing details. |

### Configuring via properties file

The Spring Boot application can also be configured via properties files located in the `app/src/main/resources` directory.

Properties key | Sample value | Description |
--- | --- | --- |
... | ... | ... |


## local.env

The project includes a file named `local.env`, which stores the application details.

Environment variable | Sample value | Description |
--- | --- | --- |
APPNAME | sample-module | The name of the application |
VERSION | 0.0.1-SNAPSHOT | The version number of the application |

## Building the Docker Image

To build the Docker image for this application, use the following command:

```bash
make build-docker-image
```

This command reads the configuration from the `local.env` file, builds the Docker image with the specified settings, and tags it according to the `APPNAME` and `VERSION` values in the configuration.

## Logging

The project utilizes the `ELK stack` for `centralized log collection` and monitoring:

- Logstash: Extracts logs from the application and forwards them to Elasticsearch.
- Elasticsearch: Stores, indexes, and makes the application's logs searchable.
- Kibana: Provides a user interface for managing the logs stored in Elasticsearch.

> **_NOTE:_** To enable log forwarding to Logstash, set `SPRING_PROFILES_ACTIVE` to `logstash` in the container’s startup configuration.

View logs in Kibana:
![View logs in Kibana](img/kibana.png)

## Monitoring

The project integrates the following tools for monitoring and observability:

- Jaeger: Collects and displays tracing information.
- Prometheus: Collects and stores application metrics.
- Grafana: Visualizes metrics in an intuitive interface.

To enable full monitoring capabilities (logs, tracing, and metrics), use the following command for starting the application:

```bash
make all
```

View tracing informations in Jaeger:
![View tracing informations in Jaeger](img/jaeger.png)

App monitoring in Grafana:
![App monitoring in Grafana](img/grafana.png)

## Contributions

Contributions to the project are welcome! If you find issues or have suggestions for improvements, feel free to open an issue or submit a pull request.

## Documentation

For more documentation, see: [Documentation](/docs/docs.md)

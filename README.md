# TickR

`TickR` is a robust solution built using Spring Boot, designed to handle scheduled tasks efficiently. Initially focused on sending HTTP requests, the platform is engineered to support additional use cases in the future. By leveraging `Quartz` for task scheduling, `TickR` ensures precise and reliable execution of tasks. Designed as a standalone microservice, it can be seamlessly integrated into larger systems, offering flexibility and ease of use for managing scheduled operations.

The starter project: `springboot - microbase` is an open-source starter project for quickly building `scalable` and `maintainable` Spring Boot-based microservices. For more details: [Evocelot/springboot-microbase](https://github.com/Evocelot/springboot-microbase).

## Technologies used

- **Java 21**
- **SpringBoot 3.4.1**
- **Docker / Podman**
- **Make**
- **Quartz**
- **Elasticsearch**
- **Logstash**
- **Kibana**
- **Jaeger**
- **Prometheus**
- **Grafana**

## How to run:

The project includes a `Makefile` to simplify application startup. Each Makefile target can be executed independently.

> **_NOTE:_** If you are using Docker instead of Podman, replace `podman` with `docker` in the Makefile commands.

### Run with full stack

To run the application along with ELK stack and observability features, execute:

```bash
make all
```

This command starts the following containers:

- elasticsearch
- logstash
- kibana
- jaeger
- prometheus
- grafana
- tickr-module

By default, the tickr-module runs on port `8081`.

### Run TickR module only

To run only the TickR module only:

```bash
make start-local-container
```

> **_NOTE:_** To disable log collection and tracing, manually set the `LOGSTASH_ENABLED` and `TRACING_ENABLED` environment variables to `"false"` in the `Makefile`.

## Job types

The TickR application currently supports two distinct job types, tailored to meet different operational needs. Each job type can be configured via `environment variables`, application's `YAML file` or `properties file`.

### 1. HTTP job

`HTTP jobs` are designed to perform scheduled HTTP requests, making them ideal for tasks like invoking APIs or triggering webhooks. These jobs include details such as the HTTP method, URL, and body.

#### Configuration examples

Via environment variables:

```bash
podman run \
  -e TZ=UTC \
  -e SCHEDULER_TASKS_0_NAME=testHttpTask \
  -e SCHEDULER_TASKS_0_CRON="0 * * * * ?" \
  -e SCHEDULER_TASKS_0_HTTP_METHOD=GET \
  -e SCHEDULER_TASKS_0_HTTP_URL=http://example.com \
  -e SCHEDULER_TASKS_0_HTTP_BODY=null \
  evocelot/tickr:1.0.0
```

Via YAML:

```yml
scheduler:
  tasks:
    - name: testHttpTask
      cron: "0 * * * * ?"
      http:
        method: GET
        url: http://example.com
        body: null
```

When this job is triggered:

- The configured HTTP request is executed.
- The job logs the request details and the response for debugging and monitoring purposes.

### 2. Custom Jobs

Custom jobs are simpler and serve primarily as scheduled logging tasks.
This type of job is intended to guide the implementation of custom jobs.

#### Configuration examples

Via environment variables:

```bash
podman run \
  -e TZ=UTC \
  -e SCHEDULER_TASKS_0_NAME=testTask \
  -e SCHEDULER_TASKS_0_CRON="0 * * * * ?" \
  -e SCHEDULER_TASKS_0_CUSTOM_MESSAGE="Hourly log message for system status." \
  evocelot/tickr:1.0.0
```

Via YAML:

```yml
scheduler:
  tasks:
    - name: testTask
      cron: "0 * * * * ?"
      custom:
        message: "Hourly log message for system status."
```
When this job is triggered:

The specified message is logged using the application's logging framework.

## Logging

The project utilizes the `ELK stack` for `centralized log collection` and monitoring:

- Logstash: Extracts logs from the application and forwards them to Elasticsearch.
- Elasticsearch: Stores, indexes, and makes the application's logs searchable.
- Kibana: Provides a user interface for managing the logs stored in Elasticsearch.

> **_NOTE:_** To enable log forwarding to Logstash, set the `LOGSTASH_ENABLED` environment variable to `"true"` in the container’s startup configuration.

View logs in Kibana:
![View logs in Kibana](img/kibana.png)

## Monitoring

The project integrates the following tools for monitoring and observability:

- Jaeger: Collects and displays tracing information.
- Prometheus: Collects and stores application metrics.
- Grafana: Visualizes metrics in an intuitive interface.

> **_NOTE:_** To enable tracing collection, set the `TRACING__ENABLED` environment variable to `"true"` in the container’s startup configuration.

View tracing informations in Jaeger:
![View tracing informations in Jaeger](img/jaeger.png)

App monitoring in Grafana:
![App monitoring in Grafana](img/grafana.png)

## Documentation

Detailed documentation is available here: [Documentation](/docs/docs.md)

## Contributions

Contributions to the project are welcome! If you find issues or have suggestions for improvements, feel free to open an issue or submit a pull request.

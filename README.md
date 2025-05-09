# TickR

`TickR` is a robust solution built using Spring Boot, designed to handle scheduled tasks efficiently. Initially focused on sending HTTP requests, the platform is engineered to support additional use cases in the future. By leveraging `Quartz` for task scheduling, `TickR` ensures precise and reliable execution of tasks. Designed as a standalone microservice, it can be seamlessly integrated into larger systems, offering flexibility and ease of use for managing scheduled operations.

The starter project: `springboot - microbase` is an open-source starter project for quickly building `scalable` and `maintainable` Spring Boot-based microservices. For more details: [Evocelot/springboot-microbase](https://github.com/Evocelot/springboot-microbase).

## Technologies used

- **Java 21**
- **SpringBoot 3.4.5**
- **Docker / Podman**
- **Make**
- **Quartz**

## Core Functions

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

### 2. Kafka Producer jobs

This job automatically sends messages to a Kafka topic based on the provided configuration.

#### Configuration examples

Via environment variables:

```bash
podman run \
  -e TZ=UTC \
  -e SCHEDULER_TASKS_0_NAME=kafkaJob \
  -e SCHEDULER_TASKS_0_CRON="0 * * * * ?" \
  -e SCHEDULER_TASKS_0_KAFKA_PRODUCER_TOPIC="Test topic" \
  -e SCHEDULER_TASKS_0_KAFKA_PRODUCER_MESSAGE="Test message" \
  evocelot/tickr:1.0.0
```

Via YAML:

```yml
scheduler:
  tasks:
    - name: kafkaJob
      cron: "0 * * * * ?"
      kafka-producer:
        topic: test-topic
        message: test-message
```
When this job is triggered:

It sends the specified message to the defined Kafka topic.

### Custom Jobs

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

## How to run:

The project includes a `Makefile` to simplify application startup. Each Makefile target can be executed independently.

> **_NOTE:_** If you are using Docker instead of Podman, replace `podman` with `docker` in the Makefile commands.

### Run with full stack

To run the application along with ELK stack and observability features, execute:

```bash
make start-monitoring-containers
make start-kafka
make start-local-container
```

This commands starts the following containers:

- elasticsearch
- logstash
- kibana
- jaeger
- prometheus
- grafana
- zookeeper
- kafka-ui
- kafka
- tickr-module

By default, the tickr-module runs on port `8082`.

### Run the module only

To run only the module only:

```bash
make start-local-container
```

> **_NOTE:_** To disable log collection, tracing and communication via kafka, manually set the `LOGSTASH_ENABLED`,  `TRACING_ENABLED` and `KAFKA_ENABLED` environment variables to `"false"` in the `Makefile`.

## Docker Images

The released Docker images for this application are available at: [dockerhub](https://hub.docker.com/r/evocelot/tickr)

## Documentation

Detailed documentation is available here: [Documentation](/docs/index.md)

## Contributions

Contributions to the project are welcome! If you find issues or have suggestions for improvements, feel free to open an issue or submit a pull request.

# TickR

## Application Architecture  
The application's architectural design is illustrated in the diagram below:

![architecture.drawio](architecture.drawio.png)

## Environment variables

The following environment variables can be configured when running the application:

Environment variable | Sample value | Description |
--- | --- | --- |
SPRING_PROFILES_ACTIVE | dev | The profile to run with the application. |
LOGSTASH_ENABLED | "true" | Enable or disable log forwarding to Logstash (`"true"` or `"false"`). |
LOGSTASH_HOST | logstash | The name of the logstash container to push the logs from the springboot app. It is only required when the `LOGSTASH_ENABLED` environemnt variable is `"true"`. |
LOGSTASH_PORT | 5000 | The port of the logstash container. It is only required when the `LOGSTASH_ENABLED` environemnt variable is `"true"`. |
TRACING_ENABLED | "true" | Enable or disable tracing (`"true"` or `"false"`). |
TRACING_URL | http://jaeger:4318/v1/traces | The url of the jaeger instance for sending tracing details. It is only required when the `TRACING_ENABLED` environemnt variable is `"true"`. |
SCHEDULER_TASKS_X_NAME | testTask | The name of the scheduled task. `X` represents the task index (e.g., `SCHEDULER_TASKS_0_NAME`).
SCHEDULER_TASKS_X_CRON | "0 * * * * ?" | The cron expression that defines the schedule for the task. `X` represents the task index (e.g., `SCHEDULER_TASKS_0_CRON`).
SCHEDULER_TASKS_X_CUSTOM_MESSAGE | testMessage | The custom log message displayed when the task runs, applicable for `custom tasks`. `X` represents the task index (e.g., `SCHEDULER_TASKS_0_CUSTOM_MESSAGE`).

## local.env

The project includes a file named `local.env`, which stores the application details.

Environment variable | Sample value | Description |
--- | --- | --- |
APPNAME | tickr-module | The name of the application |
VERSION | 0.0.1-SNAPSHOT | The version number of the application |

## Building the Docker Image

To build the Docker image for this application, use the following command:

```bash
make build-docker-image
```

This command reads the configuration from the `local.env` file, builds the Docker image with the specified settings, and tags it according to the `APPNAME` and `VERSION` values in the configuration.

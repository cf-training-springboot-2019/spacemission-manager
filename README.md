# spacecrew-manager
[![License](https://img.shields.io/github/license/cf-training-springboot-2019/spacecrew-manager)](https://github.com/cf-training-springboot-2019/y/blob/master/LICENSE)

A small Spring microservice aimed towards both governance, management and operational tasks applied towards for the various space mission resources within the Spaceover platform.
The application uses Spring Boot parent bom version 2.4.5 as its foundation.

## Terminology
| Name | Description |
|:----------:|:-------------|
| SpaceMission | A service managed model entity that retains various information associated with a space mission. |


## Operations
| Name | Description |
|:----------:|:-------------|
| getSpaceMissions | Synchronous operation that allows the retrieval of multiples persisted space mission resource entries. |
| getSpaceMission |  Synchronous operation that allows the retrieval of a single persisted space mission resource entry. |
| createSpaceMission |  Synchronous operation that allows the persistence of a single space mission resource entry. |
| patchSpaceMission| Synchronous operation that allows the partial update of a single space mission persisted resource entry. |
| putSpaceMission| Synchronous operation that allows the full update of a single space mission resource entry. |
| deleteSpaceMission | Synchronous operation that allows the removal of a single space mission resource entry. |

## Getting Started

To quickly run the application simply execute spring-boot mvn plugin's **run** goal. This action most be performed from the project's root directory by using the following command in a terminal of your choosing.
```
mvn spring-boot:run
```

An alternative method would be through the creation of an OCI image by executing the plugin's build-image goal.
This image can then be used to run a standalone docker container by using the following command sequence
```
mvn spring-boot:build-image
docker run -d --name [container-name] -p 8080:8080 ${docker.registry}/${project.artifactId}:v${project.version}
```

## Structure
Here are the services beans and relationship:

```mermaid
graph LR
A[SpaceMissionController] --> B1(SpaceMissionService)
A --> B2(SpaceShipClientService)
B1 --> C1(SpaceMissionRepository)
```

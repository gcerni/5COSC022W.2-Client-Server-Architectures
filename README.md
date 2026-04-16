
# 5COSC022W.2-Client-Server-Architectures
# Smart Campus API

A RESTful web service built with Java JAX-RS and Apache Tomcat for managing campus rooms, sensors and sensor data.

The API is designed around a Hierarchical Resource Model. Resources are nested to maintain logical context (e.g., readings belong to a specific sensor context).

### Key Features:
Decoupled Validation: Uses custom ExceptionMappers to separate business logic validation from HTTP response formatting.

Observability: Implements a ContainerFilter to log every lifecycle event (Method, URI, and Status Code) to the server console.

Error Handling: Implements specific HTTP status codes for business conflicts:

403 Forbidden: Maintenance mode locks.

409 Conflict: Room deletion with active dependencies.

422 Unprocessable Entity: Linked resource (Room ID) validation.


## Installation
#### Step-by-Step Launch

Clone the project to your local workspace.

Open Project: Open the project in your IDE.

Clean and Build: 
* Right-click the project and select Clean and Build. This ensures all dependencies are linked and the .war file is generated.

Configure Tomcat:
* Ensure your Apache Tomcat server is added to the IDE's "Servers" tab.

Run:

* Right-click the project and select Run. The IDE will deploy the application to Tomcat.

The API base URL will be: http://localhost:8080/smartCampusAPI/api/v1


## Usage/Examples

Sample cURL Commands
Use these commands to interact with the API via terminal. Replace localhost:8080 if your port differs.

####   1. Discovery end point

```terminal
curl -X GET http://localhost:8080/smartCampusAPI/api/v1
```

####   2. List all rooms

```terminal
curl -X GET http://localhost:8080/smartCampusAPI/api/v1/rooms
```

####   3. Register a New Sensor (Successful)
Registers a new sensor to an existing room (LIB-301).

```terminal
curl -X POST http://localhost:8080/smartCampusAPI/api/v1/sensors -H "Content-Type: application/json" -d '{"id": "SENS-007", "type": "TEMPERATURE", "status": "ACTIVE", "currentValue": 25.4,"roomId": "LIB-301"}'
```


####   4. Add a Sensor Reading
Adds a new data point to a specific sensor's history.

```terminal
curl -X POST http://localhost:8080/smartCampusAPI/api/v1/sensors/SENS-001/readings  -H "Content-Type: application/json" -d '{"value": 22.5}'
```


####   5. Attempt Deletion of Occupied Room (409 Conflict)
Demonstrates the RoomNotEmptyException mapper.

```terminal
curl -i -X DELETE http://localhost:8080/smartCampusAPI/api/v1/rooms/LIB-301
```


####   6. Post to a Maintenance Sensor (403 Forbidden)
Demonstrates the SensorUnavailableException mapper.

```terminal
curl -i -X POST http://localhost:8080/smartCampusAPI/api/v1/sensors/MAINT-01/readings -H "Content-Type: application/json" -d '{"value": 10.0}'
```


## Report Answers

### Part 1: Service Architecture & Setup
#### 1.1 - Request lifecycle
When working with a JAX-RS resource class, the default lifecycle is per-request, which means that it creates a new instance at runtime for each incoming matching request, rather than reusing a singleton instance. You have to specifically configure the request as a singleton for it to be reused across requests. 
With the default lifecycle, instance fields belong to only one request. This keeps short lived state in them without concurrent access from other requests. Instead, with a singleton resource, fields are shared among all threads serving requests.
To prevent data loss, we use static data structures in the form of ArrayList or HashMap, outside the resource class itself and then access them within each resource class, so the data isn't deleted with each request to the resource class. If we were to place the data in a resource, then this would typically start empty with every new request.

#### 1.2 - Hypermedia / HATEOAS
Hypermedia is considered the staple of advanced RESTful design because it provides not only the data that exists on the server, but also what the client can do as they use the API through links that are provided after the request response is sent. This improves discoverability, as the developers can find available actions directly in the response instead of having to search in a static documentation.

### Part 2: Room Management
#### 2.1 - Returning single IDs vs full room objects
Selectively returning only room IDs reduces payload size versus having to return the full size data present for each room. This saves network bandwidth, but it adds extra steps for the client who must make additional requests to gather the details of each room. Returning the full details instead uses more bandwidth upfront, but it eases the client usage because they don’t have to perform multiple requests.

#### 2.2 - Is the DELETE operation idempotent?
The way I have implemented my DELETE operations in the room and sensor resources achieves idempotency. For instance, calling it multiple times on the same ID leaves the data in the same final state after the first successful delete. If the client made multiple requests with the same DELETE operation, the first would delete the instance and then it would throw an error because the ID  they were trying to delete wouldn’t be there anymore. 

### Part 3: Sensor Operations & Linking
#### 3.1 - How does JAX-RS handle JSON mismatch?
When a POST method is annotated with the @Consumes(MediaType.APPLICATION_JSON), JAX-RS would only accept and route requests that have JSON as their content-type. Anything else that would be a mismatch, the runtime would treat it as not valid and would typically reject the request with a 415 status for an unsupported media type.

#### 3.2 - Why use @QueryParams for filtering?
Query parameters are designed for filtering, while path parameters identify a specific resource. When we use @QueryParams, we allow the client to optionally filter by type or other criteria to help them find data in a resource. Instead, a path would only give us specific data from a resource without the option to be flexible.

### Part 4: Deep Nesting with Sub - Resources
#### 4.1 - Benefits of the subresource locators pattern
A single controller that defines every nested path with time would become hard to read, to test and to maintain as the API grows. Using subresource locators splits the URI tree into smaller classes, where each class has its own behaviour, validation and dependency. This separation reduces bloat to a single file and helps with the scalability of a project over time. It also makes testing easier as you can test for functionalities independently.

### Part 5: Advanced Error Handling, Exception Mapping & Logging
#### 5.2 - Why is 422 more accurate than 404 when missing a reference in a payload?
A 404 usually means the target resource was not found, while a 422 means the server understood the request body, but it couldn't process the instructions it contained. For example, when the payload contains content that refers to an invalid or unavailable instance, such as a wrong roomId.

#### 5.4 - The risks of exposing Java stack traces
From a security perspective, exposing internal Java stack traces to external API consumers provides errors that can be used as reconnaissance material by malevolent actors. For example, a Java stack trace can expose class names, package structure, method names or reveal libraries in use, so attackers can use this information to exploit vulnerabilities. The main strategy to avoid leaks is to return generic error messages to the client and log the full trace only on the server side.

#### 5.5 - Why use JAX-RS filters for logging?
Using JAX-RS filters for logging eliminates code duplication and avoids the need to introduce Logger.info() in every resource method. Instead, keeping the logging behaviour in one class makes it more scalable, easier to maintain and it gives you one interception point for all matching requests. Filters are really useful when needing request and response traces for debugging or auditing the system. Also, they make it easier to keep the log format uniform, which helps with search and monitoring tools.

## Authors

- [@gcerni](https://github.com/gcerni)
- [w2112281] Gianluca Cerniglia


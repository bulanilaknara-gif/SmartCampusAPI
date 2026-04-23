# Smart Campus API

## Overview

This project is a RESTful API developed using JAX-RS for managing rooms, sensors, and sensor readings within a smart campus system.

The system allows:

* Managing rooms and their capacities
* Registering sensors to rooms
* Recording and retrieving sensor readings
* Handling errors with proper HTTP responses

---

## How to Run the Project

1. Open the project in NetBeans
2. Ensure server (Tomcat) is configured
3. Run the project
4. Access API at:

```
http://localhost:8080/SmartCampusAPI/api/v1
```

---

## Postman Requests

### 1. Create Room

* Method: **POST**
* URL:

```
http://localhost:8080/SmartCampusAPI/api/v1/rooms
```

* Headers:

```
Content-Type: application/json
```

* Body (raw JSON):

```json
{
  "id": "R1",
  "name": "Lab",
  "capacity": 50
}
```

---

### 2. Get Rooms

* Method: **GET**
* URL:

```
http://localhost:8080/SmartCampusAPI/api/v1/rooms
```

---

### 3. Create Sensor

* Method: **POST**
* URL:

```
http://localhost:8080/SmartCampusAPI/api/v1/sensors
```

* Headers:

```
Content-Type: application/json
```

* Body:

```json
{
  "id": "S1",
  "type": "Temp",
  "roomId": "R1",
  "status": "ACTIVE"
}
```

---

### 4. Add Reading

* Method: **POST**
* URL:

```
http://localhost:8080/SmartCampusAPI/api/v1/sensors/S1/readings
```

* Headers:

```
Content-Type: application/json
```

* Body:

```json
{
  "value": 25.5
}
```

---

### 5. Get Readings

* Method: **GET**
* URL:

```
http://localhost:8080/SmartCampusAPI/api/v1/sensors/S1/readings
```

---






## 📄 Report

[Download Full Report](answers.pdf)


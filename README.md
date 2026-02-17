# Distributed Real-Time Monitoring & Alerting System

A production-grade distributed monitoring and alerting platform built using **Spring Boot Microservices Architecture** and **Apache Kafka** for real-time event streaming and fault-tolerant alert processing.

---

## 🚀 Overview

This system processes real-time events, evaluates alert conditions using stateful processing, and dispatches notifications through decoupled services.  

It demonstrates:
- Event-driven microservice architecture
- Fault-tolerant message processing
- Docker-based containerized deployment

---

## 🏗️ Architecture

### High-Level Flow

Event Source  
→ Kafka Topic  
→ Monitoring Service  
→ Alert Engine  
→ Notification Service  

### Services

### 1️⃣ Monitoring Service
- Publishes system/application metrics to Kafka topics.
- Acts as the event producer.

### 2️⃣ Alert Engine
- Kafka consumer with configurable consumer groups.
- Performs stateful processing on incoming events.
- Evaluates alert rules.
- Publishes alert events to a separate topic.

### 3️⃣ Notification Service
- Independent microservice.
- Consumes alert events.
- Sends notifications (Email/SMS/Mock logs).
- Fully decoupled from alert evaluation logic.

---

## 🧠 Key Concepts Implemented

- Apache Kafka event streaming
- Topic partitioning for scalability
- Consumer groups for horizontal scaling
- Offset management & fault tolerance
- JSON serialization/deserialization
- Decoupled microservice communication
- Production-ready Docker containerization

---

## ⚙️ Tech Stack

- Java 17
- Spring Boot
- Spring Kafka
- Apache Kafka
- Docker
- REST APIs
- YAML Configuration

---

## 📦 Kafka Topics

| Topic Name        | Purpose |
|------------------|----------|
| monitoring-events | Raw system metrics/events |
| alert-events      | Generated alerts |

---

## 🐳 Docker Setup

Each microservice runs inside its own container:

- monitoring-service
- alert-engine
- notification-service
- kafka
- zookeeper

This enables:
- Easy deployment
- Environment isolation
- Horizontal scaling
- Production-grade setup

---

## 📊 Scalability Design

- Kafka partitions enable parallel consumption.
- Consumer groups allow multiple instances of Alert Engine.
- Services are stateless except alert processing logic.
- Notification service can scale independently.

---

## 🛠️ How to Run

docker-compose up --build

--> Verify services
docker ps

--> To check logs
docker-compose logs -f

--> Stop the system
docker-compose down
```bash
docker-compose up -d

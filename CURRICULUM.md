# Java Microservices Mastery — Curriculum & Roadmap

**Learner:** Mariam (complete beginner to Java)
**Goal:** A resume-worthy, production-grade microservices e-commerce platform, built from zero Java knowledge, one session at a time.
**Format:** Each session is a live lesson — concept taught first, then applied immediately by writing real code for the project. Nothing is copy-pasted without understanding it.

This document is the single source of truth for where we are. At the start of every new session, read this file first to see what's done and what's next. After every session, update the checkboxes and add a short "session log" note.

## The project we're building: ShopSphere

An e-commerce platform split into independently deployable microservices:

- **user-service** — registration, login, profiles
- **product-service** — product catalog, categories, search
- **inventory-service** — stock levels, reservations
- **order-service** — cart → order lifecycle
- **payment-service** — payment processing (simulated gateway)
- **notification-service** — emails/SMS on order events (async, event-driven)
- **api-gateway** — single entry point for clients
- **discovery-server** — service registry (Eureka)
- **config-server** — centralized configuration

Cross-cutting infrastructure added progressively: PostgreSQL per service, Kafka for events, Resilience4j for fault tolerance, JWT/OAuth2 security, Docker & docker-compose, Kubernetes manifests, Prometheus/Grafana + Zipkin tracing, centralized logging (ELK), GitHub Actions CI/CD.

## Phase 0 — Foundations (Java from scratch)

- [x] Session 0: Roadmap + workspace setup (this session)
- [ ] Session 1: What Java is (JVM/JDK/JRE), setting up locally, variables, primitive types, operators
- [ ] Session 2: Classes, objects, constructors, methods, `this`, encapsulation — write `Product` and `User` domain classes
- [ ] Session 3: Inheritance, polymorphism, abstract classes, interfaces — model shared behavior across domain classes
- [ ] Session 4: Collections (List/Set/Map), generics — manage collections of products/orders in memory
- [ ] Session 5: Exceptions, try/catch/finally, custom exceptions — robust error handling patterns
- [ ] Session 6: Streams & lambdas, functional interfaces — filtering/searching product catalogs
- [ ] Session 7: File I/O, JSON basics (Jackson), building a tiny console app version of the catalog
- [ ] Session 8: Build tools — Maven fundamentals (POM, dependencies, lifecycle), project structure conventions

## Phase 1 — Web fundamentals & Spring Boot (single service first)

- [ ] Session 9: HTTP fundamentals, REST principles, what a web server does
- [ ] Session 10: Spring & Spring Boot intro — Inversion of Control, Dependency Injection, `@SpringBootApplication`
- [ ] Session 11: Build `product-service` as a REST API — controllers, `@RestController`, request/response, DTOs
- [ ] Session 12: Persistence — Spring Data JPA, PostgreSQL, entities, repositories, running Postgres in Docker
- [ ] Session 13: Validation, exception handling (`@ControllerAdvice`), layered architecture (controller/service/repository)
- [ ] Session 14: Testing — JUnit 5, Mockito, unit tests for services, integration tests with Testcontainers

## Phase 2 — Going from one service to many

- [ ] Session 15: What actually makes something a "microservice" — boundaries, database-per-service, why/when to split
- [ ] Session 16: Build `user-service` and `order-service` following the same pattern independently
- [ ] Session 17: Service-to-service sync communication — RestTemplate/WebClient, OpenFeign
- [ ] Session 18: Service discovery — Eureka `discovery-server`, registering all services
- [ ] Session 19: API Gateway — Spring Cloud Gateway, routing, single entry point for clients
- [ ] Session 20: Centralized configuration — Spring Cloud Config Server, externalized per-environment config

## Phase 3 — Making it resilient and event-driven

- [ ] Session 21: Async messaging concepts, Kafka fundamentals, running Kafka in Docker
- [ ] Session 22: Event-driven order flow — order-service publishes events, inventory/notification-service consume
- [ ] Session 23: Resilience patterns — Resilience4j circuit breaker, retry, rate limiter, fallback methods
- [ ] Session 24: Idempotency, distributed transaction basics (Saga pattern) for order + payment + inventory

## Phase 4 — Security

- [ ] Session 25: Authentication vs authorization, JWT fundamentals
- [ ] Session 26: Spring Security — securing endpoints, issuing/validating JWTs across services
- [ ] Session 27: (Optional depth) OAuth2 / centralized auth server

## Phase 5 — Containerization & orchestration

- [ ] Session 28: Docker fundamentals — Dockerfile per service, multi-stage builds
- [ ] Session 29: docker-compose — running the entire platform locally with one command
- [ ] Session 30: Kubernetes fundamentals — pods, deployments, services, ConfigMaps/Secrets
- [ ] Session 31: Deploying ShopSphere to a local Kubernetes cluster (kind/minikube)

## Phase 6 — Observability & production polish

- [ ] Session 32: Centralized logging — structured logs, ELK stack (or Loki) basics
- [ ] Session 33: Distributed tracing — Zipkin/Jaeger with Micrometer Tracing, following a request across services
- [ ] Session 34: Metrics & dashboards — Prometheus + Grafana
- [ ] Session 35: CI/CD — GitHub Actions pipeline (build, test, containerize, deploy)

## Phase 7 — Resume packaging

- [ ] Session 36: Architecture diagrams, polished top-level README, demo script
- [ ] Session 37: Resume bullet points + interview talking points for this project (what to say, what questions to expect)

---

## How each session works

1. **Concept first** — I explain the "why" before the "how," in plain language, with analogies. No jargon without a definition.
2. **Apply immediately** — we write real code for ShopSphere, not throwaway toy examples (except brief syntax drills when needed).
3. **You do a small exercise** — a short task to prove the concept landed, before we move on.
4. **Session log** — I update this file's checkboxes and add a one-paragraph note below so any future session (even a brand new one) can pick up exactly where we left off.

## Session log

**Session 0 (2026-09-14):** Set up curriculum, created `ecommerce-microservices` git repo skeleton, saved this roadmap to the project. Next: Session 1 — Java fundamentals (JVM/JDK/JRE, variables, types, operators).

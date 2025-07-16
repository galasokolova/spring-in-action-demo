# Taco Cloud – Chapter 11: Introducing Reactor
##

The `chap_11_reactor_demo` module demonstrates core concepts from **Chapter 11: Reactive Spring** using **Reactor's Flux and Mono APIs**.

It includes two modes of execution:

---

## 🚀 Execution Modes

### 1. JavaFX GUI Mode
An interactive desktop interface for testing each reactive method separately.

```bash
mvn clean javafx:run
```
Each button triggers a single method execution with visual output in a `TextArea`.

### 2. Console Mode
Run all methods together with full log output. 
To use:
```bash
    mvn spring-boot:run
```
Or directly run the `main()` method in 
```
    Chap11ReactorDemoApplication.java
```

---
## ⚙️ Services and Methods
The application is divided into 4 service layers:

#### 🧪 CreationService
- `createAFluxFromArray()` — Emits elements from a static array.
- `createAFluxFromIterable()` — Emits elements from a list.
- `createAFluxRange()` — Emits numbers from a range.
- `createAFluxInterval()` — Emits elements over time intervals.

#### 🔗 CombinationService
- `mergeFluxes()` — Merges two fluxes with different delays.
- `zipFluxes()` — Combines two fluxes element by element.
- `zipFluxesToObject()` — Zips fluxes and maps to structured output.
- `firstWithSignalFlux()` — Emits from the first flux that responds.

#### 🔄 TransformationService
- `skipAFew()` — Skips a fixed number of elements.
- `skipAFewSeconds()` — Skips items emitted in the first few seconds.
- `take()` — Takes only the first N elements.
- `takeForAwhile()` — Takes elements for a limited time duration.
- `filter()` — Filters elements based on a condition.
- `distinct()` — Eliminates duplicate values.
- `map()` — Transforms each element.
- `flatMap()` — Flattens and maps nested elements.
- `buffer()` — Groups elements into batches.
- `bufferAndFlatMap()` — Buffers then flattens each group.
- `collectList()` — Collects all elements into a single List.
- `collectMap()` — Collects elements into a Map using a key function.

#### ✅ LogicService
- `all()` — Checks if all elements match a predicate.
- `any()` — Checks if any element matches a predicate.

#### 📦 Structure
- `service/` — Contains the logic for each method group.
- `fx/ReactiveMethodsFxApp.java` — JavaFX launcher and UI builder.
- `Chap11ReactorDemoApplication.java` — Console-based entry point.
- `util/Utils.java` — Contains sleep() utility for managing time-based streams.
-  Some methods use `Utils.sleep()` to allow interval-based emissions to complete

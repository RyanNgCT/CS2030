# Exercise 05
For each query we are at, we will need to know the bus services in the current bus stop that is available.

Then for each bus service, i.e. Bus `96`, we need to obtain the list of bus stop that the particular service serves and find all those with the given name. i.e. regex grep for `Clementi`.

## Asynchronous Levels
1. Can specify query-level to be asynchronous (as they are independent)


2. For each query can make the API call for a specific bus to be asynchronous
    - when doing `getBuses()`
    - we should do this first as it is the "deeper level"

## BusAPI
- does all the API calling
- we use `HttpClient`'s `sendAsync()` method, which will return a `CompletableFuture`
    - `sendAsync()` issues the `http` request and then returns immediately (since wrapping it in a `CompletableFuture`)


## `CompletableFuture<T>`
- is a promise can come back immediately



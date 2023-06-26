# micrometer-tracing-gh-296

This is a reproducer for [Micrometer tracing Github issue 296](https://github.com/micrometer-metrics/tracing/issues/296).

Run the application with `mvn spring-boot:run`.

To reproduce the issue, run below command:

```bash
curl -kv -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.NHVaYe26MbtOYhSKkoKYdFVomg4i8ZJd8_-RU8VNbftc4TSMb4bXP3l3YlNWACwyXPGffz5aXHc6lty1Y2t4SWRqGteragsVdZufDn5BlnJl9pdR_kdVFUsra2rWKEofkZeIC4yWytE58sMIihvo9H1ScmmVwBcQP6XETqYd0aSHp1gOa9RdUPDvoXQ5oqygTqVtxaDr6wUFKrKItgBMzWIdNZ6y7O9E0DhEPTbE9rfBo6KTFsHAZnMg4k68CDp2woYIaXbmYTWcvbzIuHO7_37GT79XdIwkm95QJ7hYC9RiwrV7mesbY4PAahERJawntho0my942XheVLmGwLMBkQ" http://localhost:8080
```

The application throws below error:

```
2023-06-26T18:54:17.785+02:00 " WARN [,,]" 56320 --- [ctor-http-nio-3] i.m.o.c.ObservationThreadLocalAccessor   : There is no current scope in thread local. This situation should not happen
2023-06-26T18:54:17.785+02:00 "ERROR [,,]" 56320 --- [ctor-http-nio-3] reactor.core.publisher.Operators         : Operator called default onErrorDropped

java.lang.NullPointerException: Cannot invoke "io.micrometer.observation.Observation$Scope.getPreviousObservationScope()" because "scope" is null
	at io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor.restore(ObservationThreadLocalAccessor.java:139) ~[micrometer-observation-1.11.1.jar:1.11.1]
	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
Error has been observed at the following site(s):
	*__checkpoint ⇢ ServerWebExchangeReactorContextWebFilter [DefaultWebFilterChain]
Original Stack Trace:
		at io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor.restore(ObservationThreadLocalAccessor.java:139) ~[micrometer-observation-1.11.1.jar:1.11.1]
		at io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor.restore(ObservationThreadLocalAccessor.java:32) ~[micrometer-observation-1.11.1.jar:1.11.1]
		at io.micrometer.context.DefaultContextSnapshot$DefaultScope.resetThreadLocalValue(DefaultContextSnapshot.java:144) ~[context-propagation-1.0.3.jar:1.0.3]
		at io.micrometer.context.DefaultContextSnapshot$DefaultScope.close(DefaultContextSnapshot.java:136) ~[context-propagation-1.0.3.jar:1.0.3]
		at reactor.core.publisher.MonoContextWriteRestoringThreadLocals.subscribe(MonoContextWriteRestoringThreadLocals.java:45) ~[reactor-core-3.5.7.jar:3.5.7]
		at reactor.core.publisher.MonoDeferContextual.subscribe(MonoDeferContextual.java:55) ~[reactor-core-3.5.7.jar:3.5.7]
		at reactor.core.publisher.InternalMonoOperator.subscribe(InternalMonoOperator.java:64) ~[reactor-core-3.5.7.jar:3.5.7]
		at reactor.core.publisher.MonoDefer.subscribe(MonoDefer.java:53) ~[reactor-core-3.5.7.jar:3.5.7]
		at reactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:165) ~[reactor-core-3.5.7.jar:3.5.7]
```

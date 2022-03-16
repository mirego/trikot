# Module kotlinx-coroutines-reactive

Utilities for [Reactive Streams](https://www.reactive-streams.org).

Coroutine builders:

| **Name**                              | **Result**  | **Scope**       | **Description**                                                |
| ------------------------------------- | ----------- | --------------- | -------------------------------------------------------------- |
| [kotlinx.coroutines.reactive.publish] | `Publisher` | [ProducerScope] | Cold reactive publisher that starts the coroutine on subscribe |

Integration with [Flow]:

| **Name**           | **Result**  | **Description**                                      |
| ------------------ | ----------- | ---------------------------------------------------- |
| [Publisher.asFlow] | `Flow`      | Converts the given publisher to a flow               |
| [Flow.asPublisher] | `Publisher` | Converts the given flow to a TCK-compliant publisher |

If these adapters are used along with `kotlinx-coroutines-reactor` in the classpath, then Reactor's `Context` is properly
propagated as coroutine context element (`ReactorContext`) and vice versa.

Suspending extension functions and suspending iteration:

| **Name**                                                                           | **Description**                                                             |
| ---------------------------------------------------------------------------------- | --------------------------------------------------------------------------- |
| [Publisher.awaitFirst][org.reactivestreams.publisher.awaitfirst]                   | Returns the first value from the given publisher                            |
| [Publisher.awaitFirstOrDefault][org.reactivestreams.publisher.awaitfirstordefault] | Returns the first value from the given publisher or default                 |
| [Publisher.awaitFirstOrElse][org.reactivestreams.publisher.awaitfirstorelse]       | Returns the first value from the given publisher or default from a function |
| [Publisher.awaitFirstOrNull][org.reactivestreams.publisher.awaitfirstornull]       | Returns the first value from the given publisher or null                    |
| [Publisher.awaitLast][org.reactivestreams.publisher.awaitfirst]                    | Returns the last value from the given publisher                             |
| [Publisher.awaitSingle][org.reactivestreams.publisher.awaitsingle]                 | Returns the single value from the given publisher                           |

<!--- MODULE kotlinx-coroutines-core -->
<!--- INDEX kotlinx.coroutines -->
<!--- INDEX kotlinx.coroutines.flow -->

[flow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html

<!--- INDEX kotlinx.coroutines.channels -->

[producerscope]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-producer-scope/index.html

<!--- MODULE kotlinx-coroutines-reactive -->
<!--- INDEX kotlinx.coroutines.reactive -->

[kotlinx.coroutines.reactive.publish]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/publish.html
[publisher.asflow]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/as-flow.html
[flow.aspublisher]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/as-publisher.html
[org.reactivestreams.publisher.awaitfirst]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/await-first.html
[org.reactivestreams.publisher.awaitfirstordefault]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/await-first-or-default.html
[org.reactivestreams.publisher.awaitfirstorelse]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/await-first-or-else.html
[org.reactivestreams.publisher.awaitfirstornull]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/await-first-or-null.html
[org.reactivestreams.publisher.awaitsingle]: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactive/kotlinx.coroutines.reactive/await-single.html

<!--- END -->

# Package kotlinx.coroutines.reactive

Utilities for [Reactive Streams](https://www.reactive-streams.org).

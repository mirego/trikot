# Trikot.datasources

Multiplaform cache layers abstraction.

- Interfaces unifying accessing cachable data
- Abstract implementation for implementers
- Simple memory cache without invalidation

# Concepts

- 1 datasource by data type to allow cache tuning
- Cascading approach `MasterDataSource` -> `FirstCacheLayer` -> `SecondCacheLayer` (Ex: `Http` -> `Memory` -> `Disk`)
- Request sent to datasource can specify if cache can be used, if not, cache will only be use if cache refresh fails
- Simple invalidation process
- Sharing of concurrent reads. When a read is pending, subsequent read calls will share the initial read execution.

# Modules

[trikot.datasources.core](./datasources-core) -- Core module for streams and flow
[trikot.datasources.streams](./datasources-streams) -- Trikot.Streams datasource implementation
[trikot.datasources.flow](./datasources-flow) -- Kotlin coroutine flow datasource implementation

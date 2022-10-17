# Trikot.datasources.streams

# How to Create a new DatasourceType

##### 1 - Create a new DatasourceRequestType extending `DataSourceRequest`

```kotlin
data class MyDataSourceRequest<T>(
    val query: MySpecialQueryType,
    override val cachableId: String,
    override val requestType: DataSourceRequest.Type = DataSourceRequest.Type.USE_CACHE
) : DataSourceRequest
```

##### 2 - Create a new DatasourceType extending `BaseDataSource` and override internalRead

```kotlin
class MyDataSource<T>(
    cacheDataSource: DataSource<MyDataSourceRequest<T>, T>? = null
) : BaseDataSource<MyDataSourceRequest<T>, T>(cacheDataSource) {
    override fun internalRead(request: MyDataSourceRequest<T>): ExecutablePublisher<T> {
        val myExecutablePublisher = new MyExecutablePublisher(request.query)
        return myExecutablePublisher
    }

    override fun save(request: MyDataSourceRequest<T>, data: T?) {
        // Save content to cache if needed
    }
}
```

##### 3 - Create an instance

In this example we will use memory cache

```
val myDataSource = MyDataSource<String>(MemoryCacheDataSource())
```

##### 4 - Send a request to the datasource

In this example we will use memory cache

```kotlin
val request = myDataSource.myMyDataSource.read(MyDataSourceRequest(query, "queryCacheId"))
request.subscribe(cancelableManager) {
    print(it)
}
```

# Invalidation

To invalidate data, simply send a get request using REFRESH_CACHE. No subscription is needed for the invalidation to happens

```kotlin
myDataSource.myMyDataSource.read(MyDataSourceRequest(query, "queryCacheId", DataSourceRequest.Type.REFRESH_CACHE))
```

# Complete Datasource invalidation

`InvalidatableDataSource` is a special wrapper around a `MasterDataSource` that invalidate all cached elements when the associated publishers publish `true`.

```kotlin
val invalidatingPublisher = Publishers.behaviorSubject()
val invalidatableDatasource = InvalidatableDataSource(myDataSource, invalidatingPublisher)
invalidatingPublisher.value = true // Will invalidate the whole myDataSource cache
```

## Installation

##### Import dependencies

```groovy
    dependencies {
        maven { url("https://s3.amazonaws.com/mirego-maven/public") }
    }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:datasources-streams:$trikot_version"
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                 implementation "com.mirego.trikot:datasources-streams:$trikot_version"
            }
        }
    }
```

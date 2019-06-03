# Trikot.streams

The purpose of Trikot.streams is to provide reactive foundations for Kotlin Multiplatform.

It allows to write immutable and concurrent code

**Preview**
```
class ProfileController() {
    private val userPublisher = userService.currentUser // Publisher<User>
    val userNameString = userPublisher.map { it.userName } // User.userName
    val userSignOnServiceDetail = userPublisher.switchMap {
        return if (it.service == "facebook") 
                facebookService.getFacebookUserDetailPublisher(it.userId)
            else
                defaultService.getUserDetailPublisher(it.userId)
    } // Publisher<Map<String, String>>
    
}
```


## [Publishers and Processors](./documentation/PUBLISHERS.md)
Foundation of trikot.streams is based on a immutable and concurrent implementation of [Reactive-Streams](https://www.reactive-streams.org/)

See the documentation [here](./documentation/PUBLISHERS.md)


## [Publishers and Processors](./documentation/PUBLISHERS.md)
Foundation of trikot.streams is based on a immutable and concurrent implementation of [Reactive-Streams](https://www.reactive-streams.org/)

See the documentation [here](./documentation/PUBLISHERS.md)










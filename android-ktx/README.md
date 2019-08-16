# Trikot.streams Android Kotlin extensions

- Easily use Trikot.streams publisher as Android's `LiveData`.

### Observe
```kotlin
fun <T> Publisher<T>.observe(lifecycleOwner: LifecycleOwner, observeBlock: ObserveBlock<T>)
```
Subscribe to a publisher and execute the block when a value is received. The `LifecycleOwner` handle the unsubscribe.

In the sample below, subscription will be cancelled automatically when `LifecycleOwner`'s `LifeCycle` is destroyed.

```kotlin
fun bind(textView: TextView, label: Publisher<String>, lifecycleOwner: LifecycleOwner) {
    label.observe(lifecycleOwner) { textView.text = it }
}
```


### Data Binding
```kotlin
fun <T> Publisher<T>.asLiveData(): LiveData<T>
```

By exposing `LiveData` in `android.arch.lifecycle.ViewModel` and use it in your xml, the data binding library will handle the subscription (as long you set the lifecycleOwner on your binding class).

In the sample below, the subscription to the `Publisher<List<User>>` will be handled by the Android data binding library

fragment_users.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="viewModel"
            type="com.android.AndroidUsersViewModel" />

    </data>

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:users="@{viewModel.users}"/>

</layout>

```


```kotlin

interface UsersRepository {
    val users: Publisher<List<User>>
}

class AndroidUsersViewModel(usersRepo: UsersRepository) : ViewModel() {
    val users: LiveData<List<User>> = usersRepo.users.asLiveData()
}

class UsersFragment : Fragment() {

    private lateinit var usersViewModel: AndroidUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel = ViewModelProviders.of(this).get(AndroidUsersViewModel::class.java)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val binding = DataBindingUtil.inflate<FragmentUsersBinding>(
                inflater,
                R.layout.fragment_users,
                container,
                false
            )

            with(binding) {
                lifecycleOwner = viewLifecycleOwner
                viewModel = homePageViewModel
                return root
            }
        }
}

@BindingAdapter("users")
fun RecyclerView.bind(data: List<Users>?) {
    //Bind users
}
```

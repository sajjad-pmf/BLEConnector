# BLE Connector

- Scanning for nearby BLE devices
- Connecting to BLE devices
- Discovering services and characteristics
- Reading and writing data on characteristics and descriptors
- Bonding with a BLE device
- Implementing your own BLE operations serial queuing mechanism

## Tech stack - Library:

- [Kotlin](https://kotlinlang.org/)
- [JetPack-Compose](https://developer.android.com/jetpack/compose)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) - Flow is used to pass (send) a stream of data that can be computed asynchronously
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for dependency injection.
- [Kotlin-DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Used to handle gradle dependencies and config versions
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - For reactive style programming (from VM to UI). 
  - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Used to get the lifecycle event of an activity or fragment and performs some action in response to change
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Used to navigate between fragments
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, and card view.



## Setup

1. Clone the project to your directory of choice.

```
git clone [https://github.com/sajjad-pmf/BLEConnector.git]
```

2. Launch Android Studio and select "Open an existing Android Studio project".
3. Navigate to the directory where you cloned the project to, and double click on it.
4. Wait for Gradle sync to complete.

## Requirements

This project targets Android 10 and has a min SDK requirement of 21 (Android 5.0), in line with our recommendation in [4 Tips to Make Android BLE Actually Work](https://punchthrough.com/android-ble-development-tips/).

## Contributing

### Reporting bugs

Please [open an issue](https://github.com/sajjad-pmf/BLEConnector/issues/new) to report a bug if the app isn't behaving as expected.

### Opening a Pull Request

Please fork the repository and create a feature branch before opening a Pull Request against the `master` branch.



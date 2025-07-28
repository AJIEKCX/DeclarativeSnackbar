# Declarative Snackbar

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-EAEAEA.svg?style=flat)
![badge-jvm](https://img.shields.io/badge/platform-jvm-DB413D.svg?style=flat)

A declarative API for Snackbars in Compose Multiplatform (Android, iOS, Desktop) that makes it easy to display and manage snackbar messages in your application.

## Features
- **Declarative API**: Unlike the official Scaffold-based approach, this library provides a fully declarative API that fits better with Compose's paradigm.
- **Global and local snackbars**: Easily create both application-wide and screen-specific snackbars without overlapping critical UI elements.
- **Type-safe messages**: Support for custom message types beyond just strings.
- **Flexible positioning**: Position snackbars at the top or bottom in your layout.
- **Custom UI**: No dependency on the Material snackbar, you can provide any custom UI you want.  
- **Custom animations**: Customize the enter and exit animations for your snackbars.

Global and local | Custom content |
| ---- | ---- |
| <image src="https://github.com/user-attachments/assets/840c5bff-9a18-4447-aa6c-bd22e8e6e943"/>| <image src="https://github.com/user-attachments/assets/29bf8aae-bc2d-479e-bbaa-5dad116c84a8"/> |

## Installation


Add the dependency to your app's build.gradle.kts file:

```kotlin
dependencies {
    implementation("io.github.ajiekcx:declarative-snackbar-compose:0.2.0") 
    implementation("io.github.ajiekcx:declarative-snackbar-core:0.2.0") // Optional, for modules without compose dependency
}
```

## Usage

### Global Snackbar

1. Create a global snackbar component:

⚠️ For demonstration purposes only, use DI in real applications!

```kotlin
object GlobalSnackbarComponent: SnackbarComponent<String> by SnackbarComponent()
```

2. Wrap your app content with SnackbarBox:

```kotlin
SnackbarBox(
    component = GlobalSnackbarComponent,
    snackbarContent = { message ->
        Snackbar { Text(message) }
    },
) {
    // Your app content here
}
```

3. Show a snackbar message from anywhere in your app (in the presentation layer — ViewModel, etc.):

```kotlin
GlobalSnackbarComponent.show(
    SnackbarMessage(
        content = "This is a global message",
        duration = SnackbarDuration.Short
    )
)
```

4. To prevent overlapping important UI elements, you can apply built-in modifiers:

```kotlin
SnackbarBox() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Title") },
                modifier = Modifier.noOverlapTopContentBySnackbar()
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.noOverlapBottomContentBySnackbar()
            )
        }
    )
}
   
```

Use the `noOverlapTopContentBySnackbar` modifier to prevent the snackbar (with top alignment) from 
overlapping top UI content, and `noOverlapBottomContentBySnackbar` for bottom-aligned snackbars.


### Local Snackbar

1. Optionally create a custom message types for type-safe snackbars:

```kotlin
sealed interface MySnackbarMessage {
    val message: String

    class Text(override val message: String) : MySnackbarMessage

    class TextWithAction(
        override val message: String,
        val actionTitle: String,
        val onActionClick: () -> Unit
    ) : MySnackbarMessage
}
```

2. Create a local snackbar component in your ViewModel:

```kotlin
class MyViewModel : ViewModel() {
    val snackbarComponent: SnackbarComponent<MySnackbarMessage> = SnackbarComponent()

    fun showMessage() {
        snackbarComponent.show(
            SnackbarMessage(
                content = MySnackbarMessage.Text("This is a local message"),
                duration = SnackbarDuration.Short
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        snackbarComponent.onDestroy()
    }
}
```

3. Use SnackbarBox in your screen:

```kotlin
SnackbarBox(
    component = viewModel.snackbarComponent,
    alignment = Alignment.TopCenter,
    snackbarContent = { message ->
        Snackbar(
            modifier = Modifier.padding(horizontal = 16.dp),
            action = {
                if (message is MySnackbarMessage.TextWithAction) {
                    TextButton(onClick = { message.onActionClick() }) {
                        Text(message.actionTitle)
                    }
                }
            }
        ) {
            Text(message.message)
        }
    },
) {
    // Your screen content here
}
```

See the sample app for more examples.

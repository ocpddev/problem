# problem

This module is designed to handle errors from Spring Security and other common errors. It provides unified error
handling, similar to Zalando Problem.

## Usage

⚠️ When importing `AdviceTrait`, be mindful. It has both servlet and reactive versions, so make sure you import the
correct one. ⚠️

For general errors, use `GeneralAdviceTrait`.

```kotlin
@RestControllerAdvice
class WebExceptionHandler : ResponseEntityExceptionHandler(), GeneralAdviceTrait {
}
```

For Spring Security errors, use `SecurityAdviceTrait`.

```kotlin
@RestControllerAdvice
class WebExceptionHandler : ResponseEntityExceptionHandler(), SecurityAdviceTrait {
}
```

Also, they can both work together.

```kotlin
@RestControllerAdvice
class WebExceptionHandler : ResponseEntityExceptionHandler(), GeneralAdviceTrait, SecurityAdviceTrait {
}
```

## License

problem is licensed under [Apache License, Version 2.0](LICENSE).

<div align="center">
  <img src="docs/clinia-logo.svg" width="250">
  <h1>Vision Android</h1>
  <h4>Android library that lets you create a health-care search experience using Clinia's search API.</h4>
  <p>
    <a href="#why">Why</a> •
    <a href="#features">Features</a> •
    <a href="#getting-started">Getting Started</a> •
    <a href="#examples">Examples</a> •
    <a href="#contributing">Contributing</a> •
    <a href="#-license">License</a>
  </p>
</div>

# Why

Vision Android is the result of Clinia's effort to make its expertise more accessible to its partners. The Vision tools allow partners to create their own health-care search experience, for internal or external use, using Clinia's search API. It is built on top of Clinia's [Kotlin API Client][kotlin-client] to provide you a high-level solution to build health care search
interfaces.

# Features


# Getting Started

You can add Vision to your Android application by adding the following line to your `build.gradle`'s dependencies.

```groovy
implementation "ca.clinia:vision-android:1.0.0"
```

### Proguard rules

When proguard `minifyEnabled` option is set to `true` , you might get this error:

```
Can't locate argument-less serializer for class e.a.b.g.n.c (Kotlin reflection is not available). For generic classes, such as lists, please provide serializer explicitly.
```

Add this proguard rule to solve it.

```
-keep class ca.clinia.search.model.** { *; }
```

# Examples

# Contributing

We welcome all contributors, from casual to regular. You are only one command away to start the developer environment, [read our CONTRIBUTING guide](.github/CONTRIBUTING.md).

# License

Vision Android is an open-sourced software licensed under the [MIT license](LICENSE)

<!-- links -->

[kotlin-client]: https://github.com/clinia/cliniasearch-client-kotlin
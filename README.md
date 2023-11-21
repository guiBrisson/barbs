# Barbs

Barbs is a desktop application developed in Kotlin and powered by Jetpack Compose. The primary objective of 
this project is to showcase my skills and proficiency developing an application using the OpenAI assistants.
[Test](test.md)<br> 
**The assistant feature is currently in BETA during the development of this project.**

## Getting Started

1. To run this project, you need to create a `local.properties` file at the root of the project and add the 
following variable:

    ```properties
    openai.api.key=your-api-key-here
    ```
    Replace `your-api-key-here` with your actual OpenAI API key.

2. The app fetch all your assistants and use the first on the list to use as the main assistant in the app. 
    So, in order for the app to work properly, you need to have at least one assistant set in your OpenAI account.  

## Features

- **Jetpack Compose UI**: Utilizes the modern UI toolkit for building native Android applications.
- **OpenAI Integration**: Demonstrates integration with the OpenAI API to showcase assistants.

[Here a some future improvements for the app](improvements.md)

## Dependencies

- **[Ktor](https://ktor.io)**: Network request made with Ktor with the dependencies such as: Core, CIO, Logging, 
    Content Negotiation and Serialization,
- **[Koin](https://insert-koin.io)**: A dependency injection dependency that works well with the navigator used on 
    this project.
- **[Voyager](https://voyager.adriel.cafe)**: A multiplatform navigation library built for, and seamlessly integrated 
    with, Jetpack Compose. 

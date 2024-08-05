## Project Overview
This project is an Android application that displays a list of Pokemon and their details. It uses various libraries and follows the MVVM architecture to ensure a clean and maintainable codebase.

## Libraries Used
- Retrofit
- Hilt
- Chucker
- Coil
- Paging
- Compose

## Project Structure
The project follows the MVVM (Model-View-ViewModel) architecture and is organized into the following directories:
- `api`: Contains the API service interfaces and response models.
- `di`: Contains the dependency injection modules.
- `models`: Contains the data models.
- `repositories`: Contains the repository classes that handle data operations.
- `sources`: Contains the data sources, such as paging sources.
- `ui`: Contains the UI-related classes, including screens and view models.
- `components`: Contains reusable UI components.

## Dependency Injection
Dependency injection is managed using Hilt. The `AppModule.kt` file in the `di` directory provides the necessary dependencies, such as Retrofit and ApiService.

## Network Calls
Retrofit is used for making network calls. The `ApiService` interface defines the endpoints for fetching Pokemon data.

## Paging
The Paging library is used for handling paginated data. The `PokemonRepository.kt` file contains the implementation of the repository that fetches paginated Pokemon data using the `PokemonPagingSource`.

## UI
Jetpack Compose is used for building the UI.

## [APK](https://github.com/mevivek/pw_android_sde1_assignment/blob/master/apk/app-release.apk)

## [Screen Recording](https://github.com/mevivek/pw_android_sde1_assignment/blob/master/screenshots/3.mp4)

## Screenshots
- ![Screenshot 1](https://github.com/mevivek/pw_android_sde1_assignment/blob/master/screenshots/1.png)
- ![Screenshot 2](https://github.com/mevivek/pw_android_sde1_assignment/blob/master/screenshots/1.png)


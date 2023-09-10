# iMusic - Simple Android Music Player App

This is a simple Android music player app that uses the iTunes API to search songs and play a preview of it.

## App Features

- Search functionality, to search song by artist / song / album name
- The songs result can be played / previewed (30s)
- Support dynamic theme on Android 12+ & Light / Dark Mode

## Tech Stack

- Android Studio Giraffe
- JDK 17
- Kotlin 1.9
- Min SDK 24
- Compiles & Target SDK 33
- Jetpack Compose (BOM 2023.06.01)
- Dagger Hilt for dependency injection framework with ksp
- Gradle Version Catalog for dependencies management
- Retrofit, Moshi & OkHttp for networking
- Jetpack Media3 for media/audio player
- Jetpack Lifecycle
- Mockk for mocking / stubbing purpose on unit test
- Sandwich to wrap / model retrofit response for better mapping / exception handling
- Material3 for overal ui
- Landscapist coil for fetching network image

## Project Structure

This a single module project (only `app` module) as the feature should be fairly simple, but the package structuring is designed to be easily migrated into multi-module project if the need arise.

The top level package separation are `core`, `features`, `ui`, which is fairly common multi-module structure. 

The feature have the commonly used package separation that follows clean arch approach: `data`, `domain` & `presentation`. Currently there are 2 features, `search` & `splash`.

The `data` layer model (api response) are differentiated from `domain` layer model, for separation of concern. 

I use combination of Repository Pattern and MVVM with State & Event (a bit of Unidirectional Data Flow) due to the nature of declarative ui code with compose.

## Unit Test

There are only 2 unit test as of now:
- Search feature repository (itunes service), it test mapping functionality from api response into domain model
- Search feature use case (itunes service), honestly there are no particular logic in it, so it only delegating repository

## CI/CD

- The project is hosted on github as private project
- I use github actions + fastlane for CI & CD pipeline
- The pipeline is very rudimentary as it's my very first time implementing it from scratch with github actions & fastlane
  - The steps that has been implemented are: Unit Test, Build App & Upload Artifact to github project repo
- Until the last moment, the pipeline to upload AAB to playstore internal app sharing is still not successfully implemented due to the app that is not yet published (`Google Api Error: Invalid request - UploadException: NOT_PUBLISHED [[]] (dev.hyuwah.imusic)`)

## Publishing

- The app is published to Google Play Console / Store but still on internal test track which require user to be invited / registered as tester to download the app.
- I could promote it to beta track but it certainly require some time for the app to be reviewed and fully available in play store

## Notes

Personally, this is a very fun & quite challenging for me because I rarely touch project that involves media player, and also for implementing CI/CD pipeline from scratch using freely available tools.

There are some small effort but nice improvement that could've been done if given more time, such as:
- Local list of history / recent query search when search bar is activated
- Local list of recently played track, might use Room DB
- Ability to search podcast & audiobook, as it's already supported by the api
Other than that, I'm still curious to make the CI/CD part work as expected.

Hopefully this project can be considered okay enough for me to continue the hiring process. 

I thank you for your time to review this submission.

## Author

Muhamad Wahyudin
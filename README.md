# iMusic - Simple Android Music Player App

This is a simple Android music player app that uses the **iTunes API** to search songs and play a preview of it.

## App Features

- Search functionality, to search song by artist / song / album name
- The songs result can be played / previewed (30s)
- Support dynamic theme on Android 12+ & Light / Dark Mode

## Preview

**Demo**

https://github.com/hyuwah/imusic/assets/5181388/57c7edb3-a4da-4737-b027-707f0ca6a9b0

**Theme Switch**

https://github.com/hyuwah/imusic/assets/5181388/7b65eb38-3d70-44f8-9e5e-e0b26805bec8

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

![Project Architecture](https://res.cloudinary.com/hyuwah-github-io/image/upload/v1695765855/iMusic/imusic-modules.png)

This is a single module project (only `app` module) as the feature should be fairly simple, but the package structuring is designed to be easily migrated into multi-module project if the need arise.

The top level package separation are `core`, `features`, `ui`, which is fairly common multi-module structure.

![Feature layering](https://res.cloudinary.com/hyuwah-github-io/image/upload/v1695765855/iMusic/imusic-feature-layering.png)

The feature have the commonly used package separation that follows clean arch approach: `data`, `domain` & `presentation`. Currently there are 2 features, `search` & `splash`.

The `data` layer model (api response) are differentiated from `domain` layer model, for separation of concern. 

This project use combination of Repository Pattern and MVVM with State & Event (a bit of Unidirectional Data Flow) due to the nature of declarative ui code with compose.

## Unit Test

There are only 2 unit test as of now:
- Search feature repository (itunes service), it test mapping functionality from api response into domain model
- Search feature use case (itunes service), honestly there are no particular logic in it, so it only delegates to repository

## Release Build

`release_keystore.jks` & `keystore.properties` are needed to make release build

- `release_keystore.jks`
  - on local system, the file needs to be placed under `/config` folder & excluded from git
  - on CI, the file will be generated by the workflows from env secret on github repo
- `keystore.properties` will contains password for the release keystore (refer to `:app` module `build.gradle`)
  - on local system, the file needs to be placed under `/` (project root) folder & excluded from git
  - on CI, the file will be generated by the workflows from env secret on github repo

## CI/CD

- This project use github actions + fastlane for CI & CD pipeline
- There are 2 pipelines / workflows:
  - **build pull request**
    - only triggers on PR to master branch
    - has 3 parallel jobs
      - `unit test (debug) + upload report`
      - `lint (debug) + upload report`
      - `build apk (debug) + upload artifact`
  - **deploy** (to play store internal test track)
    - triggered manually
    - only 1 job, `deploy to internal` via fastlane

## Publishing

- The app is published to Google Play Store in Open Beta Track which can be discovered & downloaded publicly
- The continuous deployment part are still on **internal test track** only, which require user to be **invited / registered as tester to download the app.**

### Fastlane

Fastlane needs **GCP service account** json file. **[[Refer to here](https://docs.fastlane.tools/getting-started/android/setup/)]**

- on local system, the file needs to be placed under `/config` folder & excluded from git
- on CI, the file will be generated by the workflows from env secret on github repo

## Author

Muhamad Wahyudin

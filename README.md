# BoxCast SDK for Android

**This repository is no longer being maintained or supported. Please use at your own risk.**

The official BoxCast SDK for integrating with the BoxCast API on Android.

## Features

- List Live and Archived Broadcasts
- Detail A Broadcast
- Watch Broadcasts
- Documentation

## Requirements

- Android 11(SDK 30)+
- Android Studio 3.2+

## Installation

Add the library as a dependency in the App `build.gradle(:app)` file:

```groovy
dependencies {
    implementation 'com.boxcast.android:boxcast-sdk:0.1.0'
}
```

## Gradle 7.5 Configuration Additions
**Updated 20221013

If the build fails with a `NoClassDefFoundError`
Add in the Project `build.gradle(projectName)` file:

```groovy
allprojects {
    repositories {
        maven {
            url 'com.boxcast.android:boxcast-sdk:0.1.0'
        }
    }
}
```

And comment out of Project `settings.gradle` (Gradle 6.8+) :

```groovy
// dependencyResolutionManagement {
//   repositoriesMade.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//   repositories {...} } 
```
[see Gradle Dependency Management](https://docs.gradle.org/6.8/release-notes.html#dependency-management-improvements)

Add `enableJetifier` flag in Project `gradle.properties`: 

```groovy
android.enableJetifier=true
```

## Usage

### Accessing Resources

#### Get Live Broadcasts

```java
mClient = BoxCastClient.getInstance();
mClient.getLiveBroadcasts("YOUR_CHANNEL_ID", new BoxCastCallback<BroadcastList>() {
    
    @Override
    public void onSuccess(BroadcastList result) {
    	// Do something special with the live broadcasts.
    }

    @Override
    public void onFailure(Exception exception) {
        // Handle exception.
    }

});
```

#### Get Archived Broadcasts

```java
mClient = BoxCastClient.getInstance();
mClient.getArchivedBroadcasts("YOUR_CHANNEL_ID", new BoxCastCallback<BroadcastList>() {
    
    @Override
    public void onSuccess(BroadcastList result) {
    	// Do something special with the archived broadcasts.
    }

    @Override
    public void onFailure(Exception exception) {
        // Handle exception.
    }

});
```

#### Get Detailed Broadcast

```java
mClient = BoxCastClient.getInstance();
mClient.getBroadcast("CHANNEL_ID", "BROADCAST_ID", new BoxCastCallback<Broadcast>() {

    @Override
    public void onSuccess(Broadcast result) {
        // Do something special with the detailed broadcast.
    }

    @Override
    public void onFailure(Exception exception) {
        // Handle exception.
    }

});
```

#### Get Broadcast View

```java
mClient = BoxCastClient.getInstance();
mClient.getBroadcastView("BROADCAST_ID", new BoxCastCallback<Broadcast>() {

    @Override
    public void onSuccess(Broadcast result) {
        // Do something special with the broadcast view.
    }

    @Override
    public void onFailure(Exception exception) {
        // Handle exception.
    }

});
```

### Playback

After getting a detailed broadcast and broadcast view you can use the two resources to start playback with a `BoxCastVideoView`. This object is a simple sublcass of `VideoView` and can be used in a similar fashion.

```java
mVideoView = (BoxCastVideoView) findViewById(R.id.videoView);
mVideoView.setBroadcast(mBroadcast);
mVideoView.setBroadcastView(mBroadcastView);
mVideoView.start();
```

## Demo

There is a demo app included with this project to help you get a feel for how this SDK can be used. Download the SDK and run the `demo` configuration.

## Documentation

Documentation can be found [here](https://boxcast.github.io/boxcast-sdk-android/).

## License

BoxCast SDK is released under the MIT license. [See LICENSE](https://github.com/boxcast/boxcast-sdk-android/blob/master/LICENSE) for details.

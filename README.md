# Demo

<video id="video" controls="" preload="none">
    <source id="mp4" src="https://github.com/qslibrary/EasyTransition/blob/main/show/video_imageview.mp4" type="video/mp4">
</video>

<video id="video" controls="" preload="none">
    <source id="mp4" src="https://github.com/qslibrary/EasyTransition/blob/main/show/video_textview.mp4" type="video/mp4">
</video>

# Download

Base Gradle

```css
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

App Gradle

```css
dependencies {
	implementation 'com.github.qslibrary:EasyTransition:1.0.3'
}
```

# Usage

### Step 1: initialize in application

```kotlin
ET.init(this)
```

### Step 2: use before startActivity

```kotlin
//ivTest is source view
//R.id.ivTest is the shared view id in next activity
ET.from(ivTest).to(R.id.ivTest)

startActivity(Intent(this, TestActivity::class.java))
```

# Advance

### lazy mode( if you have some data lazily to set in shared view )

```kotlin
ET.from(ivTest).toLazy(TestActivity::class.java)
```

```kotlin
//use it after you have set data
ET.releaseLazy(this,ivTest)
```

### disable back transition

```kotlin
ET.from(tvTest).disableBack().to(R.id.ivTest)
```
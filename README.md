# Demo

###### ImageView

<img src="https://github.com/qslibrary/EasyTransition/blob/main/show/imageview.gif"/>

###### TextView

<img src="https://github.com/qslibrary/EasyTransition/blob/main/show/textview.gif"/>

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

Gradle

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

# License

```
Copyright (C) 2021 qslibrary

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
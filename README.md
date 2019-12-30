# Currency Converter
Currency converter by using revolut api

- Fetches update currencies from revolut api every 1 second.
- Calculates other currencies by using users currency and amount selection

![sample_image](https://github.com/emreakcan/currency-converter-android/blob/master/sample.png)


# Architecture


*Modules* 
- App
    Contains App code & Tests
- Common 
    Contains common variables such as constants
- Repo
    Contains local and remote repo, models and local databases here.

![diagram](https://github.com/emreakcan/currency-converter-android/blob/master/diagram.png)


# Libraries -  App & Testing 

  ```
    implementation project(':repo')
    implementation project(':common')

    // Dagger
    kapt "com.google.dagger:dagger-compiler:${libraries.dagger2Version}"
    kapt "com.google.dagger:dagger-android-processor:${libraries.dagger2Version}"

    implementation 'androidx.lifecycle:lifecycle-extensions:2.1.0'

    //Logger
    implementation "com.orhanobut:logger:${libraries.logger}"
    api "com.squareup.okhttp3:logging-interceptor:${libraries.okhttp}"

    testImplementation "org.junit.jupiter:junit-jupiter-api:${libraries.jUnit}"
    testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:${libraries.jUnit}"
    androidTestImplementation "de.mannodermaus.junit5:android-instrumentation-test:${libraries.jUnitInstrumentation}"
    androidTestRuntimeOnly "de.mannodermaus.junit5:android-instrumentation-test-runner:${libraries.jUnitInstrumentation}"

    //  mockk
    testImplementation("io.mockk:mockk:${libraries.mockkVersion}")
    testImplementation 'androidx.arch.core:core-testing:2.1.0'
    testImplementation "com.google.truth:truth:${libraries.truthVersion}"

    implementation 'androidx.recyclerview:recyclerview:1.1.0'

    implementation 'com.squareup.picasso:picasso:2.71828'
    
```


# Thanks
- For icons https://www.flaticon.com/authors/flags/rounded
- Architecture https://mindorks.com/android/store/Architecture/patrickyin/clean-architecture-android-kotlin

# Possible improvements
Store last updated currencies and display last known currencies to user by informing last update date, so when there is no internet use can see last updated currencies.

Create UI tests by Appium - Selendroid

Improve font according to branding and set company logo.

Add splash screen.

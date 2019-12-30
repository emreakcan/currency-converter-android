# Currency Converter
Currency coverter by using revolut api

- Fetches update currencies from revolut api every 1 second.
- Calculates other currencies by using users currency and amount selection

![sample_image](https://github.com/emreakcan/currency-converter-android/blob/master/sample.png)


# Architecture


*Modules* 
1. App
    1. Contains App code & Tests
2. Common 
    1. Contains common variables such as constans
3. Repo
    1. Contains local and remote repo, models and local databases here.

![diagram](https://github.com/emreakcan/currency-converter-android/blob/master/diagram.png)




# Thanks
- For icons https://www.flaticon.com/authors/flags/rounded
- Architecture https://mindorks.com/android/store/Architecture/patrickyin/clean-architecture-android-kotlin

# Possible improvements
Store last updated currencies and display last known currencies to user by informing last update date, so when there is no internet use can see last updated currencies.

Create UI tests by Appium - Selendroid

Improve font according to branding and set company logo.

Add splash screen.

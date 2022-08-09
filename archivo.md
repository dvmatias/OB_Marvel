# OpenBank Test Android Application

This application represents a test which will be evaluated for OpenBank responsible team.

## Description
It is required the development of a structure for an application that shows a Marvel characters list
and it allows to see each character details in a separated way.

### Specs
* Make use of Marvel's API (https://developer.marvel.com/docs)
* Get characters list -> v1/public/characters
* Get characters details -> v1/public/characters/{characterId}

### Functionalities
* To show characters list.
* To show any selected character details.

## Modularization
Each module inside the app represents an isolated block of logic/assets/functionalities.
This app will contains two kinds of modules:
* General purpose modules: Modules in which the content is dedicated to general purposes such as:
  provide dependency injection capabilities, assets/resources providers, styling/theming, base classes, 
  ui model classes, server response model classes, etc.
* Feature modules: Feature modules inside the project are named as ph_*module_name*. The purpose of
  this modules is to wrap up the specific logic for a particular feature such as: ui elements 
  (Fragments, Activities, ViewModels, etc.), adapters, listeners, etc. This modules contains the 
  classes which are part of a specific flow inside the app.

### Modules
* _app_: Application module. In here should lie the logic that should be alive the entire app lifecycle.
* _common_: Common logic and assets/resources are here. This module will hold custom common view 
  components, global assets, global constants, extension classes, etc. Common module do not depend
  on any other module.
* _core_: Essential core/base logic is in here. This module will hold base classes, navigation pattern
  classes and everything that is central in the app. Core module depends on data and domain modules
  as well as common implementations.
* _data_: Data/Business layer logic resides here. This module will hols classes to handle data such as
  repositories, DAOs, models, etc. Data module only depends on domain module and common/core 
  implementations.
* _domain_: Domain layer logic resides here. Domain module only depends on common and core 
  implementations.
* _ph_character_details_: Feature module. This module contains th classes for the 'Character Details'
  screen/flow to display: 
  * Character image.
  * Character description.
  * Character comics.
  * Character series.
  * Character stories (not implemented yet).
* _ph_home_: Feature module. This module contains the classes for the home screen to display:
  * Home screen bottom navigation bar.
  * Character list fragment to load all the Marvel's available characters.
  * Favorite character list to display user's favorite characters.

## Architecture - Clean
The project is divided into layers. Each layer contains the classes and the logic which is tied to a 
specific purpose inside the app.

### Layers
#### Domain
This layer contains the application business rules and it is the most isolated layer. This
layer don't know about any other layer but the common stuffs. It contains the models and use cases
for data requests and definitions for data providers.

It is represented by _domain_ module.

#### Data
This layer contains the business logic and includes the _domain_ layer. It contains the
definitions of the data sources and offers logic to data treatment and data transformation.

It is represented by _data_ module.

#### Presentation (App)
This layer contains all the logic that interacts with the UI and finally the
user. In this layer resides activities, fragments, view models, view adapter, item decorators,
etc. This layer includes both _data_ and _domain_ layers.

It is represented by _app_ module and every feature module (such as _ph_home_ and 
_ph_character_details_).

## Dependency Injection - Koin
This application uses Koin library to provide dependency injection capabilities (even though Koin is 
a service locator framework).

Dependencies are declared in separated files according to the nature and purpose of each one of them.
For this sake, the app holds a set of files inside _app_ module:
  * [AdapterModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/AdapterModule.kt): To declare and provide view adapters dependencies.
  * [ErrorHandlerModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/ErrorHandlerModule.kt): To declare and provide _ApiErrorHandler_ implementations dependencies.
  * [NavigationModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/NavigationModule.kt): To declare and provide navigation classes dependencies (custom implementation
    of navigation).
  * [NetworkModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/NetworkModule.kt): To declare and provide networking related classes such as retrofit and network 
    handlers implementations.
  * [RepositoryModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/RepositoryModule.kt): To declare and provide repositories implementations.
  * [RoomModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/RoomModule.kt): To declare and provide Room database classes implementations.
  * [ServiceModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/ServiceModule.kt): To declare and provide Retrofit API interfaces implementations.
  * [UseCaseModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/UseCaseModule.kt): To declare and provide use cases implementations.
  * [ViewModelModule](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/koin/ViewModelModule.kt): To declare and provide Android ViewModel implementations.

## Navigation
### Navigation Component (Native)
Each feature module implements Android's Navigation Component. That means that every module can and 
will hold a single Activity and each screen is and will be represented with a Fragment. Intra module
navigation is achieved by implementing Navigation Component.

### Navigation Pattern (Custom)
On the other hand, navigation between activities (inter modules navigation) is achieved through a 
custom Navigation Pattern. This patter is declared in _core_ module and implemented in _app_ module.
An Activity in module X that need to start another Activity in module Y doesn't need to know nothing
about module Y (feature module do not depend on each other).
  * [Navigator](https://github.com/dvmatias/OB_Marvel/blob/master/core/src/main/java/com/cmdv/core/navigator/Navigator.kt) interface declares possible destinations inside the app. This destinations represents 
    flows entry points (Activities).
    ```
    fun toCharacterDetails(origin: Activity, bundle: Bundle, finishPrevious: Boolean)
    ```
  * [Navigator Implementation](https://github.com/dvmatias/OB_Marvel/blob/master/app/src/main/java/com/cmdv/obmarvel/navigator/NavigatorImpl.kt) implements previous interface. This implementations make use of 
    [Activity's extension function](https://github.com/dvmatias/OB_Marvel/blob/master/common/src/main/java/com/cmdv/common/extensions/ActivityNavigationExtensions.kt) to manage the launch of a new Activity.
    ```
    override fun toCharacterDetails(origin: Activity, bundle: Bundle, finishPrevious: Boolean) {
        origin.navigateTo<CharacterDetailsActivity>(bundle, finishPrevious)
    }
    ```
  * Each activity has a Navigator implementation instance which allows inter module navigation. This
    dependency is injected/located in BaseActivity class.

## Pattern: MVVM + LiveData + Use Case + Repository
Activities and Fragments are tied to Android ViewModels. View models talks with UseCases to ask for
action on data. UseCases depends on Repositories to send/fetch data. Repositories make use of 
Services and DAOs to send/fetch data from/to API/DB.

```                             
Activity/Fragment/ViewModel  _to/from_  Use Case _to/from_  Repository  _to/from_  API/DB               
```                             

## API Calls
_TODO_

## Testing
Implemented Unit Testing (not every class is covered yet, WIP).
Implemented Functional Testing (not every class is covered yet, WIP).
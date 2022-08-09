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
  * Navigator interface declares possible destinations inside the app. This destinations represents 
    flows entry points (Activities).
    ```
    fun toCharacterDetails(origin: Activity, bundle: Bundle, finishPrevious: Boolean)
    ```
  * Navigator Implementation implements previous interface. This implementations make use of 
    Activity's extension function to manage the launch of a new Activity.
    ```
    override fun toCharacterDetails(origin: Activity, bundle: Bundle, finishPrevious: Boolean) {
        origin.navigateTo<CharacterDetailsActivity>(bundle, finishPrevious)
    }
    ```
  * Each activity has a Navigator implementation instance which allows inter module navigation. This
    dependency is injected/located in BaseActivity class.
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
* Feature modules: Modules which purpose is to wrap up the specific logic for a particular feature
such as: ui elements (Fragments, Activities, ViewModels, etc.), adapters, listeners, etc.

### Modules
* app: Application module. In here should lie the logic that should be alive the entire app lifecycle.
* common: Common logic and assets/resources are here. This module will hold custom common view 
  components, global assets, global constants, extension classes, etc.
* core: Essential core/base logic is in here. This module will hold base classes, navigation pattern
  classes and everything that is central in the app.
* data: Data/Business layer logic resides here. This module will hols classes to handle data such as
  repositories, daos, models, etc.
* domain: Domain layer logic resides here.
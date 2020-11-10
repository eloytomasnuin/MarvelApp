# MarvelApp

 Application about the Marvel Heroes, Using a Clean Arquitecture with MVVM Pattern.

## Modules

- **APP** (The main module)
- **DOMAIN** (Domain classes)
- **DATA** (Repositories local and remote)
- **USE CASES** (Use cases of application)

## Features

- Clean Architecture
- Dagger2 as dependency injection
- Room as local Data base
- Moshi as Json parser
- Navigation 
- Coroutines

## Possible Improvements

- Implement pagination
- Load more details in Hero detail page
- Main screen can be a Grid instead of a List
- Rotation and tablets implementation
- Dark Mode.

## Functionality

The application starts with a 20 heroes list bring them from the Marvel Api, shows their photos, and name. If you want to know about one of them, click in one an shows more detail: descriptions, comics and histories where the appear.

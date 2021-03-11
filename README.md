# DoorDashLite

An application that uses the DoorDash API to show a list of restaurants. Clicking on a restaurant takes you to a more detailed screen about the restaurant.
This application was design using Android Architecture Components and it uses the default MVVM architecture.

## Libraries
This application uses the most modern Android libraries for clean architecture including: Glide, Room, Hilt, livedata, viewmodel. navigation, pagination, coroutines, retrofit and more.

## Tests
Using the MVVM architecture and the separation of concerns, the following tests were created:
* RestaurantListViewModelTest.kt: Unit test that mocks the repository layer to only mock the one public api of the viewmodel
* RestaurantTest.kt: Unit test for a model class
* RestaurantDetailDaoTest.kt: Instrumentation test for verifying the integrity of the database
* MainActivityTest.kt: Espresso test for verifying the navigation works

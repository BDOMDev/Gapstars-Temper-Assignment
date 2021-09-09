Assumptions

- App display jobs that are available for the current day only.

Libraries Used

- Retrofit
- RXKotlin
- Google Play Services
- Dagger Hilt for dependency Injection
- Glide for image loading
- Junit and Mockito for testing

Notes

- Did not implement pagination for the recyclerview, since the api did not mention any way to paginate data returned from the api.
- RxKotlin was used to communicate between Repository layer and the Retrofit API.
- Used LiveData instead of the RxJava to communicate between the View and ViewModel because LiveData is LifeCycle Aware.

Testing

- Testing was only written for the ViewModel Skipped Other Utility methods, Repositories etc. Due to Time Constraints.
# Jing Ming: ming.j@northeastern.edu
This repo contains the assignments from the class CS5520-Summer 2022.

## Week1 Hello World
1. Shows text "Hello World!"
2. Press About Me button brings up a toast

## Week2 New Activity
1. Press About Me button brings up a new activity

## Week3 Button Click
1. Press About Me button brings up a toast
2. Press Clicky Clicky bring up a new activity with 6 buttons
3. Press each button will show a message below as "Pressed: [button_ID]"

## Week4 Recycler View
1. Press About Me button brings up a new activity
2. Press Link Collector button brings up a new activity shown a list of links
3. Link list is initially empty
4. Tap floating action button to enter a name and URL of a link
5. Press Save Link button store the information to link list, and back to the previous activity
6. Snackbar shows link added successful if the information is added to link list
7. UNDO action in snackbar removes the former added link
8. Tap link launches the URL in web browser

## Week5 Thread
1. Press Find Primes button brings up a new activity
2. New activity has 2 buttons: Find Primes and Terminate
3. Find Primes button starts a worker thread that searches for prime numbers (check from 2)
4. Terminate button stops and finishes the search, next search start from 2
5. While searching, displays "Latest Prime Found: []" and "[] is being checked"
6. There is a checkbox "Pacifier Switch" for user to check and uncheck. Checkbox is used to test if the UI thread is blocked or not
7. While searching, press back button brings up a dialog to inform user whether he/she wants to terminate the search or not

## Week6 Current Location and Track
1. Press Location button brings up a new activity 
2. Display the current location in (latitude, longitude), total travel distance, accuracy selected and accuracy
3. Update (latitude, longitude), total travel distance and accuracy when location changes
4. Update accuracy selected when 4 accuracy buttons are pressed
5. Total travel distance is persist in screen rotation
6. Press Reset Distance button resets the total travel distance to 0 and restarts recording
7. Press Highest Accuracy button shows the location with ACCESS_FINE_PERMISSION and PRIORITY_HIGH_ACCURACY
8. Press Balanced Accuracy button shows the location with PRIORITY_BALANCED_POWER_ACCURACY
9. Press lowest Accuracy button shows the location with PRIORITY_LOW_POWER
10. Press Passive Request button shows the location with PRIORITY_PASSIVE

## Week7 Web Service
1. Press Web Service button brings up a new activity
2. Provide search service with The Nobel Prize API: https://api.nobelprize.org/2.1/laureates and https://api.nobelprize.org/2.1/nobelPrizes
3. Press category drop down menu to select one category of Nobel Prize
4. Press FROM button to choose a year from 1901 to 2021
5. Press To button to choose a year from "from" to 2021
6. Type given name and family name of a Nobel laureate to the EditText view
7. Provides two kinds of search. One is by category and from year (to year is optional). Another is search by the laureates name.
8. Press SEARCH button starts connect to the API, sends user input and waits for the JSON object results
9. A loading panel shown on the screen during the waiting time
10. After search, the results displayed as a list of items on the screen
11. Press Clear button to clear previous user option and the search result

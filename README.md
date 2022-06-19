# Jing Ming: ming.j@northeastern.edu
This repo contains the assignments from the class CS5520-Summer 2022.

## Week1
1. Shows text "Hello World!"
2. Press About Me button brings up a toast

## Week2
1. Press About Me button brings up a new activity

## Week3
1. Press About Me button brings up a toast
2. Press Clicky Clicky bring up a new activity with 6 buttons
3. Press each button will show a message below as "Pressed: [button_ID]"

## Week4
1. Press About Me button brings up a new activity
2. Press Link Collector button brings up a new activity shown a list of links
3. Link list is initially empty
4. Tap floating action button to enter a name and URL of a link
5. Press Save Link button store the information to link list, and back to the previous activity
6. Snackbar shows link added successful if the information is added to link list
7. UNDO action in snackbar removes the former added link
8. Tap link launches the URL in web browser

## Week5
1. Press Find Primes button brings up a new activity
2. New activity has 2 buttons: Find Primes and Terminate
3. Find Primes button starts a worker thread that searches for prime numbers (check from 2)
4. Terminate button stops and finishes the search, next search start from 2
5. While searching, displays "Latest Prime Found: []" and "[] is being checked"
6. There is a checkbox "Pacifier Switch" for user to check and uncheck. Checkbox is used to test if the UI thread is blocked or not
7. While searching, press back button brings up a dialog to inform user whether he/she wants to terminate the search or not

## Week6
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

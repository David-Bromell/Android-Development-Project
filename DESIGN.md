# Technical Design of Tutor Finder

## Technologies used
* Google Maps API
* Firebase (used as backend for database and user authentication)
* Glide for storing users profile images
* Paypal API 
* Camera API

## Which technical design choices did you make?
# Firebase 
* We chose Firebase for multiple reasons, firstly due to its ease of use and secondly due to its realtime database and asynchronous 
capabilities, this in turn allows for live updates to the users and messages database. Thirdly the use of firebase analytics and error handling
proved crucial to our team when it came to troubleshooting various aspects of the application. 

# Paypal
* We chose to use paypal due to its ease of use and the ease of accesability of the platform for each user. The majority of people have
paypal accounts and the platform is reknowned for both its security and reliability. 

# Google Maps
* We selected the google maps api in oder to allow the user to find tutors directly on the map and see if there are any around their location. We used Google Maps because the api is simple to use, Google Maps are fairly accurate and with the ability to click on a pin and navigate to it, it enhances the user experience of our app. 

## Any lessons learned? E.g., what would you do differently next time?
Throughout this project we found that time management and planning ahead were crucial to the applications development,
for example when creating the users list we had to do so in a way that the adapter and application would communicate correctly,
i.e all getter and setter variables were alligned with those variables contained by our database. Initially this caused much confusion, however
we were able to overcome this and had accounted for same the next time we had to implement a function with the database in mind.

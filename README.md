# WhatsappAP2023

This is a chat application built with android studio.

When the application starts, it takes you to the login page where users are required to enter their registered username and password to access the chat screen. If the user doesn't have an account, they can register by clicking on the "click here" button in the login screen, thus navigating to the register screen. Then they can register by providing their details, including a username (any number of characters except for whitespaces), display name (only letter, whitespaces, numbers, underline and dash), password (8 characters with at least 1 uppercase letter, 1 lowercase letter, and 1 number - for security purposes), and a profile picture. If the user doesn't choose a picture, a default image will be chosen for him.

The registration details are stored in a user database using mongoDB. Once registered, users are navigated to the login page, where they must enter their username and password to access the chat screen. Upon successful login, the user's profile picture and display name appear at the top of the screen in the Chats screen, along with all their recent chats.

To talk to other users, the user must add contacts by pressing the "Add Contact" button on the bottom right corner of the page and entering the contact's name. the user has to add registered contacts, if the username entered is not of an actual contact the adding of a chat will fail. If it succeeds The added contact will appear on user chats screen. If the user wants to talk to any of the contact they have added a conversation with, they can click on the contact's name from the list, and then they will be navigated to the conversation screen which displays all the past messages between the current user and the chosen contact, assuming there is a message history between them. the messages will be saved even after logout so that when logging back in, the message history will appear. 
To navigate back to the chat list from a specific conversation the user can press the "back" button at the top left part of the screen.

in the login screen there is a settings button (represented with a settings icon), by clicking on it you will be navigated to the settings screen. in the settings screen there are 2 options:
  1. Night Mode - change the theme of the app from regular mode to night mode.
  2. Server URL - change the server url that the user is working on in order to connect to a different database.
     the default server address is "http://10.0.2.2:5000/api/", so until the first change, that will be the URL. if the user wants to change the address he will have to enter in the text input something in the following format "http://" or "https://" followed by the rest of the address, the "/api/" will be concatenated to what the user enters automatically so the user has to make sure not to add "/api/" by himself to prevent duplication. Once the user changes the server URL it will be saved even after closing and re-entering the app.
the settings can also be accessed and changed through the chat list screen after logging in. 

To use the chat application, open a terminal and type the following commands:

- to clone the assignment enter the following command - git clone -b ex3 https://github.com/guyreuveni33/WhatsappAP2023.git
  - cd WhatsappAP2023
  - cd server
  - npm i
  - npm start (at this point the server should run on port 5000)
  - open a new terminal on cd WhatsappAP2023
  - cd MyApplication
  - run the code by clicking the play button (at this point the app will start and you can register then login)

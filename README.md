# WhatsappAP2023

This is a chat application built using HTML, CSS, Bootstrap, JavaScript, NodeJS, MongoDB, and React.

When the application starts, it takes you to the login page where users are required to enter their registered username and password to access the chat screen. If the user doesn't have an account, they can register by clicking on the "click here" button in the login screen, thus navigating to the register screen. Then they can register by providing their details, including a username (any number of characters except for whitespaces), display name (only letter, whitespaces, numbers, underline and dash), password (8 characters with at least 1 uppercase letter, 1 lowercase letter, and 1 number - for security purposes), and a profile picture. If the user doesn't choose a picture, a default image will be chosen for him.

The registration details are stored in a user database using mongoDB. Once registered, users are navigated to the login page, where they must enter their username and password to access the chat screen. Upon successful login, the user's profile picture and display name appear at the top of the sidebar in the Chats screen, along with all their recent chats.

To talk to other users, the user must add contacts by pressing the "Add Contact" button and entering the contact's name. the user has to add registered contacts, if the username entered is not of an actual contact the adding of a chat will fail. If it succeeds The added contact will appear on the sidebar, and if the user wants to talk to them, they can click on the contact's name, and the chat screen will display the chat history with that contact. If there are previous conversations, they will appear in the chat, the chats will be saved in the database even after user logs out. Notice that if no user is selected, there will be no option to start a converation or to send a message.
The user can send a message in two ways, first is by clicking the "send" button or second buy pressing the "Enter" button.

The code uses Socket.IO, so when two users are logged in and are in the chat with each other, the masseages will appear instantly in the receiving side. even if the receiver is not currently in the specific chat, when the message is received the specific chat in the sidebar will jump to the top of the chat list, that's how he will know he has a new message from a user.

To use the chat application, open a terminal and type the following commands:

- to clone part 1 enter the following command - git clone -b connect_Chat_To_Swagger https://github.com/guyreuveni33/WhatsappAP2023.git
  - cd WhatsappAP2023
  - npm i
  - npm start
  - open your browser, then open address: http://localhost:3000/ and use the chat


- to clone part 2 enter the following command - git clone -b part2Server https://github.com/guyreuveni33/WhatsappAP2023.git
  - Please make sure you have .NET (version 6.0), NodeJS and MongoDB installed on your pc before doing the following commands
  - cd WhatsappAP2023
  - cd React
  - npm i
  - npm start
  - open your browser, then open address: http://localhost:3000/
  - open a new terminal on cd WhatsappAP2023
  - cd server
  - npm i
  - npm start

- to clone part 3 enter the following command - git clone -b part3 https://github.com/guyreuveni33/WhatsappAP2023.git
  - Please make sure you have .NET (version 6.0), NodeJS and MongoDB installed on your pc before doing the following commands
  - cd WhatsappAP2023
  - cd React
  - npm i
  - npm start
  - open your browser, then open address: http://localhost:3000/
  - open a new terminal on cd WhatsappAP2023
  - cd server
  - npm i
  - npm start

- to clone the entire project: git clone https://github.com/guyreuveni33/WhatsappAP2023.git
  - Please make sure you have .NET (version 6.0), NodeJS and MongoDB installed on your pc before doing the following commands
  - cd WhatsappAP2023
  - cd React
  - npm i
  - npm start
  - open your browser, then open address: http://localhost:3000/
  - open a new terminal on cd WhatsappAP2023
  - cd server
  - npm i
  - npm start


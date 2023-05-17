# WhatsappAP2023

This is a chat application built using HTML, CSS, Bootstrap, JavaScript, and React.

When the application is cloned and run with 'npm start' in the terminal, it takes you to the login page where users are required to enter their registered username and password to access the chat screen. If the user doesn't have an account, they can register by clicking on the "click here" button in the login screen, thus navigating to the register screen. Then they can register by providing their details, including a username (any number of characters except for whitespaces), display name (only letter, whitespaces, numbers, underline and dash), password (8 characters with at least 1 uppercase letter, 1 lowercase letter, and 1 number - for security purposes), and a profile picture. If the user doesn't choose a picture, a default image will be chosen for him.

The registration details are stored in a user database. Once registered, users are navigated to the login page, where they must enter their username and password to access the chat screen. Upon successful login, the user's profile picture and display name appear at the top of the sidebar in the Chat screen.

To talk to other users, the user must add contacts by pressing the "Add Contact" button and entering the contact's name. At this stage, the user does not have to add actual registered contacts, just write some contact name. The added contact will appear on the sidebar, and if the user wants to talk to them, they can click on the contact's name, and the chat screen will display the chat history with that contact. If there are previous conversations, they will appear in the chat, until the user logs out. Notice that if no user is selected, there will be no option to start a converation or to send a message.
The user can send a message in two ways, first is by clicking the "send" button or second buy pressing the "Enter" button.

Once the user logs out, all the chats will be erased, but the user's registry details will be saved in the database in case he wants to log in again. At this point, only the user can send messages, and there will be no reply from the other contact they are talking to.

To use the chat application, open a terminal and type the following commands:

- git clone https://github.com/guyreuveni33/WhatsappAP2023.git
- cd WhatsappAP2023
- npm i
- npm start
- open your browser, then open address: http://localhost:3000/
- Access the login page and either login with registered details or register for a new account.
- Add contacts to the chat screen.
- Start chatting by clicking on a contact's name in the side bar.
- To log out, simply click on the "Logout" button, which will take you back to the login page.

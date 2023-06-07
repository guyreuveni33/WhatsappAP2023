// Import the chat model
const Chat = require('../models/Chats').model;
const Msg = require('../models/Messages').model;
const jwt = require("jsonwebtoken");
const User = require("../models/Users");

// Service function to create a new chat
const createChat = async (user, addedUser) => {
    try {

        if (user === addedUser)
            return 1;//added user is same as current user
        const newChatUser = await User.findOne({username: addedUser});
        let chat;
        if (newChatUser) {
            //const chatExist1 = await Chat.findOne({users: addedUser});
            // Query for finding a chat where the first user is user and the second user is addedUser
            const chatExist1 = await Chat.findOne({'users.0': addedUser, 'users.1': user});
            // Query for finding a chat where the first user is addedUser and the second user is user
            const chatExist2 = await Chat.findOne({'users.0': user, 'users.1': addedUser});
            if (chatExist1 || chatExist2)
                return 2;// added user is already on the current chat
            // Create a new chat using the Chat model
            chat = new Chat({users: [addedUser, user], messages: []});
            // Save the chat to the database
            await chat.save();
            //return await User.findOne({username: user});
            return {
                id: chat._id,
                user: {
                    username: newChatUser.username,
                    displayName: newChatUser.displayName,
                    profilePic: newChatUser.profilePic
                }
            };
        } else
            return 3; //added user isn't a real user
    } catch (e) {
        return -1;
    }

};


// Service function to fetch all chats
const getAllChats = async (username) => {
    try {

        // Fetch all chats where the given username is included in the users array
        const myChats = await Chat.find({users: username});
        // Create an array to store the formatted chats
        const formattedChats = [];
        for (let chat of myChats) {
            // Create an object to store the formatted chat details
            const formattedChat = {
                id: chat._id,
                user: {},
                lastMessage: null
            };
            // Find the user object associated with the chat
            const users = await User.find({username: {$in: chat.users}});
            // Iterate over the users and set the user object in the formatted chat
            for (let user of users) {
                if (user.username !== username) {
                    formattedChat.user = {
                        username: user.username,
                        displayName: user.displayName,
                        profilePic: user.profilePic
                    };
                }
            }
            // Check if the chat has any messages
            if (chat.messages.length > 0) {
                const lastMessage = chat.messages[chat.messages.length - 1];
                formattedChat.lastMessage = {
                    id: lastMessage._id,
                    created: lastMessage.created,
                    content: lastMessage.content
                };
            }
            // Push the formatted chat to the array
            formattedChats.push(formattedChat);
        }
        return formattedChats;
    } catch (e) {
        return -1;
    }
};

// Service function to fetch all chats
const getChatsByID = async (chatId, me) => {
    try {
        const myChat = await Chat.findOne({_id: chatId});
        const users = myChat.users;
        var myUser1, myUser2;
        if (users[0] === me) {
            myUser1 = await User.findOne({username: me});
            myUser2 = await User.findOne({username: users[1]});
        } else {
            myUser1 = await User.findOne({username: me});
            myUser2 = await User.findOne({username: users[0]});
        }
        const messages = myChat.messages;
        const formattedMessages = [];
        var currentSender;
        for (let message of messages) {
            if (message.sender === myUser1.username) {
                currentSender = myUser1.username;
            } else {
                currentSender = myUser2.username;
            }
            const messageSender = await User.findOne({username: currentSender});
            const formattedMessage = {
                id: message._id,
                created: message.created,
                sender: {
                    username: messageSender.username,
                    displayName: messageSender.displayName,
                    profilePic: messageSender.profilePic
                },
                content: message.content
            };
            formattedMessages.push(formattedMessage);
        }
        // Create the formatted chat object
        const formattedChat = {
            id: chatId,
            users: [
                {
                    username: myUser2.username,
                    displayName: myUser2.displayName,
                    profilePic: myUser2.profilePic
                },
                {
                    username: myUser1.username,
                    displayName: myUser1.displayName,
                    profilePic: myUser1.profilePic
                }
            ],
            messages: formattedMessages
        };
        return formattedChat;
    } catch (e) {
        return -1;
    }
};

const deleteChat = async (chatId) => {
    try {
        return await Chat.findOneAndDelete({_id: chatId});
    } catch (error) {
        return -1;
    }
};

const sendMessage = async (chatId, message, username) => {
    try {
        const currentDate = new Date();
        const formattedDate = currentDate.toISOString();
        const newMessage = new Msg({
            chatId: chatId,
            sender: username,
            created: formattedDate,
            content: message
        });
        await newMessage.save();
        // Find the chat document by its ID
        const chat = await Chat.findOne({_id: chatId});
        // Check if the messages array exists
        if (!chat.messages) {
            chat.messages = [];
        }
        // Add the new message to the chat's messages array
        const updateChat = await Chat.updateOne(
            {_id: chatId},
            {$push: {messages: newMessage}}
        );
        // Save the updated chat document
        const senderUser = await User.findOne({username: username});
        const lastMessage = await Msg.findOne().sort({created: -1}).exec();
        const formattedResponse = {
            id: lastMessage._id,
            created: lastMessage.created,
            sender: {
                username: senderUser.username,
                displayName: senderUser.displayName,
                profilePic: senderUser.profilePic
            },
            content: message
        };
            return formattedResponse;
    } catch (error) {
        return -1;
    }
};

const getMessages = async (chatId) => {
    try {
    const chat = await Chat.findOne({_id: chatId});
    const messagesArray = chat.messages; // Access the messages array
    let a = 0;
    const formattedResponse = [];
    for (let message of messagesArray) {
        const formattedMessage = {
            id: message._id,
            created: message.created,
            sender: message.sender.username,
            content: message.content
        };
        formattedResponse.push(formattedMessage);
    }
        return formattedResponse.reverse(); // Reverse the array order
    }
    catch (error){
        return -1;
    }
};

module.exports = {createChat, getAllChats, getChatsByID, deleteChat, sendMessage, getMessages}

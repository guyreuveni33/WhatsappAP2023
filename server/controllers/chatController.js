// Import the chat service
const chatService = require('../services/chatService');
const jwt = require('jsonwebtoken');
const loginService = require("../services/loginService");

// Controller function to create a new chat
const createChat = async (req, res) => {
    if (!req.body.username)
        return res.status(400).json("Bad Request");
    // Extract data from the request body
    const user = req.body.username;
    // Call the chat service to create a new chat
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        try {
            var data = await jwt.verify(token, "guyandnivkey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const result = await chatService.createChat(user, data.username);
        if (result === 1) {
            return res.status(400).json("added user is same as current user");
        } else if (result === 2) {
            return res.status(404).json("added user is already on the current chat");
        } else if (result === 3) {
            return res.status(400).json("added user isn't a real user");
        } else if (result === -1)
            return res.status(404).json("error");
        else if (result)
            return res.json(result);
    } else
        res.status(400).json({error: 'Failed to create chat'});
};

// Controller function to get all chats
const getAllChats = async (req, res) => {
    // Call the chat service to fetch all chats
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        try {
            var data = await jwt.verify(token, "guyandnivkey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const result = await chatService.getAllChats(data.username);
        if (!result) {
            return res.status(404).json("error");
        }
        return res.json(result);
    } else
        res.status(400).json({error: 'Failed to fetch chats'});
};


// Controller function to chat by ID
const getChatByID = async (req, res) => {
    // Extract chat ID from request parameters
    const chatId = req.params.id;
    if (!chatId)
        return res.status(400).json("Bad Request");
    // Call the chat service to fetch the chat by ID
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        try {
            var data = await jwt.verify(token, "guyandnivkey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const result = await chatService.getChatsByID(chatId, data.username);
        if (!result) {
            return res.status(404).json("Chat not found");
        }
        if (result === -1)
            return res.status(401).json("error");
        return res.json(result);
    } else
        res.status(400).json({error: 'Failed to fetch chat by id'});
};
const deleteChat = async (req, res) => {
    // Extract chat ID from request parameters
    const chatId = req.params.id;
    if (!chatId)
        return res.status(400).json("Bad Request");
    // Call the chat service to delete the chat by ID
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        try {
            const data = await jwt.verify(token, "guyandnivkey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const result = await chatService.deleteChat(chatId);
        if (result === -1)
            return res.status(404).json("error");
        else if (!result)
            return res.status(404).json("Chat not found");
        return res.json("chat deleted");
    } else {
        res.status(400).json({error: 'Failed to delete chat'});
    }
};

const sendMessage = async (req, res) => {
    // Extract data from the request body
    const chatId = req.params.id;
    if (!chatId)
        return res.status(400).json("Bad Request");
    const message = req.body.msg;
    if (!message)
        return res.status(400).json("Bad Request");
    // Call the chat service to create a new chat
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        try {
            var data = await jwt.verify(token, "guyandnivkey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const result = await chatService.sendMessage(chatId, message, data.username);
        if (result === -1)
            return res.status(404).json("error");
        else if (!result)
            return res.status(401).json("Unauthorized");
        return res.json(result);
    } else
        res.status(400).json({error: 'Failed to send message'});
};

const getMessages = async (req, res) => {
    // Extract data from the request body
    const chatId = req.params.id;
    if (!chatId)
        return res.status(400).json("Bad Request");
    // Call the chat service to create a new chat
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        try {
            var data = await jwt.verify(token, "guyandnivkey");
        } catch (error) {
            return res.status(401).json("Unauthorized");
        }
        const result = await chatService.getMessages(chatId);
        if (result === -1)
            return res.status(404).json("error");
        else if (!result)
            return res.status(401).json("Unauthorized");
        return res.json(result);
    } else {
        res.status(400).json({error: 'Failed to get messages'});
    }
};

module.exports = {createChat, getChatByID, getAllChats, deleteChat, sendMessage, getMessages}
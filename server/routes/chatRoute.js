const express = require('express');
const router = express.Router();
const chatController = require('../controllers/chatController');
const {get} = require("mongoose");
const loginController = require("../controllers/loginController");

// Create a new chat
router
    .post('/', chatController.createChat)
    .get('/', chatController.getAllChats)
    .get('/:id', chatController.getChatByID)
    .delete('/:id', chatController.deleteChat)
    .post('/:id/Messages', chatController.sendMessage)
    .get('/:id/Messages', chatController.getMessages);

module.exports = router;


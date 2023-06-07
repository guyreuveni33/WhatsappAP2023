const registerController = require('../controllers/registerController');
const loginController = require('../controllers/loginController');
const express = require('express');
const router = express.Router();

// User registration
router.route('/')
    .post(registerController.registerUser);

router.route('/:username')
    .get(loginController.userData);

module.exports = router;

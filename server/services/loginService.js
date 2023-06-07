// Import the user model
const User = require('../models/Users');
const jwt = require('jsonwebtoken');
// Service function to handle user login
const userData = async (token) => {
    try {
        const data = await jwt.verify(token, "guyandnivkey");
        return await User.findOne({username: data.username});
    } catch (error) {
        return false;
    }
};

const validateUser = async (username, password) => {
    try {
        const user = await User.findOne({username: username, password: password});
        if (user)
            return true;
        return false;
    } catch (error) {
        return false;
    }
};
module.exports = {userData, validateUser}



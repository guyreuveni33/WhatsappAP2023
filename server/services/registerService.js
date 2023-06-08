// Import the user model
const User = require('../models/Users');

// Service function to handle user registration
registerUser = async (username, password, displayName, profilePic) => {
    // Create a new user using the User model
    const userCheck = await User.findOne({username: username});
if (!userCheck){
    const user = new User({ username, password, displayName, profilePic });
    // Save the user to the database
    return await user.save();
}
return false;
};

module.exports = {registerUser}

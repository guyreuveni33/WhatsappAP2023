// Import the register service
const registerService = require('../services/registerService');

// Controller function to handle user registration
const registerUser = async (req, res) => {
        if (!req.body.username || !req.body.password || !req.body.displayName || !req.body.profilePic) {
            return res.status(400).json("Bad Request")
        }
        // Extract data from the request body
        await console.log(await req.body.username)
        // Call the register service to register a new user
        const user = await registerService.registerUser(req.body.username, req.body.password,
            req.body.displayName, req.body.profilePic);
        if (!user) {
            // Handle the case when the username already exists
            return res.status(409).json({error: 'Username already exists'});
        }
        res.json(user)
    }
;

module.exports = {registerUser}
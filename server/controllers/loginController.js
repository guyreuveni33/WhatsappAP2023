// Import the login service
const loginService = require('../services/loginService');
const jwt = require('jsonwebtoken');
// Controller function to handle user login
const loginUser = async (req, res) => {
    if (!req.body.username || !req.body.password)
        return res.status(400).json("Bad Request");
    // Extract data from the request body
    const data = {username: req.body.username};
    // Call the login service to perform user login
    const user = await loginService.validateUser(req.body.username, req.body.password);
    if (user) {
        const token = jwt.sign(data, "guyandnivkey");
        res.send(token);
    }
    else
        res.status(404).json({error: 'Invalid username or password'});
};

const userData = async (req, res) => {
    if (!req.params.username) {
        return res.status(400).json("Bad Request");
    }
    if (req.headers.authorization) {
        const token = req.headers.authorization.split(" ")[1];
        const result = await loginService.userData(token);
        if (!result) {
            return res.status(401).json("Unauthorized");
        } else {
            if (result.username !== req.params.username) {
                return res.status(401).json("Unauthorized");
            }
            const x = {username: result.username, displayName: result.displayName, profilePic: result.profilePic}
            return res.json(x);
        }
    } else {
        return res.status(400).json("Bad Request");
    }
}


module.exports = {loginUser, userData}
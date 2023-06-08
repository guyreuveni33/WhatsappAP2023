const express = require('express');
const bodyParser = require ('body-parser');
const cors = require( 'cors');
const mongoose = require ('mongoose');
const app = express();
const customEnv = require ( 'custom-env');
customEnv.env(process.env.NODE_ENV,'./config')
const http = require('http');
const server = http.createServer(app)
const {Server} = require("socket.io");
const io = new Server(server);
const port = 5001;

app.use(express.json());
app.use(cors())
app.use(bodyParser.urlencoded({extended: true}))
mongoose.connect(process.env.CONNECTION_STRING, {useNewUrlParser: true, useUnifiedTopology: true});

// app.use(express.static('/public'));
///TODO CHANGE IT TO PUBLIC AT THE END OF THE ASSIGNMENT
const site = (express.static('../React/build'));
app.use('/', site);
app.use('/Chat', site);
app.use('/Register', site);

// // Import the routes
const registerRoutes = require('./routes/registerRoute');
app.use('/api/Users', registerRoutes);
const loginRoutes = require('./routes/loginRoute');
app.use('/api/Tokens', loginRoutes);
const chatRoutes = require('./routes/chatRoute');
app.use('/api/Chats', chatRoutes);
//
io.on('connection', (socket) => {
    socket.on("connecting", (username) => {
        socket.join(username);
        console.log(username," connected");
    })    // Add contact
    socket.on('addContact', (username) => {
        // Process the contactInfo and add the contact
        // Example: Emit an event to the client to notify success or failure
         socket.in(username).emit('addContact');
    });

    // Send message
    socket.on('receiveMessage', (msg) => {
        console.log(msg.receiver.name)
        // Process the message and send it to the recipient
        // Example: Emit an event to the client to notify success or failure
         socket.in(msg.receiver.name).emit('receiveMessage', msg);
    });
});
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});


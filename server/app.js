const express = require('express');
const bodyParser = require ('body-parser');
const cors = require( 'cors');
const mongoose = require ('mongoose');
const customEnv = require ( 'custom-env');
//customEnv.env(process.env.NODE_ENV,'./config');
const app = express();
const port = 5000;

app.use(express.json());
app.use(cors())
app.use(bodyParser.urlencoded({extended: true}))
mongoose.connect("mongodb://127.0.0.1:27017/chat", {useNewUrlParser: true, useUnifiedTopology: true});

app.use(express.static('../react/public'));
const site = (express.static('../react/public'));
app.use('/', site);
// // Import the routes
const registerRoutes = require('./routes/registerRoute');
app.use('/api/Users', registerRoutes);
const loginRoutes = require('./routes/loginRoute');
app.use('/api/Tokens', loginRoutes);
const chatRoutes = require('./routes/chatRoute');
app.use('/api/Chats', chatRoutes);
//

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});



//const articles = require('•/routes/article');
//

// // Register the routes
// app.use('/api/login', loginRoutes);
// app.use('/api/register', registerRoutes);

//
// console.log (process.env.CONNECTION_STRING)
// console.log (process.env.PORT)
// mongoose. connect (process.env. CONNECTION_STRING,
//     {
//         useNewUrlParser: true, useUnifiedTopology: true
//     });
// app.use (cors ());
// app.use (bodyParser.urlencoded({ extended: true }));
// app.use(express.json());
//app.use('/articles', articles);



import './LoginRegister.css';
import LoginUserInput from "./login/userInput/LoginUserInput";
import PageStruct from "./login/pageStruct/PageStruct";
import {Link} from "react-router-dom";
import {useState} from "react";
import users from "./UsersDatabase";


function Login({setUsernameNew,setToken,token}) {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    // This check for which page the user will navigate
    const [navigate, setNavigate] = useState("");
    // This check for a match between the userName and the password
    const [match, setMatch] = useState(false);
    // This is the Database of the users
    const storedUsers = users;
    // Event handlers for username and password changes
    const handleUsernameChange = (value) => {
        setUsername(value);
    };
    // Event handlers for password changes
    const handlePasswordChange = (value) => {
        setPassword(value);
    };
    
    // Event handler for setting the navigate path based on the user input
    // Event handler for setting the navigate path based on the user input
    // Event handler for setting the navigate path based on the user input
    const handleNavigatePath = async () => {
        try {
            const response = await fetch('http://localhost:5001/api/Tokens', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: username,
                    password: password,
                }),
            });

            if (response.ok) {
                const tokenValue = await response.text();
                const token = "Bearer " + tokenValue;
                setToken(token);
                console.log('Token created successfully');
                console.log(token);
                setUsernameNew(username);
                setNavigate('/Chat');
                setMatch(true);
            } else {
                throw new Error('Token creation failed');
            }
        } catch (error) {
            console.error(error);
            setNavigate('/');
        }
    };


    //This pop alert in case there is no match between the password and the userName
    const handleLoginClick = () => {
        if (!match) {
            alert("Incorrect username or password.");
        }
    }

    return (
        <PageStruct margins="mb-md-5 mt-md-4 pb-5" title="LOGIN" cardSetting="card-body p-5 text-center"
                    classSetting="register" registerStatus="Not registered?" currentStatusLink="/Register">
            <LoginUserInput
                holderText=" Username"
                value={username}
                onChange={handleUsernameChange}
                onBlur={handleNavigatePath}
            />
            <LoginUserInput
                typeText="password"
                holderText=" Password"
                value={password}
                onChange={handlePasswordChange}
                onBlur={handleNavigatePath}
            />
            <br></br>
            <Link to={navigate}>
                <button
                    className="btn btn-outline-light btn-lg px-4"
                    type="submit"
                    onClick={handleLoginClick}
                >Login
                </button>
            </Link>
            <br></br>
            <br></br>
            <br></br>
        </PageStruct>
    );
}

export default Login;

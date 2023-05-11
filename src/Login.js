import './LoginRegister.css';
import LoginUserInput from "./login/userInput/LoginUserInput";
import PageStruct from "./login/pageStruct/PageStruct";
import {Link} from "react-router-dom";
import {useState} from "react";
import users from "./UsersDatabase";


function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [navigate, setNavigate] = useState("");
    const [match, setMatch] = useState(false);
    const storedUsers = users;
    const handleUsernameChange = (value) => {
        setUsername(value);
    };

    const handlePasswordChange = (value) => {
        setPassword(value);
    };

    const handleNavigatePath = () => {
        for (const key of Object.keys(storedUsers)) {
            if (username === key && password === storedUsers[key].password) {
                setNavigate("/Chat");
                setMatch(true);
                return;
            }
            else {
                setNavigate("/");
            }
        }
    };

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

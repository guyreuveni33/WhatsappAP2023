import './LoginRegister.css';
import UserInput from "./login/userInput/UserInput";
import PageStruct from "./login/pageStruct/PageStruct";

function Login() {
    return (
        <PageStruct margins="mb-md-5 mt-md-4 pb-5" title="LOGIN" cardSetting="card-body p-5 text-center" classSetting="register" registerStatus="Not registered?" currentStatusLink="register.html">
            <UserInput holderText=" Username"/>
            <UserInput typeText="password" holderText=" Password"/>
            <br></br>
            <button className="btn btn-outline-light btn-lg px-4" type="submit">Login</button>
            <br></br>
            <br></br>
            <br></br>
            <p className="register">Not registered? <a href="register.html">Click here</a></p>
        </PageStruct>
    );
}

export default Login;
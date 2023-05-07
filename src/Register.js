import './LoginRegister.css';
import UserInput from "./login/userInput/UserInput";
import PageStruct from "./login/pageStruct/PageStruct";
import ImageUpload from "./login/imageUpload/ImageUpload";

function Register() {
    return (
        <PageStruct
            cardSetting="card-body text-center"
            margins="mb-md-5 mt-md-4"
            title="REGISTER"
            registerStatus="Already registered?"
            currentStatusLink="login.html"
        >
            <UserInput
                holderText=" Username"
                pattern={/^[a-zA-Z0-9]+$/}
            />
            <UserInput
                typeText="password"
                holderText=" Password"
                pattern={/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/}
            />
            <UserInput
                typeText="password"
                holderText=" Re-Enter Password"
                pattern={/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/}
            />
            <UserInput
                holderText=" Display Name"
                pattern={/^[a-zA-Z\s]+$/}
            />
            <ImageUpload />
            <button className="btn btn-outline-light btn-lg px-4" type="submit">
                Register
            </button>
        </PageStruct>
    );
}


export default Register;
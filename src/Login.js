import './LoginRegister.css';

function Login() {
    return (
        <section className="mainBG">
            <div className="d-flex justify-content-center align-items-center">
                <div className="card bg-dark text-white" style={{borderRadius: '1rem'}}>
                    <div className="card-body p-5 text-center">
                        <div className="mb-md-5 mt-md-4 pb-5">

                            <div className="card-body">
                                <h1 className="card-title">LOGIN</h1>
                                <br></br>
                                <br></br>

                                <div className="mb-4"><input className="bg-dark text-white border-white rounded"
                                                             placeholder=" Username"></input>
                                </div>
                                <div className="mb-4"><input type="password" className="bg-dark text-white border-white rounded"
                                                             placeholder=" Password"></input>
                                </div>
                                <br></br>

                                <button className="btn btn-outline-light btn-lg px-4" type="submit">Login</button>
                                <br></br>

                                <br></br>
                                <br></br>
                                <p className="register">Not registered? <a href="register.html">Click here</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default Login;

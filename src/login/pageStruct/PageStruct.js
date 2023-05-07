import UserInput from "../userInput/UserInput";

function PageStruct({children,title,margins,cardSetting,classSetting,registerStatus,currentStatusLink}){
    return(
        <section className="mainBG">
            <div className="d-flex justify-content-center align-items-center">
                <div className="card bg-dark text-white" style={{borderRadius: '1rem'}}>
                    <div className={cardSetting}>
                        <div className={margins}>
                            <div className="card-body">
                                <h1 className="card-title">{title}</h1>
                                <br></br>
                                <br></br>
                                {children}
                                <p className={classSetting}>{registerStatus} <a href={currentStatusLink}>Click here</a></p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}
export default PageStruct;
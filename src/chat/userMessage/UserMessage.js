function UserMessage({text}){
    return(
        <div className="clearfix">
            <div className="message userTextMessage">
                <p>{text}</p>
            </div>
        </div>

    );
}
export default UserMessage;
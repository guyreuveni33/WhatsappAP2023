import "./UserMessage.css"

function UserMessage({text, time}) {
    return (
        <div className="clearfix">
            <div className="message userTextMessage">
                <div className="message-content">
                    <p className="message-text">{text}</p>
                    <p className="message-time">{time}</p>
                </div>
            </div>
        </div>
    );
}

export default UserMessage;
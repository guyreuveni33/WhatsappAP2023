import ReceivedMessage from "../../receivedMessage/ReceivedMessage";
import UserMessage from "../../userMessage/UserMessage";
import './ChatMessages.css';

/* The component receives a `messages` prop which is an array of message objects. operate the matching component
according to if the message is a user message or a received message */
function ChatMessages({messages}) {
    return (
        <div className="chat-messages overflow-scroll hover">
            <div>
                {messages.map((message, index) => {
                    if (message.type === "received") {
                        return (
                            <div key={index} className="message-container">
                                <ReceivedMessage text={message.text}/>
                            </div>
                        );
                    } else if (message.type === "user") {
                        return (
                            <div key={index} className="message-container">
                                <UserMessage text={message.text} time={message.time}/>
                            </div>
                        );
                    } else {
                        return null;
                    }
                })}
            </div>
        </div>
    );
}

export default ChatMessages;
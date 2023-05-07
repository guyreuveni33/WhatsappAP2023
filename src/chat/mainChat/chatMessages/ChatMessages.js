import ReceivedMessage from "../../receivedMessage/ReceivedMessage";
import UserMessage from "../../userMessage/UserMessage";

function ChatMessages(){
    return(
        <div className="chat-messages">
            <div>
                <ReceivedMessage text="Hello, how are u?">
                </ReceivedMessage>
                <UserMessage text="I'm doing good, and you?">
                </UserMessage>
                <ReceivedMessage text="Great, just about to finish my AP 2 program">
                </ReceivedMessage>
                <UserMessage text="That sounds phenomenal as Noa Kirel would say ;)">
                </UserMessage>
                <ReceivedMessage text="See you next month in Italy?">
                </ReceivedMessage>
                <UserMessage text="Sure!">
                </UserMessage>
            </div>
        </div>
    );
}
export default ChatMessages;
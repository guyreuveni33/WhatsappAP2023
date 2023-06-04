import './chatMessages/ChatMessages.css';
import {useState, useEffect} from "react";
import MainChatHeader from "./mainChatHeader/MainChatHeader";
import ChatMessages from "./chatMessages/ChatMessages";
import SendMessageBar from "./sendMessageBar/SendMessageBar";

function MainChat(props) {
    const [messages, setMessages] = useState([]);
    // This checks if a new message has been added and if it has, displays the message on the screen
    useEffect(() => {
        setMessages(props.initialMessages || []);
    }, [props.initialMessages]);

    return (
        <div className="main-chat overflow-hidden container-width">
            <MainChatHeader selectedContact={props.selectedContact} profilePic={props.profilePic}
                            displayName={props.displayName}/>
            <ChatMessages messages={messages}/>
            <SendMessageBar  messages={messages} setMessages={setMessages} selectedContact={props.selectedContact}
                            contacts={props.contacts} setLastMessage={props.setLastMessage} token={props.token}
                            fetchSelectedUserMessages={props.fetchSelectedUserMessages} lastMessage={props.lastMessage}

            />
        </div>
    );
}

export default MainChat;
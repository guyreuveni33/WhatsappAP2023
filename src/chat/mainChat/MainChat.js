import {useState, useEffect} from "react";
import MainChatHeader from "./mainChatHeader/MainChatHeader";
import ChatMessages from "./chatMessages/ChatMessages";
import SendMessageBar from "./sendMessageBar/SendMessageBar";

function MainChat(props) {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        setMessages(props.initialMessages || []);
    }, [props.initialMessages]);

    return (
        <div className="main-chat overflow-hidden">
            <MainChatHeader selectedContact={props.selectedContact} contacts={props.contacts}/>
            <ChatMessages messages={messages}/>
            <SendMessageBar messages={messages} setMessages={setMessages} selectedContact={props.selectedContact} contacts={props.contacts} />
        </div>
    );
}

export default MainChat;
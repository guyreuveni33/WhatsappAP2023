import MainChatHeader from "./mainChatHeader/MainChatHeader";
import ChatMessages from "./chatMessages/ChatMessages";
import SendMessageBar from "./sendMessageBar/SendMessageBar";

function MainChat(){
    return(
        <div className="main-chat overflow-hidden">
            <MainChatHeader></MainChatHeader>
            <ChatMessages></ChatMessages>
            <SendMessageBar></SendMessageBar>
        </div>
    );
}
export default MainChat;
import './Chat.css';
import Sidebar from "./chat/sidebar/Sidebar";
import MainChat from "./chat/mainChat/MainChat";
import ModalScreen from "./chat/modalScreen/ModalScreen";

function Chat() {
    return (
        <div className="mainBG d-flex justify-content-center p-4">
            <div className="chat-container">
                <Sidebar></Sidebar>
                <MainChat></MainChat>
            </div>
            <ModalScreen></ModalScreen>
        </div>
    );
}

export default Chat;

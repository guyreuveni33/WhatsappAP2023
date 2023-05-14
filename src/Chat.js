import {useState} from "react";
import "./Chat.css";
import Sidebar from "./chat/sidebar/Sidebar";
import MainChat from "./chat/mainChat/MainChat";
import ModalScreen from "./chat/modalScreen/ModalScreen";
import MessageDB from "./chat/dataBase/MessagesDB";

function Chat({username}) {
    const currentUser = username;
    // Define the contacts state and handleAddContact function in the Chat component
    const [contacts, setContacts] = useState([]);

    // Add contact to the contact list
    const handleAddContact = (newContact) => {
        setContacts([...contacts, {...newContact, lastMessage: ""}]);
    };

    // Define the selected contact state and handleContactClick function in the Chat component
    const [selectedContact, setSelectedContact] = useState("0");
    const [lastMessage, setLastMessage] = useState(" ");

    const handleContactClick = (contactName) => {
        setSelectedContact(contactName);
    };

    return (
        <div className="mainBG d-flex justify-content-center p-4">
            <div className="chat-container">
                {/* Pass the contacts, handleAddContact and handleContactClick as props to the Sidebar component */}
                <Sidebar
                    contacts={contacts}
                    handleAddContact={handleAddContact}
                    handleContactClick={handleContactClick}
                    selectedContact={selectedContact}
                    currentUser={currentUser}
                    lastMessage={lastMessage}
                />
                {/* Pass the selected contact and messages as props to the MainChat component */}
                <MainChat initialMessages={selectedContact === null ? MessageDB[0] : MessageDB[selectedContact]}
                          selectedContact={selectedContact}
                          contacts={contacts}
                          setLastMessage={setLastMessage}/>
            </div>
            <ModalScreen></ModalScreen>
        </div>
    );
}

export default Chat;
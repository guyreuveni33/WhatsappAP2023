import { useState } from "react";
import "./Chat.css";
import Sidebar from "./chat/sidebar/Sidebar";
import MainChat from "./chat/mainChat/MainChat";
import ModalScreen from "./chat/modalScreen/ModalScreen";
import MessageDB from "./chat/dataBase/MessagesDB";
import ContactsDB from "./chat/dataBase/ContactsDB";

function Chat() {

    // Define the contacts state and handleAddContact function in the Chat component
    const [contacts, setContacts] = useState([
        {
            name: "Niv Swisa",
            lastMessage: "Sure!",
            date: "09/02/2023",
            profilePicture: "https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg",
        },
        {
            name: "Guy Reuveni",
            lastMessage: "Hello World!",
            date: "09/02/2023",
            profilePicture: "https://i.postimg.cc/L47zRrjW/dvd.jpg",
        },
    ]);

    const handleAddContact = (newContact) => {
        setContacts([...contacts, newContact]);
        ContactsDB[selectedContact]= [ ...ContactsDB[selectedContact], {
            name:newContact.name,
            lastMessage:newContact.lastMessage,
            date: newContact.date,
            profilePicture: newContact.profilePicture,
        }];
        console.log(newContact);
        console.log(MessageDB);
    };

    // Define the selected contact state and handleContactClick function in the Chat component
    const [selectedContact, setSelectedContact] = useState("");

    const handleContactClick = (contactName) => {
        setSelectedContact(contactName);
        console.log(contactName);
        console.log(MessageDB[contactName]);
    };

    return (
        <div className="mainBG d-flex justify-content-center p-4">
            <div className="chat-container">
                {/* Pass the contacts, handleAddContact and handleContactClick as props to the Sidebar component */}
                <Sidebar contacts={contacts} handleAddContact={handleAddContact} handleContactClick={handleContactClick} selectedContact={selectedContact} />
                {/* Pass the selected contact and messages as props to the MainChat component */}
                <MainChat initialMessages={MessageDB[selectedContact]} selectedContact={selectedContact} contacts={contacts} />
            </div>
            <ModalScreen></ModalScreen>
        </div>
    );
}

export default Chat;
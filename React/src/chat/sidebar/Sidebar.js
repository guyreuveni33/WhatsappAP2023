import { useState, useEffect } from "react";
import './SideBar.css';
import HeaderProfiles from '../headerProfiles/HeaderProfiles';
import ModalScreen from '../modalScreen/ModalScreen';
import ContactProfile from '../contactProfile/ContactProfile';
import {io} from "socket.io-client";

function Sidebar({ handleAddContact, handleContactClick, currentUser, displayName,
                     profilePic, userToken, selectedDisplayName, lastMessage, socket, contacts, setContacts,
                 setSelectedUsername}) {




        const fetchChats = async () => {
            try {
                const response = await fetch("http://localhost:5001/api/Chats", {
                    method: "get",
                    headers: {
                        "Content-Type": "application/json",
                        authorization: userToken,
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    //setContacts(data);
                    setContacts(ADAPTER_contactList(data));
                    console.log("hey");
                    //const adaptedContacts = adaptContactsData(data);
                    //setContacts(adaptedContacts);
                } else {
                    console.error("Error fetching chats:", response.status);
                }
            } catch (error) {
                console.error("Error fetching chats:", error);
            }
        };

    useEffect(() => {
            fetchChats();
    }, [userToken]);

    useEffect(() => {
        fetchChats(); // Call fetchChats when lastMessage updates
        console.log("adfadfadf");
        //setContacts();
    }, [lastMessage]);

    const modalScreenProps = {
        handleAddContact,
        token: userToken,
        fetchChats: fetchChats,
        contacts: contacts,
        socket: socket,
        currentUser: currentUser
    };

function ADAPTER_contactList (data) {
    var newData = [];
    for (let contact of data) {
        const newContact = {
            id: contact["id"],
            username: contact["user"]["username"],
            name: contact["user"]["displayName"],
            profilePicture: contact["user"]["profilePic"],
            lastMessage: contact[ "lastMessage"] === null ? "" : contact["lastMessage"]["content"],
            date: formatDate(contact["lastMessage"] === null ? "" : contact["lastMessage"]["created"]),
        };
        console.log(newContact)
        newData = [...newData, newContact]
    }
    return newData;
}

    function formatDate(dateString) {
        const date = new Date(dateString);
        const formattedDate = date.toLocaleDateString("en-GB", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
        });
        const formattedTime = date.toLocaleTimeString("en-US", {
            hour: "2-digit",
            minute: "2-digit",
            hour12: false,
        });
        return `${formattedDate}`;
    }


    return (
        <div className="sidebar overflow-hidden bg-dark">
            <div className="userSpace overflow-hidden">
                <HeaderProfiles
                    margins="ms-1"
                    pictureSetting="profileCurrentChatPic"
                    profilePicture={profilePic}
                    textPosition="text-white ms-2"
                    textSetting="fw-bold mb-0"
                    name={displayName}
                />
                <div className="ms-auto">
                    <button
                        type="button"
                        className="btn bg-dark text-white"
                        data-bs-toggle="modal"
                        data-bs-target="#exampleModal"
                    >
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="16"
                            height="16"
                            fill="currentColor"
                            className="bi bi-person-plus-fill"
                            viewBox="0 0 16 16"
                        >
                            <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path>
                            <path
                                fillRule="evenodd"
                                d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"
                            ></path>
                        </svg>
                    </button>
                </div>
            </div>
            <ul className="list-unstyled text-white overflow-scroll">
                {contacts.map((contact) => (
                    <button
                        key={contact.name}
                        type="button"
                        className="btn t btn-fixed-width contactHover text-start p-0 text-white"
                        onClick={() => {
                            handleContactClick(contact.id, contact.name)
                            setSelectedUsername({name :contact.username, id:contact.id});
                        }
                    }
                    >
                        <ContactProfile
                            name={contact.name}
                            lastMessage={contact.lastMessage}
                            date={contact.date}
                            profilePicture={contact.profilePicture}
                        />
                    </button>
                ))}
            </ul>
            <ModalScreen {...modalScreenProps}  />
        </div>
    );
}

export default Sidebar;

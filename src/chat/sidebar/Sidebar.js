import { useState } from 'react';
import './SideBar.css';
import HeaderProfiles from '../headerProfiles/HeaderProfiles';
import ModalScreen from '../modalScreen/ModalScreen';
import ContactProfile from '../contactProfile/ContactProfile';
import users from "../../UsersDatabase";
import Login from "../../Login";

function Sidebar({ contacts, handleAddContact, handleContactClick, currentUser }) {
    //username.current.value
    const userDisplayName = users[currentUser].displayName;
    const userImage = users[currentUser].image;
    //console.log(userDisplayName);
    //console.log(userImage);
    return (
        <div className="sidebar overflow-hidden bg-dark">
            <div className="userSpace overflow-hidden">
                <HeaderProfiles
                    margins="ms-1"
                    pictureSetting="profileCurrentChatPic"
                    profilePicture={userImage}
                    textPosition="text-white ms-2"
                    textSetting="fw-bold mb-0"
                    name={userDisplayName}
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
                        className="btn text-start p-0 text-white"
                        onClick={() => handleContactClick(contact.name)}
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
            <ModalScreen handleAddContact={handleAddContact} />
        </div>
    );
}

export default Sidebar;

import HeaderProfiles from "../../headerProfiles/HeaderProfiles";
import plaster from "./plaster.png"
import {Link} from "react-router-dom";
import MessageDB from "../../dataBase/MessagesDB";

function MainChatHeader(props) {
    // This extracts the number of the contact in the array of contacts in order to display it at the chat header
    // function contactIden() {
    //     for (let i = 0; i < props.contacts.length; i++) {
    //         if (props.contacts[i].name === props.selectedContact) {
    //             return i;
    //         }
    //     }
    //     return -1;
    // }
    //
    // // Save the current contact index
    // const index = contactIden();

    // When the logout button is pressed the message database will clear all the messages
    function handleLogout() {
        for (let contact in MessageDB) {
            if (MessageDB.hasOwnProperty(contact)) {
                MessageDB[contact] = [];
            }
        }
    }

    return (
        <div className="chat-header ">
            <HeaderProfiles pictureSetting="profileCurrentChatPic d-flex align-self-center me-3"
                            profilePicture={props.profilePic}
                            textPosition="col text-white align-self-center"
                            textSetting="fw-bold mb-0 ms-1 "
                            name={props.displayName}></HeaderProfiles>
            <div className="chat-input">
                <Link
                    to="/"
                    className="bg-dark text-white btn link-underline-opacity-0 link-underline"
                    onClick={handleLogout}
                >Logout
                </Link>
            </div>
        </div>
    );
}

export default MainChatHeader;
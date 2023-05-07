import HeaderProfiles from "../../headerProfiles/HeaderProfiles";

function MainChatHeader(){
    return(
        <div className="chat-header overflow-hidden">
            {/* <!-- Contact name or chat title will be displayed here -->*/}
            <HeaderProfiles pictureSetting="profileCurrentChatPic d-flex align-self-center me-3"
                            profilePicture="https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg"
                            textPosition="col text-white align-self-center"
                            textSetting="fw-bold mb-0 ms-1 " name="Niv Swisa"></HeaderProfiles>
            <div className="chat-input">
                <button className="bg-dark">Log Out</button>
            </div>
        </div>
    );
}
export default MainChatHeader;
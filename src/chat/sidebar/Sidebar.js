import HeaderProfiles from "../headerProfiles/HeaderProfiles";
import ContactProfile from "../contactProfile/ContactProfile";

function Sidebar(){
    return(
        <div className="sidebar overflow-hidden bg-dark">
            {/* <!-- Button trigger modal -->*/}
            <div className="userSpace overflow-hidden">
                <HeaderProfiles margins="ms-1" pictureSetting="profileCurrentChatPic"
                                profilePicture="https://i.postimg.cc/kGZZvf65/52-SNp-DJJCg-BVf-Ztib-Lu-XN1-Zrli-Cdvv.jpg"
                                textPosition="text-white ms-2" textSetting="fw-bold mb-0"
                                name="Elon Musk"></HeaderProfiles>
                <div className="ms-auto">
                    {/*<!-- Button trigger modal -->*/}
                    <button type="button" className="btn bg-dark text-white" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             className="bi bi-person-plus-fill" viewBox="0 0 16 16">
                            <path
                                d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path>
                            <path fill-rule="evenodd"
                                  d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"></path>
                        </svg>
                    </button>
                </div>
            </div>
            <ul className="list-unstyled text-white">
                <ContactProfile name="Niv Swisa" lastMessage="Sure!" date="09/02/2023"
                                profilePicture="https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg"></ContactProfile>
                <ContactProfile name="Guy Reuveni" lastMessage="Hello World!" date="09/02/2023"
                                profilePicture="https://i.postimg.cc/L47zRrjW/dvd.jpg"></ContactProfile>
            </ul>
        </div>
    );
}
export default Sidebar;
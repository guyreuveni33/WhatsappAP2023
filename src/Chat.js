import './Chat.css';
import $ from "jquery";

function Chat() {
    return (
        <div className="mainBG d-flex justify-content-center p-4">
            <div className="chat-container">

                <div className="sidebar overflow-hidden bg-dark">
                    {/* <!-- Button trigger modal -->*/}
                    <div className="userSpace overflow-hidden">
                        <div className="ms-1">
                            <img className="profileCurrentChatPic"
                                 src=" https://i.postimg.cc/kGZZvf65/52-SNp-DJJCg-BVf-Ztib-Lu-XN1-Zrli-Cdvv.jpg"
                                 alt="avatar"
                                 width="50"></img>
                        </div>
                        <div className="text-white ms-2">
                            <p className="fw-bold mb-0">Elon Musk</p>
                        </div>
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
                        <li className=" p-0.5 mt-2 chat-borders">
                            <div className="d-flex justify-content-between">
                                <div className="d-flex flex-row">
                                    <div>
                                        <img className="profilePic d-flex align-self-center me-3"
                                             src="https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg"
                                             alt="avatar"  width="50"></img>
                                    </div>
                                    <div>
                                        <p className="fw-bold mb-0 ms-1 me-1">Niv Swisa</p>
                                        <p className="small ms-1">Sure!</p>
                                    </div>
                                </div>
                                <p className="small mb-1 ">09/02/2023</p>
                            </div>
                        </li>
                        <li className="p-0.5 mt-2 chat-borders ">
                            <div className="d-flex justify-content-between ">
                                <div className="d-flex flex-row">
                                    <div>
                                        <img className="profilePic" src="https://i.postimg.cc/L47zRrjW/dvd.jpg"
                                             alt="avatar" className="d-flex align-self-center me-3" width="50"></img>
                                    </div>
                                    <div>
                                        <p className="fw-bold mb-0 ms-1 me-1">Guy Reuveni</p>
                                        <p className="small ms-1">Hello World!</p>
                                    </div>
                                </div>
                                <p className="small mb-1">09/02/2023</p>
                            </div>
                        </li>
                    </ul>
                </div>

                <div className="main-chat overflow-hidden">
                    <div className="chat-header overflow-hidden">
                        {/* <!-- Contact name or chat title will be displayed here -->*/}


                        <div>
                            <img className="profileCurrentChatPic" src="https://i.postimg.cc/BvrXRGr5/IMG-3411dvdv.jpg"
                                 alt="avatar" className="d-flex align-self-center me-3" width="50"></img>
                        </div>
                        <div className="col text-white align-self-center ">
                            <p className="fw-bold mb-0 ms-1 ">Niv Swisa</p>
                        </div>
                        <div className="chat-input">
                            <button className="bg-dark">Log Out</button>
                        </div>
                    </div>
                    <div className="chat-messages">
                        <div>
                            <div className="message ">
                                <p>Hello, how are u?</p>
                            </div>
                            <div className="clearfix">
                                <div className="message userTextMessage">
                                    <p>I'm doing good, and you?</p>
                                </div>
                            </div>
                            <div className="message  ">
                                <p>Great, just about to finish my AP 2 program</p>
                            </div>
                            <div className="clearfix">
                                <div className="message userTextMessage">
                                    <p>That sounds phenomenal as Noa Kirel would say ;)</p>
                                </div>
                            </div>
                            <div className="message">
                                <p>See you next month in Italy?</p>
                            </div>
                            <div className="message userTextMessage">
                                <p>Sure!</p>
                            </div>
                        </div>
                    </div>
                    <div className="chat-input ">
                        <input type="text" className="text-white" placeholder="Type your message here..."></input>
                        <button className="bg-dark">Send</button>
                    </div>
                </div>
            </div>
            {/* <!-- Modal -->*/}
            <div className="modal fade " id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="exampleModalLabel">Add new contact</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div className="modal-body ">
                            <div className="form-group">
                                <input type="text" className="form-control" placeholder="Contact's identifier"></input>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-primary">Add</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default Chat;

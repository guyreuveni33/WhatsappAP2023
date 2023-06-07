function HeaderProfiles({margins, pictureSetting, profilePicture, textPosition, textSetting, name}) {
    return (
        <>
            <div className={margins}>
                <img className={pictureSetting}
                     src={profilePicture}
                     alt="avatar"
                     width="50"></img>
            </div>
            <div className={textPosition}>
                <p className={textSetting}>{name}</p>
            </div>
        </>
    );
}

export default HeaderProfiles;
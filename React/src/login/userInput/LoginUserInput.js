function LoginUserInput({typeText, holderText, onInput, id, onChange, onBlur}) {
    // This function handles the change event and calls the onChange
    const handleChange = (event) => {
        onChange(event.target.value);
    };
    //This call the handleNavigatePath
    const handleBlur = () => {
        onBlur();
    };

    return (
        <div className="mb-4">
            <input
                type={typeText}
                className="bg-dark text-white border-white rounded"
                placeholder={holderText}
                onChange={handleChange}
                onInput={onInput}
                onBlur={handleBlur}
                id={id}
            />
        </div>
    );
}

export default LoginUserInput;

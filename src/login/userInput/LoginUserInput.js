
function LoginUserInput({ typeText, holderText, onInput, id, onChange, onBlur }) {

    const handleChange = (event) => {
        onChange(event.target.value);
    };

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

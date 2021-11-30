// Import useState hook from react
import { useState } from "react";

// Input Password Component
export default function PasswordInput(props) {
  // Initialize a boolean state
  const [passwordShown, setPasswordShown] = useState(false);

  // Password toggle handler
  const togglePassword = () => {
    // When the handler is invoked
    // inverse the boolean state of passwordShown
    setPasswordShown(!passwordShown);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-xl-3 p-xl-2">
          {props.label && <label className="float-left">{props.label}</label>}
        </div>
        
          <div className="col-xl-6 p-xl-2">
            <input 
              name={props.name}
              className="form-control" 
              minLength="8"
              type={passwordShown ? "text" : "password"} 
              placeholder={props.placeholder}
              onChange={props.onChange}
              value={props.value}
              required  
            />
            {props.hasError && (
              <span className="invalid-feedback">{props.error}</span>
            )}
          </div>
          <div className="col-xl-3 p-xl-2">
            <button 
              className="btn btn-sm btn-outline-info" 
              onClick={togglePassword}>{passwordShown ? "Hide" : "Show"}
            </button>
          </div>
        </div>
    </div>
  );
}

PasswordInput.defaultProps = {
  onChange: () => {}
};
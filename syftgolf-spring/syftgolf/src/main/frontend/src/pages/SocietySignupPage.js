import React, {useState} from 'react';
import {connect} from "react-redux";
import {UserSignupPage} from "./UserSignupPage";
import * as authActions from "../redux/authActions";
import Input from "../components/Input";
import ButtonWithProgress from "../components/ButtonWithProgress";

export const SocietySignupPage = (props) => {

    const [form, setForm] = useState({
        name: ''

    });

    const [errors, setErrors] = useState({});
    const [pendingApiCall, setPendingApiCall] = useState(false);

    const onChange = (event) => {
        const { value, name } = event.target;

    setForm((previousForm) => {
        return {
            ...previousForm,
            [name]: value
        };
    });

    setErrors((previousErrors) => {
        return {
            ...previousErrors,
            [name]: undefined
        };
    });
};

    const onClickSignup = () => {

        const society = {
            name: form.name.toLowerCase()

        };
        setPendingApiCall(true);
        props.actions
            .postSignupSociety(society)
            .then((response) => {
                setPendingApiCall(false);
                props.history.push('/members');
            })
            .catch((apiError) => {
                if (apiError.response.data && apiError.response.data.validationErrors) {
                    setErrors(apiError.response.data.validationErrors);
                }
                setPendingApiCall(false);
            });
    };

return (
    <div className="container">
        <h1 className="text-center">Register New Society</h1>
        <div className="col-12 mb-3">
            <Input
                name="name"
                label="Society Name"
                placeholder="Society Name"
                value={form.name}
                onChange={onChange}
                hasError={errors.name && true}
                error={errors.name}
            />
        </div>
        <div className="text-center">
            <ButtonWithProgress
                onClick={onClickSignup}
                pendingApiCall={pendingApiCall}
                text="Sign Up"/>
            <hr></hr>
        </div>
    </div>
);
}


UserSignupPage.defaultProps = {
    actions: {
        postSignupSociety: () =>
            new Promise((resolve, reject) => {
                resolve({});
            })
    },
    history: {
        push: () => {}
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        actions: {
            postSignupSociety: (society) => dispatch(authActions.signupSocietyHandler(society))
        }
    };
};

export default connect(null, mapDispatchToProps)(SocietySignupPage);

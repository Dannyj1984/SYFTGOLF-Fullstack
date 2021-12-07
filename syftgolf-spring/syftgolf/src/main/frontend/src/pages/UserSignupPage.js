import React, {useState} from 'react';
import ButtonWithProgress from '../components/ButtonWithProgress';
import Input from '../components/Input';
import { connect } from 'react-redux';
import * as authActions from '../redux/authActions';

export const UserSignupPage = (props) => {

  

const [form, setForm] = useState({
    username: '',
    firstname: '',
    surname: '',
    email: '',
    mobile: '',
    handicap: '',
    cdh:'',
    homeclub: '',
    password: '',
    passwordRepeat: ''
  
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

  //Set the user details to be sent to the server
  const user = {
      username: form.username.toLowerCase(),
      firstname: form.firstname,
      surname: form.surname,
      handicap: form.handicap,
      email: form.email,
      cdh: form.cdh,
      homeclub: form.homeclub,
      mobile: form.mobile,
      password: form.password,
      society: {
        id: props.user.society.id
      }
    };
    setPendingApiCall(true);
    props.actions
      .postSignup(user)
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

  let passwordRepeatError;
  const { password, passwordRepeat } = form;
  if (password || passwordRepeat) {
    passwordRepeatError =
      password === passwordRepeat ? '' : 'Passwords do not match';
  }
  return (
    <div className="container">
      <h1 className="text-center">Register</h1>
      <div className="col-12 mb-3">
          <Input
          name="username"
          label="Username"
          placeholder="Username"
          value={form.username}
          onChange={onChange}
          hasError={errors.username && true}
          error={errors.username}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
        name="firstname"
          label="First name"
          placeholder="First name"
          value={form.firstname}
          onChange={onChange}
          hasError={errors.firstname && true}
          error={errors.firstname}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
        name="surname"
          label="Surname"
          placeholder="Surname"
          value={form.surname}
          onChange={onChange}
          hasError={errors.surname && true}
          error={errors.surname}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
          name="email"
          label="Email" 
          placeholder="Email"
          type="email"
          value={form.email}
          onChange={onChange}
          hasError={errors.email && true}
          error={errors.email}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
          name="mobile"
          label="Mobile"
          placeholder="Mobile"
          value={form.mobile}
          onChange={onChange}
          hasError={errors.mobile && true}
          error={errors.mobile}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
          name="handicap"
          label="Handicap"
          placeholder="Handicap"
          value={form.handicap}
          onChange={onChange}
          hasError={errors.handicap && true}
          error={errors.handicap}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
          name="cdh"
          label="CDH"
          placeholder="CDH"
          value={form.cdh}
          onChange={onChange}
          hasError={errors.cdh && true}
          error={errors.cdh}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
          name="homeclub"
          label="Home Club"
          placeholder="Home Club"
          value={form.homeclub}
          onChange={onChange}
          hasError={errors.homeclub && true}
          error={errors.homeclub}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
          name="password"
          label="Password"
          placeholder="Password"
          type="password"
          value={form.password}
          onChange={onChange}
          hasError={errors.password && true}
          error={errors.password}
        />
      </div>
      <div className="col-12 mb-3">
      <Input
          name="passwordRepeat"
          label="Repeat password"
          placeholder="Repeat password"
          type="password"
          value={form.passwordRepeat}
          onChange={onChange}
          hasError={errors.passwordRepeat && true}
          error={errors.passwordRepeat}
        />
      </div>
        <div className="text-center">
        <ButtonWithProgress
            onClick={onClickSignup}
            disabled={
              pendingApiCall || passwordRepeatError ? true : false
            }
            pendingApiCall={pendingApiCall}
            text="Sign Up"/>
            <hr></hr>
        </div>
    </div>
    );
  }


UserSignupPage.defaultProps = {
  actions: {
    postSignup: () =>
      new Promise((resolve, reject) => {
        resolve({});
      })
    },
    history: {
      push: () => {}
    }
  };

  const mapStateToProps = (state) => {
    return {
      user: state
    };
  };

  const mapDispatchToProps = (dispatch) => {
    return {
      actions: {
        postSignup: (user) => dispatch(authActions.signupHandler(user))
      }
    };
  };
  
  export default connect(mapStateToProps, mapDispatchToProps)(UserSignupPage);

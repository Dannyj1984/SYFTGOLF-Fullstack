import React from 'react';
import Input from '../components/Input';
import ButtonWithProgress from '../components/ButtonWithProgress';
import { connect } from 'react-redux';
import * as  authActions from '../redux/authActions';
import { Link } from 'react-router-dom';


export class LoginPage extends React.Component {
  state = {
    username: '',
    password: '',
    checkBox: false, // state of the checkbox
    apiError: undefined,
    pendingApiCall: false
  };

  onChangeUsername = (event) => {
    const value = event.target.value;
    this.setState({
      username: value,
      apiError: undefined
    });
  };

  onChangePassword = (event) => {
    const value = event.target.value;
    this.setState({
      password: value,
      apiError: undefined
    });
  };

  onClickLogin = () => {
    const body = {
      username: this.state.username.toLowerCase(),
      password: this.state.password
    };
    this.setState({ pendingApiCall: true });
    this.props.actions
    .postLogin(body)
    .then((response) => {
    this.setState({ pendingApiCall: false }, () => {
          this.props.history.push('/members');
        });
      })
      .catch((error) => {
        if (error.response) {
          this.setState({
            apiError: error.response.data.message,
            pendingApiCall: false
          });
        }
      });
  };

  render() {
    let disableSubmit = false;
    if (this.state.username === '') {
      disableSubmit = true;
    }
    if (this.state.password === '') {
      disableSubmit = true;
    }
    if (!this.state.checkBox) {
      disableSubmit = true;
    }

    return (
      <div className="container">
        <h1 className="text-center">Login</h1>
        <div className="col-12 mb-3">
          <Input
            label="Username"
            placeholder="Your username"
            value={this.state.username}
            onChange={this.onChangeUsername}
          />
        </div>
        <div className="col-12 mb-3">
          <Input
            label="Password"
            placeholder="Your password"
            type="password"
            value={this.state.password}
            onChange={this.onChangePassword}
          />
        </div>
        {this.state.apiError && (
          <div className="col-12 mb-3">
            <div className="alert alert-danger">{this.state.apiError}</div>
          </div>
        )}
        <div className="check-box ml-5">
        
          <label>Agree to <Link to="/terms"> T&Cs </Link></label>
        
          <input 
            className="ml-2" 
            type="checkbox" 
            defaultChecked={this.state.checkBox}
            onChange={() => this.setState(prevState => ({checkBox: !prevState.checkBox}))} /> {/*toggles the value of checkBox state to enable and disable button when t&c boxes checked and unchecked*/}
        </div>
        <div className="text-center">
          <ButtonWithProgress
            onClick={this.onClickLogin}
            disabled={disableSubmit || this.state.pendingApiCall}
            text="Login"
            pendingApiCall={this.state.pendingApiCall}
          />
        </div>
    </div>
   );
  }
}



LoginPage.defaultProps = {
  actions: {
    postLogin: () => new Promise((resolve, reject) => resolve({}))
  },
  dispatch: () => {}
};

const mapDispatchToProps = dispatch => {
  return{
    actions: {
      postLogin: (body) => dispatch(authActions.loginHandler(body))
    }
  };

  
};



export default connect(null, mapDispatchToProps)(LoginPage);

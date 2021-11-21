import React from 'react';
import * as apiCalls from '../api/apiCalls';
import ProfileCard from '../components/ProfileCard';
import { connect } from 'react-redux';


class UserPage extends React.Component{
    state = {
        user: undefined,
        userNotFound: false,
        isLoadingUser: false,
        inEditMode: false,
        inPasswordEditMode: false,
        originalUsername: undefined,
        originalEmail: undefined,
        originalHomeclub: undefined,
        originalMobile: undefined,
        originalHandicap: undefined,
        pendingUpdateCall: false,
        image: undefined,
        errors: {}
      };

      

      componentDidMount() {
        this.loadUser();
        }
        componentDidUpdate(prevProps) {
          if (prevProps.match.params.username !== this.props.match.params.username) {
            this.loadUser();
          }
        }

        
        //Load data for this user
        loadUser = () => {
          const username = this.props.match.params.username;
          if (!username) {
            return;
          }
          this.setState({ userNotFound: false, isLoadingUser: true });
          apiCalls
            .getUser(username)
            .then((response) => {
              this.setState({ user: response.data, isLoadingUser: false });
            })
            .catch((error) => {
              this.setState({
                userNotFound: true,
                isLoadingUser: false
              });
            });
        };

        //User edit 

    onClickEdit = () => {
      this.setState({
        inEditMode: true
      });
    };

    //Password edit
    onClickEditPassword = () => {
      this.setState({
        inPasswordEditMode: true
      });
      console.log(this.state.inPasswordEditMode);
    };
  
    onClickCancel = () => {
      const user = { ...this.state.user };
    if (this.state.originalUsername !== undefined) {
      user.username = this.state.originalUsername;
      user.handicap = this.state.originalHandicap;
      user.email = this.state.originalEmail;
      user.mobile = this.state.originalMobile;
      this.homeclub = this.state.originalHomeclub;
    }
      this.setState({
        user,
        errors: {},
        originalUsername: undefined,
        originalHandicap: undefined,
        originalEmail: undefined,
        originalMobile: undefined,
        originalHomeclub: undefined,
        inEditMode: false,
        inPasswordEditMode: false,
        image: undefined
        
      });
    };

    

    onClickSave = () => {
      const userId = this.props.loggedInUser.id;
      const userUpdate = {
        username: this.state.user.username,
        handicap: this.state.user.handicap,
        email: this.state.user.email,
        homeclub: this.state.user.homeclub,
        mobile: this.state.user.mobile,
        image: this.state.image && this.state.image.split(',')[1]
      };
      this.setState({ pendingUpdateCall: true });
    apiCalls
      .updateUser(userId, userUpdate)
      .then((response) => {
        const user = { ...this.state.user };
        user.image = response.data.image;
        this.setState(
          {
            inEditMode: false,
            inPasswordEditMode: false,
            originalUsername: undefined,
            originalHandicap: undefined,
            originalEmail: undefined,
            originalHomeclub:undefined,
            originalMobile: undefined,
            pendingUpdateCall: false,
            user,
            image: undefined
          },
          () => {
            const action = {
              type: 'update-success',
              payload: user
            };
            this.props.dispatch(action);
          }
        );
      })
      .catch((error) => {
        let errors = {};
        if (error.response.data.validationErrors) {
          errors = error.response.data.validationErrors;
        }
        this.setState({
          pendingUpdateCall: false,
          errors
        });
       });
    };
  
    onChangeUsername = (event) => {
      const user = { ...this.state.user };
      let originalUsername = this.state.originalUsername;
    if (originalUsername === undefined) {
      originalUsername = user.username;
    }
      user.username = event.target.value;
      const errors = { ...this.state.errors };
      errors.username = undefined;
      this.setState({ user, originalUsername, errors });
    };

    onChangeHandicap = (event) => {
      const user = { ...this.state.user };
      let originalHandicap = this.state.originalHandicap;
    if (originalHandicap === undefined) {
      originalHandicap = user.handicap;
    }
      user.handicap = event.target.value;
      const errors = { ...this.state.errors };
      errors.handicap = undefined;
      this.setState({ user, originalHandicap, errors });
    };

    onChangeEmail = (event) => {
      const user = { ...this.state.user };
      let originalEmail = this.state.originalEmail;
    if (originalEmail === undefined) {
      originalEmail = user.email;
    }
      user.email = event.target.value;
      const errors = { ...this.state.errors };
      errors.email = undefined;
      this.setState({ user, originalEmail, errors });
    };

    onChangeMobile = (event) => {
      const user = { ...this.state.user };
      let originalMobile = this.state.originalMobile;
    if (originalMobile === undefined) {
      originalMobile = user.mobile;
    }
      user.mobile = event.target.value;
      const errors = { ...this.state.errors };
      errors.mobile = undefined;
      this.setState({ user, originalMobile, errors });
    };

    onChangeHomeclub = (event) => {
      const user = { ...this.state.user };
      let originalHomeclub = this.state.originalHomeclub;
    if (originalHomeclub === undefined) {
      originalHomeclub = user.homeclub;
    }
      user.homeclub = event.target.value;
      const errors = { ...this.state.errors };
      errors.homeclub = undefined;
      this.setState({ user, originalHomeclub, errors });
    };

    onFileSelect = (event) => {
      if (event.target.files.length === 0) {
        //If no file exists do nothing
        return;
      }
      const errors = { ...this.state.errors };
      errors.image = undefined;
      const file = event.target.files[0];
      let reader = new FileReader();
      reader.onloadend = () => {
        this.setState({
          image: reader.result,
          errors
        });
      };
      reader.readAsDataURL(file);
    };
    
    render() {
    let pageContent;
    if (this.state.isLoadingUser) {
      pageContent = (
        <div className="d-flex">
          <div className="spinner-border text-black-50 m-auto">
            <span className="sr-only">Loading...</span>
          </div>
        </div>
      );
    } else if (this.state.userNotFound) {
      pageContent = (
          <div className="alert alert-danger text-center">
            <div className="alert-heading">
              <i className="fas fa-exclamation-triangle fa-3x" />
            </div>
            <h5>User not found</h5>
          </div>
        );
      } else {
        const isEditable =
        this.props.loggedInUser.username === this.props.match.params.username;
        pageContent = this.state.user && (
          <ProfileCard
          user={this.state.user}
          isEditable={isEditable}
          inPasswordEditMode={this.state.inPasswordEditMode}
          inEditMode={this.state.inEditMode}
          onClickEdit={this.onClickEdit}
          onClickEditPassword={this.onClickEditPassword}
          onClickCancel={this.onClickCancel}
          onClickSave={this.onClickSave}
          onChangeUsername={this.onChangeUsername}
          onChangeHandicap = {this.onChangeHandicap}
          onChangeHomeclub = {this.onChangeHomeclub}
          onChangeMobile = {this.onChangeMobile}
          onChangeEmail = {this.onChangeEmail}
          pendingUpdateCall={this.state.pendingUpdateCall}
          loadedImage={this.state.image}
          onFileSelect={this.onFileSelect}
          errors={this.state.errors}
        />
      );
      }
      return <div data-testid="userpage">{pageContent}</div>;
    }
}

UserPage.defaultProps = {
    match: {
      params: {}
    }
  };

  const mapStateToProps = (state) => {
    return {
      loggedInUser: state
    };
  };
  
  export default connect(mapStateToProps)(UserPage);

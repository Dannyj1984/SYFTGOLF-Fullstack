import React, {useEffect, useReducer} from 'react';
import * as apiCalls from '../api/apiCalls';
import ProfileCard from '../components/ProfileCard';
import { connect } from 'react-redux';
import Spinner from '../components/Spinner';


const reducer = (state, action) => {
  if (action.type === 'loading-user') {
    return {
      ...state,
      isLoadingUser: true,
      userNotFound: false,
    };
  } else if (action.type === 'load-user-success') {
    return {
      ...state,
      isLoadingUser: false,
      user: action.payload,
    };
  } else if (action.type === 'load-user-failure') {
    return {
      ...state,
      isLoadingUser: false,
      userNotFound: true,
    };
  } else if (action.type === 'cancel') {
    console.log("cancel")
    let username = state.user.username;
    let email = state.user.email;
    let homeclub = state.user.homeclub;
    let cdh = state.user.cdh;
    let mobile = state.user.mobile;
    let handicap = state.user.handicap;
    if (state.originalUsername) {
      username = state.originalUsername;
    }
    if (state.originalEmail) {
      email = state.originalEmail;
    }
    if (state.originalHomeclub) {
      homeclub = state.originalHomeclub;
    }
    if (state.originalCDH) {
      cdh = state.originalCDH;
    }
    if (state.originalMobile) {
      mobile = state.originalMobile;
    }
    if (state.originalHandicap) {
      handicap = state.originalHandicap;
    }
    return {
      ...state,
      inEditMode: false,
      inPasswordEditMode: false,
      image: undefined,
      errors: {},
      user: {
        ...state.user,
        username,
        email,
        homeclub,
        handicap,
        mobile,
        cdh
      },
      originalUsername: undefined,
      originalCDH:undefined,
      originalEmail: undefined,
      originalHandicap: undefined,
      originalHomeclub: undefined,
      originalMobile: undefined,
    };
  } else if (action.type === 'update-progress') {
    return {
      ...state,
      pendingUpdateCall: true,
    };
  } else if (action.type === 'update-success') {
    return {
      ...state,
      inEditMode: false,
      originalUsername: undefined,
      originalCDH: undefined, 
      originalHandicap: undefined,
      originalEmail: undefined,
      originalHomeclub: undefined,
      originalMobile: undefined,
      image: undefined,
      pendingUpdateCall: false,
      user: {
        ...state.user,
        image: action.payload,
      },
    };
  } else if (action.type === 'update-failure') {
    return {
      ...state,
      pendingUpdateCall: false,
      errors: action.payload,
    };
  } else if (action.type === 'update-username') {
    //set originalUsername to value in state
    let originalUsername = state.originalUsername;

    //if username is undefined then set originalUsername to value of state.user.username
    if (!originalUsername) {
      originalUsername = state.user.username;
    }
    const errors = state.errors;
    errors.username = undefined;
    console.log(state.user.username)
    return {
      ...state,
      errors,
      originalUsername,
      user: {
        ...state.user,
        username: action.payload,
      },
    };
  } else if (action.type === 'update-email') {
    let originalEmail = state.originalEmail;
    if (!originalEmail) {
      originalEmail = state.user.email;
    }
    const errors = state.errors;
    errors.email = undefined;
    return {
      ...state,
      errors,
      originalEmail,
      user: {
        ...state.user,
        email: action.payload,
      },
    };
  } else if (action.type === 'update-mobile') {
    let originalMobile = state.originalMobile;
    if (!originalMobile) {
      originalMobile = state.user.mobile;
    }
    const errors = state.errors;
    errors.mobile = undefined;
    return {
      ...state,
      errors,
      originalMobile,
      user: {
        ...state.user,
        mobile: action.payload,
      },
    };
  } else if (action.type === 'update-cdh') {
    let originalCDH = state.originalCDH;
    if (!originalCDH) {
      originalCDH = state.user.cdh;
    }
    const errors = state.errors;
    errors.cdh = undefined;
    return {
      ...state,
      errors,
      originalCDH,
      user: {
        ...state.user,
        cdh: action.payload,
      },
    };
  } else if (action.type === 'update-homeclub') {
    let originalHomeclub = state.originalHomeclub;
    if (!originalHomeclub) {
      originalHomeclub = state.user.homeclub;
    }
    const errors = state.errors;
    errors.homeclub = undefined;
    return {
      ...state,
      errors,
      originalHomeclub,
      user: {
        ...state.user,
        homeclub: action.payload,
      },
    };
  } else if (action.type === 'update-handicap') {
    let originalHandicap = state.originalHandicap;
    if (!originalHandicap) {
      originalHandicap = state.user.handicap;
    }
    const errors = state.errors;
    errors.handicap = undefined;
    return {
      ...state,
      errors,
      originalHandicap,
      user: {
        ...state.user,
        handicap: action.payload,
      },
    };
  } else if (action.type === 'select-file') {
    const errors = state.errors;
    errors.image = undefined;
    return {
      ...state,
      errors,
      image: action.payload,
    };
  } else if (action.type === 'edit-mode') {
    console.log("edit")
    return {
      ...state,
      inEditMode: true,
    };
  } else if (action.type === 'editPassword-mode') {
    console.log("edit password clicked")
    return {
      ...state,
      inPasswordEditMode: true,
    };
  }
  return state;
};

const UserPage = (props) => {
  const [state, dispatch] = useReducer(reducer, {
    user: undefined,
    userNotFound: false,
    isLoadingUser: false,
    inEditMode: false,
    inPasswordEditMode: false,
    originalUsername: undefined,
    originalEmail: undefined,
    originalHomeclub: undefined,
    originalCDH: undefined,
    originalMobile: undefined,
    originalHandicap: undefined,
    pendingUpdateCall: false,
    image: undefined,
    errors: {},
  });

  console.log(props)

  useEffect(() => {
    const loadUser = () => {
      const username = props.match.params.username;
      if (!username) {
        return;
      }
      dispatch({ type: 'loading-user' });
      apiCalls
        .getUser(username)
        .then((response) => {
          dispatch({ type: 'load-user-success', payload: response.data });
        })
        .catch((error) => {
          dispatch({ type: 'load-user-failure' });
        });
    };
    loadUser();
  }, [props.match.params.username]);

  const onClickSave = () => {
    const userId = props.loggedInUser.id;
    const userUpdate = {
      username: state.user.username,
      email: state.user.email,
      mobile: state.user.mobile,
      cdh: state.user.cdh,
      handicap: state.user.handicap,
      homeclub: state.user.homeclub,
      image: state.image && state.image.split(',')[1],
    };
    dispatch({ type: 'update-progress' });
    apiCalls
      .updateUser(userId, userUpdate)
      .then((response) => {
        dispatch({ type: 'update-success', payload: response.data.image });
        const updatedUser = { ...state.user };
        updatedUser.image = response.data.image;
        const action = {
          type: 'update-success',
          payload: updatedUser,
        };
        props.dispatch(action);
      })
      .catch((error) => {
        let errors = {};
        if (error.response.data.validationErrors) {
          errors = error.response.data.validationErrors;
        }
        dispatch({ type: 'update-failure', payload: errors });
      });
  };

  const onFileSelect = (event) => {
    if (event.target.files.length === 0) {
      return;
    }
    const file = event.target.files[0];
    let reader = new FileReader();
    reader.onloadend = () => {
      dispatch({ type: 'select-file', payload: reader.result });
    };
    reader.readAsDataURL(file);
  };

  let pageContent;
  if (state.isLoadingUser) {
    pageContent = <Spinner />;
  } else if (state.userNotFound) {
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
      props.loggedInUser.username === props.match.params.username;
    pageContent = state.user && (
      <ProfileCard
        user={state.user}
        isEditable={isEditable}
        inEditMode={state.inEditMode}
        inPasswordEditMode={state.inPasswordEditMode}
        onClickEdit={() => dispatch({ type: 'edit-mode' })}
        onClickEditPassword={() => dispatch({ type: 'editPassword-mode'})}
        onClickCancel={() => dispatch({ type: 'cancel' })}
        onClickSave={onClickSave}
        history={props.history}
        onChangeUsername={(event) =>
          dispatch({ type: 'update-username', payload: event.target.value })
        }
        onChangeEmail={(event) =>
          dispatch({ type: 'update-email', payload: event.target.value })
        }
        onChangeHandicap={(event) =>
          dispatch({ type: 'update-handicap', payload: event.target.value })
        }
        onChangeMobile={(event) =>
          dispatch({ type: 'update-mobile', payload: event.target.value })
        }
        onChangeCDH={(event) =>
          dispatch({ type: 'update-cdh', payload: event.target.value })
        }
        onChangeHomeclub={(event) =>
          dispatch({ type: 'update-homeclub', payload: event.target.value })
        }
        pendingUpdateCall={state.pendingUpdateCall}
        loadedImage={state.image}
        onFileSelect={onFileSelect}
        errors={state.errors}
      />
    );
  }

  return (
    <div data-testid="userpage">
      <div className="row">
        <div className="col">{pageContent}</div>
      </div>
    </div>
  );
};
UserPage.defaultProps = {
  match: {
    params: {},
  },
};

const mapStateToProps = (state) => {
  return {
    loggedInUser: state,
  };
};

export default connect(mapStateToProps)(UserPage);



// class UserPage extends React.Component{
//     state = {
//         user: undefined,
//         userNotFound: false,
//         isLoadingUser: false,
//         inEditMode: false,
//         inPasswordEditMode: false,
//         originalUsername: undefined,
//         originalEmail: undefined,
//         originalHomeclub: undefined,
//         originalCDH: undefined,
//         originalMobile: undefined,
//         originalHandicap: undefined,
//         pendingUpdateCall: false,
//         image: undefined,
//         errors: {}
//       };

      

//       componentDidMount() {
//         this.loadUser();
//         }
//         componentDidUpdate(prevProps) {
//           if (prevProps.match.params.username !== this.props.match.params.username) {
//             this.loadUser();
//           }
//         }

        
//         //Load data for this user
//         loadUser = () => {
//           const username = this.props.match.params.username;
//           if (!username) {
//             return;
//           }
//           this.setState({ userNotFound: false, isLoadingUser: true });
//           apiCalls
//             .getUser(username)
//             .then((response) => {
//               this.setState({ user: response.data, isLoadingUser: false });
//             })
//             .catch((error) => {
//               this.setState({
//                 userNotFound: true,
//                 isLoadingUser: false
//               });
//             });
//         };

//         //User edit 

//     onClickEdit = () => {
//       this.setState({
//         inEditMode: true
//       });
//     };

//     //Password edit
//     onClickEditPassword = () => {
//       this.setState({
//         inPasswordEditMode: true
//       });
//       console.log(this.state.inPasswordEditMode);
//     };
  
//     onClickCancel = () => {
//       const user = { ...this.state.user };
//     if (this.state.originalUsername !== undefined) {
//       user.username = this.state.originalUsername;
//       user.handicap = this.state.originalHandicap;
//       user.email = this.state.originalEmail;
//       user.mobile = this.state.originalMobile;
//       this.homeclub = this.state.originalHomeclub;
//       this.cdh = this.state.originalCDH;
//     }
//       this.setState({
//         user,
//         errors: {},
//         originalUsername: undefined,
//         originalHandicap: undefined,
//         originalEmail: undefined,
//         originalMobile: undefined,
//         originalHomeclub: undefined,
//         originalCDH: undefined,
//         inEditMode: false,
//         inPasswordEditMode: false,
//         image: undefined
        
//       });
//     };

    

//     onClickSave = () => {
//       const userId = this.props.loggedInUser.id;
//       const userUpdate = {
//         username: this.state.user.username,
//         handicap: this.state.user.handicap,
//         email: this.state.user.email,
//         homeclub: this.state.user.homeclub,
//         mobile: this.state.user.mobile,
//         cdh: this.state.user.cdh,
//         image: this.state.image && this.state.image.split(',')[1]
//       };
//       this.setState({ pendingUpdateCall: true });
//     apiCalls
//       .updateUser(userId, userUpdate)
//       .then((response) => {
//         const user = { ...this.state.user };
//         user.image = response.data.image;
//         this.setState(
//           {
//             inEditMode: false,
//             inPasswordEditMode: false,
//             originalUsername: undefined,
//             originalHandicap: undefined,
//             originalEmail: undefined,
//             originalHomeclub:undefined,
//             originalMobile: undefined,
//             originalCDH: undefined,
//             pendingUpdateCall: false,
//             user,
//             image: undefined
//           },
//           () => {
//             const action = {
//               type: 'update-success',
//               payload: user
//             };
//             this.props.dispatch(action);
//           }
//         );
//       })
//       .catch((error) => {
//         let errors = {};
//         if (error.response.data.validationErrors) {
//           errors = error.response.data.validationErrors;
//         }
//         this.setState({
//           pendingUpdateCall: false,
//           errors
//         });
//        });
//     };
  
//     onChangeUsername = (event) => {
//       const user = { ...this.state.user };
//       let originalUsername = this.state.originalUsername;
//     if (originalUsername === undefined) {
//       originalUsername = user.username;
//     }
//       user.username = event.target.value;
//       const errors = { ...this.state.errors };
//       errors.username = undefined;
//       this.setState({ user, originalUsername, errors });
//     };

//     onChangeHandicap = (event) => {
//       const user = { ...this.state.user };
//       let originalHandicap = this.state.originalHandicap;
//     if (originalHandicap === undefined) {
//       originalHandicap = user.handicap;
//     }
//       user.handicap = event.target.value;
//       const errors = { ...this.state.errors };
//       errors.handicap = undefined;
//       this.setState({ user, originalHandicap, errors });
//     };

//     onChangeEmail = (event) => {
//       const user = { ...this.state.user };
//       let originalEmail = this.state.originalEmail;
//     if (originalEmail === undefined) {
//       originalEmail = user.email;
//     }
//       user.email = event.target.value;
//       const errors = { ...this.state.errors };
//       errors.email = undefined;
//       this.setState({ user, originalEmail, errors });
//     };

//     onChangeMobile = (event) => {
//       const user = { ...this.state.user };
//       let originalMobile = this.state.originalMobile;
//     if (originalMobile === undefined) {
//       originalMobile = user.mobile;
//     }
//       user.mobile = event.target.value;
//       const errors = { ...this.state.errors };
//       errors.mobile = undefined;
//       this.setState({ user, originalMobile, errors });
//     };

//     onChangeCDH = (event) => {
//       const user = { ...this.state.user };
//       let originalCDH = this.state.originalCDH;
//     if (originalCDH === undefined) {
//       originalCDH = user.cdh;
//     }
//       user.cdh = event.target.value;
//       console.log(user.cdh);
//       const errors = { ...this.state.errors };
//       errors.cdh = undefined;
//       this.setState({ user, originalCDH, errors });
//     };

//     onChangeHomeclub = (event) => {
//       const user = { ...this.state.user };
//       let originalHomeclub = this.state.originalHomeclub;
//     if (originalHomeclub === undefined) {
//       originalHomeclub = user.homeclub;
//     }
//       user.homeclub = event.target.value;
//       const errors = { ...this.state.errors };
//       errors.homeclub = undefined;
//       this.setState({ user, originalHomeclub, errors });
//     };

//     onFileSelect = (event) => {
//       if (event.target.files.length === 0) {
//         //If no file exists do nothing
//         return;
//       }
//       const errors = { ...this.state.errors };
//       errors.image = undefined;
//       const file = event.target.files[0];
//       let reader = new FileReader();
//       reader.onloadend = () => {
//         this.setState({
//           image: reader.result,
//           errors
//         });
//       };
//       reader.readAsDataURL(file);
//     };
    
//     render() {
//     let pageContent;
//     if (this.state.isLoadingUser) {
//       pageContent = (
//         <div className="d-flex">
//           <div className="spinner-border text-black-50 m-auto">
//             <span className="sr-only">Loading...</span>
//           </div>
//         </div>
//       );
//     } else if (this.state.userNotFound) {
//       pageContent = (
//           <div className="alert alert-danger text-center">
//             <div className="alert-heading">
//               <i className="fas fa-exclamation-triangle fa-3x" />
//             </div>
//             <h5>User not found</h5>
//           </div>
//         );
//       } else {
//         const isEditable =
//         this.props.loggedInUser.username === this.props.match.params.username;
//         pageContent = this.state.user && (
//           <ProfileCard
//           user={this.state.user}
//           isEditable={isEditable}
//           inPasswordEditMode={this.state.inPasswordEditMode}
//           inEditMode={this.state.inEditMode}
//           onClickEdit={this.onClickEdit}
//           onClickEditPassword={this.onClickEditPassword}
//           onClickCancel={this.onClickCancel}
//           onClickSave={this.onClickSave}
//           onChangeUsername={this.onChangeUsername}
//           onChangeHandicap = {this.onChangeHandicap}
//           onChangeHomeclub = {this.onChangeHomeclub}
//           onChangeMobile = {this.onChangeMobile}
//           onChangeEmail = {this.onChangeEmail}
//           onChangeCDH = {this.onChangeCDH}
//           pendingUpdateCall={this.state.pendingUpdateCall}
//           loadedImage={this.state.image}
//           onFileSelect={this.onFileSelect}
//           errors={this.state.errors}
//         />
//       );
//       }
//       return <div data-testid="userpage">{pageContent}</div>;
//     }
// }

// UserPage.defaultProps = {
//     match: {
//       params: {}
//     }
//   };

//   const mapStateToProps = (state) => {
//     return {
//       loggedInUser: state
//     };
//   };
  
//   export default connect(mapStateToProps)(UserPage);

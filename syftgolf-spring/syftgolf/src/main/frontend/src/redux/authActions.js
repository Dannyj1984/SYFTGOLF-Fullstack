import * as apiCalls from '../api/apiCalls';

export const loginSuccess = (loginUserData) => {
  return {
    type: 'login-success',
    payload: loginUserData
  };
};

export const loginHandler = (credentials) => {
  return async function(dispatch) {
    const response = await apiCalls.login(credentials);
    dispatch(
      loginSuccess({
        ...response.data,

        password: credentials.password
      })
    );
    return response;
  };
};

export const signupHandler = (user) => {
  return async function(dispatch) {
    await apiCalls.signup(user);
  };
};

//course sign up
export const courseSignupHandler = (course) => {
  return async function(dispatch) {
    await apiCalls.signupCourse(course);
    return;
  };
};

//event sign up

export const eventSignupHandler = (event) => {
  return async function(dispatch) {
    await apiCalls.signupEvent(event);
    return;
  };
};

export const enterEntrantHandler = (entrant) => {
  return async function(dispatch) {
    console.log(entrant);
    await apiCalls.eventEnter(entrant);
    return;
  };
};


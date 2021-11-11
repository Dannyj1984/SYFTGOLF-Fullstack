const initialState = {
  id: 0,
  username: '',
  firstname: '',
  surname: '',
  handicap: '',
  email: '',
  mobile: '',
  homeclub: '',
  cdh: '',
  role: '',
  societyHcpReduction: '',
  societyHcp: '',
  password: '',
  wins: '',
  isLoggedIn: false 
};

export default function authReducer(state = initialState, action) {
  if (action.type === 'logout-success') {
    return { ...initialState };
  } else if (action.type === 'login-success') {
    return {
      ...action.payload,
      isLoggedIn: true
    };
  } else if (action.type === 'update-success') {
    return{
      ...state,
      username: action.payload.username,
      handicap: action.payload.handicap,
      email: action.payload.email,
      homeclub: action.payload.homeclub,
      role: action.payload.role,
      mobile: action.payload.mobile,
      image: action.payload.image
    };
  } else if (action.type === 'event-update-success') {
  }
  return state;
}

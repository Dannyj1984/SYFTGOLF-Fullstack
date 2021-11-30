import axios from 'axios';

//const url = 'https://glacial-sierra-91195.herokuapp.com';

const url = 'http://localhost:8080';

//user calls

let id = '';

export const signup = (user) => {
  return axios.post(url + '/api/1.0/management/users', user);
};

export const login = (user) => {
  return axios.post(url + '/api/1.0/login', {}, { auth: user });
};

export const setAuthorizationHeader = ({ username, password, isLoggedIn }) => {
  if (isLoggedIn) {
    axios.defaults.headers.common['Authorization'] = `Basic ${btoa(
      username + ':' + password
    )}`;
  } else {
    delete axios.defaults.headers.common['Authorization'];
  }
};

export const listUsers = (id, param = { page: 0, size: 9 }) => {
  const path = url + `/api/1.0/societyUsers/${id}?page=${param.page || 0}&size=${param.size || 9}&sort=username,asc`;
  return axios.get(path);
};

export const getUser = (username) => {
  return axios.get(url + `/api/1.0/users/${username}`);
};

export const updateUser = (userId, body) => {
  return axios.put(url + '/api/1.0/management/users/' + userId, body);
};

export const deleteMember = (userid) => {
  return axios.delete(url + '/api/1.0/management/users/delete/' + userid);
};

export const makeAdmin = (userid) => {
  return axios.put(url + '/api/1.0/management/users/admin/' + userid);
};

export const makeHcpAdmin = (userid) => {
  return axios.put(url + '/api/1.0/management/users/HcpAdmin/' + userid);
};

export const makeEventAdmin = (userid) => {
  return axios.put(url + '/api/1.0/management/users/EventAdmin/' + userid);
};

export const makeUser = (userid) => {
  return axios.put(url + '/api/1.0/management/users/User/' + userid);
};

export const updateHandicap = (userid, body) => {
  return axios.put(url + '/api/1.0/management/users/handicap/' + userid, body);
}

export const addWin = (userid) => {
  return axios.put(url + '/api/1.0/management/user/win/' + userid);
}

export const takeWin = (userid) => {
  return axios.put(url + '/api/1.0/management/user/' + userid);
}

export const changePassword = (userid, user) => {
  return axios.put(url + '/api/1.0/user/passwordChange/' + userid, user);
}


//course calls

export const signupCourse = (course) => {
  return axios.post(url + '/api/1.0/management/courses', course);
};

//Getting courses for new event page dropdown list
export const getCourses = (id) => {
  return axios.get(url + `/api/1.0/management/getCourses/` + id);
};

//Showing page of courses on course page
export const listCourses = (id, param = { page: 0, size: 9 }) => {
  //id = JSON.parse(localStorage.getItem('syft-auth')).society.id;
  const path = url + `/api/1.0/getCourses/${id}?page=${param.page || 0}&size=${param.size || 9}&sort=courseName,asc`;
  return axios.get(path);
};
  
export const getCourse = (coursename) => {
  return axios.get(url + `/api/1.0/courses/${coursename}`);
};

export const updateCourse = (courseId, body) => {
  return axios.put(url + '/api/1.0/management/courses/' + courseId, body);
};

export const deleteCourse = (courseId) => {
  return axios.delete(url + '/api/1.0/management/courses/delete/' + courseId);
};

export const addHoleDetails = (list) => {
  return axios.post(url + '/api/1.0/management/holes', list);
};

export const getCourseHoles = (courseid)=> {
  return axios.get(url + '/api/1.0/getListOfHoles/' + courseid);
}

//Event calls

export const signupEvent = (event) => {
  return axios.post(url + '/api/1.0/management/events', event);
};

export const listEvents = (id, param = { page: 0, size: 9 }) => {
  const path = url + `/api/1.0/upcomingEvents/${id}?page=${param.page || 0}&size=${param.size || 9}&sort=date,asc`;
  return axios.get(path);
};

export const listPreviousEvents = (id, param = { page: 0, size: 9 }) => {
  const path = url + `/api/1.0/previousEvents/${id}?page=${param.page || 0}&size=${param.size || 9}&sort=date,desc`;
  return axios.get(path);
};
  
export const getEvent = (eventname) => {
  return axios.get(url + `/api/1.0/events/${eventname}`);
};

export const getEvents = () => {
  return axios.get(url + `/api/1.0/management/events`);
};

export const updateEvent = (eventId, body) => {
  return axios.put(url + '/api/1.0/management/events/' + eventId, body);
};

export const updateTeeSheetCall = (eventId, body) => {
  return axios.put(url + '/api/1.0/management/events/teesheet/' + eventId, body);
};


export const deleteEvent = (eventId) => {
  return axios.delete(url + '/api/1.0/management/events/delete/' + eventId);
};

export const getCourseDetails = (eventid) => {
  return axios.get(url + '/api/1.0/management/events/courseDetails/' + eventid);
}

export const eventEnter = (entrant) => {
  return axios.post(url + '/api/1.0/management/events/entrants', entrant);
}

export const getTeesheet = (eventid) => {
  return axios.get(url + '/api/1.0/event/getTeeSheet/'+ eventid);
}

//Society calls

export const signupSociety = (society) => {
  return axios.post(url + '/api/1.0/management/society', society);
};

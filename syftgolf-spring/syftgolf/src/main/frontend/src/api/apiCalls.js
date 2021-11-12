import axios from 'axios';

//user calls

export const signup = (user) => {
  return axios.post('/api/1.0/management/users', user);
};

export const login = (user) => {
  return axios.post('/api/1.0/login', {}, { auth: user });
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

export const listUsers = (param = { page: 0, size: 9 }) => {
  const path = `/api/1.0/users?page=${param.page || 0}&size=${param.size || 9}&sort=username,asc`;
  return axios.get(path);
};

export const getUser = (username) => {
  return axios.get(`/api/1.0/users/${username}`);
};

export const updateUser = (userId, body) => {
  return axios.put('/api/1.0/management/users/' + userId, body);
};

export const deleteMember = (userid) => {
  return axios.delete('/api/1.0/management/users/delete/' + userid);
};

export const makeAdmin = (userid) => {
  return axios.put('/api/1.0/management/users/admin/' + userid);
};

export const makeHcpAdmin = (userid) => {
  return axios.put('/api/1.0/management/users/HcpAdmin/' + userid);
};

export const makeEventAdmin = (userid) => {
  return axios.put('/api/1.0/management/users/EventAdmin/' + userid);
};

export const makeUser = (userid) => {
  return axios.put('/api/1.0/management/users/User/' + userid);
};

export const updateHandicap = (userid, body) => {
  return axios.put('api/1.0/management/users/handicap/' + userid, body);
}

//course calls

export const signupCourse = (course) => {
  return axios.post('/api/1.0/management/courses', course);
};

export const getCourses = () => {
  return axios.get(`/api/1.0/management/courses`);
};

export const listCourses = (param = { page: 0, size: 9 }) => {
  const path = `/api/1.0/courses?page=${param.page || 0}&size=${param.size || 9}&sort=courseName,asc`;
  return axios.get(path);
};
  
export const getCourse = (coursename) => {
  return axios.get(`/api/1.0/courses/${coursename}`);
};

export const updateCourse = (courseId, body) => {
  return axios.put('/api/1.0/management/courses/' + courseId, body);
};

export const deleteCourse = (courseId) => {
  return axios.delete('/api/1.0/management/courses/delete/' + courseId);
};

export const addHoleDetails = (list) => {
  return axios.post('/api/1.0/management/holes', list);
};

export const getCourseHoles = (courseid)=> {
  return axios.get('/api/1.0/getListOfHoles/' + courseid);
}

//Event calls

export const signupEvent = (event) => {
  return axios.post('/api/1.0/management/events', event);
};

export const listEvents = (param = { page: 0, size: 9 }) => {
  const path = `/api/1.0/events?page=${param.page || 0}&size=${param.size || 9}&sort=eventname,asc`;
  return axios.get(path);
};
  
export const getEvent = (eventname) => {
  return axios.get(`/api/1.0/events/${eventname}`);
};

export const getEvents = () => {
  return axios.get(`/api/1.0/management/events`);
};

export const updateEvent = (eventId, body) => {
  return axios.put('/api/1.0/management/events/' + eventId, body);
};

export const deleteEvent = (eventId) => {
  return axios.delete('/api/1.0/management/events/delete/' + eventId);
};

export const getCourseDetails = (eventid) => {
  return axios.get('/api/1.0/management/events/courseDetails/' + eventid);
}

export const eventEnter = (entrant) => {
  return axios.post('/api/1.0/management/events/entrants', entrant);
}

//Society calls

export const signupSociety = (society) => {
  return axios.post('/api/1.0/management/society', society);
};

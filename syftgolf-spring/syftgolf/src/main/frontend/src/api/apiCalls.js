import axios from 'axios';
const url = 'https://glacial-sierra-91195.herokuapp.com';

//const url = 'http://localhost:8080';

//user calls

export const signup = (user) => {
  return axios.post(url + `/api/1.0/management/member`, user);
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

export const userCSV = (id) => {
  return axios.get(url + `/api/1.0/management/users/export/${id}`);
}

export const listUsers = (id, param = { page: 0, size: 9 }) => {
  const path = url + `/api/1.0/members/${id}?page=${param.page || 0}&size=${param.size || 9}&sort=username,asc`;
  return axios.get(path);
};


export const listFilteredUsers = (param = { page: 0, size: 9 }, id, nameFilter) => {
  const path = url + `/api/1.0/societyFilteredUsers/${id}?query=${nameFilter}&page=${param.page || 0}&size=${param.size || 9}&sort=username,asc`;
  return axios.get(path);
};

export const getUser = (username) => {
  return axios.get(url + `/api/1.0/member/${username}`);
};

export const updateUser = (userId, body) => {
  return axios.put(url + '/api/1.0/updateMember/' + userId, body);
};

export const deleteMember = (userid) => {
  return axios.delete(url + '/api/1.0/management/member/' + userid);
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

export const changePassword = (userid, userpassword) => {
  return axios.put(url + '/api/1.0/member/passwordChange/' + userid, userpassword);
}

export const resetWins = (societyId) => {
  return axios.put(url + '/api/1.0/management/member/resetWins/' + societyId);
}

export const resetHcpReductions = (societyId) => {
  return axios.put(url + '/api/1.0/management/member/resetHcpRed/' + societyId);
}

export const getRandomUserList = (userList) => {
  const path = url + '/api/1.0/users/random?usernames=' + userList
  return axios.get(path);
}

export const randomiseEntrants = (eventId, perTee) => {
  const path = url + '/api/1.0/event/entrants/teesheet/' + eventId + '/' + perTee;
  return axios.put(path);
}


//course calls

export const signupCourse = (societyId, course) => {
  return axios.post(url + `/api/1.0/management/course/${societyId}`, course);
};

//Getting courses for new event page dropdown list
export const getCourses = (id) => {
  return axios.get(url + `/api/1.0/getCourses/` + id);
};

//Showing page of courses on course page
export const listCourses = (id, param = { page: 0, size: 9 }) => {
  //id = JSON.parse(localStorage.getItem('syft-auth')).society.id;
  const path = url + `/api/1.0/getCourses/${id}?page=${param.page || 0}&size=${param.size || 9}&sort=courseName,asc`;
  return axios.get(path);
};

//List of filtered courses
export const listFilteredCourses = (param = { page: 0, size: 9 }, id, nameFilter) => {
  const path = url + `/api/1.0/societyFilteredCourses/${id}?query=${nameFilter}&page=${param.page || 0}&size=${param.size || 9}&sort=name,asc`;
  return axios.get(path);
};
  
export const getCourse = (courseName) => {
  const path = url + `/api/1.0/course/${courseName}`;
  return axios.get(url + `/api/1.0/course/${courseName}`);
};

export const updateCourse = (courseId, body) => {
  return axios.put(url + '/api/1.0/management/courses/' + courseId, body);
};

export const deleteCourse = (courseId) => {
  return axios.delete(url + '/api/1.0/management/courses/delete/' + courseId);
};

export const addHoleDetails = (courseId, list) => {
  return axios.post(url + `/api/1.0/management/hole/${courseId}`, list);
};

export const getCourseHoles = (courseid)=> {
  return axios.get(url + '/api/1.0/hole/' + courseid);
}

//Event calls

export const signupEvent = (event, societyId, courseId) => {
  return axios.post(url + '/api/1.0/management/event/'+societyId+'/'+courseId, event);
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
  return axios.put(url + '/api/1.0/management/event/' + eventId, body);
};

export const completeEvent = (eventId) => {
  return axios.put(url + `/api/1.0/management/complete/${eventId}`);
};

export const updateTeeSheetCall = (teeSheetId, body) => {
  return axios.put(url + '/api/1.0/management/events/teesheet/update/' + teeSheetId, body);
};

export const deleteTeeSheet = (teeSheetId) => {
  return axios.delete(url + '/api/1.0/management/teeSheet/' + teeSheetId);
};

export const deleteEvent = (eventId) => {
  return axios.delete(url + '/api/1.0/management/event/' + eventId);
};

export const getCourseDetails = (eventid) => {
  return axios.get(url + '/api/1.0/management/events/courseDetails/' + eventid);
}

export const eventEnter = (eventid, memberid) => {
  return axios.post(url + '/api/1.0/management/events/entrants/' + eventid + '/' + memberid);
}

export const getTeesheet = (eventid) => {
  return axios.get(url + '/api/1.0/teeSheets/'+ eventid);
}

export const getSingleTeesheet = (teesheetid) => {
  return axios.get(url + '/api/1.0/event/getASingleTeeSheet/'+ teesheetid);
}

export const createTeeSheet = (eventid, body) => {
  return axios.post(url + '/api/1.0/management/teeSheet/' + eventid, body);
}

//Add event entrant
export const addEntrant = (eventid, memberid) => {
  const path = url + '/api/1.0/entrants/' + eventid + '/'+ memberid;
  return axios.post(path);
}

//Get event entrants
export const getEntrants = (eventid) => {
  return axios.get(url + '/api/1.0/entrants/' + eventid);
}

//Remove entrant from event
export const removeEntrant = (eventid, memberid) => {
  const path = url + '/api/1.0/event/entrants/' + eventid + '/'+ memberid;
  return axios.delete(path);
}

//Update an entrants score
export const updateScore = (eventid, memberid, currentHole, score) => {
  const path = url + `/api/1.0/event/entrants/scorecard/${eventid}/${memberid}/${currentHole}`;
  return axios.put(path, score);
}

//Get a list of previous events for a single entrant for profile card
export const getPreviousEventsForEntrant = (memberid) => {
  return axios.get(url + '/api/1.0/event/getPreviousEventsForEntrant/' + memberid);
}

//Get a list of future events for a single entrant for profile card
export const getUpcomingEventsForEntrant = (memberid) => {
  return axios.get(url + '/api/1.0/event/getUpcomingEventsForEntrant/' + memberid);
}

//Society calls

export const signupSociety = (society) => {
  return axios.post(url + '/api/1.0/management/society', society);
};

import React, {useState, useEffect} from 'react';
import ProfileImageWithDefault from './ProfileImageWithDefault';
import { Link } from 'react-router-dom';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';
import { connect } from 'react-redux';
import * as authActions from '../redux/authActions';
import * as ApiCalls from '../api/apiCalls';
import PasswordInput from './PasswordInput';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import moment from 'moment';

const ProfileCard = (props) => {
  const { id, username, firstname, surname, handicap, email, image, mobile, cdh, homeclub, wins } = props.user
  const [errors, setErrors] = useState({});
  const [pendingApiCall, setPendingApiCall] = useState(false);

  const [form, setForm] = useState({
    password: '',
    passwordRepeat: ''
  });

  const [upcomingEvents, setUpcomingEvents] = useState([]);

  const [previousEvents, setPreviousEvents] = useState([]);

  //Change form for password change
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

  const showEditButton = props.isEditable && !props.inEditMode;

  const showEditPasswordButton = props.isEditable && !props.inPasswordEditMode;

  const [showUpcoming, setShowUpcoming] = useState(false);
  const [showPast, setShowPast] = useState(false);

  const handleShowUpcoming = () => {
    setShowUpcoming(!showUpcoming);
  }

  const handleShowPast = () => {
    setShowPast(!showPast);
  }

  //Cancel button on password change form
  const onClickCancel = () => {
    setForm({
      password: '',
      passwordRepeat: ''
    });
  };
  //Save password button clicked
  const onClickSavePassword = () => {
    const id = props.user.id;
    const userPasswordUpdate = {
        password: form.password
  
      };
      setPendingApiCall(true);
    ApiCalls
      .changePassword(id, userPasswordUpdate)
      .then((response) => {
        setPendingApiCall(false);
        if(response.data.message === "Ah Ah Ah, Dont change test passwords") {
          confirmAlert({
            title: 'Naughty Naughty',
            message: `Ah Ah Ah, don't change test passwords`,
            buttons: [
              {
                label: 'Sorry',
                onClick: () => props.history.push('/members')
                  
              }
            ]
          });
        } else{
        alert("Password changed, please log back in")
        props.history.push('/login');
        const action = {
          type: 'logout-success',
        };
        props.dispatch(action);
      }
      })
    
      .catch((apiError) => {
        if (apiError.response.data && apiError.response.data.validationErrors) {
          setErrors(apiError.response.data.validationErrors);
          if(apiError){
            alert('Please check you chosen password matches the criteria')
          }
        }
        setPendingApiCall(false);
      });
  };

  useEffect(() => {
    ApiCalls
      .getUpcomingEventsForEntrant(id)
      .then((response) => {
        setUpcomingEvents(response.data);
      },[])
      .catch((apiError) => {
        setErrors(apiError.response);
    });
    ApiCalls
      .getPreviousEventsForEntrant(id)
      .then((response) => {
        setPreviousEvents(response.data);
      },[])
      .catch((apiError) => {
        setErrors(apiError.response);
    });
  })

  //Calculate days till the event
  function diffInDays(start) {
    const last = new Date(moment());
    const first = new Date(start);

    // One day in milliseconds
    const oneDay = 1000 * 60 * 60 * 24;

    // Calculating the time difference between two dates
    const diffInTime = last.getTime() - first.getTime();

    // Calculating the no. of days between two dates
    const diffInDays = Math.round(diffInTime / oneDay);

    return diffInDays;
  }

  //Calculate days since the event
  function diffInDaysPast(end) {
    const first = new Date(moment());
    const last = new Date(end);

    // One day in milliseconds
    const oneDay = 1000 * 60 * 60 * 24;

    // Calculating the time difference between two dates
    const diffInTime = last.getTime() - first.getTime();

    // Calculating the no. of days between two dates
    const diffInDays = Math.round(diffInTime / oneDay);

    return diffInDays;
  }

  //Check passwords match when updating user password
  let passwordRepeatError;
  const { password, passwordRepeat } = form;
  if (password || passwordRepeat) {
    passwordRepeatError =
      password === passwordRepeat ? '' : 'Passwords do not match';
  }


  
  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImageWithDefault
          alt="profile"
          width="200"
          height="200"
          image= {image}
          src={props.loadedImage}
          className="rounded-circle shadow"
        />
      </div>
      <div className="card-body text-center">
      {!props.inEditMode && 
      <h4>{`${firstname} ${surname}`}</h4>}
      <h5>{`Handicap: ${handicap}`}</h5>
      <h5>{`Email: ${email}`}</h5>
      <h5>{`Mobile: ${mobile}`}</h5>
      <h5>{`CDH: ${cdh}`}</h5>
      <h5>{`Home club: ${homeclub}`}</h5>
      <h5>{`Wins: ${wins}`}</h5>
      <div>
        {!showPast &&<button className="btn btn-primary m-2" onClick={ () => {handleShowUpcoming(true)}} >{showUpcoming ? "Hide" : "Upcoming events"}</button>}
        {!showUpcoming &&<button className="btn btn-secondary" onClick={ () => {handleShowPast(true)}}>{!showPast ? "Past events" : "Hide" }</button>}
      </div>

      {showUpcoming &&
      <div className="container">
      <hr />
        <div className="row justify-content-center">
          {upcomingEvents.map((event => 
            <Link
              to={`/event/${event.eventname}`}>
              <div key={event.id} className="col p-2">
                  <div className ="card-title">
                    <div className="card-header">
                      {event.eventname} 
                    </div>
                    <div className="card-text p-4">
                    <p>Course : {event.course.courseName}</p>
                    Date : {moment(event.date).format('DD-MM-YYYY')}
                    </div>
                  </div>
                  <div className="card-footer text-muted">
                  Starts in {diffInDays(event.date)} days
                  </div>
              </div>
            </Link>
          ))}
        </div>
      </div>}

      {showPast &&
        <div className="container">
      <hr />
        <div className="row justify-content-center">
          {previousEvents.map((event => 
            <Link
              to={`/event/${event.eventname}`}>
              <div key={event.id} className="col p-2">
                  <div className ="card-title">
                    <div className="card-header">
                      {event.eventname} 
                    </div>
                    <div className="card-text p-4">
                    <p>Course : {event.course.courseName}</p>
                    <p>Date : {moment(event.date).format('DD-MM-YYYY')}</p>
                    <p>Winner : {event.winner}</p>
                    </div>
                  </div>
                  <div className="card-footer text-muted">
                  {diffInDaysPast(event.date)} ago
                  </div>
              </div>
            </Link>
          ))}
        </div>
      </div>}

        {props.inEditMode && (
          <div className="mb-2">
            <Input
              value={username}
              label={`Change username for ${username}`}
              onChange={props.onChangeUsername}
              haserror={props.errors.username && true}
              error={props.errors.username}
            />
            <Input
              value={props.user.handicap}
              label={`Change handicap for ${username}`}
              onChange={props.onChangeHandicap}
              haserror={props.errors.handicap && true}
              error={props.errors.handicap}
            />
            <Input
              value={email}
              label={`Change email for ${username}`}
              onChange={props.onChangeEmail}
              haserror={props.errors.email && true}
              error={props.errors.email}
            />
            <Input
              value={homeclub}
              label={`Change home club for ${username}`}
              onChange={props.onChangeHomeclub}
              haserror={props.errors.homeclub && true}
              error={props.errors.homeclub}
            />
            <Input
              value={mobile}
              label={`Change mobile for ${username}`}
              onChange={props.onChangeMobile}
              haserror={props.errors.mobile && true}
              error={props.errors.mobile}
            />
            <Input
              value={cdh}
              label={`Change CDH for ${username}`}
              onChange={props.onChangeCDH}
              haserror={props.errors.cdh && true}
              error={props.errors.cdh}
            />
            <input
              type="file"
              onChange={props.onFileSelect}
              haserror={props.errors.image && true}
              error={props.errors.image}
            />
          </div>
        )}

          {props.inPasswordEditMode && (
            
          <div className="mb-2">
            <div className="border">
              <p>New password should contain -</p>
              <ul>
                <li>Minimum 8 characters</li>
                <li>An upper case character, lower case character and a number</li>
              </ul>
            </div>
            <PasswordInput
              name="password"
              placeholder=" Password"
              value={form.password}
              type="password"
              onChange={onChange}
              label={`Change password`}
              haserror={errors.password && true}
              error={errors.password}
            />
            <PasswordInput
              name="passwordRepeat"
              placeholder="Repeat password"
              value={form.passwordRepeat}
              type="password"
              onChange={onChange}
              label={`Confirm password`}
            />
            </div>
          )}
        
        {showEditButton && (
          <button
          className="btn btn-outline-success m-2"
          onClick={props.onClickEdit}
        >
            <i className="fas fa-user-edit" /> Edit Details
          </button>
        )}
        {(showEditPasswordButton) && (
          <button
          className="btn btn-outline-success"
          onClick={props.onClickEditPassword}
        >
            <i className="fas fa-user-edit" /> Change Password
          </button>
        )}
        {props.inEditMode && (
          <div>
            <ButtonWithProgress
              className="btn btn-primary"
              onClick={props.onClickSave}
              text={
                <span>
                  <i className="fas fa-save" /> Save
                </span>
              }
              pendingApiCall={props.pendingUpdateCall}
              disabled={props.pendingUpdateCall}
            />
            <button
              className="btn btn-outline-secondary ml-1"
              onClick={props.onClickCancel}
              disabled={props.pendingUpdateCall}
            >
              <i className="fas fa-window-close" /> Cancel
            </button>
          </div>
        )}
        {props.inPasswordEditMode && (
          <div>
            <ButtonWithProgress
              className="btn btn-primary"
              onClick={onClickSavePassword}
              disabled={
              pendingApiCall || passwordRepeatError ? true : false
            }
              text={
                <span>
                  <i className="fas fa-save" /> Save
                </span>
              }
              pendingApiCall={props.pendingUpdateCall}
            />
            <button
              className="btn btn-outline-secondary ml-1"
              onClick={() => {onClickCancel(); props.onClickCancel()}}
              disabled={props.pendingUpdateCall}
            >
              <i className="fas fa-window-close" /> Cancel
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

ProfileCard.defaultProps = {
  actions: {
    changePassword: () =>
        new Promise((resolve, reject) => {
            resolve({});
        })
},
  errors: {},
  history: {
    push: () => {}
}
};

const mapDispatchToProps = (dispatch) => {
  return {
    actions: {
      changePassword: (id, user) => dispatch(authActions.changePassword(id, user))
    }
  };
};

export default connect(mapDispatchToProps)(ProfileCard);

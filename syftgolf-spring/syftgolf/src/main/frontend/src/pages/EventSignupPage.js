import React, {useState, useEffect} from 'react';
import ButtonWithProgress from '../components/ButtonWithProgress';
import Input from '../components/Input';
import { connect } from 'react-redux';
import * as authActions from '../redux/authActions';
import * as apiCalls from '../api/apiCalls';

export const EventSignupPage = (props) => {
    //state = Json object to add fields to
    const [form, setForm] = useState({
      eventname: '',
      eventtype: '',
      course_id: '',
      date: '',
      info: '',
      maxentrants: '',
      qualifier: '',
      cost: 0.00
    
    });

  const [errors, setErrors] = useState([]);
  const [pendingApiCall, setPendingApiCall] = useState(false);
  const [courseList, setCoursesList] = useState([]);
  const [courseSelected, setCourseSelected] = useState(false);
  const [eventTypeSelected, setEventTypeSelected] = useState(false);
  

  useEffect(() => { 
    let id = props.user.society.id;
    setPendingApiCall(true)
    apiCalls.getCourses(id)
    .then((response) => {
      setCoursesList(response.data)
      setPendingApiCall(false)
    })
    .catch(errors)
  }, [errors] );

  const onChange = (event) => {
    const { value, name } = event.target;
    if(name === "course_id"){
      setCourseSelected(true);
    }
    if(value === ""){
      setCourseSelected(false);
    }

    if(name === "eventtype"){
      setEventTypeSelected(true);
    }
    if(value === ""){
      setEventTypeSelected(false);
    }

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

      const onClickEventRegister = () => {
        const event = {
          eventname: form.eventname,
          eventtype: form.eventtype,
          date: form.date,
          info: form.info,
          maxentrants: form.maxentrants,
          currententrants: 0,
          qualifier: form.qualifier,
          cost: form.cost,
          course: {
            courseid: form.course_id.split(" ")[0]
          },
          society: {
            id: props.user.society.id
          }
      };
      
        setPendingApiCall(true);
          props.actions
            .postSignupEvent(event)
            .then((response) => {
            setPendingApiCall(false);
          props.history.push('/events');
          })
        .catch((apiError) => {
          if (apiError.response.data && apiError.response.data.validationErrors) {
            setErrors(apiError.response.data.validationErrors);
          }
          setPendingApiCall(false);
        });
      };

        return (
          <div className="container">
            <h1 className="text-center">Register Event</h1>
            <div>
            <p className="invalid-feedback">Hello</p>
            </div>
            <div className="col-12 mb-3">
                <Input
                name="eventname"
                label="Event Name"
                placeholder="Event name"
                value={form.eventname}
                onChange={onChange}
                hasError={errors.eventname && true}
                error={errors.eventname}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="date"
                label="Date"
                placeholder="Date"
                type="date"
                value={form.date}
                onChange={onChange}
                hasError={errors.date && true}
                error={errors.date}
              />
            </div>
            {/* <div className="col-12 mb-3">
            <Input
                name="eventtype"
                label="Event type"
                placeholder="Event Type"
                value={form.eventtype}
                onChange={onChange}
                hasError={errors.eventtype && true}
                error={errors.eventtype}
              />
            </div> */}
            <div className="col-12 mb-3">
            <label>Event Type</label>
              <select  name="eventtype" id="eventtype" className={`form-control ${eventTypeSelected ? "is-valid" : "is-invalid"} `} label="eventtype" placeholder="select" onChange={onChange} required>
                <option selected disabled value="">Please select</option>
                  <option>Stableford</option>
                  <option>Medal</option>
                  <option>4BBB - Stableford</option>
                  <option>4BBB - Medal</option>
                  <option>4 man team - Stableford</option>
                  <option>4 man team - Medal</option>
              </select>
              <div id="eventtypeFeedback" className="invalid-feedback">
                Please select a valid event type.
              </div>
            </div>
            <div className="col-12 mb-3">
            <Input
                name="maxentrants"
                label="Max entrants"
                placeholder="Max entrants"
                value={form.maxentrants}
                onChange={onChange}
                hasError={errors.maxentrants && true}
                error={errors.maxentrants}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="qualifier"
                label="Qualifier (true / false)"
                placeholder="Qualifier"
                value={form.qualifier}
                onChange={onChange}
                hasError={errors.qualifier && true}
                error={errors.qualifier}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="cost"
                label="Cost"
                placeholder="Cost"
                type="number"
                value={form.cost}
                onChange={onChange}
                hasError={errors.cost && true}
                error={errors.cost}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="info"
                label="Info"
                placeholder="Info"
                className="form-select"
                value={form.info}
                onChange={onChange}
                hasError={errors.info && true}
                error={errors.info}
              />
            </div>
            <div className="col-12 mb-3">
            <label>Course</label>
              <select  name="course_id" id="course_id" className={`form-control ${courseSelected ? "is-valid" : "is-invalid"} `}  label="Course" placeholder="select" onChange={onChange} required>
                <option selected disabled value="">Please select</option>
                {courseList.map((courses) => (
                  <option key={courses.courseid}> {courses.courseid} - {courses.courseName} </option>
                ))}
              </select>
              <div id="course_idFeedback" className="invalid-feedback">
                Please select a valid course.
              </div>
            </div>
            
            <div className="text-center">
              <ButtonWithProgress
                  onClick={onClickEventRegister}
                  disabled={
                    pendingApiCall
                  }
                  pendingApiCall={pendingApiCall}
                  text="Create"
                />
                  <hr></hr>
              </div>
          </div>
          );
        }
    

    EventSignupPage.defaultProps = {
        actions: {
            postSignupEvent: () =>
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
                postSignupEvent: (event) => dispatch(authActions.eventSignupHandler(event))
              }
            };
          };

    export default connect(
        mapStateToProps,
        mapDispatchToProps
      )(EventSignupPage);
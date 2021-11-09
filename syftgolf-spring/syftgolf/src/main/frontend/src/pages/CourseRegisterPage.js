import React, {useState} from 'react';
import ButtonWithProgress from '../components/ButtonWithProgress';
import Input from '../components/Input';
import { connect } from 'react-redux';
import * as authActions from '../redux/authActions';

export const CourseRegisterPage = (props) => {
    //state = Json object to add fields to
    const [form, setForm] = useState({
      courseName: '',
      postCode: '',
      par: '',
      courseRating: '',
      slopeRating: ''
    
    });

  const [errors, setErrors] = useState({});
  const [pendingApiCall, setPendingApiCall] = useState(false);

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

      const onClickCourseRegister = () => {
        const course = {
          courseName: form.courseName,
          postCode: form.postCode,
          par: form.par,
          courseRating: form.courseRating,
          slopeRating: form.slopeRating
      };
      
      setPendingApiCall(true);
    props.actions
      .postSignupCourse(course)
      .then((response) => {
        setPendingApiCall(false);
        props.history.push('/courses');
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
            <h1 className="text-center">Register Course</h1>
            <div className="col-12 mb-3">
                <Input
                name="courseName"
                label="Course Name"
                placeholder="Course name"
                value={form.courseName}
                onChange={onChange}
                hasError={errors.courseName && true}
                error={errors.courseName}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="postCode"
                label="Postcode"
                placeholder="Postcode"
                value={form.postCode}
                onChange={onChange}
                hasError={errors.postCode && true}
                error={errors.postCode}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="par"
                label="Par"
                placeholder="Par"
                type="number"
                value={form.par}
                onChange={onChange}
                hasError={errors.par && true}
                error={errors.par}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="courseRating"
                label="Course rating" 
                placeholder="Course rating"
                type="number"
                value={form.courseRating}
                onChange={onChange}
                hasError={errors.courseRating && true}
                error={errors.courseRating}
              />
            </div>
            <div className="col-12 mb-3">
            <Input
                name="slopeRating"
                label="Slope rating"
                placeholder="Slope rating"
                value={form.slopeRating}
                onChange={onChange}
                hasError={errors.slopeRating && true}
                error={errors.slopeRating}
              />
            </div>
            <div className="text-center">
              <ButtonWithProgress
                  onClick={onClickCourseRegister}
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
    

    CourseRegisterPage.defaultProps = {
        actions: {
          signupCourse: () =>
            new Promise((resolve, reject) => {
              resolve({});
            })
          },
          history: {
            push: () => {}
          }
        };

        const mapDispatchToProps = (dispatch) => {
            return {
              actions: {
                postSignupCourse: (course) => dispatch(authActions.courseSignupHandler(course))
              }
            };
          };

    export default connect(
        null,
        mapDispatchToProps
      )(CourseRegisterPage);
      

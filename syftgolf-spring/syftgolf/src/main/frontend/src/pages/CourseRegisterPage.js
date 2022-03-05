import React, {useState} from 'react';
import ButtonWithProgress from '../components/ButtonWithProgress';
import Input from '../components/Input';
import { connect } from 'react-redux';
import * as authActions from '../redux/authActions';
import * as ApiCalls from '../api/apiCalls';

export const CourseRegisterPage = (props) => {
    //state = Json object to add fields to
    const [form, setForm] = useState({
      name: '',
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
          name: form.name.trim(),
          par: form.par,
          courseRating: form.courseRating,
          slopeRating: form.slopeRating
          };
          setPendingApiCall(true);
        props.actions
          .postSignupCourse(props.user.society.id, course)
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
                name="name"
                label="Course Name"
                placeholder="Course name"
                value={form.name}
                onChange={onChange}
                hasError={errors.name && true}
                error={errors.name}
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
                type="number"
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

        const mapStateToProps = (state) => {
          return {
            user: state
          };
        };

        const mapDispatchToProps = (dispatch) => {
            return {
              actions: {
                postSignupCourse: (societyId, course) => dispatch(authActions.courseSignupHandler(societyId, course))
              }
            };
          };

    export default connect(
        mapStateToProps,
        mapDispatchToProps
      )(CourseRegisterPage);
      

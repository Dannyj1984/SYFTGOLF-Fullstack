import React from 'react';
import * as apiCalls from '../api/apiCalls';
import CourseProfileCard from '../components/CourseProfileCard';
import { connect } from 'react-redux';
import Spinner from '../components/Spinner';

class CourseInfoPage extends React.Component{
    state = {
        course: undefined,
        courseNotFound: false,
        isLoadingCourse: false,
        inEditMode: false,
        originalCoursename: undefined,
        originalPar: undefined,
        originalSlope: undefined,
        originalRating: undefined,
        pendingUpdateCall: false,
        image: undefined,
        errors: {}
      };
      componentDidMount() {
        this.loadCourse();
        }
        componentDidUpdate(prevProps) {
          if (prevProps.match.params.courseName !== this.props.match.params.courseName) {
            this.loadCourse();
          }
        }

        loadCourse = () => {
          const courseName = this.props.match.params.coursename;
          if (!courseName) {
            return;
          }
          this.setState({ courseNotFound: false, isLoadingCourse: true });
          apiCalls
            .getCourse(courseName)
            .then((response) => {
              this.setState({ course: response.data, isLoadingCourse: false });
            })
            .catch((error) => {
              this.setState({
                courseNotFound: true,
                isLoadingCourse: false
              });
            });
        };

    onClickEdit = () => {
      this.setState({
        inEditMode: true
      });
    };
  
    onClickCancel = () => {
      const course = { ...this.state.course };
    if (this.state.originalCoursename !== undefined) {
      course.courseName = this.state.originalCoursename;
      course.par = this.state.originalPar;
      course.slope = this.state.originalSlope;
      course.rating = this.state.originalRating;
      course.image = this.state.image;
    }
      this.setState({
        course,
        errors: {},
        originalCoursename: undefined,
        originalPar: undefined,
        originalSlope: undefined,
        originalRating: undefined,
        inEditMode: false,
        image: undefined
        
      });
    };

    onClickSave = () => {
      const course = { ...this.state.course };
      const courseId = course.id;
      const courseUpdate = {
        courseName: course.courseName,
        par: course.par,
        slopeRating: course.slopeRating,
        courseRating: course.courseRating,
        image: this.state.image && this.state.image.split(',')[1]
      };
      this.setState({ pendingUpdateCall: true });
    apiCalls
      .updateCourse(courseId, courseUpdate)
      .then((response) => {
        const course = { ...this.state.course };
        course.image = response.data.image;
        this.setState(
          {
            inEditMode: false,
            originalCoursename: undefined,
            originalPar: undefined,
            originalRating: undefined,
            originalSlope:undefined,
            pendingUpdateCall: false,
            course,
            image: undefined
          },
          () => {
            const action = {
              type: 'course-update-success',
              payload: course
            };
            this.props.dispatch(action);
          }
        );
      })
      .catch((error) => {
        let errors = {};
        if (error.response.data.validationErrors) {
          errors = error.response.data.validationErrors;
        }
        this.setState({
          pendingUpdateCall: false,
          errors
        });
       });
    };
  
    onChangeCoursename = (event) => {
      const course = { ...this.state.course };
      let originalCoursename = this.state.originalCoursename;
    if (originalCoursename === undefined) {
        originalCoursename = course.courseName;
    }
      course.courseName = event.target.value;
      const errors = { ...this.state.errors };
      errors.courseName = undefined;
      this.setState({ course, originalCoursename, errors });
    };

    onChangePar = (event) => {
      const course = { ...this.state.course };
      let originalPar = this.state.originalPar;
    if (originalPar === undefined) {
        originalPar = course.par;
    }
      course.par = event.target.value;
      const errors = { ...this.state.errors };
      errors.par = undefined;
      this.setState({ course, originalPar, errors });
    };

    onChangeSlope = (event) => {
      const course = { ...this.state.course };
      let originalSlope = this.state.originalSlope;
    if (originalSlope === undefined) {
        originalSlope = course.slopeRating;
    }
      course.slopeRating = event.target.value;
      const errors = { ...this.state.errors };
      errors.slopeRating = undefined;
      this.setState({ course, originalSlope, errors });
    };

    onChangeRating = (event) => {
      const course = { ...this.state.course };
      let originalRating = this.state.originalRating;
    if (originalRating === undefined) {
        originalRating = course.courseRating;
    }
      course.courseRating = event.target.value;
      const errors = { ...this.state.errors };
      errors.courseRating = undefined;
      this.setState({ course, originalRating, errors });
    };

    onFileSelect = (event) => {
      if (event.target.files.length === 0) {
        //If no file exists do nothing
        return;
      }
      const errors = { ...this.state.errors };
      errors.image = undefined;
      const file = event.target.files[0];
      let reader = new FileReader();
      reader.onloadend = () => {
        this.setState({
          image: reader.result,
          errors
        });
      };
      reader.readAsDataURL(file);
    };
    
    render() {
    let pageContent;
    if (this.state.isLoadingCourse) {
      pageContent = (
        <Spinner></Spinner>
      );
    } else if (this.state.courseNotFound) {
      pageContent = (
          <div className="alert alert-danger text-center">
            <div className="alert-heading">
              <i className="fas fa-exclamation-triangle fa-3x" />
            </div>
            <h5>Course not found</h5>
          </div>
        );
      } else {
        
        const isEditable = true
        //TODO get logged in user details and check if ADMIN
        //this.props.user.role === "ADMIN" || "EVENTADMIN";
        pageContent = this.state.course && (
          <CourseProfileCard
          course={this.state.course}
          isEditable={isEditable}
          inEditMode={this.state.inEditMode}
          onClickEdit={this.onClickEdit}
          onClickCancel={this.onClickCancel}
          onClickSave={this.onClickSave}
          onChangeCoursename={this.onChangeCoursename}
          onChangePar = {this.onChangePar}
          onChangeSlope = {this.onChangeSlope}
          onChangeRating = {this.onChangeRating}
          pendingUpdateCall={this.state.pendingUpdateCall}
          loadedImage={this.state.image}
          onFileSelect={this.onFileSelect}
          errors={this.state.errors}
        />
      );
      }
      return <div data-testid="coursepage">{pageContent}</div>;
    }
}

CourseInfoPage.defaultProps = {
    match: {
      params: {}
    }
  };

  const mapStateToProps = (state) => {
    return {
      loggedInCourse: state
    };
  };
  
  export default connect(mapStateToProps)(CourseInfoPage);  

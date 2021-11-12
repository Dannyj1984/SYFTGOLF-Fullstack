import React from 'react';
import { Link } from 'react-router-dom';
import CourseImageWithDefault from '../components/CourseImageWithDefault';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import * as apiCalls from '../api/apiCalls';

const CourseListItem = (props) => {

    const submit = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will delete this course',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.deleteCourse(props.course.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      }

     

      const userObj = localStorage.getItem('syft-auth');
      const authorityJSON = JSON.parse(userObj);

  return (
            <div className="card col-12">
                <div className="card-body">
                    <div className="col-4">
                        <CourseImageWithDefault
                            className="rounded-circle"
                            alt="profile"
                            width="32"
                            height="32"
                            image={props.course.image}
                        />                    
                    </div>
                    <div className="col-12 card-title align-self-center mb-0">
                        <h5>{props.course.courseName} </h5>
                        <p className="m-0">Par : {props.course.par}</p>
                        <p className="m-0">Slope : {props.course.slopeRating}</p>
                        <p className="m-0">Rating : {props.course.courseRating}</p>
                        <p className="m-0">Postcode : {props.course.postcode}</p>
                    </div>
                </div>

                <hr/>
                
                <div className="card-body">
                    <div className="float-left btn-group btn-group-sm">
                    <Link
                        to={`/course/${props.course.courseName}`}>
                            <button  className="btn btn-primary tooltips float-left" data-placement="left" data-toggle="tooltip" data-original-title="view"><i className="fa fa-eye"/> </button>
                    </Link>
                    </div>

                    {(authorityJSON.role === 'ADMIN' || authorityJSON.role === 'EVENTADMIN' || authorityJSON.role === 'SUPERUSER') &&

                    <div className="float-left btn-group btn-group-m pl-2">
                      <Link
                        to={{
                          pathname: `/holes`, 
                          state: {courseid: props.course.id}
                          }} >
                          <button  
                            className="btn btn-primary tooltips float-left" 
                            data-placement="left" 
                            data-toggle="tooltip" 
                            data-original-title="view"
                            ><i 
                            className="fa fa-edit">

                            </i> 
                          </button>
                      </Link>
                    </div>}

                    <div className="float-right btn-group btn-group-m">
                      {(authorityJSON.role === 'ADMIN' || authorityJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-secondary tooltips" 
                                onClick={submit} 
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                    <i className="fa fa-times"/>
                            </button>
                        }
                    </div>

                    
                </div>
            </div>
        
    
  );
};

export default CourseListItem;
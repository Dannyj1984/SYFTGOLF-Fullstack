import React, { useState, useEffect } from 'react';
import CourseImageWithDefault from './CourseImageWithDefault';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';
import { Modal, Button, Table } from "react-bootstrap";
import * as apiCalls from '../api/apiCalls';

const CourseProfileCard = (props) => {

  const { courseName, par, courseRating, image, postcode, slopeRating, id } = props.course;

  const [myDataObject, setmyDataObject] = useState({
    hole: [{
      "hole": undefined,
      "par": undefined,
      "stroke": undefined,
      "yards": undefined
    }]
  });

  useEffect(() => {
    loadData();
  }, []);

  

  const loadData = () => {
    apiCalls
      .getCourseHoles(id)
      .then((response) => {
        setmyDataObject({ ...myDataObject, hole: response.data, loadError: undefined})
      })
      .catch((error) => {
        setmyDataObject({ ...myDataObject, loadError: "Hole load failed" });
      });
  };


  const showEditButton = props.isEditable && !props.inEditMode;

  const userObj = localStorage.getItem('syft-auth');
  const authorityJSON = JSON.parse(userObj);

  //Hole modal setup

  const [showModalHoles, setShowHoles] = useState(false);

  const handleCloseHoles = () => setShowHoles(false);
  const handleShowHoles = () => setShowHoles(true);

  //get holes

  
  
  return (
    <div className="card">
      <div className="card-header text-center">
        <CourseImageWithDefault
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
      <h4>{`${courseName}`}</h4>}
      <h5>{`Par: ${par}`}</h5>
      <h5>{`Postcode: ${postcode}`}</h5>
      <h5>{`Slope: ${slopeRating}`}</h5>
      <h5>{`Rating: ${courseRating}`}</h5>
      <button 
        className="btn btn-primary"
        onClick={handleShowHoles}
        >View holes
      </button>
      <hr />
        {props.inEditMode && (  
          
          <div className="mb-2">
            <Input
              name="courseName"
              value={courseName}
              label={`Change course name for ${courseName}`}
              onChange={props.onChangeCoursename}
              hasError={props.errors.courseName && true}
              error={props.errors.courseName}
            />
            <Input
              name="par"
              value={par}
              label={`Change par for ${courseName}`}
              onChange={props.onChangePar}
              hasError={props.errors.par && true}
              error={props.errors.par}
            />
            <Input
              name="slopeRating"
              value={slopeRating}
              label={`Change slope for ${courseName}`}
              onChange={props.onChangeSlope}
              hasError={props.errors.slopeRating && true}
              error={props.errors.slope}
            />
            <Input
              name="courseRating"
              value={courseRating}
              label={`Change rating club for ${courseName}`}
              onChange={props.onChangeRating}
              hasError={props.errors.courseRating && true}
              error={props.errors.courseRating}
            />
            <input
              type="file"
              onChange={props.onFileSelect}
              haserror={props.errors.image && true}
              error={props.errors.image}
            />
          </div>
        )}
        {showEditButton && (authorityJSON.role === 'ADMIN' || authorityJSON.role === 'EVENTADMIN' || authorityJSON.role === 'SUPERUSER') && (
          <button
          className="btn btn-outline-success"
          onClick={props.onClickEdit}
        >
            <i className="fas fa-user-edit" /> Edit Course
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
      </div>

      
{/*Show holes modal*/}
      <>
        <Modal show={showModalHoles} onHide={handleCloseHoles} >
            <Modal.Header closeButton>
              <Modal.Title>Holes for {courseName}</Modal.Title>
            </Modal.Header>
              <Modal.Body>
              
                {myDataObject.hole.map((holes, index) => { 
                  const { hole, par, stroke, yards} = holes
                  return(
                  <div className="container" key={index}>
                  <Table striped bordered hover>
                      <thead>
                        <tr>
                          <th scope="col">Hole</th>
                          <th scope="col">Par</th>
                          <th scope="col">Stroke</th>
                          <th scope="col">Yards</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th scope="row">{hole}</th>
                          <td>{par}</td>
                          <td>{stroke}</td>
                          <td>{yards}</td>
                        </tr>
                      </tbody>
                    </Table>
                      <hr />
                  </div>
                  )
                })}
                
              </Modal.Body>
              <Modal.Footer>
                <Button variant="secondary" onClick={handleCloseHoles}>
                        Close
                </Button>
              </Modal.Footer>
        </Modal>
      </>
    </div>
  );
};

CourseProfileCard.defaultProps = {
  errors: {}
};

export default CourseProfileCard;

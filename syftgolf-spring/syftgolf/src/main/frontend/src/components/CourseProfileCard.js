import React, { useState, useEffect } from 'react';
import CourseImageWithDefault from './CourseImageWithDefault';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';
import { Modal, Button, Table } from "react-bootstrap";
import * as apiCalls from '../api/apiCalls';
import { connect } from 'react-redux';
import Spinner from './Spinner';

const CourseProfileCard = (props) => {

  const { name, par, courseRating, image, slopeRating, id } = props.course;

  const [pendingApiCall, setPendingApiCall] = useState(false)

  const [myDataObject, setmyDataObject] = useState({
    hole: [{
      "holeNumber": undefined,
      "par": undefined,
      "strokeIndex": undefined,
      "yards": undefined
    }],
    loadError: undefined
  });

  useEffect(() => {
    setPendingApiCall(true)
    apiCalls
      .getCourseHoles(id)
      .then((response) => {
        setPendingApiCall(false)
        setmyDataObject({ ...myDataObject, hole: response.data, loadError: undefined})
      })
      .catch((error) => {
        setPendingApiCall(false)
        setmyDataObject({ ...myDataObject, loadError: "Hole load failed" });
      });
  }, []);


  const showEditButton = props.isEditable && !props.inEditMode;

  

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
      <h4>{`${name}`}</h4>}
      <h5>{`Par: ${par}`}</h5>
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
              value={name}
              label={`Change course name for ${name}`}
              onChange={props.onChangeCoursename}
              hasError={props.errors.courseName && true}
              error={props.errors.courseName}
            />
            <Input
              name="par"
              value={par}
              label={`Change par for ${name}`}
              onChange={props.onChangePar}
              hasError={props.errors.par && true}
              error={props.errors.par}
            />
            <Input
              name="slopeRating"
              value={slopeRating}
              label={`Change slope for ${name}`}
              onChange={props.onChangeSlope}
              hasError={props.errors.slopeRating && true}
              error={props.errors.slope}
            />
            <Input
              name="courseRating"
              value={courseRating}
              label={`Change rating club for ${name}`}
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
        {showEditButton && props.loggedInUser.role === 'ADMIN' && (
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
              <Modal.Title>Holes for {name}</Modal.Title>
            </Modal.Header>
            {pendingApiCall &&
              <Modal.Body>
                <Spinner></Spinner>
              </Modal.Body>
                    }
                    {!pendingApiCall &&
                    <Modal.Body>
                 
                  <div className="container">
                  <Table striped bordered hover responsive>
                      <thead>
                        <tr>
                          <th >Hole</th>
                          <th >Par</th>
                          <th >Stroke</th>
                          <th >Yards</th>
                        </tr>
                      </thead>
                      {myDataObject.hole.map((holes, index) =>
                      <tbody key={index}>
                        <tr>
                          <td>{holes.holeNumber}</td>
                          <td>{holes.par}</td>
                          <td>{holes.strokeIndex}</td>
                          <td>{holes.yards}</td>
                        </tr>
                      </tbody>
                      )}
                    </Table>
                      <hr />
                  </div>
                
                
              </Modal.Body>}
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

const mapStateToProps = (state) => {
  return {
    loggedInUser: state
  };
};

CourseProfileCard.defaultProps = {
  errors: {}
};

export default connect(mapStateToProps)(CourseProfileCard);

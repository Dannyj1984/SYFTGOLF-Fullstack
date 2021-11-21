import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import ProfileImageWithDefault from '../components/ProfileImageWithDefault'
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import * as apiCalls from '../api/apiCalls';
import { Modal, Button } from 'react-bootstrap';
import Input from './Input';
import ButtonWithProgress from "./ButtonWithProgress";


const UserListItem = (props) => {

    //
    const [userHcpDetails, setUserHcpDetails] = useState({
        originalHandicap: props.user.handicap,
        originalSochcpred: props.user.sochcpred,
        errors: {}
    });

    //Store temp user details incase of cancellation of handicap updates
    const [tempUser] = useState({
        handicap: props.user.handicap,
        sochcpred: props.user.sochcpred
    });

    const [pendingUpdateCall, setPendingUpdateCall] = useState(false);

    //Get details of current logged in user
    const userObj = localStorage.getItem('syft-auth');
    const roleJSON = JSON.parse(userObj);

    const [showModal, setShow ] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    //Update handicap when save button pressed
    const onClickSaveHandicap = () => {

        const id = props.user.id;

        const handicapUpdate = ({
            handicap: userHcpDetails.originalHandicap,
            sochcpred: userHcpDetails.originalSochcpred
        });
        setPendingUpdateCall(true);
        apiCalls
            .updateHandicap(id, handicapUpdate)
            .then((response) => {
                setPendingUpdateCall(false);
                window.location.reload();
            })
    };

    //If cancelling after making a change to the data, then return back to previous state
    const onClickCancel = () => {
        //Set value of the data object back to the original values stored in the tempuser
        setUserHcpDetails({
            errors: {},
            originalHandicap: tempUser.handicap,
            originalSochcpred: tempUser.sochcpred
        });

        //Close modal
        handleClose();
    };

    //When handicap field is changed
    const onChangeHandicap = (event) => {
        let originalHandicap = event.target.value;
        const originalSocHcpRed = userHcpDetails.originalSochcpred
        const errors =  userHcpDetails.errors ;

        setUserHcpDetails({originalHandicap: originalHandicap, originalSochcpred: originalSocHcpRed, errors: errors })
    };

    //when handicap reduction field is changed.
    const onChangeHandicapReduction = (event) => {
        let originalSocHcpRed = event.target.value;
        const originalHandicap = userHcpDetails.originalHandicap;
        const errors = userHcpDetails.errors;

        setUserHcpDetails({originalSochcpred: originalSocHcpRed, originalHandicap: originalHandicap, errors: errors});



    }

    //Delete current member
    const submitDelete = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will delete this member',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.deleteMember(props.user.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };

    //Change current member to Admin
    const submitAdmin = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will give this member admin priviledges',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.makeAdmin(props.user.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };

    //Change current member to Handicap Admin
    const submitHcpAdmin = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will make this member a Handicap admin',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.makeHcpAdmin(props.user.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };

    //Change current member to Event Admin
    const submitEventAdmin = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will make this member an Event Admin',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.makeEventAdmin(props.user.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };
      //Change current member to a user
    const submitUser = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will make this member a general user',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.makeUser(props.user.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };

      //Add win for member
      const addWin = () => {

        confirmAlert({
          title: 'Add to this members win tally?',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.addWin(props.user.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };

      //Take win from member
      const takeWin = () => {

        confirmAlert({
          title: 'Take away a win from this members win tally?',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.takeWin(props.user.id)
                .then (response => window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };


   return (
            <div className="card col-12">
                <div className="card-body">
                    <div className="col-4">
                    <ProfileImageWithDefault
                        className="rounded-circle"
                        alt="profile"
                        width="128"
                        height="128"
                        image={props.user.image}
                    />
                    </div>
                    <div className="col-12 card-title align-self-center mb-0">
                        <h5>{props.user.firstname} {props.user.surname}</h5>
                        <p className="m-0">Society Handicap : {Number(props.user.handicap - props.user.sochcpred).toFixed(1)}</p> {/*Show society handicap to 1dp*/}
                        <p className="m-0">Home club : {props.user.homeclub}</p>
                        <p className="m-0">Role : {props.user.role}</p>
                        <p className="m-0">Wins : {props.user.wins}</p> 
                        {(roleJSON.role === 'EVENTADMIN' && <button className="btn btn-outline-success" onClick={addWin}>+</button> )} 
                        {(roleJSON.role === 'EVENTADMIN' && <button className="btn btn-outline-danger" onClick={takeWin}>-</button> )}
                    </div>
                </div>
                <div>
                    <ul className="list-group list-group-flush">
                        <li className="list-group-item"><i className="fa fa-envelope float-right"/>Email : {props.user.email}</li>
                        <li className="list-group-item"><i className="fa fa-phone float-right"/>Mobile : {props.user.mobile}</li>
                    </ul>
                </div>
                <div className="card-body">
                    <div className="float-left btn-group btn-group-sm">
                    <Link
                        /*Link to profile page*/
                        to={`/member/${props.user.username}`}>
                            <button  className="btn btn-warning tooltips float-left"
                                     data-placement="left"
                                     data-toggle="tooltip"
                                     data-original-title="view">
                                <i
                                    className="fa fa-eye"/>
                            </button>
                    </Link>
                    </div>
                    <div className="float-right btn-group btn-group-m">
                        {/*Button to delete member*/}
                    {(roleJSON.role === 'ADMIN' || roleJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-danger tooltips"  
                                onClick={submitDelete}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                <i className="fa fa-times"/>
                            </button>
                        }
                    </div>
                    {/*Button to view edit handicap modal*/}
                    <div className="float-right btn-group btn-group-m">
                    {roleJSON.role === 'HANDICAPADMIN'  && 
                            <button  
                                className="btn btn-secondary tooltips"  
                                onClick={handleShow}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Edit">
                                <i className="fa fa-edit"/>
                            </button>
                        }
                    </div>
                </div>

                {/*Modal for changing handicap*/}
                <Modal show={showModal} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>handicaps</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <p>Edit handicap for {props.user.firstname} {props.user.surname} </p>
                        <div className="mb-2">
                            <Input
                            value={userHcpDetails.originalHandicap}
                            label={`Change handicap for ${props.user.firstname}`}
                            onChange={onChangeHandicap}
                            //hasError={props.errors.handicap && true}
                            //error={props.errors.handicap}
                            />
                            <Input
                            value={userHcpDetails.originalSochcpred}
                            label={`Change handicap reduction for ${props.user.firstname}`}
                            onChange={onChangeHandicapReduction}
                            //hasError={props.errors.sochcpred && true}
                            //error={props.errors.sochcpred}
                            />
                        </div>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" className="btn-danger" onClick={onClickCancel}>Cancel</Button>
                        <ButtonWithProgress
                            className="btn btn-primary"
                            onClick={onClickSaveHandicap}
                            text={
                                <span>
                                    <i className="fas fa-save"/> Save
                                </span>
                            }
                            pendingApiCall={pendingUpdateCall}
                            disabled={pendingUpdateCall}
                        />
                        
                    </Modal.Footer>
                </Modal>

                <div className="card-body">
                    {/*Change member to admin*/}
                    <div className="float-left btn-group btn-group-m">
                    {(roleJSON.role === 'ADMIN' || roleJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-primary tooltips m-2"  
                                onClick={submitAdmin}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="admin">
                                Make ADMIN
                            </button>
                            
                        }
                    </div>
                    {/*Change member to handicap admin*/}
                    <div className="float-left btn-group btn-group-m">
                    {(roleJSON.role === 'ADMIN' || roleJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-primary tooltips m-2"  
                                onClick={submitHcpAdmin}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="hcpAdmin">
                                Make HANDICAPADMIN
                            </button>
                            
                        }
                    </div>
                    {/*Change member to event admin*/}
                    <div className="float-left btn-group btn-group-m m-2">
                    {(roleJSON.role === 'ADMIN' || roleJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-primary tooltips"  
                                onClick={submitEventAdmin}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="eventAdmin">
                                Make EVENTADMIN
                            </button>
                            
                        }
                    </div>
                    {/*Change member to user*/}
                    <div className="float-left btn-group btn-group-m m-2">
                    {(roleJSON.role === 'ADMIN' || roleJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-primary tooltips"  
                                onClick={submitUser}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="eventAdmin">
                                Make USER
                            </button>
                            
                        }
                    </div>

                    {/*Edit handicap, only for handicap admin*/}
                    <div className="float-right btn-group btn-group-m">
                    {(roleJSON.role === 'HANDICAPADMIN' || roleJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-primary tooltips"  
                                onClick={handleShow}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                <i className="fa fa-edit"/>
                            </button>
                        }
                    </div>
                </div>
            </div>
        
    
  );
};

export default UserListItem;

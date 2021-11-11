import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import ProfileImageWithDefault from '../components/ProfileImageWithDefault'
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import * as apiCalls from '../api/apiCalls';
import { Modal, Button } from 'react-bootstrap';
import Input from './Input';


const UserListItem = (props) => {

    const userObj = localStorage.getItem('syft-auth');
    const roleJSON = JSON.parse(userObj);

    const [showModal, setShow ] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

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
      }

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
      }

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
      }

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
      }

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
      }

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
                        <p className="m-0">Society Handicap : {props.user.socHcp}</p>
                        <p className="m-0">Wins : {props.user.wins}</p>
                        <p className="m-0">Home club : {props.user.homeclub}</p>
                        <p className="m-0">Role : {props.user.role}</p>
                    </div>
                </div>
                <div>
                    <ul className="list-group list-group-flush">
                        <li className="list-group-item"><i className="fa fa-envelope float-right"></i>Email : {props.user.email}</li>
                        <li className="list-group-item"><i className="fa fa-phone float-right"></i>Mobile : {props.user.mobile}</li>
                    </ul>
                </div>
                <div className="card-body">
                    <div className="float-left btn-group btn-group-sm">
                    <Link
                        to={`/member/${props.user.username}`}>
                            <button  className="btn btn-warning tooltips float-left" data-placement="left" data-toggle="tooltip" data-original-title="view"><i className="fa fa-eye"></i> </button>
                    </Link>
                    </div>
                    <div className="float-right btn-group btn-group-m">
                    {roleJSON.role === 'ADMIN'  && 
                            <button  
                                className="btn btn-danger tooltips"  
                                onClick={submitDelete}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                <i className="fa fa-times"></i>
                            </button>
                        }
                    </div>

                    <div className="float-right btn-group btn-group-m">
                    {roleJSON.role === 'HANDICAPADMIN'  && 
                            <button  
                                className="btn btn-secondary tooltips"  
                                onClick={handleShow}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                <i className="fa fa-edit"></i>
                            </button>
                        }
                    </div>
                </div>
                <Modal show={showModal} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>handicaps</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <p>Edit handicap for {props.user.firstname} {props.user.surname} </p>
                        <div className="mb-2">
                            <Input
                            value={props.user.handicap}
                            label={`Change handicap for ${props.user.firstname}`}
                            onChange={props.onChangeHandicap}
                            // hasError={props.errors.handicap && true}
                            // error={props.errors.handicap}
                            />
                            <Input
                            value={props.user.sochcpred}
                            label={`Change handicap reduction for ${props.user.firstname}`}
                            onChange={props.onChangeSocHcpRed}
                            // hasError={props.errors.sochcpred && true}
                            // error={props.errors.sochcpred}
                            />
                        </div>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" className="btn-danger" onClick={handleClose}>Cancel</Button>
                        <Button variant="secondary" className="btn-success" onClick={handleClose}>Save</Button>
                        
                    </Modal.Footer>
                </Modal>

                <div className="card-body">
                    
                    <div className="float-left btn-group btn-group-m">
                    {roleJSON.role === 'ADMIN'  && 
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

                    <div className="float-left btn-group btn-group-m">
                    {roleJSON.role === 'ADMIN'  && 
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

                    <div className="float-left btn-group btn-group-m m-2">
                    {roleJSON.role === 'ADMIN'  && 
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

                    <div className="float-left btn-group btn-group-m m-2">
                    {roleJSON.role === 'ADMIN'  && 
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

                    <div className="float-right btn-group btn-group-m">
                    {roleJSON.role === 'HANDICAPADMIN'  && 
                            <button  
                                className="btn btn-primary tooltips"  
                                onClick={handleShow}
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                <i className="fa fa-edit"></i>
                            </button>
                        }
                    </div>
                </div>
            </div>
        
    
  );
};

export default UserListItem;

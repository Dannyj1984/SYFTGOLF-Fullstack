import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import * as apiCalls from '../api/apiCalls';
import { Modal, Button } from "react-bootstrap";
import moment from 'moment';
import * as authActions from '../redux/authActions';
import { connect } from 'react-redux';

const EventListItem = (props) => {

  //Leadboard modal setup
  const [showModalLeader, setShowLeader] = useState(false);
  const [courseName, setCourseName] = useState("");

  const handleCloseLeader = () => setShowLeader(false);
  const handleShowLeader = () => setShowLeader(true);

  //Entrants modal setup

  const [showModalEntrants, setShowEntrants] = useState(false);

  const handleCloseEntrants = () => setShowEntrants(false);
  const handleShowEntrants = () => setShowEntrants(true);

  //const [errors, setErrors] = useState({});
  //const [pendingApiCall, setPendingApiCall] = useState(false);

    const deleteEvent = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will delete this event',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.deleteEvent(props.event.id)
                .then (window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      }

      //Enter currently logged in user into an event

      const enterEvent = () => {
        const event = {...props.event}
        const eventid = event.id;
        const userid = JSON.parse(localStorage.getItem('syft-auth')).id;
        const entrant = {
          user_id: userid,
          event_id: eventid
        };
        

        confirmAlert({
          title: 'Do you want to enter this event?',
          message: 'This will enter you into this event',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                props.actions.eventEnter(entrant)
                .then (window.location.reload())
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
        
        
      };

      useEffect(() => {
          apiCalls
          .getCourseDetails(props.event.id)
          .then((response) => {
            setCourseName(response.data.course);
          });
          
      });

      

      const userObj = localStorage.getItem('syft-auth');
      const authorityJSON = JSON.parse(userObj);

      //Format date from backend to be DD-MM-YYYY

      let yourDate = props.event.date;
      const formatDate = moment(yourDate).format('DD-MM-YYYY')

  return (
            <div className="card col-12">
                <div className="card-body">
                    <div className="col-12 card-title align-self-center mb-0">
                        <h5>{props.event.eventname} </h5>
                        <p className="m-0">Course: {courseName}</p>
                        <p className="m-0">Date : {formatDate}</p>
                        <p className="m-0">Event Format : {props.event.eventtype}</p>
                        <p className="m-0">Cost : £{props.event.cost}</p>
                        
                    </div>
                </div>
                <hr/>
                
                <div className="card-body">
                    <div className="float-left btn-group btn-group-sm px-2">
                      <Link
                          to={`/event/${props.event.eventname}`}>
                              <button  
                                className="btn btn-primary tooltips float-left" 
                                data-placement="left" 
                                data-toggle="tooltip" 
                                data-original-title="view"><i
    className="fa fa-eye"/>
                              </button>
                      </Link>
                    </div>
                    <div className="float-left btn-group btn-group-m px-2">
                      <button  
                          className="btn btn-primary tooltips float-left" 
                          data-placement="left" 
                          onClick={handleShowLeader}
                          data-toggle="tooltip" 
                          data-original-title="view"><i
                            className="fa fa-trophy"/>
                      </button>
                    </div>

                    <div className="float-left btn-group btn-group-m">
                      <button  
                          className="btn btn-primary tooltips float-left" 
                          data-placement="left" 
                          onClick={handleShowEntrants}
                          data-toggle="tooltip" 
                          data-original-title="view"><i
                            className="fa fa-users"/>
                      </button>
                    </div>

                    <div className="float-right btn-group btn-group-m">
                      {(authorityJSON.role === 'ADMIN' || authorityJSON.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-secondary tooltips" 
                                onClick={deleteEvent} 
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                    <i className="fa fa-times"></i>
                            </button>
                        }
                    </div>
                    
                </div>

                <div className="float-right btn-group btn-group-m">
                            <button  
                                className="btn btn-success tooltips" 
                                onClick={enterEvent} 
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                Enter
                            </button>
                    </div>

                <>
          
                  <Modal show={showModalEntrants} onHide={handleCloseEntrants}>
                    <Modal.Header closeButton>
                      <Modal.Title>Entrants for {props.event.eventname} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    <ol>
                      <li>Danny Jebb </li>
                      <li>Danny Jebb </li>
                      <li>Danny Jebb </li>
                      <li>Danny Jebb </li>
                      <li>Danny Jebb </li>
                      <li>Danny Jebb </li>
                    </ol>
                    </Modal.Body>
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleCloseEntrants}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
              </>

                <>
          
                  <Modal show={showModalLeader} onHide={handleCloseLeader}>
                    <Modal.Header closeButton>
                      <Modal.Title>Leader board for {props.event.eventname} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    <ol>
                      <li>Danny Jebb - 41 points</li>
                      <li>Danny Jebb - 41 points</li>
                      <li>Danny Jebb - 41 points</li>
                      <li>Danny Jebb - 41 points</li>
                      <li>Danny Jebb - 41 points</li>
                      <li>Danny Jebb - 41 points</li>
                      <li>Danny Jebb - 41 points</li>
                    </ol>
                    </Modal.Body>
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleCloseLeader}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
              </>
            </div>
  );
};

EventListItem.defaultProps = {
  actions: {
    enterEvent: () =>
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
          eventEnter: (entrant) => dispatch(authActions.enterEntrantHandler(entrant))
        }
      };
    };



    export default connect(null, mapDispatchToProps)(EventListItem);

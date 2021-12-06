import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import * as apiCalls from '../api/apiCalls';
import { Modal, Button, Table, NavItem } from "react-bootstrap";
import moment from 'moment';
import * as authActions from '../redux/authActions';
import { connect } from 'react-redux';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';

const EventListItem = (props) => {

  const [errors, setErrors] = useState({});
  const [pendingApiCall, setPendingApiCall] = useState(false);

  //Check if member is in this event
  const [entered, setEntered] = useState(false);
  const [entrants, setEntrants] = useState([]);
  //sorted entrants by score for leaderboard
  const [sortedEntrants, setSortedEntrants] = useState([]);
  const [showScore, setShowScore] = useState(false);
  const [score, setScore] = useState(0);
  const scoreArea = () => {
    setShowScore(true);
  }
  const updateScore = () => {
   const eventid = props.event.id;
   const id = props.loggedInUser.id;
   apiCalls
   .updateScore(eventid, id, score)
   .then((response) => {
     setShowScore(false);
     confirmAlert({
       title: 'Thanks for updating your score',
       message: 'Please see the leaderboard for other scores',
       buttons: [
         {
         label: 'OK',
         onClick: () =>
         (window.location.reload())
         }
       ]
     })
   })
  }

  //cancel score
  const cancelScore = () => {
    setShowScore(false);
  }
 

  //useEffect loading data


  //Leadboard modal setup
  const [showModalLeader, setShowLeader] = useState(false);
  const [courseName, setCourseName] = useState("");

  const handleCloseLeader = () => setShowLeader(false);
  const handleShowLeader = () => setShowLeader(true);

  //Entrants modal setup

  const [showModalEntrants, setShowEntrants] = useState(false);

  const handleCloseEntrants = () => setShowEntrants(false);
  const handleShowEntrants = () => setShowEntrants(true);


  //Tee time modal setup
  const [showTeeTime, setShowTeeTime] = useState(false);

  const handleCloseTeeTime = () => setShowTeeTime(false);
  const handleShowTeeTime = () => setShowTeeTime(true);

  //Tee times array
  const [teeTimes, setTeeTimes] = useState({
      teetime1: '',
      p1t1: '',
      p2t1: '',
      p3t1: '',
      p4t1: '',
      teetime2: '',
      p1t2: '',
      p2t2: '',
      p3t2: '',
      p4t2: '',
      teetime3: '',
      p1t3: '',
      p2t3: '',
      p3t3: '',
      p4t3: '',
      teetime4: '',
      p1t4: '',
      p2t4: '',
      p3t4: '',
      p4t4: '',
      teetime5: '',
      p1t5: '',
      p2t5: '',
      p3t5: '',
      p4t5: ''
  });

  //Edit Tee time modal setup
  const [showEditTeeTime, setShowEditTeeTime] = useState(false);

  const handleCloseEditTeeTime = () => setShowEditTeeTime(false);
  const handleShowEditTeeTime = () => {
    //Check if device is in portrait and if so, warn that using in Landscape is advisable for this task
    if(window.innerHeight > window.innerWidth){
      alert("Please use Landscape mode when editing tee times!");
  }
    setShowEditTeeTime(true);
  }

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
        const memberid = props.loggedInUser.id;
        
        //Enter event

        confirmAlert({
          title: 'Do you want to enter this event?',
          message: 'This will enter you into this event',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.addEntrant(eventid, memberid)
                .then ((response => {
                  //Confirm entry with member
                  confirmAlert({
                    title: 'You have successully entered',
                    message: 'Please ensure you get a tee time from the event organiser',
                    buttons: [
                      {
                        label: 'OK',
                        onClick: () =>  window.location.reload()
                      }
                    ]
                  });
                }))
                .catch((apiError) => {
                  //If error returned because member is already in this event, tell them this
                  if (apiError.response.status === 500) {
                    confirmAlert({
                      title: 'You are already in this event?',
                      message: 'Please speak to the event organiser if you think there is a problem',
                      buttons: [
                        {
                          label: 'ok',
                          onClick: () => ''
                        }
                      ]
                    });
                  } 
                  setPendingApiCall(false);
                })
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      };

      //Remove user from event
      const removeEntrant = () => {
        const event = {...props.event}
        const eventid = event.id;
        const memberid = props.loggedInUser.id;

        confirmAlert({
          title: 'Do you want to be removed from this event?',
          message: 'This will remove you from this event',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.removeEntrant(eventid, memberid)
                .then ((response => {
                  //Confirm entry with member
                  confirmAlert({
                    title: 'You have been successfully removed from this event',
                    message: 'Please ensure you let the organiser know',
                    buttons: [
                      {
                        label: 'OK',
                        onClick: () =>  window.location.reload()
                      }
                    ]
                  });
                }))
                .catch((apiError) => {
                  
                  setPendingApiCall(false);
                })
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
        
      };

      //update tee times

      const updateTeeSheet = () => {
        const event = {...props.event}
        const eventid = event.id;
        setPendingApiCall(true)


        apiCalls
        .updateTeeSheetCall(eventid, teeTimes)
        .then((response) => {
          window.location.reload();
        })
        setPendingApiCall(false)

      };

      //load data - get Course details of the event, and check if the logged in user has already entered the event
      useEffect(() => {
        const event = props.event;
        const eventid = event.id;
          apiCalls
          .getCourseDetails(props.event.id)
          .then((response) => {
            setCourseName(response.data.course);
          }, []);
          //Get the entrants for this event
          apiCalls
          .getEntrants(eventid)
          .then((response) => {
            //if entrants exist, check if the currently logged in user id is present for this event
            if(response.data.length < 1){
              setEntered(false);
            } else {
              setEntrants(response.data)
              setSortedEntrants(entrants.sort((a, b) => (a.score > b.score) ? -1 : 1));
              //Check if the username of logged in user is present in the array of entrants
              function userEntered(username) {
                return entrants.some(function(el) {
                  return el.username === username;
                }); 
              }
              if(userEntered(props.loggedInUser.username)) {
                setEntered(true);
              } else {
                setEntered(false);
              }

            }
          })
      }, [entrants]);

      //Get teesheet data for event when loading that modal
      const getTeesheet = () =>  {
        apiCalls
          .getTeesheet(props.event.id)
          .then((response) => {
            setTeeTimes(response.data);
          }, []);
      }

      //Data change in edit tee sheet
      const onChange = (event) => {
        const { value, name } = event.target;

        setTeeTimes((previousTeeTimes) => {
          return {
            ...previousTeeTimes,
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

      //onChange score
      const onChangeScore = (event) => {
        const { value } = event.target;
        setScore(value);
      }

      


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
                        <p className="m-0">Entries : {props.event.currententrants} / {props.event.maxentrants}</p>
                        <p className="m-0">Event Format : {props.event.eventtype}</p>
                        <p className="m-0">Cost : £{props.event.cost}</p>
                        
                    </div>
                </div>
                <hr/>
                
                <div className="card-body">
                    <div className="float-left btn-group btn-group-m px-2 col-3">
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
                    <div className="float-left btn-group btn-group-m px-2 col-3">
                      <button  
                          className="btn btn-primary tooltips float-left" 
                          data-placement="left" 
                          onClick={handleShowLeader}
                          data-toggle="tooltip" 
                          data-original-title="view"><i
                          className="fa fa-trophy"/>
                      </button>
                    </div>

                    <div className="float-left btn-group btn-group-m px-2 col-3">
                      <button  
                          className="btn btn-primary tooltips float-left" 
                          data-placement="left" 
                          onClick={handleShowEntrants}
                          data-toggle="tooltip" 
                          data-original-title="view"><i
                          className="fa fa-users"/>
                      </button>
                    </div>

                    <div className="float-left btn-group btn-group-m px-2 col-3">
                      <button  
                          className="btn btn-primary tooltips float-left" 
                          data-placement="left" 
                          onClick={()=>{ handleShowTeeTime(); getTeesheet() }}
                          data-toggle="tooltip" 
                          data-original-title="view"><i
                          className="fa fa-clock"/>
                      </button>
                    </div>
                    {entered &&
                    <div className="float-left btn-group btn-group-m p-2 col-6">
                            <button  
                                className="btn btn-primary tooltips float-left" 
                                onClick={scoreArea} 
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                Score Entry
                            </button>
                    </div>}

                    <div className="float-right btn-group btn-group-m p-2">
                      {(props.loggedInUser.role === 'ADMIN' || props.loggedInUser.role === 'SUPERUSER')  &&
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

                    {showScore &&
                    <div className="container row m-2">
                      <div className="col-4">
                        <Input 
                          name="score"
                          value={score}
                          type="number"
                          onChange={onChangeScore} 
                        />
                      </div>
                      <div className="col-3">
                        <ButtonWithProgress
                          className="btn btn-primary"
                          onClick={updateScore}
                          text="Update"
                        />
                      </div>
                      <div className="col-3">
                        <ButtonWithProgress
                          className="btn btn-danger"
                          onClick={cancelScore}
                          text="Cancel"
                        />
                      </div>
                    </div>}

                        {!entered &&
                <div className="float-right btn-group btn-group-m">
                            <button  
                                className="btn btn-success tooltips" 
                                onClick={enterEvent} 
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                Enter
                            </button>
                    </div>}

                    {entered &&
                <div className="float-right btn-group btn-group-m">
                            <button  
                                className="btn btn-success tooltips" 
                                onClick={removeEntrant} 
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Delete">
                                Remove
                            </button>
                    </div>}
                        {/*Show entrants modal*/}
                <>
                        
                  <Modal show={showModalEntrants} onHide={handleCloseEntrants}>
                    <Modal.Header closeButton>
                      <Modal.Title>Entrants for {props.event.eventname} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                      <Table striped bordered hover>
                      <thead>
                        <tr>
                          <th scope="col">Member</th>
                        </tr>
                      </thead>
                      {entrants.map((entrant => 
                        <tbody key={entrant.username}>
                          <tr>
                            <th scope="row">{entrant.firstname} {entrant.surname} ({entrant.handicap})</th>
                          </tr>
                        </tbody>
                      ))}
                      </Table>
                    </Modal.Body>
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleCloseEntrants}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
              </>
                        {/*Show leaderboard modal*/}
                <>
                        
                  <Modal 
                    show={showModalLeader} 
                    onHide={handleCloseLeader} 
                    dialogClassName="custom-modal"
                  >
                    <Modal.Header closeButton>
                      <Modal.Title>Leader board for {props.event.eventname} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    <Table striped bordered hover>
                      <thead>
                        <tr>
                          <th scope="col">Member</th>
                          <th scope="col">Score</th>
                        </tr>
                      </thead>
                      {sortedEntrants.map((entrant =>
                        <tbody key={entrant.username}>
                        <tr>
                          <th scope="row">{entrant.firstname} {entrant.surname} ({entrant.handicap})</th>
                          <th scope="row">{entrant.score}</th>
                        </tr>
                        </tbody>
                      ))}
                    </Table>
                    </Modal.Body>
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleCloseLeader}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
              </>
                        {/*Show Tee time modal*/}
              <>
                        
                  <Modal show={showTeeTime} onHide={handleCloseTeeTime} >
                    <Modal.Header>
                      <Modal.Title>Tee sheet for {props.event.eventname} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    <Table striped bordered hover >
                      <thead>
                        <tr>
                          <th scope="col">Time</th>
                          <th scope="col">Player 1</th>
                          <th scope="col">Player 2</th>
                          <th scope="col">Player 3</th>
                          {teeTimes.p4t1 !== null &&
                          <th scope="col">Player 4</th>}
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th scope="row">{teeTimes.teetime1}</th>
                          <td>{teeTimes.p1t1}</td>
                          <td>{teeTimes.p2t1}</td>
                          <td>{teeTimes.p3t1}</td>
                          <td>{teeTimes.p4t1}</td>
                        </tr>
                        {teeTimes.teetime2 !== null &&
                        <tr>
                          <th scope="row">{teeTimes.teetime2}</th>
                          <td>{teeTimes.p1t2}</td>
                          <td>{teeTimes.p2t2}</td>
                          <td>{teeTimes.p3t2}</td>
                          <td>{teeTimes.p4t2}</td>
                        </tr>}
                        {teeTimes.teetime3 !== null &&
                        <tr>
                          <th scope="row">{teeTimes.teetime3}</th>
                          <td>{teeTimes.p1t3}</td>
                          <td>{teeTimes.p2t3}</td>
                          <td>{teeTimes.p3t3}</td>
                          <td>{teeTimes.p4t3}</td>
                        </tr>}
                        {teeTimes.teetime4 !== null &&
                        <tr>
                          <th scope="row">{teeTimes.teetime4}</th>
                          <td>{teeTimes.p1t4}</td>
                          <td>{teeTimes.p2t4}</td>
                          <td>{teeTimes.p3t4}</td>
                          <td>{teeTimes.p4t4}</td>
                        </tr>}
                        {teeTimes.teetime5 !== null &&
                        <tr>
                          <th scope="row">{teeTimes.teetime5}</th>
                          <td>{teeTimes.p1t5}</td>
                          <td>{teeTimes.p2t5}</td>
                          <td>{teeTimes.p3t5}</td>
                          <td>{teeTimes.p4t5}</td>
                        </tr>}
                      </tbody>
                    </Table>
                    </Modal.Body>
                    <Modal.Footer>
                    {(props.loggedInUser.role === 'ADMIN' || props.loggedInUser.role === 'EVENTADMIN' || props.loggedInUser.role === 'SUPERUSER') &&
                      <Button className="btn btn-primary" onClick={handleShowEditTeeTime}> Edit Times </Button>}
                      <Button variant="secondary" onClick={handleCloseTeeTime}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
              </>

                        {/*Show Edit tee time modal*/}
              <>
                        
                        <Modal show={showEditTeeTime} onHide={handleCloseEditTeeTime} dialogClassName="modal-content-full modal-dialog-full" >
                          <Modal.Header closeButton>
                            <Modal.Title>Edit Tee times for {props.event.eventname} on {formatDate}</Modal.Title>
                          </Modal.Header>
                          <Modal.Body>
                          <Table striped bordered hover>
                          <thead>
                            <tr>
                              <th scope="col">Time</th>
                              <th scope="col">Player 1</th>
                              <th scope="col">Player 2</th>
                              <th scope="col">Player 3</th>
                              {teeTimes.p4t1 !== '' &&
                              <th scope="col">Player 4</th>}
                            </tr>
                          </thead>
                            <tbody>
                              <tr>
                                <th scope="row">
                                  <Input
                                  name="teetime1"
                                  placeholder="tee time 1"
                                  value={teeTimes.teetime1}
                                  onChange={onChange} />
                                </th>
                                <td>
                                  <Input 
                                    name="p1t1"
                                    placeholder="Player 1"
                                    value={teeTimes.p1t1}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                  <Input 
                                      name="p2t1"
                                      placeholder="Player 2"
                                      value={teeTimes.p2t1}
                                      onChange={onChange}  
                                    />
                                </td>
                                <td>
                                  <Input 
                                      name="p3t1"
                                      placeholder="Player 3"
                                      value={teeTimes.p3t1}
                                      onChange={onChange}  
                                    />
                                </td>
                                <td>
                                  <Input 
                                      name="p4t1"
                                      placeholder="Player 4"
                                      value={teeTimes.p4t1}
                                      onChange={onChange}  
                                    />
                                </td>
                              </tr>
                              <tr>
                                <th scope="row">
                                <Input 
                                    name="teetime2"
                                    placeholder="tee time 2"
                                    value={teeTimes.teetime2}
                                    onChange={onChange}  
                                  />
                                </th>
                                <td>
                                <Input 
                                    name="p1t2"
                                    placeholder="Player 1"
                                    value={teeTimes.p1t2}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p2t2"
                                    placeholder="Player 2"
                                    value={teeTimes.p2t2}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p3t2"
                                    placeholder="Player 3"
                                    value={teeTimes.p3t2}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p4t2"
                                    placeholder="Player 4"
                                    value={teeTimes.p4t2}
                                    onChange={onChange}  
                                  />
                                </td>
                              </tr>
                              <tr>
                                <th scope="row">
                                <Input 
                                    name="teetime3"
                                    placeholder="tee time 3"
                                    value={teeTimes.teetime3}
                                    onChange={onChange}  
                                  />
                                </th>
                                <td>
                                <Input 
                                    name="p1t3"
                                    placeholder="Player 1"
                                    value={teeTimes.p1t3}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p2t3"
                                    placeholder="Player 2"
                                    value={teeTimes.p2t3}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p3t3"
                                    placeholder="Player 3"
                                    value={teeTimes.p3t3}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p4t3"
                                    placeholder="Player 4"
                                    value={teeTimes.p4t3}
                                    onChange={onChange}  
                                  />
                                </td>
                                </tr>
                                <tr>
                                <th scope="row">
                                <Input 
                                    name="teetime4"
                                    placeholder="tee time 4"
                                    value={teeTimes.teetime4}
                                    onChange={onChange}  
                                  />
                                </th>
                                <td>
                                <Input 
                                    name="p1t4"
                                    placeholder="Player 1"
                                    value={teeTimes.p1t4}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p2t4"
                                    placeholder="Player 2"
                                    value={teeTimes.p2t4}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p3t4"
                                    placeholder="Player 3"
                                    value={teeTimes.p3t4}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p4t4"
                                    placeholder="Player 4"
                                    value={teeTimes.p4t4}
                                    onChange={onChange}  
                                  />
                                </td>
                              </tr>
                              <tr>
                                <th scope="row">
                                <Input 
                                    name="teetime5"
                                    placeholder="tee time 5"
                                    value={teeTimes.teetime5}
                                    onChange={onChange}  
                                  />
                                </th>
                                <td>
                                <Input 
                                    name="p1t5"
                                    placeholder="Player 1"
                                    value={teeTimes.p1t5}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p2t5"
                                    placeholder="Player 2"
                                    value={teeTimes.p2t5}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p3t5"
                                    placeholder="Player 3"
                                    value={teeTimes.p3t5}
                                    onChange={onChange}  
                                  />
                                </td>
                                <td>
                                <Input 
                                    name="p4t5"
                                    placeholder="Player 4"
                                    value={teeTimes.p4t5}
                                    onChange={onChange}  
                                  />
                                </td>
                              </tr>
                            </tbody>
                          </Table>
                          </Modal.Body>
                          <Modal.Footer>
                            <Button variant="success" disabled={pendingApiCall} onClick={updateTeeSheet}>Update</Button>
                            <Button variant="secondary" onClick={handleCloseEditTeeTime}>
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

  const mapStateToProps = (state) => {
    return {
      loggedInUser: state
    };
  };

  const mapDispatchToProps = (dispatch) => {
      return {
        actions: {
          eventEnter: (eventid, memberid) => dispatch(authActions.enterEntrantHandler(eventid, memberid))
        }
      };
    };



    export default connect(mapStateToProps, mapDispatchToProps)(EventListItem);

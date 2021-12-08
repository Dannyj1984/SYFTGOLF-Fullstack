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
  const [editErrors, setEditErrors] = useState();
  const [editConfirm, setEditConfirm] = useState();
  const [deleteErrors, setDeleteErrors] = useState();

  const [pendingApiCall, setPendingApiCall] = useState(false);

  const [loadTeeSheetError, setLoadTeeSheetError] = useState('')

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
  const handleShowTeeTime = () => {
      //Check if device is in portrait and if so, warn that using in Landscape is advisable for this task
    if(window.innerHeight > window.innerWidth){
        alert("Please use Landscape mode when viewing tee times!");
    }
    setShowTeeTime(true);
}

//Add tee time modal setup
const [showAddTeeTime, setShowAddTeeTime] = useState(false);

  const handleCloseAddTeeTime = () => setShowAddTeeTime(false);
  const handleShowAddTeeTime = () => {
      //Check if device is in portrait and if so, warn that using in Landscape is advisable for this task
    if(window.innerHeight > window.innerWidth){
        alert("Please use Landscape mode when viewing tee times!");
    }
    setShowAddTeeTime(true);
}

//New tee time inputs
const [newTeeTime, setNewTeeTime] = useState({
    
        addnewTeeTime: '',
        addplayer1: '',
        addplayer2: '',
        addplayer3: '',
        addplayer4: ''
    
})

//Edit tee time inputs
  const [editTeeTime, setEditTeeTime] = useState({
      id: '',
      teetime: '',
      player1: '',
      player2: '',
      player3: '',
      player4: ''
      }
  )

  //Tee times array
  const [teeTimes, setTeeTimes] = useState([]);

  //Edit Tee time modal setup
  const [showEditTeeTime, setShowEditTeeTime] = useState(false);
  

  const handleCloseEditTeeTime = () => setShowEditTeeTime(false);

  const handleShowEditTeeTime = (teesheetid) => {
    //Check if device is in portrait and if so, warn that using in Landscape is advisable for this task
    if(window.innerHeight > window.innerWidth){
      alert("Please use Landscape mode when editing tee times!");
  }
  apiCalls
  .getSingleTeesheet(teesheetid)
  .then((response) =>{
      setEditTeeTime(response.data)
  }
  )
  .catch((apiError) => {
    setPendingApiCall(false);
  });

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
                .then ((response) => {
                    window.location.reload();
                })
                .catch((apiError) => {
                    if (apiError.response.data && apiError.response.data.validationErrors) {
                      setDeleteErrors(apiError.response.data.validationErrors);
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

      //Delete a teesheet
      const deleteTeeSheet = (teesheetid) => {
          const teeSheetId = teesheetid;
          handleCloseTeeTime();

          confirmAlert({
            title: 'Do you want to delete this Teetime?',
            message: 'This will remove this teesheet from this event!',
            buttons: [
              {
                label: 'Yes',
                onClick: () => 
                  apiCalls.deleteTeeSheet(teeSheetId)
                  .then ((response => {
                    //Confirm entry with member
                    confirmAlert({
                      title: 'You have successully deleted this teesheet',
                      message: 'Please ensure you let the members know',
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
                onClick: () => {
                    handleShowTeeTime();
                }
              }
            ]
          });

      }

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

      //update tee times, if success, show confirm message and reload window after 2 seconds
      //If fail, show error message

      const onClickUpdateTee = (teesheetid) => {
        const teeSheetId = teesheetid
        const teeSheetUpdate = {
            teetime: editTeeTime.teetime,
            player1: editTeeTime.player1,
            player2: editTeeTime.player2,
            player3: editTeeTime.player3,
            player4: editTeeTime.player4
        }
        setPendingApiCall(true)
        apiCalls
        .updateTeeSheetCall(teeSheetId, teeSheetUpdate)
        .then((response) => {
            setPendingApiCall(false);
            setEditConfirm(response.data.message)
            setTimeout(() => window.location.reload(), 2000)
        })
        .catch((apiError) => {
            if (apiError.response.data && apiError.response.data.validationErrors) {
              setEditErrors(apiError.response.data.validationErrors);
            }
            setPendingApiCall(false);
          });

      };

      //create new tee sheet
      const addTeeTime = () => {
          const eventid = props.event.id
          const newTeeSheet = {
              teetime: newTeeTime.addnewTeeTime,
              player1: newTeeTime.addplayer1,
              player2: newTeeTime.addplayer2,
              player3: newTeeTime.addplayer3,
              player4: newTeeTime.addplayer4
          }
          setPendingApiCall(true)
          apiCalls
          .createTeeSheet(eventid, newTeeSheet)
          .then((response)=> {
              setPendingApiCall(false)
              window.location.reload();
              setNewTeeTime({});
              handleCloseAddTeeTime();
          })
          .catch((apiError) => {
            if (apiError.response.data && apiError.response.data.validationErrors) {
              setErrors(apiError.response.data.validationErrors);
            }
            setPendingApiCall(false);
          });
      }

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
            if(Object.entries(teeTimes).length === 0) {
                setLoadTeeSheetError('No Tee sheet is setup for this event, please create one');
              }
          },
           []);
      }

      //Create a new teesheet
      

      //Data change in edit tee sheet
      const onChange = (event) => {
        const { value, name } = event.target;

        setNewTeeTime((previousNewTeeTime) => {
          return {
            ...previousNewTeeTime,
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

      //onChange edit fields
      const onChangeEdit = (event, teesheetid) => {
        const { value, name } = event.target;
        
        setEditTeeTime((previousEditTeeTime) => {
            return {
              ...previousEditTeeTime,
              [name]: value
            };
          });
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
                          <Modal 
                            show={showTeeTime} 
                            onHide={handleCloseTeeTime} 
                            dialogClassName="modal-content-full modal-dialog-full"
                          >
                            <Modal.Header closeButton>
                              <Modal.Title>Tee times for {props.event.eventname} on {formatDate}</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                            <Table striped bordered hover>
                              <thead>
                                <tr>
                                  <th scope="col">Tee time</th>
                                  <th scope="col">Player 1</th>
                                  <th scope="col">Player 2</th>
                                  <th scope="col">Player 3</th>
                                  <th scope="col">Player 4</th>
                                </tr>
                              </thead>
                              
                              {teeTimes.map((teetime => 
                                <tbody key={teetime.id}>
                                    <tr>
                                    <th scope="col">{teetime.teetime}</th>
                                    <th scope="col">{teetime.player1}</th>
                                    <th scope="col">{teetime.player2}</th>
                                    <th scope="col">{teetime.player3}</th>
                                    <th scope="col">{teetime.player4}</th>
                                    <th scope="col">
                                        <button className="btn btn-danger m-2" onClick={() => deleteTeeSheet(teetime.id)}>delete</button>
                                        <button className="btn btn-warning m-2" onClick={() => handleShowEditTeeTime(teetime.id)}>Update</button>
                                    </th>
                                    </tr>
                                </tbody>
                              ))}   
                            </Table>
                            </Modal.Body>
                            <Modal.Footer>
                              {(props.loggedInUser.role === 'ADMIN' || props.loggedInUser.role === 'EVENTADMIN' || props.loggedInUser.role === 'SUPERUSER') &&
                              <Button variant="success" disabled={pendingApiCall} onClick={handleShowAddTeeTime}>Add New Tee Time</Button>}
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
                                  <th scope="col">Tee teetime</th>
                                  <th scope="col">Player 1</th>
                                  <th scope="col">Player 2</th>
                                  <th scope="col">Player 3</th>
                                  <th scope="col">Player 4</th>
                                </tr>
                            </thead>
                                <tbody>
                                    <tr>
                                    <th scope="col"><Input name="teetime" value={editTeeTime.teetime} onChange={onChangeEdit} /></th>
                                    <th scope="col"><Input name="player1" value={editTeeTime.player1} onChange={onChangeEdit} /></th>
                                    <th scope="col"><Input name="player2" value={editTeeTime.player2} onChange={onChangeEdit} /></th>
                                    <th scope="col"><Input name="player3" value={editTeeTime.player3} onChange={onChangeEdit} /></th>
                                    <th scope="col"><Input name="player4" value={editTeeTime.player4} onChange={onChangeEdit} /></th>
                                    <th scope="col">
                                        <ButtonWithProgress
                                            onClick={() => onClickUpdateTee(editTeeTime.id)} 
                                            className="btn btn-warning m-2"
                                            disabled={pendingApiCall}
                                            pendingApiCall={pendingApiCall}
                                            text="Update"
                                        />
                                    </th>
                                    </tr>
                                </tbody>
                            
                          </Table>
                          {editConfirm &&
                          <div className="text-centre text-success">
                              <span>{editConfirm}</span>
                          </div>}
                          {editErrors &&
                          <div className="text-centre text-danger">
                              <span>{editErrors}</span>
                          </div>}
                          </Modal.Body>
                          <Modal.Footer>
                            <Button variant="secondary" onClick={handleCloseEditTeeTime}>
                              Close
                            </Button>
                          </Modal.Footer>
                        </Modal>
                    </>

                    {/* Add tee time modal */}
                    <>
                        
                        <Modal show={showAddTeeTime} onHide={handleCloseAddTeeTime} dialogClassName="modal-content-full modal-dialog-full" >
                          <Modal.Header closeButton>
                            <Modal.Title>Add Tee time for {props.event.eventname} on {formatDate}</Modal.Title>
                          </Modal.Header>
                          <Modal.Body>
                          <Table striped bordered hover>
                          <thead>
                            <tr>
                              <th scope="col">Time</th>
                              <th scope="col">Player 1</th>
                              <th scope="col">Player 2</th>
                              <th scope="col">Player 3</th>
                              <th scope="col">Player 4</th>
                            </tr>
                          </thead>
                          <tbody>
                              <tr>
                                  <th scope="row">
                                      <Input
                                        name="addnewTeeTime"
                                        placeholder="Tee time"
                                        value={newTeeTime.addnewTeeTime}
                                        onChange={onChange} 
                                      />
                                  </th>
                                  <th scope="row">
                                      <Input
                                        name="addplayer1"
                                        placeholder="Player 1"
                                        value={newTeeTime.addplayer1}
                                        onChange={onChange} 
                                      />
                                  </th>
                                  <th scope="row">
                                      <Input
                                        name="addplayer2"
                                        placeholder="Player 2"
                                        value={newTeeTime.addplayer2}
                                        onChange={onChange} 
                                      />
                                  </th>
                                  <th scope="row">
                                      <Input
                                        name="addplayer3"
                                        placeholder="Player 3"
                                        value={newTeeTime.addplayer3}
                                        onChange={onChange} 
                                      />
                                  </th>
                                  <th scope="row">
                                      <Input
                                        name="addplayer4"
                                        placeholder="Player 4"
                                        value={newTeeTime.addplayer4}
                                        onChange={onChange} 
                                      />
                                  </th>
                              </tr>
                          </tbody>
                          </Table>
                          </Modal.Body>
                          <Modal.Footer>
                            <Button variant="success" disabled={pendingApiCall} onClick={addTeeTime}>Add</Button>
                            <Button variant="secondary" onClick={handleCloseAddTeeTime}>
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

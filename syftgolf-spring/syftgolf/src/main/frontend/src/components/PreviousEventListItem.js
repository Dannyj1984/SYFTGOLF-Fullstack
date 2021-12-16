import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import * as apiCalls from '../api/apiCalls';
import { Modal, Button, Table } from "react-bootstrap";
import moment from 'moment';
import * as authActions from '../redux/authActions';
import { connect } from 'react-redux';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';

const PreviousEventListItem = (props) => {

  //initial setup
  const [courseName, setCourseName] = useState("");

  //Errors and loading

  const [errors, setErrors] = useState({});
  const [pendingApiCall, setPendingApiCall] = useState(false);

  //Entrants
  const [entrants, setEntrants] = useState([]);
  const [entered, setEntered] = useState(false);

  //Leadboard 
  const [showModalLeader, setShowLeader] = useState(false);
  
  //Array of entrants sorted by score
  const [sortedEntrants, setSortedEntrants] = useState([]);
  //Score
  const [showScore, setShowScore] = useState(false);
  const [score, setScore] = useState(0);
  const scoreArea = () => {
    setShowScore(true);
  }

  //Show and close leadboard modal
  const handleCloseLeader = () => setShowLeader(false);
  const handleShowLeader = () => setShowLeader(true);

  //Update members score
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

  //Entrants modal setup

  const [showEntrants, setShowEntrants] = useState(false);

  const handleCloseEntrants = () => setShowEntrants(false);
  const handleShowEntrants = () => setShowEntrants(true);


  //Tee time modal setup
  const [showTeeTime, setShowTeeTime] = useState(false);

  const handleCloseTeeTime = () => setShowTeeTime(false);
  const handleShowTeeTime = () => {
    if(window.innerHeight > window.innerWidth){
      alert("Please use Landscape mode when viewing tee times!");
  }
  setShowTeeTime(true);
}

  //Tee times 
  const [teeTimes, setTeeTimes] = useState([]);

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

      //load data
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

      //Format date from backend to be DD-MM-YYYY

      let yourDate = props.event.date;
      const formatDate = moment(yourDate).format('DD-MM-YYYY')

      //onChange score
      const onChangeScore = (event) => {
        const { value } = event.target;
        setScore(value);
      }

  return (
            <div className="card col-12">
                <div className="card-body">
                    <div className="col-12 card-title align-self-center mb-0">
                        <h5>{props.event.eventname} </h5>
                        <p className="m-0">Courses: {courseName}</p>
                        <p className="m-0">Entries : {props.event.currententrants} / {props.event.maxentrants}</p>
                        <p className="m-0">Event Format : {props.event.eventtype}</p>
                        <p className="m-0">Winner : {props.event.winner}</p>
                        
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
                      {(props.loggedInUser.role === 'ADMIN' || props.loggedInUser.role === 'SUPERUSER' || props.loggedInUser.role === 'EVENTADMIN')  &&
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

                      
                        {/*Show entrants modal*/}
                        <>
                        
                        <Modal show={showEntrants} onHide={handleCloseEntrants}>
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
                                </tr>
                                </tbody>
                              ))}
                            </Table>
                            </Modal.Body>
                            <Modal.Footer>
                              <Button variant="secondary" onClick={handleCloseTeeTime}>
                                Close
                              </Button>
                            </Modal.Footer>
                          </Modal>
                        </>
              
            </div>
  );
};

PreviousEventListItem.defaultProps = {
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

    const mapStateToProps = (state) => {
      return {
        loggedInUser: state
      };
    };

    export default connect(mapStateToProps, mapDispatchToProps)(PreviousEventListItem);
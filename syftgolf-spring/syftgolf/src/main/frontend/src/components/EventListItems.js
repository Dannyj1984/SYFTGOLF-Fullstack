import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import * as apiCalls from '../api/apiCalls';
import { Modal, Button, Table, Container, Row, Col } from "react-bootstrap";
import * as authActions from '../redux/authActions';
import { connect } from 'react-redux';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';

const EventListItem = (props) => {

  const [currentEntrant, setCurrentEntrant] = useState({})
  

  const thisEventType = props.event.type;
  const [entrants, setEntrants] = useState([{}]);
  const [scoreCardObj, setScoreCardObj] = useState({
    h1Score: 0,
    h2Score: 0,
    h3Score: 0,
    h4Score: 0,
    h5Score: 0,
    h6Score: 0,
    h7Score: 0,
    h8Score: 0,
    h9Score: 0,
    h10Score: 0,
    h11Score: 0,
    h12Score: 0,
    h13Score: 0,
    h14Score: 0,
    h15Score: 0,
    h16Score: 0,
    h17Score: 0,
    h18Score: 0
  });

  const [userScoreCard, setUserScoreCard] = useState({});

  const [errors, setErrors] = useState({});
  const [editErrors, setEditErrors] = useState();
  const [editConfirm, setEditConfirm] = useState();
  const [deleteErrors, setDeleteErrors] = useState();

  const [pendingApiCall, setPendingApiCall] = useState(false);

  //Check if member is in this event
  const [entered, setEntered] = useState(false);
  
  
  //sorted entrants by score for leaderboard
  const [sortedEntrants, setSortedEntrants] = useState([]); //For stableford scores
  const [medalEntrants, setMedalEntrants] = useState([]); //for Medal scores
  const [showScore, setShowScore] = useState(false);
  const [score, setScore] = useState(0);
  const [playerPerTee, setPlayerPerTee] = useState(0);
  const scoreArea = () => {
    setShowScore(true);
  }

  //cancel score
  const cancelScore = () => {
    setShowScore(false);
  }
  //Leadboard modal setup
  const [showModalLeader, setShowLeader] = useState(false);
  const [courseName, setCourseName] = useState("");
  const [courseSlope, setCourseSlope] = useState("");

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


const handleCloseScoreEntry = () => setShowScore(false);

//Add tee time modal setup
const [showAddTeeTime, setShowAddTeeTime] = useState(false);

  const handleCloseAddTeeTime = () => {
      setShowAddTeeTime(false);
  }
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
        noOfSlots: 4
})

//Edit tee time inputs
  const [editTeeTime, setEditTeeTime] = useState({
      id: '',
      teetime: '',
      noOfSlots: 4
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
        setPendingApiCall(false)
    }
    )
    .catch((apiError) => {
      setPendingApiCall(false);
    });

      setShowEditTeeTime(true);
  }

  //Delete an event
    const deleteEvent = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will delete this event',
          buttons: [
            {
              label: 'Yes',
              onClick: ()  => 
                apiCalls
                .deleteEvent(props.event.id)
                .then ((response) => {
                    window.location.reload();
                })
                .catch((apiError) => {
                    if (apiError.response.data && apiError.response.data.validationErrors) {
                      setDeleteErrors(apiError.response.data.validationErrors);
                    }
                  })
                
            },
            {
              label: 'No',
              onClick: () => ''
            }
          ]
        });
      }

      const completeEvent = () => {

        confirmAlert({
          title: 'Are you sure?',
          message: 'this will complete this event',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.completeEvent(props.event.id)
                .then((response) => {
                  console.log(response)
                }) (window.location.reload())
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
                  //If error returned because the course has no holes set up yet
                  if (apiError.response.status === 500) {
                    confirmAlert({
                      title: 'Oops, looks like this event isnt ready for entry yet.',
                      message: 'Please speak to the event organiser',
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
        setPendingApiCall(true);
        confirmAlert({
          title: 'Do you want to be removed from this event?',
          message: 'This will remove you from this event',
          buttons: [
            {
              label: 'Yes',
              onClick: () => 
                apiCalls.removeEntrant(eventid, memberid)
                .then ((response => {
                  setPendingApiCall(false);
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
                  console.log(apiError)
                  setPendingApiCall(false)
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
              teeTime: newTeeTime.addnewTeeTime,
              noOfSlots: newTeeTime.noOfSlots
          }
          setPendingApiCall(true)
          apiCalls
          .createTeeSheet(eventid, newTeeSheet)
          .then((response)=> {
              setPendingApiCall(false)
              window.location.reload();
              setNewTeeTime({});
              handleCloseAddTeeTime()
          })
          .catch((apiError) => {
            if (apiError.response.data && apiError.response.data.validationErrors) {
              setErrors(apiError.response.data.validationErrors);
            }
            setPendingApiCall(false);
          });
      }


      const checkIfUserEntered =(username) => {
            for(let i = 0; i < props.event.entrants.length; i++) {
              if(props.event.entrants[i].member.username === username) {
                setEntered(true)
              }
          }
      }

      

      //load data - get Course details of the event, and check if the logged in user has already entered the event
      useEffect(() => {
        const event = props.event;
        const eventid = event.id;
        const username = props.loggedInUser.username
        setPendingApiCall(true)
        apiCalls
          .getCourseDetails(eventid)
          .then((response) => {
            setCourseName(response.data.course);
            setCourseSlope(response.data.courseSlope)
            setPendingApiCall(false)
          })
          .catch((e) => {
            console.log(e)
          })
          let entrantIndex = null;
          setPendingApiCall(true)
          apiCalls.getEntrants(eventid)
          .then((response) => {
            setPendingApiCall(false)
            setEntrants(response.data)
            response.data.forEach((curEnt) => {
              if(curEnt.member.id === props.loggedInUser.id) {
                setCurrentEntrant(curEnt);
              }
            })
            function getUserIndex() {
              try{
               response.data.forEach((e, index) => {
               if(e.member.username === props.loggedInUser.username) {
                 entrantIndex = index;
                   } 
                 })
               }catch(e) {}
             }
            getUserIndex();
            try{
              setUserScoreCard(response.data[entrantIndex].scoreCard)
              const {h1Score, h2Score, h3Score, h4Score, h5Score, h6Score, h7Score, h8Score, h9Score, h10Score, h11Score, h12Score, h13Score, h14Score, h15Score, h16Score, h17Score, h18Score} = response.data[entrantIndex].scoreCard;
          setScoreCardObj({
            ...scoreCardObj,
            h1Score: h1Score,
            h2Score: h2Score,
            h3Score: h3Score,
            h4Score: h4Score,
            h5Score: h5Score,
            h6Score: h6Score,
            h7Score: h7Score,
            h8Score: h8Score,
            h9Score: h9Score,
            h10Score: h10Score,
            h11Score: h11Score,
            h12Score: h12Score,
            h13Score: h13Score,
            h14Score: h14Score,
            h15Score: h15Score,
            h16Score: h16Score,
            h17Score: h17Score,
            h18Score: h18Score
          })
                }catch(e) {}
          })    
          .catch((e) => {
            console.log(e)
          })
        checkIfUserEntered(username)
              //Check if medal or stableford using score and sort by low to high for medal and high to low for stableford
      if(thisEventType === 'Medal') {
        setSortedEntrants(props.event.entrants.sort((a, b) => (a.score < b.score) ? -1 : 1));
      }
      if(thisEventType === 'Multi round event - Medal') { 
        setSortedEntrants(props.event.entrants.sort((a, b) => (a.score < b.score) ? -1 : 1));
      }
      if(thisEventType === 'Stableford') {
        setSortedEntrants(props.event.entrants.sort((a, b) => (a.score < b.score) ? 1 : -1));
      }
      if(thisEventType === 'Multi round event - Stableford') {
        setSortedEntrants(props.event.entrants.sort((a, b) => (a.score < b.score) ? 1 : -1));
      }
      setPendingApiCall(true)
        apiCalls
          .getTeesheet(props.event.id)
          .then((response) => {
            setTeeTimes(response.data)
            setPendingApiCall(false)
          },
           [])
           .catch((e) => {
             console.log(e)
           });
           
      }, [props.event]);

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

      //onChange playerPerTee
      const onChangePlayerPerTee = (event) => {
        const { value } = event.target;
        setPlayerPerTee(value);
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

      //Randomise entrants
      const randomiseEntrants = () => {
        const eventId = props.event.id;
        setPendingApiCall(true)
        apiCalls
        .randomiseEntrants(eventId, playerPerTee)
        .then((response) => {
          setPendingApiCall(false)
          alert('Tee sheet updated');
          setTimeout( window.location.reload(), 3000)
        })
        .catch = (e) => {
          console.log(e)
          setPendingApiCall(false)
        }
      }

      //Scorecard object for submitting to backend

  const [holeIndex, setHoleIndex] = useState(0);

  const [scoreCardUpdateErrors, setScoreCardUpdateErrors] = useState({})

  const toNextHole = () => {
    if(holeIndex+1 < 18){
    setHoleIndex(holeIndex+1)
    }
  }
  const toPrevHole = () => {
    if(holeIndex+1 > 1){
    setHoleIndex(holeIndex-1)
    }
  }

  const updateScoreCard = () => {
    const eventId = props.event.id;
    const memberId = props.loggedInUser.id;
    console.log(scoreCardObj)
    setPendingApiCall(true)
    apiCalls
    .updateScore(eventId, memberId, holeIndex + 1, scoreCardObj)
    .then(
      setPendingApiCall(false),
    )
    .catch((apiError) => {
      if (apiError.response.data && apiError.response.data.validationErrors) {
        setScoreCardUpdateErrors(apiError.response.data.validationErrors);
      }
      setPendingApiCall(false)
    })
    
  }

  const completeScoreCard = () => {
    const eventId = props.event.id;
    const memberId = props.loggedInUser.id;
    console.log(scoreCardObj)
    setPendingApiCall(true)
    apiCalls
    .updateScore(eventId, memberId, holeIndex + 1, scoreCardObj)
    .then(
      setPendingApiCall(false),
      window.location.reload()
    )
    .catch((apiError) => {
      if (apiError.response.data && apiError.response.data.validationErrors) {
        setScoreCardUpdateErrors(apiError.response.data.validationErrors);
      }
      setPendingApiCall(false)
    })
    
  }

  const onChangeScoreCard = (e) => {
    const { value, name } = e.target;
    setScoreCardObj((previousScoreCardObj) => {
      return {
        ...previousScoreCardObj,
        [name]: value
      };
    });
    setErrors((previousErrors) => {
      return {
        ...previousErrors,
        [name]: undefined
      };
    });
  }

      //Format date from backend to be DD-MM-YYYY

      let yourDate = props.event.date;
      let formatDate = new Date(yourDate).toString().substring(0,15)

      console.log(currentEntrant)

  return (
            <div className="card col-12" style={{height:"100%"}}>
                <div className="card-body">
                    <div className="col-12 card-title align-self-center mb-0">
                        <h5>{props.event.name} </h5>
                        <p className="m-0">Course: {courseName}</p>
                        <p className="m-0">Date : {formatDate}</p>
                        <p className="m-0">Entries : {props.event.currentEntrants} / {props.event.maxEntrants}</p>
                        <p className="m-0">Event Format : {props.event.type}</p>
                        <p className="m-0">Cost : £{props.event.cost}</p>
                        <p className="m-0">Playing handicap : {props.event.ninetyFivePercent ? '95%' : '100%'}</p>
                    </div>
                </div>
                <hr/>
                
                <div className="card-body">
                    <div className="float-left btn-group btn-group-m px-2 col-3">
                      <Link
                          to={`/event/${props.event.name}`}>
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
                          onClick={()=>{ handleShowTeeTime() }}
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

                    <div className="float-left btn-group btn-group-m p-2">
                      {(props.loggedInUser.role === 'ADMIN' || props.loggedInUser.role === 'SUPERUSER')  &&
                            <button  
                                className="btn btn-success tooltips" 
                                onClick={completeEvent} 
                                data-placement="top" 
                                data-toggle="tooltip" 
                                data-original-title="Complete">
                                    <i className="fa fa-check"></i>
                            </button>
                        }
                    </div>
                  </div>

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
                                className="btn btn-success tooltips m-2" 
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
                      <Modal.Title>Entrants for {props.event.name} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    {pendingApiCall && 
                    <Modal.Body>
                    <div className="d-flex">
                      <div className="spinner-border text-black-50 m-auto">
                        <span className="sr-only">Loading...</span>
                      </div>
                    </div>
                    </Modal.Body>
                    }
                    {!pendingApiCall &&
                    <Modal.Body>
                    
                      <Table striped bordered hover responsive>
                      <thead>
                        <tr>
                          <th >Member</th>
                        </tr>
                      </thead>

                      {entrants.map((entrant) => 
                       entrant.member && 
                        <tbody key={entrant.member.id}>
                          <tr>
                            <th >{entrant.member.firstName} {entrant.member.surname} ({entrant.member.handicap})</th>
                          </tr>
                        </tbody>  
                      )}
                      </Table>
                    </Modal.Body>}
                    <Modal.Footer>
                    {/* Set the number of players per tee */}
                    {props.loggedInUser.role === 'ADMIN' &&
                    <Input 
                      label="Players per tee"
                      name="players"
                      value={playerPerTee}
                      type="number"
                      onChange={onChangePlayerPerTee} 
                    />}
                      {(props.loggedInUser.role === 'ADMIN' || props.loggedInUser.role === 'SUPERUSER' || props.loggedInUser.role === 'EVENTADMIN')  &&
                      <Button variant="secondary" onClick={randomiseEntrants} >
                        Randomise
                      </Button>}
                      <Button variant="secondary" onClick={handleCloseEntrants}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>
              </>
                        {/*Show leaderboard modal*/}
                        <>
                        {/* modal for stableford event type */}
                        {thisEventType ==="Stableford" &&
                  <Modal 
                    show={showModalLeader} 
                    onHide={handleCloseLeader} 
                    dialogClassName="custom-modal"
                  >
                    <Modal.Header closeButton>
                      <Modal.Title>Leader board for {props.event.name} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    {pendingApiCall && 
                    <Modal.Body>
                    <div className="d-flex">
                      <div className="spinner-border text-black-50 m-auto">
                        <span className="sr-only">Loading...</span>
                      </div>
                    </div>
                    </Modal.Body>
                    }
                    {!pendingApiCall &&
                    <Modal.Body>
                    <Table striped bordered hover responsive>
                      <thead>
                        <tr>
                          <th >Member (playing handicap)</th>
                          <th >Score</th>
                        </tr>
                      </thead>
                      {sortedEntrants.map((entrant =>
                        <tbody key={entrant.member.id}>
                        <tr>
                          {!props.event.ninetyFivePercent &&
                          <th >{entrant.member.firstName} {entrant.member.surname} ({Math.round(entrant.member.handicap/113*courseSlope)})</th>}
                          {props.event.ninetyFivePercent &&
                          <th >{entrant.member.firstName} {entrant.member.surname} ({Math.round(0.95*(entrant.member.handicap/113*courseSlope*0.95))})</th>}
                          <th >{entrant.score} {entrant.currentHole < 18 ? `(${entrant.currentHole})` : ''} </th>
                        </tr>
                        </tbody>
                      ))}
                    </Table>
                    </Modal.Body>}
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleCloseLeader}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>}

                  {/* Modal for Medal event type */}

                  {thisEventType ==="Medal" &&
                  <Modal 
                    show={showModalLeader} 
                    onHide={handleCloseLeader} 
                    dialogClassName="custom-modal"
                  >
                    <Modal.Header closeButton>
                      <Modal.Title>Leader board for {props.event.name} on {formatDate}</Modal.Title>
                    </Modal.Header>
                    {pendingApiCall && 
                    <Modal.Body>
                    <div className="d-flex">
                      <div className="spinner-border text-black-50 m-auto">
                        <span className="sr-only">Loading...</span>
                      </div>
                    </div>
                    </Modal.Body>
                    }
                    {!pendingApiCall &&
                    <Modal.Body>
                    <Table striped bordered hover responsive>
                      <thead>
                        <tr>
                          <th >Member</th>
                          <th >Score</th>
                        </tr>
                      </thead>
                      {sortedEntrants.map((entrant =>
                        <tbody key={entrant.member.id}>
                        <tr>
                          {!props.event.ninetyFivePercent &&
                          <th >{entrant.member.firstName} {entrant.member.surname} ({Math.round(entrant.member.handicap/113*courseSlope)})</th>}
                          {props.event.ninetyFivePercent &&
                          <th >{entrant.member.firstName} {entrant.member.surname} ({Math.round(0.95*(entrant.member.handicap/113*courseSlope))})</th>}
                          <th >{entrant.score}</th>
                        </tr>
                        </tbody>
                      ))}
                    </Table>
                    </Modal.Body>}
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleCloseLeader}>
                        Close
                      </Button>
                    </Modal.Footer>
                  </Modal>}

                  
              </>
                        {/*Show Tee time modal*/}
                        <>
                          <Modal 
                            show={showTeeTime} 
                            onHide={handleCloseTeeTime} 
                            dialogClassName="modal-content-full modal-dialog-full"
                            size="xl"
                            centered
                          >
                            <Modal.Header closeButton>
                              <Modal.Title>Tee times for {props.event.name} on {formatDate}</Modal.Title>
                            </Modal.Header>
                            {pendingApiCall &&
                            <Modal.Body>
                            <div className="d-flex">
                              <div className="spinner-border text-black-50 m-auto">
                                <span className="sr-only">Loading...</span>
                              </div>
                            </div>
                            </Modal.Body>
                            }
                            {!pendingApiCall &&
                            <Modal.Body>
                            
                            
                            <Table striped bordered hover responsive>
                              <thead>
                                <tr>
                                  <th >Tee time</th>
                                  <th >Player 1</th>
                                  <th>Player 2</th>
                                  <th>Player 3</th>
                                  {teeTimes[0] !== undefined && teeTimes[0].noOfSlots === 4 &&
                                  <th>Player 4</th>}
                                  {props.loggedInUser.role === 'ADMIN' &&
                                  <th id='admin'>Admin</th>}
                                </tr>
                              </thead>
                              
                              {teeTimes.map((teetime => 
                                <tbody key={teetime.id}>
                                    <tr>
                                    <td>{teetime.teeTime}</td>
                                    {teetime.entrants.map((entrant) => 
                                      <td>{entrant.member['firstName']} {entrant.member['surname']} ({entrant.member['handicap']})</td>
                                    )}
                                    {props.loggedInUser.role === 'ADMIN' &&
                                    <td headers='admin'>
                                        <button className="btn btn-danger m-2" onClick={() => deleteTeeSheet(teetime.id)}>delete</button>
                                        <button className="btn btn-warning m-2" onClick={() => handleShowEditTeeTime(teetime.id)}>Update</button>
                                    </td>}
                                    </tr>
                                </tbody>
                              ))}   
                            </Table>
                            </Modal.Body>}
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
                            <Modal.Title>Edit Tee times for {props.event.name} on {formatDate}</Modal.Title>
                          </Modal.Header>
                          {pendingApiCall && 
                          <Modal.Body>
                          <div className="d-flex">
                            <div className="spinner-border text-black-50 m-auto">
                              <span className="sr-only">Loading...</span>
                            </div>
                          </div>
                          </Modal.Body>
                          }
                          {!pendingApiCall &&
                          <Modal.Body>
                          <Table striped bordered hover responsive>
                            <thead>
                                <tr>
                                  <th >Tee teetime</th>
                                  <th >Player 1</th>
                                  <th >Player 2</th>
                                  <th >Player 3</th>
                                  <th >Player 4</th>
                                </tr>
                            </thead>
                                <tbody>
                                    <tr>
                                    <th ><Input name="teetime" value={editTeeTime.teetime} onChange={onChangeEdit} /></th>
                                    <th ><Input name="player1" value={editTeeTime.player1} onChange={onChangeEdit} /></th>
                                    <th ><Input name="player2" value={editTeeTime.player2} onChange={onChangeEdit} /></th>
                                    <th ><Input name="player3" value={editTeeTime.player3} onChange={onChangeEdit} /></th>
                                    <th ><Input name="player4" value={editTeeTime.player4} onChange={onChangeEdit} /></th>
                                    <th >
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
                          </Modal.Body>}
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
                            <Modal.Title>Add Tee time for {props.event.name} on {formatDate}</Modal.Title>
                          </Modal.Header>
                          {pendingApiCall && 
                          <Modal.Body>
                          <div className="d-flex">
                            <div className="spinner-border text-black-50 m-auto">
                              <span className="sr-only">Loading...</span>
                            </div>
                          </div>
                          </Modal.Body>
                          }
                          {!pendingApiCall &&
                          <Modal.Body>
                          <Table striped bordered hover responsive>
                          <thead>
                            <tr>
                              <th >Time</th>
                              <th >Number of slots</th>
                            </tr>
                          </thead>
                          <tbody>
                              <tr>
                                  <th >
                                      <Input
                                        name="addnewTeeTime"
                                        placeholder="Tee time"
                                        value={newTeeTime.addnewTeeTime}
                                        onChange={onChange} 
                                      />
                                  </th>
                                  <th >
                                      <Input
                                        name="noOfSlots"
                                        value={newTeeTime.noOfSlots}
                                        onChange={onChange} 
                                      />
                                  </th>
                              </tr>
                          </tbody>
                          </Table>
                          </Modal.Body>}
                          <Modal.Footer>
                            <Button variant="success" disabled={pendingApiCall} onClick={addTeeTime}>Add</Button>
                            <Button variant="secondary" onClick={handleCloseAddTeeTime}>
                              Close
                            </Button>
                          </Modal.Footer>
                        </Modal>
                    </>
                    
                    {/*Show Score entry modal*/}
                    <>
                          <Modal 
                            show={showScore} 
                            onHide={handleCloseScoreEntry} 
                            dialogClassName="modal-content-full modal-dialog-full"
                            size="m"
                            centered
                          >
                            <Modal.Header closeButton>
                              <Modal.Title id='scoreEntryModal'>
                                <Container>
                                Score Entry for {props.event.name} for player {props.loggedInUser.username}
                               </Container>
                              </Modal.Title>
                            </Modal.Header>
                            {pendingApiCall && 
                            <Modal.Body>
                            <div className="d-flex">
                              <div className="spinner-border text-black-50 m-auto">
                                <span className="sr-only">Loading...</span>
                              </div>
                            </div>
                            </Modal.Body>
                            }
                            {!pendingApiCall &&
                            <Modal.Body>
                              <Container>
                                <Row style={{margin:"auto", width:"50%"}}>
                                  <Col xs={12}>
                                    <div id='score-entry'>
                                      <h2 >Hole {holeIndex+1}</h2>
                                      <input name={`h${holeIndex+1}Score`} value={scoreCardObj[`h${holeIndex+1}Score`]}  onChange={onChangeScoreCard} type="number" min={"0"} max={"15"} style={{width:"8rem", height:"8rem",padding:"12px 20px", display:"inline-block", border:"1px solid #ccc", borderRadius: "4px", fontSize: "64px", textAlign:"center"}}></input>
                                        <p style={{color:"red", fontSize:"13px", width:"140%"}}>Please enter gross scores</p>
                                        {/* <p>Current Score : {currentEntrant.score}</p> */}
                                    </div>
                                  </Col>
                                </Row>
                                <Row style={{margin:"auto", width:"50%"}}>
                                  <Col xs={6}>
                                    {holeIndex+1 !== 1 &&
                                    <button onClick={toPrevHole} style={{fontSize: "64px"}}>{'<'}</button>}
                                  </Col>
                                  <Col xs={6}>
                                    {holeIndex+1 !== 18 &&
                                    <button onClick={function(){ toNextHole(); updateScoreCard()}} style={{fontSize: "64px"}}>{'>'}</button>}
                                  </Col>
                                </Row>
                              </Container>
                            
                            </Modal.Body>}
                            <Modal.Footer>
                            {holeIndex+1 === 18 &&
                              <Button variant='primary' onClick={completeScoreCard}>Submit</Button>}
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

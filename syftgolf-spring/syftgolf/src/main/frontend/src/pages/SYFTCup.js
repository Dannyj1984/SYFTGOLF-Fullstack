import React, {useState, useEffect} from 'react';
import * as apiCalls from '../api/apiCalls';
import { connect } from 'react-redux';
import Spinner from '../components/Spinner';
import { Table } from "react-bootstrap";


export const SYFTCup = (props) => {

    const [members, setMembers] = useState([{}]);
    const [pendingApiCall, setPendingApiCall] = useState(false);
    console.log(props.loggedInUser.society.id)

    const loadData = (requestedPage = 0) => {
        setPendingApiCall(true);
        apiCalls
        .getMemberByFedExRanking(props.loggedInUser.society.id) 
        .then((response) => {
            setMembers(response.data);
            setPendingApiCall(false);
        })
        .catch((error) => {
            console.log(error);
            setPendingApiCall(false);
        })
      };

    useEffect(() => { 
        loadData();
      }, []);

      return (
        <div >
            {pendingApiCall &&
            <div>
                <Spinner></Spinner>
                <h3 className="text-danger text-center">Loading leaderboard </h3>
            </div>}

            {!pendingApiCall &&
            <div>
                <h1 className="text-center">SYFT Cup 2022 </h1>
                <hr></hr>
                <div className='container'>
                <Table striped bordered hover responsive>
                      <thead>
                        <tr>
                          <th >Member</th>
                          <th >Score</th>
                        </tr>
                      </thead>
                      {!members && <div><Spinner /></div>}
                    {members.map((member) => (
                        <tbody key={member.id}>
                          <tr>
                            <td >{member.firstName} {member.surname}</td>
                            <td>{member.fedExPoints}</td>
                          </tr>
                        </tbody>
                    ))}
                    </Table>
                </div>
            </div>}
        </div>
      );
  
}

const mapStateToProps = (state) => {
    return {
      loggedInUser: state
    };
  };

export default connect(mapStateToProps) (SYFTCup);
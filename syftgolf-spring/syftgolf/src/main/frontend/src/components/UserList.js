import React, { useState, useEffect } from "react";
import * as apiCalls from '../api/apiCalls';
import UserListItem from './UserListItem';
import { connect } from 'react-redux';
import ExportCSV from "./ExportCSV";
import Input from './Input';

export const UserList = (props) => {
  const [page, setPage] = useState({
    content: [],
    number: 0,
    size: 9
  });

  const [pendingApiCall, setPendingApiCall] = useState(false);

  //Name filter when filtering users
  const [nameFilter, setNameFilter] = useState('');

  const clearFilter = () => {
    setNameFilter('')
    loadData()
  }
  

  const [loadError, setLoadError] = useState();

  //Change filter value
  const onChange = (event) => {
  const { value } = event.target;

  
  setNameFilter(value)
  
  loadFilter()
}

const loadFilter = async (requestedPage = 0) => {
  let name = nameFilter
  let id = props.user.society.id 
  setPendingApiCall(true);
  await apiCalls
     .listFilteredUsers({ page: requestedPage, size: 9 }, id, name.toLowerCase())
     .then ((response)  => {
      setPage(response.data);
      if(Object.entries(response.data.content).length === 0) {
        setLoadError('No members');
      }
    })
    .catch((error) => {
      setLoadError("Member load failed" );
    });
    setPendingApiCall(false);
};

  const loadData = (requestedPage = 0) => {
    let id = props.user.society.id  
    apiCalls
      .listUsers(id, { page: requestedPage, size: 9 })
      .then((response) => {
        setPage(response.data);
        setLoadError();
      })
      .catch((error) => {
        setLoadError("User load failed" );
      });
  };

  useEffect(() => {
    loadFilter();
  }, []);

  const onClickNext = () => {
    loadData(page.number + 1);
  };

  const onClickPrevious = () => {
    loadData(page.number - 1);
  };

  const { content, first, last } = page;
  

  return (

    <div>
      {(props.user.role === 'SUPERUSER' || props.user.role === 'ADMIN') &&
      <div className="container">
        <div className="row">
          <div className="col-sm">
            <div className="row">
              <Input name="nameFilter" value={nameFilter} type="text" placeholder="Search by firstname" onChange={onChange} />
              <button className="btn btn-primary" onClick={clearFilter} >Clear</button>
            </div>
          </div>
          <div className="col-sm">
            <h3 className="card-title m-auto text-center">Members</h3>
          </div>
          
          <div className="col-sm">
          <ExportCSV />
          </div>
        </div>
      </div>}
      
      <hr />
      {pendingApiCall &&
        <div className="d-flex">
          <div className="spinner-border text-black-50 m-auto">
            <span className="sr-only">Loading...</span>
          </div>
        </div>}
      {!pendingApiCall && 
      <div className="list-group list-group-flush" data-testid="usergroup">
        <div className="row">
          {content.map((user) => (
            <div key={user.id} className="col-xl-4 col-m-12 mb-4">
              <UserListItem
                user={user}
                onClickDeleteMember={() => {
                  apiCalls.deleteMember(user.id);
                }}
              />
            </div>
          ))}
        </div>
      </div>}
      
      <div className="clearfix">
        {!first && (
          <span className="badge badge-light float-left" style={{ cursor: "pointer" }} onClick={onClickPrevious}>
            <button className="btn btn-primary">Previous</button>
          </span>
        )}
        {!last && (
          <span className="badge badge-light float-right" style={{ cursor: "pointer" }} onClick={onClickNext}>
            <button className="btn btn-primary">Next</button>
          </span>
        )}
      </div>
      {loadError && <span className="text-center text-danger">{loadError}</span>}
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
    user: state
  };
};

export default connect(mapStateToProps)(UserList);



// import React from 'react';
// import * as apiCalls from '../api/apiCalls';
// import UserListItem from './UserListItem';

// class UserList extends React.Component {
//   state = {
//     user: undefined,
//     loggedInUser: undefined,
//     userNotFound: true,
//     loadError: undefined,
//     page: {
//       content: [],
//       number: 0,
//       size: 9
//     }
//   };
  
//   componentDidMount() {
//     this.loadData();
//   }

//   loadData = (requestedPage = 0) => {
//     apiCalls
//       .listUsers({ page: requestedPage, size: this.state.page.size })
//       .then((response) => {
//         this.setState({
//           page: response.data,
//           loadError: undefined
//         });
//       })
//       .catch((error) => {
//         this.setState({ loadError: 'User load failed' });
//       });
//     };


//     onClickNext = () => {
//       this.loadData(this.state.page.number + 1);
//     };
  
//     onClickPrevious = () => {
//       this.loadData(this.state.page.number - 1);
//     };

//   render() {
//     return (
//       <div >
//         <h3 className="card-title m-auto text-center">Members</h3>
//         <hr></hr>
//         <div className="list-group list-group-flush" data-testid="usergroup">
//           <div className="row">
//           {this.state.page.content.map((user) => (
//               <div key={user.id} className="col-xl-4 col-m-12 mb-4">
//               <UserListItem user={user} onClickDeleteMember= { () => {apiCalls.deleteMember(user.id)}}  />
//               </div>
//             ))}
//           </div>
//         </div>
//         <div className="clearfix">
//           {!this.state.page.first && (
//             <span
//               className="badge badge-light float-left"
//               style={{ cursor: 'pointer' }}
//               onClick={this.onClickPrevious}
//             ><button className="btn btn-primary">Previous</button></span>
//           )}
//           {!this.state.page.last && (
//             <span
//               className="badge badge-light float-right"
//               style={{ cursor: 'pointer' }}
//               onClick={this.onClickNext}
//             >
//               <button className="btn btn-primary">Next</button>
//             </span>
//           )}
//         </div>
//         {this.state.loadError && (
//           <span className="text-center text-danger">
//             {this.state.loadError}
//           </span>
//         )}
//       </div>
//     );
//   }
// }

// export default UserList;

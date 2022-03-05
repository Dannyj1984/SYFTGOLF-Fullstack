import React, {useEffect, useState} from 'react';
import * as apiCalls from '../api/apiCalls';
import CourseListItem from './CourseListItem';
import Input from './Input';
import { connect } from 'react-redux';

export const CourseList = (props) => {

    const [page, setPage] = useState({
        content: [],
        number: 0,
        size: 9
    });

    const [pendingApiCall, setPendingApiCall] = useState(false);

    const [loadError, setLoadError] = useState();

    //Name filter when filtering users
  const [nameFilter, setNameFilter] = useState('');

  const clearFilter = () => {
    setNameFilter('');
    loadData();
  }

  //Change filter value
  const onChange = (event) => {
    //set value to event.target.value
    const { value } = event.target;
    //set nameFilter as the value of the event.target.value
    setNameFilter(value)
    //run loadFilter func
    loadFilter()
  }

  const loadFilter = async (requestedPage = 0) => {
    //Create name to use in api call and set to value of loadFilter
    let name = nameFilter
    //Get the society of the user
    let id = props.user.society.id 
    setPendingApiCall(true);
    await apiCalls
      .listFilteredCourses({ page: requestedPage, size: 9 }, id, name)
       .then ((response)  => {
        setPendingApiCall(false)
        setPage(response.data);
        if(Object.entries(response.data.content).length === 0) {
          setLoadError('No courses found');
        }
      })
      .catch((error) => {
        setLoadError("Course load failed" );
        setPendingApiCall(false)
      });
  };

    

    const loadData = (requestedPage = 0) => {
      let id = props.user.society.id
        apiCalls
            .listCourses(id, { page: requestedPage, size: 9 })
            .then((response) => {
                setPage(response.data);
                setLoadError();
            })
            .catch((error) => {
                setLoadError("Course load failed" );
            });
    };

    useEffect(() => {
        loadFilter();
    }, [nameFilter]);

    const onClickNext = () => {
        loadData(page.number + 1);
    };

    const onClickPrevious = () => {
        loadData(page.number - 1);
    };

    const { content, first, last } = page;
    return (
      <div >
        
      <div className="container">
        <div className="row">
          <div className="col-sm">
            <div className="row">
              <Input name="nameFilter" value={nameFilter} type="text" placeholder="Search" onChange={onChange} />
              <button className="btn btn-primary" onClick={clearFilter} >Clear</button>
            </div>
          </div>
          <div className="col-sm">
            <h3 className="card-title m-auto text-center">Courses</h3>
          </div>
        </div>
      </div>
      
      <hr />
      {pendingApiCall &&
        <div className="d-flex">
          <div className="spinner-border text-black-50 m-auto">
            <span className="sr-only">Loading...</span>
          </div>
        </div>}
        <hr/>
        <div className="list-group list-group-flush" data-testid="coursegroup">
          <div className="row">
          {content.map((course) => (
              <div key={course.id} className="col-xl-4 col-m-12 mb-4">
              <CourseListItem  course={course}  />
              </div>
            ))}
          </div>
        </div>
        <div className="clearfix">
          {!first && (
            <span
              className="badge badge-light float-left"
              style={{ cursor: 'pointer' }}
              onClick={onClickPrevious}
            ><button className="btn btn-primary">Previous</button></span>
          )}
          {!last && (
            <span
              className="badge badge-light float-right"
              style={{ cursor: 'pointer' }}
              onClick={onClickNext}
            >
              <button className="btn btn-primary">Next</button>
            </span>
          )}
        </div>
        {loadError && (
          <span className="text-center text-danger">
            {loadError}
          </span>
        )}
      </div>
    );
};

const mapStateToProps = (state) => {
  return {
    user: state
  };
};

export default connect(mapStateToProps)(CourseList);

// class CourseList extends React.Component {
//   state = {
//     page: {
//       content: [],
//       number: 0,
//       size: 9
//     }
//   };
//
//
//   componentDidMount() {
//     this.loadData();
//   }
//
//   loadData = (requestedPage = 0) => {
//     apiCalls
//       .listCourses({ page: requestedPage, size: this.state.page.size })
//       .then((response) => {
//         this.setState({
//           page: response.data,
//           loadError: undefined
//         });
//       })
//       .catch((error) => {
//         this.setState({ loadError: 'Course load failed' });
//       });
//
//       };
//
//
//     onClickNext = () => {
//       this.loadData(this.state.page.number + 1);
//     };
//
//     onClickPrevious = () => {
//       this.loadData(this.state.page.number - 1);
//     };
//
//   render() {
//     return (
//       <div >
//         <h3 className="card-title m-auto text-center">Courses</h3>
//         <hr></hr>
//         <div className="list-group list-group-flush" data-testid="coursegroup">
//           <div className="row">
//           {this.state.page.content.map((course) => (
//               <div key={course.id} className="col-xl-4 col-m-12 mb-4">
//               <CourseListItem  course={course}  />
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
//
// export default CourseList;

import React from 'react';
import * as apiCalls from '../api/apiCalls';
import CourseListItem from './CourseListItem';

class CourseList extends React.Component {
  state = {
    page: {
      content: [],
      number: 0,
      size: 9
    }
  };

  
  componentDidMount() {
    this.loadData();
  }

  loadData = (requestedPage = 0) => {
    apiCalls
      .listCourses({ page: requestedPage, size: this.state.page.size })
      .then((response) => {
        this.setState({
          page: response.data,
          loadError: undefined
        });
        console.log(this.state.page);
      })
      .catch((error) => {
        this.setState({ loadError: 'Course load failed' });
      });
                
      };
        

    onClickNext = () => {
      this.loadData(this.state.page.number + 1);
    };
  
    onClickPrevious = () => {
      this.loadData(this.state.page.number - 1);
    };

  render() {
    return (
      <div >
        <h3 className="card-title m-auto text-center">Courses</h3>
        <hr></hr>
        <div className="list-group list-group-flush" data-testid="coursegroup">
          <div className="row">
          {this.state.page.content.map((course) => (
              <div key={course.id} className="col-xl-4 col-m-12 mb-4">
              <CourseListItem  course={course}  />
              </div>
            ))}
          </div>
        </div>
        <div className="clearfix">
          {!this.state.page.first && (
            <span
              className="badge badge-light float-left"
              style={{ cursor: 'pointer' }}
              onClick={this.onClickPrevious}
            ><button className="btn btn-primary">Previous</button></span>
          )}
          {!this.state.page.last && (
            <span
              className="badge badge-light float-right"
              style={{ cursor: 'pointer' }}
              onClick={this.onClickNext}
            >
              <button className="btn btn-primary">Next</button>
            </span>
          )}
        </div>
        {this.state.loadError && (
          <span className="text-center text-danger">
            {this.state.loadError}
          </span>
        )}
      </div>
    );
  }
}

export default CourseList;
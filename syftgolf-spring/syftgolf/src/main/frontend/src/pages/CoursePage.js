import React from 'react';
import CourseList from '../components/CourseList';

class CoursePage extends React.Component{
    render() {
        return (
            <div data-testid="coursePage">
              <CourseList />
            </div>
          );
    }
}

export default CoursePage;
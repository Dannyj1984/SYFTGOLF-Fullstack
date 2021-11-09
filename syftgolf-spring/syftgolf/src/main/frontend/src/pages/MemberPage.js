import React from 'react';
import UserList from '../components/UserList';

class MemberPage extends React.Component{



    render() {
        return (
            <div data-testid="memberPage">
              <UserList />
            </div>
          );
    }
}

export default MemberPage;
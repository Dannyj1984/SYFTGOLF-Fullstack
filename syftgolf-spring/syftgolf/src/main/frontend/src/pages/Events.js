import React from 'react';
import EventList from '../components/EventList';

class EventPage extends React.Component{
    render() {
        return (
            <div data-testid="eventPage">
              <EventList />
            </div>
          );
    }
}

export default EventPage;
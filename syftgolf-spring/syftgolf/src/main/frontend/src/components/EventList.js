import React, {useState, useEffect} from 'react';
import * as apiCalls from '../api/apiCalls';
import EventListItem from './EventListItem';

export const EventList = (props) => {

    const [event, setEvent] = useState();
    const [events, setEvents] = useState();

    const [page, setPage] = useState({
        content: [],
        number: 0,
        size: 9
    });

    const [loadError, setLoadError] = useState();

  useEffect(() => {
    loadData();
  }, []);

  const loadData = (requestedPage = 0) => {
    apiCalls
      .listEvents({ page: requestedPage, size: 9 })
      .then((response) => {
        setPage(response.data);
      })
      .catch((error) => {
        setLoadError("Event load failed" );
      });
  };

    const onClickNext = () => {
        loadData(page.number + 1);
    };

    const onClickPrevious = () => {
        loadData(page.number - 1);
    };

    const { content, first, last } = page;

  return (
          <div >
            <h3 className="card-title m-auto text-center">Events</h3>
            <hr/>
            <div className="list-group list-group-flush" data-testid="eventgroup">
              <div className="row">
              {content.map((event) => (
                  <div key={event.id} className="col-xl-4 col-m-12 mb-4">
                 
                  <EventListItem  event={event} events={events} />
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

export default EventList;

// class EventList extends React.Component {
//   state = {
//     event: undefined,
//     events: undefined,
//     page: {
//       content: [],
//       number: 0, 
//       size: 9
//     },
//     loadError: undefined
//   };

  
//   componentDidMount() {
//     this.loadData();
//   }

//   loadData = (requestedPage = 0) => {
//     apiCalls
//       .listEvents({ page: requestedPage, size: this.state.page.size })
//       .then((response) => {
//         this.setState({
//           page: response.data,
//           event: response.data,
//           loadError: undefined
//         });
//       })
//       .catch((error) => {
//         this.setState({ loadError: 'Event load failed' });
//       });

//       apiCalls
//       .getEvents()
//       .then((response) => {
//         this.setState({
//           events: response.data
//         });
//       })
//       .catch((error) => {
//         this.setState({ loadError: 'Event load failed'});
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
//         <h3 className="card-title m-auto text-center">Events</h3>
//         <hr></hr>
//         <div className="list-group list-group-flush" data-testid="eventgroup">
//           <div className="row">
//           {this.state.page.content.map((event) => (
//               <div key={event.id} className="col-xl-4 col-m-12 mb-4">
             
//               <EventListItem  event={event} events={this.state.events} />
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

// export default EventList;

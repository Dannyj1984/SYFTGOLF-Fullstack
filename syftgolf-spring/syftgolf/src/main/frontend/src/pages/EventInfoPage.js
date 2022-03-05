import React from 'react';
import * as apiCalls from '../api/apiCalls';
import EventProfileCard from '../components/EventProfileCard';
import { connect } from 'react-redux';

class EventInfoPage extends React.Component{
    state = {
        event: undefined,
        eventNotFound: false,
        isLoadingEvent: true,
        inEditMode: false,
        originalEventname: undefined,
        originalDate: undefined,
        originalWinner: undefined,
        originalEventtype: undefined,
        originalMaxentrants: undefined,
        originalQualifier: undefined,
        originalNinetyFive: undefined,
        originalCost: undefined,
        originalInfo: undefined,
        pendingUpdateCall: false,
        errors: {}
      };

      componentDidMount() {
        this.loadEvent();
        }
        componentDidUpdate(prevProps) {
          if (prevProps.match.params.eventname !== this.props.match.params.eventname) {
            this.loadEvent();
          }
        }
        
          
        loadEvent = () => {
          const eventname = this.props.match.params.eventname;
          if (!eventname) {
            return;
          }
          this.setState({ eventNotFound: false, isLoadingEvent: true });
          apiCalls
            .getEvent(eventname)
            .then((response) => {
              this.setState({ event: response.data, isLoadingEvent: false });
            })
            .catch((error) => {
              this.setState({
                EventNotFound: true,
                isLoadingEvent: false
              });
            });
        };

        onClickEdit = () => {
          this.setState({
            inEditMode: true
          });
        };

        onClickCancel = () => {
          const event = { ...this.state.event };
        if (this.state.originalEventname !== undefined) {
          event.name = this.state.originalEventname;
          event.date = this.state.originalDate;
          event.cost = this.state.originalCost;
          event.type = this.state.originalEventtype;
          event.info = this.state.originalInfo;
          event.qualifier = this.state.originalQualifier;
          event.maxEntrants = this.state.originalMaxentrants;
          event.winner = this.state.originalWinner;
          event.ninetyFivePercent = this.state.originalNinetyFive
          
        }
          this.setState({
            event,
            errors: {},
            originalEventname: undefined,
            originalDate: undefined,
            originalWinner: undefined,
            originalEventtype: undefined,
            originalMaxentrants: undefined,
            originalQualifier: undefined,
            originalCost: undefined,
            originalInfo: undefined,
            originalNinetyFive: undefined,
            inEditMode: false
            
          });
        };

        onClickSave = () => {
          const event = { ...this.state.event };
          const eventId = event.id;
          const eventUpdate = {
            name: event.name,
            date: event.date,
            winner: event.winner,
            type: event.type,
            maxEntrants: event.maxEntrants,
            qualifier: event.qualifier,
            cost: event.cost,
            info: event.info,
            ninetyFivePercent: event.ninetyFivePercent
          };
          this.setState({ pendingUpdateCall: true });
        apiCalls
          .updateEvent(eventId, eventUpdate)
          .then((response) => {
            const event = { ...this.state.event };
            this.setState(
              {
                inEditMode: false,
                originalEventname: undefined,
                originalDate: undefined,  
                originalWinner: undefined,
                originalEventtype: undefined,
                originalMaxentrants: undefined,
                originalQualifier: undefined,
                originalCost: undefined,
                originalInfo: undefined,
                originalNinetyFive: undefined,
                pendingUpdateCall: false,
                event
              },
              () => {
                const action = {
                  type: 'event-update-success',
                  payload: event
                };
                this.props.dispatch(action);
              }
            );
          })
          .catch((error) => {
            let errors = {};
            if (error.response.data.validationErrors) {
              errors = error.response.data.validationErrors;
            }
            this.setState({
              pendingUpdateCall: false,
              errors
            });
           });
        };

        onChangeEventname = (e) => {
          const event = { ...this.state.event };
          let originalEventName = this.state.originalEventname;
        if (originalEventName === undefined) {
            originalEventName = event.name;
        }
          event.name = e.target.value;
          const errors = { ...this.state.errors };
          errors.name = undefined;
          this.setState({ event, originalEventName, errors });
        };

        onChangeDate = (e) => {
          const event = { ...this.state.event };
          let originalDate = this.state.originalDate;
        if (originalDate === undefined) {
            originalDate = event.date;
        }
          event.date = e.target.value;
          const errors = { ...this.state.errors };
          errors.date = undefined;
          this.setState({ event, originalDate, errors });
        };

        onChangeQualifier = (e) => {
          const event = { ...this.state.event };
          let originalQualifier = this.state.originalQualifier;
        if (originalQualifier === undefined) {
            originalQualifier = event.qualifier;
        }
          event.qualifier = e.target.value;
          const errors = { ...this.state.errors };
          errors.qualifier = undefined;
          this.setState({ event, originalQualifier, errors });
        };

        onChangeEventtype = (e) => {
          const event = { ...this.state.event };
          let originalEventtype = this.state.originalEventtype;
        if (originalEventtype === undefined) {
          originalEventtype = event.type;
        }
          event.type = e.target.value;
          const errors = { ...this.state.errors };
          errors.Eventtype = undefined;
          this.setState({ event, originalEventtype, errors });
        };

        onChangeWinner = (e) => {
          const event = { ...this.state.event };
          let originalWinner = this.state.originalWinner;
        if (originalWinner === undefined) {
          originalWinner = event.winner;
        }
          event.winner = e.target.value;
          const errors = { ...this.state.errors };
          errors.winner = undefined;
          this.setState({ event, originalWinner, errors });
        };

        onChangeMaxentrants = (e) => {
          const event = { ...this.state.event };
          let originalMaxentrants = this.state.originalMaxentrants;
        if (originalMaxentrants === undefined) {
          originalMaxentrants = event.maxEntrants;
        }
          event.maxEntrants = e.target.value;
          const errors = { ...this.state.errors };
          errors.maxEntrants = undefined;
          this.setState({ event, originalMaxentrants, errors });
        };

        onChangeInfo = (e) => {
          const event = { ...this.state.event };
          let originalInfo = this.state.originalInfo;
        if (originalInfo === undefined) {
          originalInfo = event.info;
        }
          event.info = e.target.value;
          const errors = { ...this.state.errors };
          errors.info = undefined;
          this.setState({ event, originalInfo, errors });
        };

        onChangeCost = (e) => {
          const event = { ...this.state.event };
          let originalCost = this.state.originalCost;
        if (originalCost === undefined) {
          originalCost = event.cost;
        }
          event.cost = e.target.value;
          const errors = { ...this.state.errors };
          errors.cost = undefined;
          this.setState({ event, originalCost, errors });
        };

        onChangeNinetyFivePercent = (e) => {
          const event = { ...this.state.event };
          let originalNinetyFive = this.state.originalNinetyFive;
        if (originalNinetyFive === undefined) {
          originalNinetyFive = event.ninetyFivePercent;
        }
          const value = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
          event.ninetyFivePercent = value;
          const errors = { ...this.state.errors };  
          errors.originalNinetyFive = undefined;
          this.setState({ event, originalNinetyFive, errors });
        };
        
    
        render() {
          let pageContent;
          if (this.state.isLoadingEvent) {
            pageContent = (
              <div className="d-flex">
                <div className="spinner-border text-black-50 m-auto">
                  <span className="sr-only">Loading...</span>
                </div>
              </div>
            );
          } else if (this.state.eventNotFound) {
            pageContent = (
                <div className="alert alert-danger text-center">
                  <div className="alert-heading">
                    <i className="fas fa-exclamation-triangle fa-3x" />
                  </div>
                  <h5>Event not found</h5>
                </div>
              );
            } else {
              
              const isEditable = true
              //TODO get logged in user details and check if ADMIN
              //this.props.user.role === "ADMIN" || "EVENTADMIN";
              pageContent = this.state.event && (
                <EventProfileCard
                event={this.state.event}
                isEditable={isEditable}
                inEditMode={this.state.inEditMode}
                onClickEdit={this.onClickEdit}
                onClickCancel={this.onClickCancel}
                onClickSave={this.onClickSave}
                onChangename={this.onChangeEventname}
                onChangeDate = {this.onChangeDate}
                onChangeWinner = {this.onChangeWinner}
                onChangeEventtype = {this.onChangeEventtype}
                onChangeMaxentrants = {this.onChangeMaxentrants}
                onChangeQualifier = {this.onChangeQualifier}
                onChangeNinetyFivePercent = {this.onChangeNinetyFivePercent}
                onChangeCost = {this.onChangeCost}
                onChangeInfo = {this.onChangeInfo}
                pendingUpdateCall={this.state.pendingUpdateCall}
                errors={this.state.errors}
              />
            );
            }
            return <div data-testid="eventpage">{pageContent}</div>;
          }
}

EventInfoPage.defaultProps = {
  match: {
    params: {}
  }
};

const mapStateToProps = (state) => {
  return {
    loggedInEvent: state
  };
};

export default connect(mapStateToProps)(EventInfoPage);
import React from 'react';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';
import moment from 'moment';

const EventProfileCard = (props) => {
  const { eventname, date, maxentrants, cost, eventtype, qualifier, winner, info} = props.event;

  const showEditButton = props.isEditable && !props.inEditMode;

  const userObj = localStorage.getItem('syft-auth');
  const authorityJSON = JSON.parse(userObj);

  let winnerName = winner;

  if(winnerName === null) {
    winnerName = '';
  };
  let yourDate = date;
      const formatDate = moment(yourDate).format('DD-MM-YYYY')
  
  return (
    <div className="card">
      <div className="card-body text-center">
      {!props.inEditMode && (
      <h3>{`Event : ${eventname}`}</h3>)}
      <hr/>
      <h5>{`Date: ${formatDate}`}</h5>
      <h5>{`Format: ${eventtype}`}</h5>
      <h5>{`Qualifier: ${qualifier}`}</h5>
      <h5>{`Max Entrants: ${maxentrants}`}</h5>
      <h5>{`Cost: £${cost}`}</h5>
      <h5>{`Event info: ${info}`}</h5>
      <h5>{`Winner: ${winner}` }</h5>
      
        {props.inEditMode && (
          <div className="mb-2">
          <hr/>
          <h3>{`Amend ${eventname}`}</h3>
            <Input
              name="eventname"
              value={eventname}
              label={`Change event name for ${eventname}`}
              onChange={props.onChangeEventname}
              haserror={props.errors.eventname && true}
              error={props.errors.eventname}
            />
            <Input
              name="date"
              type="date"
              value={date}
              label={`Change date for ${eventname}`}
              onChange={props.onChangeDate}
              haserror={props.errors.date && true}
              error={props.errors.date}
            />
            <Input
              name="qualifier"
              value={qualifier}
              label={`Change qualifier for ${eventname}`}
              onChange={props.onChangeQualifier}
              haserror={props.errors.qualifier && true}
              error={props.errors.qualifier}
            />
            <Input
              name="eventtype"
              value={eventtype}
              label={`Change event type for ${eventname}`}
              onChange={props.onChangeEventtype}
              haserror={props.errors.eventtype && true}
              error={props.errors.eventtype}
            />
            <Input
              name="maxentrants"
              value={maxentrants}
              label={`Change max entrants for ${eventname}`}
              onChange={props.onChangeMaxentrants}
              haserror={props.errors.maxentrants && true}
              error={props.errors.maxentrants}
            />
            <Input
              name="info"
              value={info}
              label={`Change info for ${eventname}`}
              onChange={props.onChangeInfo}
              haserror={props.errors.info && true}
              error={props.errors.info}
            />
            <Input
              name="cost"
              value={cost}
              label={`Change cost for ${eventname}`}
              onChange={props.onChangeCost}
              haserror={props.errors.info && true}
              error={props.errors.info}
            />
            <Input
              name="winner"
              value={winnerName}
              label={`Change winner for ${eventname}`}
              onChange={props.onChangeWinner}
              haserror={props.errors.winner && true}
              error={props.errors.winner}
            />
          </div>
        )}
        {showEditButton && (authorityJSON.role === 'ADMIN' || authorityJSON.role === 'EVENTADMIN' || authorityJSON.role === 'SUPERUSER') && (
          <button
          className="btn btn-outline-success"
          onClick={props.onClickEdit}
        >
            <i className="fas fa-user-edit" /> Edit Event
          </button>
        )}
        {props.inEditMode && (
          <div>
            <ButtonWithProgress
              className="btn btn-primary"
              onClick={props.onClickSave}
              text={
                <span>
                  <i className="fas fa-save" /> Save
                </span>
              }
              pendingApiCall={props.pendingUpdateCall}
              disabled={props.pendingUpdateCall}
            />
            <button
              className="btn btn-outline-secondary ml-1"
              onClick={props.onClickCancel}
              disabled={props.pendingUpdateCall}
            >
              <i className="fas fa-window-close" /> Cancel
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

EventProfileCard.defaultProps = {
  errors: {}
};

export default EventProfileCard;

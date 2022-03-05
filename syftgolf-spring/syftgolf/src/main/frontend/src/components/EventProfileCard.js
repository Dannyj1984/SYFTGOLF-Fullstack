import React from 'react';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';

const EventProfileCard = (props) => {
  const { name, date, maxEntrants, cost, type, qualifier, winner, info, ninetyFivePercent} = props.event;

  const showEditButton = props.isEditable && !props.inEditMode;

  const userObj = localStorage.getItem('syft-auth');
  const authorityJSON = JSON.parse(userObj);

  let winnerName = winner;

  if(winnerName === null) {
    winnerName = '';
  };
  //Format date from backend to be DD-MM-YYYY

  let yourDate = date;
  let formatDate = new Date(yourDate).toString().substring(0,15)
  
      let pHandicap = '100%';
      if(ninetyFivePercent) {
        pHandicap = "95%";
      }
  
  return (
    <div className="card">
      <div className="card-body text-center">
      {!props.inEditMode && (
      <h3>{`Event : ${name}`}</h3>)}
      <hr/>
      <h5>{`Date: ${formatDate}`}</h5>
      <h5>{`Format: ${type}`}</h5>
      <h5>{`Qualifier: ${qualifier}`}</h5>
      <h5>{`Max Entrants: ${maxEntrants}`}</h5>
      <h5>{`Cost: £${cost}`}</h5>
      <h5>{`Event info: ${info}`}</h5>
      <h5>{`Playing handicap: ${pHandicap}`}</h5>
      <h5>{`Winner: ${winner}` }</h5>
      
        {props.inEditMode && (
          <div className="mb-2">
          <hr/>
          <h3>{`Amend ${name}`}</h3>
            <Input
              name="name"
              value={name}
              label={`Change event name for ${name}`}
              onChange={props.onChangename}
              haserror={props.errors.name && true}
              error={props.errors.name}
            />
            <Input
              name="date"
              type="date"
              value={date}
              label={`Change date for ${name}`}
              onChange={props.onChangeDate}
              haserror={props.errors.date && true}
              error={props.errors.date}
            />
            <Input
              name="qualifier"
              value={qualifier}
              label={`Change qualifier for ${name}`}
              onChange={props.onChangeQualifier}
              haserror={props.errors.qualifier && true}
              error={props.errors.qualifier}
            />
            <Input
              name="type"
              value={type}
              label={`Change event type for ${name}`}
              onChange={props.onChangeEventtype}
              haserror={props.errors.type && true}
              error={props.errors.type}
            />
            <Input
              name="maxEntrants"
              value={maxEntrants}
              label={`Change max entrants for ${name}`}
              onChange={props.onChangeMaxentrants}
              haserror={props.errors.maxEntrants && true}
              error={props.errors.maxEntrants}
            />
            <Input
              name="info"
              value={info}
              label={`Change info for ${name}`}
              onChange={props.onChangeInfo}
              haserror={props.errors.info && true}
              error={props.errors.info}
            />
            <Input
              name="cost"
              value={cost}
              label={`Change cost for ${name}`}
              onChange={props.onChangeCost}
              haserror={props.errors.info && true}
              error={props.errors.info}
            />
            <Input
              name="winner"
              value={winnerName}
              label={`Change winner for ${name}`}
              onChange={props.onChangeWinner}
              haserror={props.errors.winner && true}
              error={props.errors.winner}
            />
            <div>
              <label>Set playing handicap at 95% for this event</label>
              <input 
                className='form-control' 
                type="checkbox" 
                name="ninetyFivePercent"
                onChange={props.onChangeNinetyFivePercent} 
                defaultChecked={ninetyFivePercent}
              />  
              {props.hasError && (
              <span className="invalid-feedback">{props.error}</span>
              )}
            </div>
            
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

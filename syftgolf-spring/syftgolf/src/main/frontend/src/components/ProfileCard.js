import React from 'react';
import ProfileImageWithDefault from './ProfileImageWithDefault';
import Input from './Input';
import ButtonWithProgress from './ButtonWithProgress';

const ProfileCard = (props) => {
  const { username, firstname, surname, handicap, email, image, mobile, cdh, homeclub, wins } = props.user;

  const showEditButton = props.isEditable && !props.inEditMode;


  
  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImageWithDefault
          alt="profile"
          width="200"
          height="200"
          image= {image}
          src={props.loadedImage}
          className="rounded-circle shadow"
        />
      </div>
      <div className="card-body text-center">
      {!props.inEditMode && 
      <h4>{`${firstname} ${surname}`}</h4>}
      <h5>{`Handicap: ${handicap}`}</h5>
      <h5>{`Email: ${email}`}</h5>
      <h5>{`Mobile: ${mobile}`}</h5>
      <h5>{`CDH: ${cdh}`}</h5>
      <h5>{`Home club: ${homeclub}`}</h5>
      <h5>{`Wins: ${wins}`}</h5>
        {props.inEditMode && (
          <div className="mb-2">
            <Input
              value={username}
              label={`Change username for ${username}`}
              onChange={props.onChangeUsername}
              haserror={props.errors.username && true}
              error={props.errors.username}
            />
            <Input
              value={handicap}
              label={`Change handicap for ${username}`}
              onChange={props.onChangeHandicap}
              haserror={props.errors.handicap && true}
              error={props.errors.handicap}
            />
            <Input
              value={email}
              label={`Change email for ${username}`}
              onChange={props.onChangeEmail}
              haserror={props.errors.email && true}
              error={props.errors.email}
            />
            <Input
              value={homeclub}
              label={`Change home club for ${username}`}
              onChange={props.onChangeHomeclub}
              haserror={props.errors.homeclub && true}
              error={props.errors.homeclub}
            />
            <Input
              value={mobile}
              label={`Change mobile for ${username}`}
              onChange={props.onChangeMobile}
              haserror={props.errors.mobile && true}
              error={props.errors.mobile}
            />
            <input
              type="file"
              onChange={props.onFileSelect}
              haserror={props.errors.image && true}
              error={props.errors.image}
            />
          </div>
        )}
        {showEditButton && (
          <button
          className="btn btn-outline-success"
          onClick={props.onClickEdit}
        >
            <i className="fas fa-user-edit" /> Edit Details
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

ProfileCard.defaultProps = {
  errors: {}
};

export default ProfileCard;

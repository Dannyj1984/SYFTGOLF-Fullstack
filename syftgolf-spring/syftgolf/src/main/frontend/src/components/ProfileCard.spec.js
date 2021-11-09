import React from 'react';
import { render } from '@testing-library/react';
import ProfileCard from './ProfileCard';
const user = {
  id: 1,
  username: 'user1',
  firstname: 'user1',
  surname: 'test',
  handicap: '5.2',
  email: 'user1@gmail.com',
  image: 'profile.png'
};

describe('ProfileCard', () => {
  describe('Layout', () => {
    it('displays the username', () => {
      const { queryByText } = render(<ProfileCard user={user} />);
      const userInfo = queryByText('user1 test');
      expect(userInfo).toBeInTheDocument();
    });
    it('has image', () => {
      const { container } = render(<ProfileCard user={user} />);
      const image = container.querySelector('img');
      expect(image).toBeInTheDocument();
    });
    it('displays default image when user does not have one', () => {
      const userWithoutImage = {
        ...user,
        image: undefined
      };
      const { container } = render(<ProfileCard user={userWithoutImage} />);
      const image = container.querySelector('img');
      expect(image.src).toContain('/profile.png');
    });
    it('displays user image when user has one', () => {
      const { container } = render(<ProfileCard user={user} />);
      const image = container.querySelector('img');
      expect(image.src).toContain('/images/profile/' + user.image);
    });
    it('contains user`s email address', () => {
        const { queryByText } = render(<ProfileCard user={user} />);
        const userEmail = queryByText('Email: user1@gmail.com');
        expect(userEmail).toBeInTheDocument();
    });
    it('displays edit button when isEditable property set as true', () => {
      const { queryByText } = render(
        <ProfileCard user={user} isEditable={true} />
      );
      const editButton = queryByText('Edit Username');
      expect(editButton).toBeInTheDocument();
    });
    it('does not display edit button when isEditable not provided', () => {
      const { queryByText } = render(<ProfileCard user={user} />);
      const editButton = queryByText('Edit');
      expect(editButton).not.toBeInTheDocument();
    });
    it('displays userName input when inEditMode property set as true', () => {
      const { container } = render(
        <ProfileCard user={user} inEditMode={true} />
      );
      const usernameInput = container.querySelector('input');
      expect(usernameInput).toBeInTheDocument();
    });
    it('displays the current username in input in edit mode', () => {
      const { container } = render(
        <ProfileCard user={user} inEditMode={true} />
      );
      const usernameInput = container.querySelector('input');
      expect(usernameInput.value).toBe(user.username);
    });
    it('hides the username in edit mode', () => {
      const { queryByText } = render(
        <ProfileCard user={user} inEditMode={true} />
      );
      const userInfo = queryByText('user1 handicap: 5.2');
      expect(userInfo).not.toBeInTheDocument();
    });
    it('displays label for username in edit mode', () => {
      const { container } = render(
        <ProfileCard user={user} inEditMode={true} />
      );
      const label = container.querySelector('label');
      expect(label).toHaveTextContent('Change username for user1');
    });
    it('hides the edit button in edit mode and isEditable provided as true', () => {
      const { queryByText } = render(
        <ProfileCard user={user} inEditMode={true} isEditable={true} />
      );
      const editButton = queryByText('Edit');
      expect(editButton).not.toBeInTheDocument();
    });
    it('displays Save button in edit mode', () => {
      const { queryByText } = render(
        <ProfileCard user={user} inEditMode={true} isEditable={true} />
      );
      const saveButton = queryByText('Save');
      expect(saveButton).toBeInTheDocument();
    });
    it('displays Cancel button in edit mode', () => {
      const { queryByText } = render(
        <ProfileCard user={user} inEditMode={true} isEditable={true} />
      );
      const cancelButton = queryByText('Cancel');
      expect(cancelButton).toBeInTheDocument();
    });
    it('displays file input when inEditMode property set as true', () => {
      const { container } = render(
        <ProfileCard user={user} inEditMode={true} />
      );
      const inputs = container.querySelectorAll('input');
      const uploadInput = inputs[1];
      expect(uploadInput.type).toBe('file');
    });
    
  });
});
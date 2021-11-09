import React from 'react';
import { render } from '@testing-library/react';
import UserListItem from './UserListItem';
import { MemoryRouter } from 'react-router-dom';

const user = {
  username: 'user1',
  handicap: '5.2',
  firstname: 'user1',
  surname: 'test',
  email: 'user1@gmail.com',
  mobile: '07777777777',
  cdh: '1010101010'
};

const setup = (propUser = user) => {
  return render(
    <MemoryRouter>
      <UserListItem user={propUser} />
    </MemoryRouter>
  );
};

describe('UserListItem', () => {
  it('has image', () => {
    const { container } = setup();
    const image = container.querySelector('img');
    expect(image).toBeInTheDocument();
  });
  it('displays default image when user does not have one', () => {
    const userWithoutImage = {
      ...user,
      image: undefined
    };
    const { container } = setup(userWithoutImage);
    const image = container.querySelector('img');
    expect(image.src).toContain('/profile.png');
  });
  it('displays users image when user have one', () => {
    const { container } = setup();
    const image = container.querySelector('img');
    expect(image.src).toContain('/images/profile/' + user.image);
  });
});
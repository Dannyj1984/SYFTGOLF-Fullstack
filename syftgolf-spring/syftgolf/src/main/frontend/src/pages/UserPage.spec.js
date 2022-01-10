import React from 'react';
import {
  render,
  fireEvent,
  waitForElementToBeRemoved,
  waitFor,
} from '@testing-library/react';
import UserPage from './UserPage';

import { Provider } from 'react-redux';
import configureStore from '../redux/configureStore';
import axios from 'axios';
import * as apiCalls from '../api/apiCalls';

const mockSuccessGetUser = {
  data: {
    id: 1,
    username: 'user1',
    handicap: '5.2',
    firstname: 'user1',
    surname: 'test',
    mobile: '07777777777',
    email: 'user1@gmail.com',
    cdh: '1010101010',
    socHcpRed: '0',
    socHcp: '5.2',
    homeclub: 'Stamford',
    image: '',
    wins: 0,
    role: 'ADMIN'
  }
};

const mockSuccessUpdateUser = {
  data: {
    id: 1,
    username: 'user1-update',
    image: 'profile1-update.png'
  }
};

const mockFailGetUser = {
  response: {
    data: {
      message: 'User not found'
    }
  }
};

const mockFailUpdateUser = {
  response: {
    data: {
      validationErrors: {
        username: 'It must have minimum 4 and maximum 255 characters',
        image: 'Only PNG and JPG files are allowed'
      }
    }
  }
};

const match = {
  params: {
    username: 'user1'
  }
};

let store;
const setup = (props) => {
   store = configureStore(false);
  return render(
    <Provider store={store}>
      <UserPage {...props} />
    </Provider>
  );
};
beforeEach(() => {
  localStorage.clear();
  delete axios.defaults.headers.common['Authorization'];
});
const setUserOneLoggedInStorage = () => {
  localStorage.setItem(
    'syft-auth',
    JSON.stringify({
      id: 1,
      username: 'user1',
      firstname: 'user1',
      surname: 'test',
      handicap: '5.2',
      mobile: '07777777777',
      email: 'user1@gmail.com',
      cdh: '1010101010',
      password: 'P4ssword',
      image: 'profile.png',
      role: "ADMIN",
      wins: 0,
      isLoggedIn: true
    })
  );
};

describe('UserPage', () => {
    describe('Layout', () => {
        it('has root page div', () => {
          const { queryByTestId } = setup()
          const userPageDiv = queryByTestId('userpage');
          expect(userPageDiv).toBeInTheDocument();
        });
        it('displays the username when user data loaded', async () => {
            apiCalls.getUser = jest.fn().mockResolvedValue(mockSuccessGetUser);
            const { findByText } = setup({ match });
            const text = await findByText('user1 test');
            expect(text).toBeInTheDocument();
        });
        it('displays not found alert when user not found', async () => {
          apiCalls.getUser = jest.fn().mockRejectedValue(mockFailGetUser);
          const { findByText } = setup({ match });
          const alert = await findByText('User not found');
          expect(alert).toBeInTheDocument();
        });
        it('displays spinner while loading user data', () => {
          const mockDelayedResponse = jest.fn().mockImplementation(() => {
            return new Promise((resolve, reject) => {
              setTimeout(() => {
                resolve(mockSuccessGetUser);
              }, 300);
            });
          });
          apiCalls.getUser = mockDelayedResponse;
          const { queryByText } = setup({ match });
          const spinner = queryByText('Loading...');
          expect(spinner).toBeInTheDocument();
        });
    });
    describe('Lifecycle', () => {
      it('calls getUser when it is rendered', () => {
        apiCalls.getUser = jest.fn().mockResolvedValue(mockSuccessGetUser);
        setup({ match });
        expect(apiCalls.getUser).toHaveBeenCalledTimes(1);
      });
      it('calls getUser for user1 when it is rendered with user1 in match', () => {
        apiCalls.getUser = jest.fn().mockResolvedValue(mockSuccessGetUser);
        setup({ match });
        expect(apiCalls.getUser).toHaveBeenCalledWith('user1');
      });
      it('displays the edit button when loggedInUser matches to user in url', async () => {
        setUserOneLoggedInStorage();
        apiCalls.getUser = jest.fn().mockResolvedValue(mockSuccessGetUser);
        const { queryByText, findByText } = setup({ match });
        await findByText('user1 test');
        const editButton = queryByText('Edit Details');
        expect(editButton).toBeInTheDocument();
      });
    });
    describe('ProfileCard Interactions', () => {

      const setupForEdit = async () => {
        setUserOneLoggedInStorage();
        apiCalls.getUser = jest.fn().mockResolvedValue(mockSuccessGetUser);
        const rendered = setup({ match });
        const editButton = await rendered.findByText('Edit Details')
        fireEvent.click(editButton);
        return rendered;
      };

      const mockDelayedUpdateSuccess = () => {
        return jest.fn().mockImplementation(() => {
          return new Promise((resolve, reject) => {
            setTimeout(() => {
              resolve(mockSuccessUpdateUser);
            }, 300);
          });
        });
      };

      it('displays edit layout when clicking edit button', async () => {
        const { queryByText } = await setupForEdit();
        expect(queryByText('Save')).toBeInTheDocument();
      });

      it('returns back to none edit mode after clicking cancel', async () => {
        const { queryByText } = await setupForEdit();
  
        const cancelButton = queryByText('Cancel');
        fireEvent.click(cancelButton);
  
        expect(queryByText('Edit Details')).toBeInTheDocument();
      });
      it('does not throw error after file not selected', async () => {
        const { container } = await setupForEdit();
        const inputs = container.querySelectorAll('input');
        const uploadInput = inputs[1];
        expect(() =>
          fireEvent.change(uploadInput, { target: { files: [] } })
        ).not.toThrow();
      });
      it('updates redux state after updateUser api call success', async () => {
        const { queryByRole, container } = await setupForEdit();
        let usernameInput = container.querySelector('input');
        fireEvent.change(usernameInput, { target: { value: 'user1-update' } });
        apiCalls.updateUser = jest.fn().mockResolvedValue(mockSuccessUpdateUser);
  
        const saveButton = queryByRole('button', { name: 'Save' });
        fireEvent.click(saveButton);
        await waitForElementToBeRemoved(saveButton);
        const storedUserData = store.getState();
        expect(storedUserData.username).toBe(
          mockSuccessUpdateUser.data.username
        );
        expect(storedUserData.image).toBe(mockSuccessUpdateUser.data.image);
      });
      it('updates localStorage after updateUser api call success', async () => {
        const { queryByRole, container } = await setupForEdit();
        let usernameInput = container.querySelector('input');
        fireEvent.change(usernameInput, { target: { value: 'user1-update' } });
        apiCalls.updateUser = jest.fn().mockResolvedValue(mockSuccessUpdateUser);
  
        const saveButton = queryByRole('button', { name: 'Save' });
        fireEvent.click(saveButton);
        await waitForElementToBeRemoved(saveButton);
        const storedUserData = JSON.parse(localStorage.getItem('syft-auth'));
        expect(storedUserData.username).toBe(
          mockSuccessUpdateUser.data.username
        );
        expect(storedUserData.image).toBe(mockSuccessUpdateUser.data.image);
      });
    });
  });

      

console.error = () => {};
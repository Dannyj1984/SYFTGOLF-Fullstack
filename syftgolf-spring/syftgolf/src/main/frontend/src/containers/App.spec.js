import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';
import { Provider } from 'react-redux';
import axios from 'axios';
import configureStore from '../redux/configureStore';
import * as apiCalls from '../api/apiCalls';

apiCalls.listUsers = jest.fn().mockResolvedValue({
  data: {
    content: [],
    number: 0,
    size: 9
  }
});

apiCalls.getUser = jest.fn().mockResolvedValue({
  data: {
    id: 1,
    username: 'user1',
    firstname: 'User1',
    surname: 'test',
    handicap: '5.2'
  }
});

const mockSuccessGetUser1 = {
  data: {
    id: 1,
    username: 'user1',
    firstname: 'User1',
    surname: 'test',
    handicap: '5.2'
  }
};
const mockSuccessGetUser2 = {
  data: {
    id: 2,
    username: 'user2',
    firstname: 'User2',
    surname: 'test',
    handicap: '5.2'
  }
};

const mockFailGetUser = {
  response: {
    data: {
      message: 'User not found'
    }
  }
};

const setup = (path) => {
  const store = configureStore(false);
  return render(
    <Provider store={store}>
      <MemoryRouter initialEntries={[path]}>
        <App />
      </MemoryRouter>
    </Provider>
  );
};

const changeEvent = (content) => {
  return {
    target: {
      value: content
    }
  };
};

const setUserOneLoggedInStorage = () => {
  localStorage.setItem(
    'syft-auth',
    JSON.stringify({
      id: 1,
      username: 'user1',
      firstname: 'User1',
      surname: 'Test',
      email: 'user1@gmail.com',
      homeclub: 'Augusta',
      mobile: '07777777777',
      cdh: '1010101010',
      handicap: '3.4',
      password: 'P4ssword',
      isLoggedIn: true
    })
  );
};

describe('App', () => {
  it('displays homepage when url is /', () => {
    const { queryByTestId } = setup('/');
    expect(queryByTestId('homepage')).toBeInTheDocument();
  });

  it('displays LoginPage when url is /login', () => {
    const { container } = setup('/login');
    const header = container.querySelector('h1');
    expect(header).toHaveTextContent('Login');
  });

  it('displays only LoginPage when url is /login', () => {
    const { queryByTestId } = setup('/login');
    expect(queryByTestId('homepage')).not.toBeInTheDocument();
  });

  it('displays UserSignupPage when url is /signup', () => {
    const { container } = setup('/signup');
    const header = container.querySelector('h1');
    expect(header).toHaveTextContent('Register');
  });

  it('displays userpage when url is other than /, /login or /signup', () => {
    const { queryByTestId } = setup('/user1');
    expect(queryByTestId('userpage')).toBeInTheDocument();
  });

  it('displays topBar when url is /', () => {
    const { container } = setup('/');
    const navigation = container.querySelector('nav');
    expect(navigation).toBeInTheDocument();
  });

  it('displays topBar when url is /login', () => {
    const { container } = setup('/login');
    const navigation = container.querySelector('nav');
    expect(navigation).toBeInTheDocument();
  });

  it('displays topBar when url is /signup', () => {
    const { container } = setup('/signup');
    const navigation = container.querySelector('nav');
    expect(navigation).toBeInTheDocument();
  });

  it('displays topBar when url is /user1', () => {
    const { container } = setup('/user1');
    const navigation = container.querySelector('nav');
    expect(navigation).toBeInTheDocument();
  });

  it('shows the UserSignupPage when clicking signup', () => {
    const { queryByText, container } = setup('/');
    const signupLink = queryByText('Sign Up');
    fireEvent.click(signupLink);
    const header = container.querySelector('h1');
    expect(header).toHaveTextContent('Register');
  });

  it('shows the LoginPage when clicking login', () => {
    const { queryByText, container } = setup('/');
    const loginLink = queryByText('Login');
    fireEvent.click(loginLink);
    const header = container.querySelector('h1');
    expect(header).toHaveTextContent('Login');
  });

  it('shows the HomePage when clicking the logo', () => {
    const { queryByTestId, container } = setup('/login');
    const logo = container.querySelector('img');
    fireEvent.click(logo);
    expect(queryByTestId('homepage')).toBeInTheDocument();
  });


it('displays My Profile on TopBar after login success', async () => {
  const { queryByPlaceholderText, container, findByText } = setup('/login');
  const changeEvent = (content) => {
    return {
      target: {
        value: content
      }
    };
  };
  const usernameInput = queryByPlaceholderText('Your username');
  fireEvent.change(usernameInput, changeEvent('user1'));
  const passwordInput = queryByPlaceholderText('Your password');
  fireEvent.change(passwordInput, changeEvent('P4ssword'));
  const button = container.querySelector('button');
  axios.post = jest.fn().mockResolvedValue({
    data: {
      id: 1,
      username: 'user1',
      handicap: '5.2'
    }
  });
  fireEvent.click(button);

  const myProfileLink = await findByText('My Profile');
  expect(myProfileLink).toBeInTheDocument();
  });

  it('displays My Profile on TopBar after signup success', async () => {
    const { queryByPlaceholderText, container, queryByText, findByText } = setup('/signup');
    const usernameInput = queryByPlaceholderText('Username');
    const firstNameInput = queryByPlaceholderText('First name');
    const surnameInput = queryByPlaceholderText('Surname');
    const emailInput = queryByPlaceholderText('Email');
    const mobileInput = queryByPlaceholderText('Mobile');
    const handicapInput = queryByPlaceholderText('Handicap');
    const cdhInput = queryByPlaceholderText('CDH');
    const homeclubInput = queryByPlaceholderText('Home Club');
    const passwordInput = queryByPlaceholderText('Password');
    const passwordRepeat = queryByPlaceholderText('Repeat password');

    
    fireEvent.change(usernameInput, changeEvent('user1'));
    fireEvent.change(firstNameInput, changeEvent('User1'));
    fireEvent.change(surnameInput, changeEvent('Test'));
    fireEvent.change(emailInput, changeEvent('user1@gmail.com'));
    fireEvent.change(mobileInput, changeEvent('07777777777'));
    fireEvent.change(handicapInput, changeEvent('3.4'));
    fireEvent.change(cdhInput, changeEvent('1010101010'));
    fireEvent.change(homeclubInput, changeEvent('Augusta'));
    fireEvent.change(passwordInput, changeEvent('P4ssword'));
    fireEvent.change(passwordRepeat, changeEvent('P4ssword'));

    const button = container.querySelector('button');
    axios.post = jest
      .fn()
      .mockResolvedValueOnce({
        data: {
          message: 'User saved'
        },
      })
      .mockResolvedValueOnce({
        data: {
          id: 1,
          username: 'user1',
          firstname: 'User1',
          surname: 'Test',
          email: 'user1@gmail.com',
          mobile: '07777777777',
          handicap: '3.4',
          cdh: '1010101010',
          homeclub: 'Augusta'
        },
      });

    fireEvent.click(button);

    const myProfileLink = await findByText('User1');
    expect(myProfileLink).toBeInTheDocument();
  });
  it('saves logged in user data to localStorage after login success', async () => {
    const { queryByPlaceholderText, container, findByText } = setup('/login');
    const usernameInput = queryByPlaceholderText('Your username');
    fireEvent.change(usernameInput, changeEvent('user1'));
    const passwordInput = queryByPlaceholderText('Your password');
    fireEvent.change(passwordInput, changeEvent('P4ssword'));
    const button = container.querySelector('button');
    axios.post = jest.fn().mockResolvedValue({
      data: {
        id: 1,
        username: 'user1',
      }
    });
    fireEvent.click(button);

    await findByText('Test1 User');
    const dataInStorage = JSON.parse(localStorage.getItem('syft-auth'));
    expect(dataInStorage).toEqual({
      id: 1,
      username: 'user1',
      password: 'P4ssword',
      isLoggedIn: true
    });
  });
  it('displays logged in topBar when storage has logged in user data', () => {
    setUserOneLoggedInStorage();
    const { queryByText } = setup('/');
    const myProfileLink = queryByText('My Profile');
    expect(myProfileLink).toBeInTheDocument();
  });
  it('sets axios authorization with base64 encoded user credentials after login success', async () => {
    const { queryByPlaceholderText, container, findByText } = setup('/login');
    const usernameInput = queryByPlaceholderText('Your username');
    fireEvent.change(usernameInput, changeEvent('user1'));
    const passwordInput = queryByPlaceholderText('Your password');
    fireEvent.change(passwordInput, changeEvent('P4ssword'));
    const button = container.querySelector('button');
    axios.post = jest.fn().mockResolvedValue({
      data: {
        id: 1,
        username: 'user1',
        handicap: '5.2'
      }
    });
    fireEvent.click(button);

    await findByText('My Profile');
    const axiosAuthorization = axios.defaults.headers.common['Authorization'];

    const encoded = btoa('user1:P4ssword');
    const expectedAuthorization = `Basic ${encoded}`;
    expect(axiosAuthorization).toBe(expectedAuthorization);
  });
  it('sets axios authorization with base64 encoded user credentials when storage has logged in user data', () => {
    setUserOneLoggedInStorage();
    setup('/');
    const axiosAuthorization = axios.defaults.headers.common['Authorization'];
    const encoded = btoa('user1:P4ssword');
    const expectedAuthorization = `Basic ${encoded}`;
    expect(axiosAuthorization).toBe(expectedAuthorization);
  });
  it('removes axios authorization header when user logout', async () => {
    setUserOneLoggedInStorage();
    const { queryByText } = setup('/');
    fireEvent.click(queryByText('Logout'));

    const axiosAuthorization = axios.defaults.headers.common['Authorization'];
    expect(axiosAuthorization).toBeFalsy();
  });
  it('updates user page after clicking my profile when another user page was opened', async () => {
    apiCalls.getUser = jest
      .fn()
      .mockResolvedValueOnce(mockSuccessGetUser2)
      .mockResolvedValueOnce(mockSuccessGetUser1);

    setUserOneLoggedInStorage();
    const { queryByText, findByText } = setup('/user2');

    await findByText('User2 test');

    const myProfileLink = queryByText('My Profile');
    fireEvent.click(myProfileLink);
    const user1Info = await findByText('User1 test');
    expect(user1Info).toBeInTheDocument();
  });
  it('updates user page after clicking my profile when another non existing user page was opened', async () => {
    apiCalls.getUser = jest
      .fn()
      .mockRejectedValueOnce(mockFailGetUser)
      .mockResolvedValueOnce(mockSuccessGetUser1);

    setUserOneLoggedInStorage();
    const { findByText } = setup('/user50');

    await findByText('User not found');

    const myProfileLink = await findByText('My Profile');
    fireEvent.click(myProfileLink);
    const user1Info = await findByText('User1 test');
    expect(user1Info).toBeInTheDocument();
  });
});

console.error = () => {};

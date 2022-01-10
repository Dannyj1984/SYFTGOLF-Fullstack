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
});

console.error = () => {};

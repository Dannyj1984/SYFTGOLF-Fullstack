import React from 'react';
import {
  render,
  fireEvent,
  waitForElementToBeRemoved,
} from '@testing-library/react';
import { LoginPage } from './LoginPage';
import { BrowserRouter } from 'react-router-dom';

describe('LoginPage', () => {
  describe('Layout', () => {
    it('has header of Login', () => {
      const { container } = render(<BrowserRouter> <LoginPage /> </BrowserRouter>);
      const header = container.querySelector('h1');
      expect(header).toHaveTextContent('Login');
    });

    it('has input for username', () => {
      const { queryByPlaceholderText } = render(<BrowserRouter><LoginPage /> </BrowserRouter>);
      const usernameInput = queryByPlaceholderText('Your username');
      expect(usernameInput).toBeInTheDocument();
    });

    it('has input for password', () => {
      const { queryByPlaceholderText } = render(<BrowserRouter><LoginPage /> </BrowserRouter>);
      const passwordInput = queryByPlaceholderText('Your password');
      expect(passwordInput).toBeInTheDocument();
    });

    it('has password type for password input', () => {
      const { queryByPlaceholderText } = render(<BrowserRouter><LoginPage /> </BrowserRouter>);
      const passwordInput = queryByPlaceholderText('Your password');
      expect(passwordInput.type).toBe('password');
    });
    it('has login button', () => {
      const { container } = render(<BrowserRouter><LoginPage /> </BrowserRouter>);
      const button = container.querySelector('button');
      expect(button).toBeInTheDocument();
    });
  });
  describe('Interactions', () => {
    const changeEvent = (content) => {
      return {
        target: {
          value: content
        }
      };
    };
    const mockAsyncDelayed = () => {
      return jest.fn().mockImplementation(() => {
        return new Promise((resolve, reject) => {
          setTimeout(() => {
            resolve({});
          }, 300);
        });
      });
    };
    let usernameInput, passwordInput, checkBox, button;

    const setupForSubmit = (props) => {
      const rendered = render(<BrowserRouter><LoginPage {...props} /> </BrowserRouter>);

      const { container, queryByPlaceholderText, queryByLabelText } = rendered;

      usernameInput = queryByPlaceholderText('Your username');
      fireEvent.change(usernameInput, changeEvent('my-user-name'));
      passwordInput = queryByPlaceholderText('Your password');
      fireEvent.change(passwordInput, changeEvent('P4ssword'));
      button = container.querySelector('button');
      checkBox =  queryByLabelText('Agree to T&Cs');
      fireEvent.change(checkBox.changeEvent(true));

      return rendered;
    };

    it('sets the username value into state', () => {
      const { queryByPlaceholderText } = render(<BrowserRouter><LoginPage /> </BrowserRouter>);
      const usernameInput = queryByPlaceholderText('Your username');
      fireEvent.change(usernameInput, changeEvent('my-user-name'));
      expect(usernameInput).toHaveValue('my-user-name');
    });
    it('sets the password value into state', () => {
      const { queryByPlaceholderText } = render(<BrowserRouter><LoginPage /> </BrowserRouter>);
      const passwordInput = queryByPlaceholderText('Your password');
      fireEvent.change(passwordInput, changeEvent('P4ssword'));
      expect(passwordInput).toHaveValue('P4ssword');
    });
  });
});

console.error = () => {};
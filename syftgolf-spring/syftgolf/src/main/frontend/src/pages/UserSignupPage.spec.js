  
import React from 'react';
import {
  render,
  fireEvent,
  waitForElementToBeRemoved,

} from '@testing-library/react';
import { UserSignupPage } from './UserSignupPage';

describe('UserSignupPage', () => {
  describe('Layout', () => {
    it('has header of Register', () => {
      const { container } = render(<UserSignupPage />);
      const header = container.querySelector('h1');
      expect(header).toHaveTextContent('Register');
    });
    it('has input for first name', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const firstnameInput = queryByPlaceholderText('First name');
      expect(firstnameInput).toBeInTheDocument();
    });
    it('has input for surname', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const surnameInput = queryByPlaceholderText('Surname');
      expect(surnameInput).toBeInTheDocument();
    });
    it('has input for username', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const usernameInput = queryByPlaceholderText('Username');
      expect(usernameInput).toBeInTheDocument();
    });
    it('has input for email', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const emailInput = queryByPlaceholderText('Email');
      expect(emailInput).toBeInTheDocument();
    });
    it('has input for mobile', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const mobileInput = queryByPlaceholderText('Mobile');
      expect(mobileInput).toBeInTheDocument();
    });
    it('has input for password', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const passwordInput = queryByPlaceholderText('Password');
      expect(passwordInput).toBeInTheDocument();
    });
    it('has password type for password input', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const passwordInput = queryByPlaceholderText('Password');
      expect(passwordInput.type).toBe('password');
    });
    it('has input for password repeat', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const passwordRepeat = queryByPlaceholderText('Repeat password');
      expect(passwordRepeat).toBeInTheDocument();
    });
    it('has password type for password repeat input', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const passwordRepeat = queryByPlaceholderText('Repeat password');
      expect(passwordRepeat.type).toBe('password');
    });
    it('has submit button', () => {
      const { container } = render(<UserSignupPage />);
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

    let button, homeclubInput, usernameInput, firstnameInput, surnameInput, emailInput,
        mobileInput, handicapInput, cdhInput, passwordInput, passwordRepeat;

    const setupForSubmit = (props) => {
      const rendered = render(<UserSignupPage {...props} />);

      const { container, queryByPlaceholderText } = rendered;

      usernameInput = queryByPlaceholderText('Username');
      firstnameInput = queryByPlaceholderText('First name');
      surnameInput = queryByPlaceholderText('Surname');
      emailInput = queryByPlaceholderText('Email');
      mobileInput = queryByPlaceholderText('Mobile');
      handicapInput = queryByPlaceholderText('Handicap');
      cdhInput = queryByPlaceholderText('CDH');
      homeclubInput = queryByPlaceholderText('Home Club');
      passwordInput = queryByPlaceholderText('Password');
      passwordRepeat = queryByPlaceholderText('Repeat password');

       fireEvent.change(usernameInput, changeEvent('new username'));
       fireEvent.change(firstnameInput, changeEvent('new firstname'));
       fireEvent.change(surnameInput, changeEvent('new surname'));
       fireEvent.change(emailInput, changeEvent('new email'));
       fireEvent.change(mobileInput, changeEvent('0777777777'));
       fireEvent.change(handicapInput, changeEvent('5.2'));
       fireEvent.change(homeclubInput, changeEvent('new homeclub'));
       fireEvent.change(cdhInput, changeEvent('1111111111'));
       fireEvent.change(passwordInput, changeEvent('P4ssword'));
       fireEvent.change(passwordRepeat, changeEvent('P4ssword'));

      button = container.querySelector('button');
      return rendered;
    };

    it('sets the username value into state', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const usernameInput = queryByPlaceholderText('Username');
      const changeEvent = {
        target: {
          value: 'username'
        }
      };
      fireEvent.change(usernameInput, changeEvent);
      expect(usernameInput).toHaveValue('username');
    });
    it('sets the firstname value into state', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const firstnameInput = queryByPlaceholderText('First name');
      const changeEvent = {
        target: {
          value: 'firstname'
        }
      };
      fireEvent.change(firstnameInput, changeEvent);
      expect(firstnameInput).toHaveValue('firstname');
    });
    it('sets the surname value into state', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const surnameInput = queryByPlaceholderText('Surname');
      const changeEvent = {
        target: {
          value: 'surname'
        }
      };
      fireEvent.change(surnameInput, changeEvent);
      expect(surnameInput).toHaveValue('surname');
    });
    it('sets the email value into state', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const emailInput = queryByPlaceholderText('Email');
      const changeEvent = {
        target: {
          value: 'email'
        }
      };
      fireEvent.change(emailInput, changeEvent);
      expect(emailInput).toHaveValue('email');
    });
    it('sets the mobile value into state', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const mobileInput = queryByPlaceholderText('Mobile');
      const changeEvent = {
        target: {
          value: '07777777777'
        }
      };
      fireEvent.change(mobileInput, changeEvent);
      expect(mobileInput).toHaveValue('07777777777');
    });
    it('sets the handicap value into state', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const handicapInput = queryByPlaceholderText('Handicap');
      const changeEvent = {
        target: {
          value: '5.2'
        }
      };
      fireEvent.change(handicapInput, changeEvent);
      expect(handicapInput).toHaveValue('5.2');
    });
    it('sets the CDH value into state', () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const cdhInput = queryByPlaceholderText('CDH');
      const changeEvent = {
        target: {
          value: '1111111111'
        }
      };
      fireEvent.change(cdhInput, changeEvent);
      expect(cdhInput).toHaveValue('1111111111');
    });
    it('enables the signup button when password and repeat password have same value', () => {
      setupForSubmit();
      expect(button).not.toBeDisabled();
    });

    it('disables the signup button when password repeat does not match password', () => {
      setupForSubmit();
      fireEvent.change(passwordRepeat, changeEvent('new-pass'));
      expect(button).toBeDisabled();
    });

    it('disables the signup button when password does not match password repeat', () => {
      setupForSubmit();
      fireEvent.change(passwordInput, changeEvent('new-pass'));
      expect(button).toBeDisabled();
    });
  });
});

console.error = ()=> {}

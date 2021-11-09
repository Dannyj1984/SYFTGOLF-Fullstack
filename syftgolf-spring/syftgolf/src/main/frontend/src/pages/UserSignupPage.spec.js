  
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
    it('calls postSignUp when the fields are valid and the actions are provided in props', () => {
      const actions = {
        //mockResolved value mocks successful result
        postSignup: jest.fn().mockResolvedValueOnce({})
      };
      setupForSubmit({ actions });
      fireEvent.click(button);
      expect(actions.postSignup).toHaveBeenCalledTimes(1);
       
    });
    it('does not throw exception when clicking the button when actions not provided in props', () => {
      setupForSubmit();
      expect(() => fireEvent.click(button)).not.toThrow();
    });
    it('calls post with user body when the fields are valid', () => {
      const actions = {
        postSignup: jest.fn().mockResolvedValueOnce({})
      };
      setupForSubmit({ actions });
      fireEvent.click(button);
      const expectedUserObject = {
        username: 'new username',
        firstname: 'new firstname',
        surname: 'new surname',
        email: 'new email',
        homeclub: 'new homeclub',
        password: 'P4ssword',
        cdh: '1111111111',
        mobile: '0777777777',
        handicap: '5.2'
      };
      expect(actions.postSignup).toHaveBeenCalledWith(expectedUserObject);
    });
    it('does not allow user to click the Sign Up button when there is an ongoing api call', () => {
      const actions = {
        postSignup: mockAsyncDelayed()
      };
      setupForSubmit({ actions });
      fireEvent.click(button);

      fireEvent.click(button);
      expect(actions.postSignup).toHaveBeenCalledTimes(1);
    });
    it('displays spinner when there is an ongoing api call', () => {
      const actions = {
        postSignup: mockAsyncDelayed()
      };
      const {queryByText} = setupForSubmit({ actions });
      fireEvent.click(button);

      const spinner = queryByText('Loading...')
      expect(spinner).toBeInTheDocument();
    });
    it('hides spinner after completion of successful API call', async() => {
      const actions = {
        postSignup: mockAsyncDelayed()
      };
      const {queryByText} = setupForSubmit({ actions });
      fireEvent.click(button);
      const spinner = queryByText('Loading...')
      await waitForElementToBeRemoved(spinner);

      expect(spinner).not.toBeInTheDocument();
    });
    it('hides spinner after API finishes with fail', async() => {
      const actions = {
        postSignup: jest.fn().mockImplementation(() => {
          return new Promise((resolve, reject) => {
            setTimeout(() => {
              reject({
                response: {data: {}}
              });
            }, 300);
          });
        })
      };
      const {queryByText} = setupForSubmit({ actions });
      fireEvent.click(button);
      const spinner = queryByText('Loading...')
      await waitForElementToBeRemoved(spinner);

      expect(spinner).not.toBeInTheDocument();
    });
    it('displays validation error for username when error is received for the field', async() => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                username: ' Cannot be null'
              }
            }
          }
        })
      }
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);
      const errorMessage = await findByText('Cannot be null');
      expect(errorMessage).toBeInTheDocument();
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

    it('hides the validation error when user changes the content of username', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                username: 'Cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('Cannot be null');
      fireEvent.change(usernameInput, changeEvent('name updated'));
      expect(errorMessage).not.toBeInTheDocument();
    });

    it('hides the validation error when user changes the content of firstname', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                firstname: 'Name cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('Name cannot be null');
      fireEvent.change(firstnameInput, changeEvent('name updated'));
      expect(errorMessage).not.toBeInTheDocument();
    });
    it('hides the validation error when user changes the content of surname', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                surname: 'Name cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('Name cannot be null');
      fireEvent.change(surnameInput, changeEvent('name updated'));
      expect(errorMessage).not.toBeInTheDocument();
    });
    it('hides the validation error when user changes the content of email', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                email: 'Email cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('Email cannot be null');
      fireEvent.change(emailInput, changeEvent('email updated'));
      expect(errorMessage).not.toBeInTheDocument();
    });
    it('hides the validation error when user changes the content of mobile', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                mobile: 'mobile cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('mobile cannot be null');
      fireEvent.change(mobileInput, changeEvent('name updated'));
      expect(errorMessage).not.toBeInTheDocument();
    });
    it('hides the validation error when user changes the content of handicap', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                handicap: 'Handicap cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('Handicap cannot be null');
      fireEvent.change(handicapInput, changeEvent('handicap updated'));

      
      expect(errorMessage).not.toBeInTheDocument();
    });
    it('hides the validation error when user changes the content of cdh', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                cdh: 'CDH cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('CDH cannot be null');
      fireEvent.change(cdhInput, changeEvent('CDH updated'));
      
      expect(errorMessage).not.toBeInTheDocument();
    });
    it('hides the validation error when user changes the content of password', async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: {
              validationErrors: {
                password: 'Cannot be null'
              }
            }
          }
        })
      };
      const { findByText } = setupForSubmit({ actions });
      fireEvent.click(button);

      const errorMessage = await findByText('Cannot be null');
      fireEvent.change(passwordInput, changeEvent('updated-password'));

      
      expect(errorMessage).not.toBeInTheDocument();
    });
    it('redirects to LoginPage after successful signup', async () => {
      const actions = {
        postSignup: jest.fn().mockResolvedValue({})
      };
      const history = {
        push: jest.fn()
      };
      const { queryByText } = setupForSubmit({ actions, history });
      fireEvent.click(button);

      await waitForElementToBeRemoved(() => queryByText('Loading...'))

      expect(history.push).toHaveBeenCalledWith('/login');
    });
  });
});

console.error = ()=> {}

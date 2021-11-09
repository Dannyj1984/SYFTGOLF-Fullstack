import React from 'react';
import {
  render,
  fireEvent,
  waitFor
} from '@testing-library/react';
import UserList from './UserList';
import * as apiCalls from '../api/apiCalls';
import { MemoryRouter } from 'react-router-dom';

apiCalls.listUsers = jest.fn().mockResolvedValue({
  data: {
    content: [],
    number: 0,
    size: 9
  }
});

const setup = () => {
  return render(
    <MemoryRouter>
      <UserList />
    </MemoryRouter>
  );
};

const mockedEmptySuccessResponse = {
  data: {
    content: [],
    number: 0,
    size: 9
  }
};

const mockSuccessGetSinglePage = {
  data: {
    content: [
      {
        username: 'user1',
        handicap: '5.2'
      },
      {
        username: 'user2',
        handicap: '13.1'
      },
      {
        username: 'user3',
        handicap: '21.3'
      }
    ],
    number: 0,
    first: true,
    last: true,
    size: 9,
    totalPages: 1
  }
};

const mockSuccessGetMultiPageFirst = {
  data: {
    content: [
      {
        username: 'user1',
        handicap: '5.2'
      },
      {
        username: 'user2',
        handicap: '5.1'
      },
      {
        username: 'user3',
        handicap: '5.3'
      }
    ],
    number: 0,
    first: true,
    last: false,
    size: 9,
    totalPages: 2
  }
};

const mockSuccessGetMultiPageLast = {
  data: {
    content: [
      {
        username: 'user4',
        handicap: '5.3'
      }
    ],
    number: 1,
    first: false,
    last: true,
    size: 9,
    totalPages: 2
  }
};

const mockFailGet = {
  response: {
    data: {
      message: 'Load error'
    }
  }
};

describe('UserList', () => {
  describe('Layout', () => {
    it('has header of Members', () => {
      const { container } = setup();
      const header = container.querySelector('h3');
      expect(header).toHaveTextContent('Members');
    });
    it('displays three items when listUser api returns three users', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockSuccessGetSinglePage);
      const { queryByTestId } = setup();
      await waitFor(() => {
        const userGroup = queryByTestId('usergroup');
        expect(userGroup.childElementCount).toBe(3);
      });
    });
    it('displays the username when listUser api returns users', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockSuccessGetSinglePage);
      const { findByText } = setup();
      const firstUser = await findByText('User1 Test');
      expect(firstUser).toBeInTheDocument();
    });
    it('displays the next button when response has last value as false', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockSuccessGetMultiPageFirst);
      const { findByText } = setup();
      const nextLink = await findByText('Next');
      expect(nextLink).toBeInTheDocument();
    });
    it('hides the next button when response has last value as true', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockSuccessGetMultiPageLast);
      const { findByText } = setup();
      const nextLink = await findByText('Next');
      expect(nextLink).not.toBeInTheDocument();
    });
    it('displays the previous button when response has first value as false', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockSuccessGetMultiPageLast);
      const { findByText } = setup();
      const previous = await findByText('Previous');
      expect(previous).toBeInTheDocument();
    });
    it('hides the previous button when response has first value as true', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockSuccessGetMultiPageFirst);
      const { findByText } = setup();
      const previous = await findByText('Previous');
      expect(previous).not.toBeInTheDocument();
    });
    it('has link to UserPage', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockSuccessGetSinglePage);
      const { findByText, container } = setup();
      await findByText('User1 Text');
      const firstAnchor = container.querySelectorAll('a')[0];
      expect(firstAnchor.getAttribute('href')).toBe('/user1');
    });
  });
  

  describe('Lifecycle', () => {
    it('calls listUsers api when it is rendered', () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockedEmptySuccessResponse);
      setup();
      expect(apiCalls.listUsers).toHaveBeenCalledTimes(1);
    });
    it('calls listUsers method with page zero and size three', () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValue(mockedEmptySuccessResponse);
      setup();
      expect(apiCalls.listUsers).toHaveBeenCalledWith({ page: 0, size: 3 });
    });
  });

  describe('Interactions', () => {
    it('loads next page when clicking the next button', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValueOnce(mockSuccessGetMultiPageFirst)
        .mockResolvedValueOnce(mockSuccessGetMultiPageLast);
      const { findByText } = setup();
      const nextLink = await findByText('Next');
      fireEvent.click(nextLink);

      const secondPageUser = await findByText('User4 test');
      expect(secondPageUser).toBeInTheDocument();
    });
    it('loads previous page when clicking the previous button', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValueOnce(mockSuccessGetMultiPageLast)
        .mockResolvedValueOnce(mockSuccessGetMultiPageFirst);
      const { findByText } = setup();
      const previousLink = await findByText('Previous');
      fireEvent.click(previousLink);

      const firstPageUser = await findByText('User1 Test');
      expect(firstPageUser).toBeInTheDocument();
    });
    it('displays error message when loading other page fails', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValueOnce(mockSuccessGetMultiPageLast)
        .mockRejectedValueOnce(mockFailGet);
      const { findByText } = setup();
      const previousLink = await findByText('Previous');
      fireEvent.click(previousLink);

      const errorMessage = await findByText('User load failed');
      expect(errorMessage).toBeInTheDocument();
    });
    it('hides error message when successfully loading other page', async () => {
      apiCalls.listUsers = jest
        .fn()
        .mockResolvedValueOnce(mockSuccessGetMultiPageLast)
        .mockRejectedValueOnce(mockFailGet)
        .mockResolvedValueOnce(mockSuccessGetMultiPageFirst);
      const { findByText } = setup();
      const previousLink = await findByText('Previous');
      fireEvent.click(previousLink);
      await findByText('User load failed');
      fireEvent.click(previousLink);
      const errorMessage = await findByText('User load failed');
      expect(errorMessage).not.toBeInTheDocument();
    });
  });
});

console.error = () => {};
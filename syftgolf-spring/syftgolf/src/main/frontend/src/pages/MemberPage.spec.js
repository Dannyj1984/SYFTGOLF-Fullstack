import React from 'react';
import { render } from '@testing-library/react';
import MemberPage from './MemberPage';
import * as apiCalls from '../api/apiCalls';

apiCalls.listUsers = jest.fn().mockResolvedValue({
  data: {
    content: [],
    number: 0,
    size: 9
  }
});

describe('MemberPage', () => {
    describe('Layout', () => {
        it('has root page div', () => {
            const { queryByTestId } = render(<MemberPage />);
            const memberPageDiv = queryByTestId('memberPage');
            expect(memberPageDiv).toBeInTheDocument();
        });
    });
  });
  
  console.error = () => {};

import React from 'react'
import { Alert } from 'react-bootstrap';
import Input from './Input';

const Search = () => {

    const click = () => {
        alert('Coming soon!!');
    }

    return (
        <div className="row">
            <Input type="text" placeholder="Search" />
            <button className="btn btn-primary" onClick={click}>Search</button>
        </div>
    )

};

export default Search;
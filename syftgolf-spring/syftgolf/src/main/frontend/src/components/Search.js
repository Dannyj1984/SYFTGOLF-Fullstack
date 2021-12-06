import React from 'react'
import Input from './Input';

const Search = (props) => {

    const click = () => {
        alert('Coming soon!!');
    }

    return (
        <div className="row">
            <Input name="filter" type="text" placeholder="Search" onChange={props.onChangeFilter} />
            <button className="btn btn-primary" onClick={props.onChangeFilter}>Search</button>
        </div>
    )

};

export default Search;
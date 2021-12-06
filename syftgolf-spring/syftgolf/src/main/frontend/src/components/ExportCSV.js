import React, { useState, useEffect } from 'react';
import { CSVLink } from 'react-csv';
import * as apiCalls from '../api/apiCalls';
import { connect } from 'react-redux';

const ExportCSV = (props) => {

const [fileData, setFileData] = useState();

// json key should match the header's key

const [fileHeaders] = useState([
  {label: 'User ID', key: 'User ID'},
  {label: 'E-mail', key: 'E-mail'},
  {label: 'First Name', key: 'First Name'},
  {label: 'Surname', key: 'Surname'},
  {label: 'CDH', key: 'CDH'},
  {label: 'Handicap', key: 'Handicap'},
])


const handleDataFetch = async() => {
  let id = props.user.society.id;
  const response = await apiCalls.userCSV(id);
  setFileData(response.data)
};

useEffect(()=>{
  handleDataFetch();
}, [])

const [fileName] = useState(props.user.society.name + " members.csv");


return (
  <div>
    {fileData?.length &&
    <button className="btn btn-outline-info">
      <CSVLink
        headers={fileHeaders}
        data={fileData}
        filename={fileName}
        target="_blank"
      >
        Export to CSV
      </CSVLink>
      </button>
    }
  </div>
);
}

const mapStateToProps = (state) => {
  return {
    user: state
  };
};


export default connect(mapStateToProps)(ExportCSV);
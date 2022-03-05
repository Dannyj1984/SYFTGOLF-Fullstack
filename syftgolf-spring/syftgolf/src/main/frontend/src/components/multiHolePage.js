import React from 'react';
import { useLocation } from "react-router-dom";
import Input from '../components/Input';

const MultiHolePage = (props) => {

    const location = useLocation();
    // get the form props from parent 
    const { form } = props;

    //Get the course id from the location hook
    const courseid = location.state.courseid;

    
 
    // there is this onChange function to take the user entries
    const onChange = (event) => {
        const { name, value } = event.target;
        // whenever entries updated, we will notify the parent component with this new value
        // and for this we take another prop
        props.onChange(props.index, name, value)
        // this sign up form is part of an array, therefore we need to know which one is having change
        // so as an identifier, we can pass this specific form's index in forms array
        // parent can pass this information to SignUpForm. And then we can use it right here
        // and since we only care about the name and value coming from event target, we can directly pass them
    }

    
 
 
  // Hole register form
  return (
    <div className="card">
        <h3 className="text-center card-header">Create Hole  </h3>
        <div className="card-body row">
          <div className="col-6 mb-3">
            <Input
              name="holeNumber"
              label="Hole"
              placeholder="Hole"
              value={form.holeNumber}
              onChange={onChange}
            />
          </div>
          <div className="col-6 mb-3">
            <Input
              name="par"
              label="Par"
              placeholder="Par"
              value={form.par}
              onChange={onChange}
            />
          </div>
          <div className="col-6 mb-3">
            <Input
              name="strokeIndex"
              label="Stroke Index"
              placeholder="Stroke Index"
              value={form.strokeIndex}
              onChange={onChange}
            />
          </div>
          <div className="col-6 mb-3">
            <Input
              name="yards"
              label="Yardage"
              placeholder="Yardage"
              value={form.yards}
              onChange={onChange}
            />
          </div>
          {/*Hide this input as we only need it to store the courseid*/}
          <div className="col-6 mb-3">
            <Input
              name="courseid"
              value={courseid}
              type="hidden"
            />
          </div>
        </div>
    </div>
  )
}

export default MultiHolePage;

import React, {useState} from 'react';
import ButtonWithProgress from '../components/ButtonWithProgress';
import MultiHolePage from '../components/multiHolePage';
import * as apiCalls from '../api/apiCalls';
import { useLocation } from "react-router-dom";

const MultipleSignUp = (props) => {

  const location = useLocation();

  const newCourseid = location.state.courseid;
    // we have array of form
    const [forms, setForms] = useState([
      {
        hole: "",
        par: "",
        stroke: "",
        yards: "",
        course: {
          courseid: newCourseid
        }
      },
    ]);
    
    // this function will be called by the SignUpForm
    // this is taking the index as param and also the name and value of that input
    const onChange = (index, name, value) => {
      // then here we can update the forms array
      setForms((previousForms) => {
          const copyForms = [...previousForms]
          // we know which form is being updated by checking the index.
          copyForms[index][name] = value;
        return copyForms;
        
      });
    };
   
    const onClickSignup = () => {
      // forms is containing the data you are looking for
      apiCalls
      .addHoleDetails(forms)
      .then((response) => {
        props.history.push('/courses');
      })
    };
   
    // this is for adding new hole form to the screen.
    const addNew = () => {
      setForms((previousForms) => {
        return [
          ...previousForms,
          {
            hole: "",
            par: "",
            stroke: "",
            yards: "",
            course: {
              courseid: newCourseid
            }
          },
        ];
      });
    };
   
    return (
      <div className="container">
      <div className="row">
  {/* here looping in form array and showing Hole form for each by setting necessary props
   */}
         { forms.map((form, index) => <MultiHolePage key={index} index={index} onChange={onChange} form={form}/>)}

        <div className="text-center col-6">
        <button className="btn btn-primary" onClick={addNew}>
          Add Extra hole
        </button>
          
          </div>
          <div className="col-6">
          <ButtonWithProgress
            onClick={onClickSignup}
            text="Add Holes"
          />
        </div>
        </div>
      </div>
    );
  };

  export default MultipleSignUp;

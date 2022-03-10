import React from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';
import MemberPage from '../pages/MemberPage';
import LoginPage from '../pages/LoginPage';
import UserSignupPage from '../pages/UserSignupPage';
import EventSignupPage from '../pages/EventSignupPage';
import UserPage from '../pages/UserPage';
import TopBar from '../components/TopBar';
import CoursePage from '../pages/CoursePage';
import HomePage from '../pages/HomePage.js';
import AboutPage from '../pages/AboutPage';
import Footer from '../components/Footer';
import CourseRegisterPage from '../pages/CourseRegisterPage';
import CourseInfoPage from '../pages/CourseInfoPage';
import EventInfoPage from '../pages/EventInfoPage';
import Events from '../pages/Events';
import PreviousEvents from '../pages/PreviousEvents';
import Terms from '../pages/Terms';
import HolesPage from '../pages/HolesPage';
import MultipleSignUp from '../components/multiHolePage';
import SocietySignupPage from "../pages/SocietySignupPage";
import FedExCup from '../pages/SYFTCup';



function App() {
  return (
    <div>
      <TopBar />
      <div className="container">
      {/* When using switch need it will try each route in order. If two routes match it will show the first one. */}
        <Switch>
          <Redirect exact from="/" to="/login" />
          <Redirect exact from="/member/login" to="/login" />
          <Redirect exact from="/event/login" to="/login" />
          <Redirect exact from="/course/login" to="/login" />
          <Route  path="/" exact component={HomePage} />
          <Route  path="/about" exact component={AboutPage} />
          <Route  path="/login" exact component={LoginPage} />
          <Route  path="/test" exact component={MultipleSignUp} />
          <Route  path="/terms" exact component={Terms} />
          <Route  path="/holes" exact component={HolesPage} />
          <Route  path="/signup" exact component={UserSignupPage} />
          <Route  path="/createSociety" exact component={SocietySignupPage} />
          <Route  path="/coursesignup" exact component={CourseRegisterPage} />
          <Route  path="/eventSignup" exact component={EventSignupPage} />
          <Route  path="/members" exact component={MemberPage} />
          <Route  path="/events" exact component={Events} />
          <Route  path="/previousEvent" exact component={PreviousEvents} />
          <Route  path="/courses" exact component={CoursePage} />
          <Route  path="/member/:username" exact component={UserPage} />
          <Route path="/course/:coursename" extact component={CourseInfoPage} />
          <Route path="/event/:eventname" extact component={EventInfoPage} />
          <Route path="/syftCup" extact component={FedExCup} />
          
        </Switch>
      </div>
      <Footer />
    </div>
  );
}

export default App;

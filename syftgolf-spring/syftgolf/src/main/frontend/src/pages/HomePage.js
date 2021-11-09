import React from 'react';
import { Link } from 'react-router-dom';

export class HomePage extends React.Component {

    state = {
        isLoggedIn: false,
        firstname: '',
        date: new Date()
    }

    

    componentDidMount() {
        const ls = JSON.parse(localStorage.getItem('syft-auth'));
        const name = ls.firstname;
        if(ls.id === 0) {
            this.setState({
                isLoggedIn: false
            });
        } else {
            this.setState({
                isLoggedIn: true,
                firstname: name
            });
        }
    }

    render() {
        return(
            !this.state.isLoggedIn ?
            <div className="container" data-testid="homepage">
                <h1 className="text-center">Welcome to SYFT golf society</h1>
                
                <p> Existing member please <Link
                to={`/login`}>login
                </Link>
                </p>

                <p>To enquire about joining, please read <Link
                to={`/about`}>read more
                </Link></p>

            </div>
            :

            <div className="container">
                <h2>Welcome {this.state.firstname}</h2>

            </div>

        )
    };

};

export default HomePage;
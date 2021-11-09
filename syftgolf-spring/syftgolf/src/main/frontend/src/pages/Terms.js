import React from 'react';

export class AboutPage extends React.Component {


    render() {
        return(
            <div className="container">
                <h1 className="text-center">Welcome to SYFT golf society</h1>
                <h3 className="text-center"> Terms & Conditions </h3>
                <div className="row">
                    <div className="card col-l-4">
                        <div className="card-body">
                            <p>By creating an account and logging in, you abide by the terms set out below </p>
                            <ul>
                                <li>Your details will be visible to other members of the society, including but not limited to
                                name, email, phone number</li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>

        )
    };

};

export default AboutPage;
import React from 'react';

export class TestPage extends React.Component {


    render() {
        return(
            <div className="container">
                
                <h1 className="text-center">Welcome to SYFT golf society</h1>
                <div className="row">
                    <div className="card col-l-4">
                        <div className="card-body">
                            <p>"Let's face it, we're all here to think that that great iron we 
                                just hit proves we COULD be scratch golfers if we really wanted to. 
                                But so long as someone hits worse shots than you, we've always got a friend to rip the piss out of." 
                                Lee O'Connell (Society Handicap Secretary)</p>
                        </div>
                    </div>

                </div>

                
                <h2 className="text-center">Aims of the society</h2>
                <div className="row">

                    <div className="card col-l-3 m-3">
                        <div className="card-title">
                            <h4>Ethos</h4>
                        </div>
                        <div className="card-body">
                            <p>This is the ethos that binds our northwest based golf society which was formed in 
                                2020 by a group of friends and strangers that first met on a golf trip to portugal 
                                in 2019. We have a range of playing abilities from 6 to 25 handicaps, and we all have 
                                one thing in common, our love for the real "beautiful game".</p>
                        </div>
                    </div>

                    <div className="card col-l-3 m-3">
                        <div className="card-title">
                            <h4>Playing</h4>
                        </div>
                        <div className="card-body">
                        <p>We hold regular golf days usually playing once or twice a month. There are also arranged games 
                            between memebers, usually playing at each others courses for members guest fees. We also try to play
                            in opens once a month and have an annual weekend away.
                        </p>
                        </div>
                    </div>

                    <div className="card col-l-3 m-3">
                        <div className="card-title">
                            <h4>Playing</h4>
                        </div>
                        <div className="card-body">
                            <p>This is the ethos that binds our northwest based golf society which was formed in 
                                2020 by a group of friends and strangers that first met on a golf trip to portugal 
                                in 2019. We have a range of playing abilities from 6 to 25 handicaps, and we all have 
                                one thing in common, our love for the real "beautiful game".</p>
                        </div>
                    </div>
                </div>
                <div className="text-center">
                
                    <h2 className="text-center">Want to join us?</h2>
                    <a className="btn btn-primary " href="mailto:admin@syftgolf.co.uk"> Contact</a>
                    <hr></hr>
                </div>

            </div>
        )
    };

};

export default TestPage;

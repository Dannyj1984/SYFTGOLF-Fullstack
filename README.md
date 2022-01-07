This repo contains the full code for my Golf Society web application. The Application uses the React JS library and Spring framework as well as a postgres database. 

Controllers accept the API calls from the frontend and return the required data from the postgres database. I am a competetant Java developer and this project was used as my final year project for my computing degree. This application is constantly being updated with new features and is currently in use by my golf society.

The application holds data on the members and is used to track their current handicaps, and local society handicap adjustments, wins etc. Current and past events. Current events can be created and members can view and enter these via the app. Once an event is full, then the app has the capability of creating a random draw and creating a tee sheet for the members in that event with the results of the random draw. Each event shows a list of entrants and also a leaderboard with members inputting their scores after a round. Once an event is complete, the admin team will close and event and set the winnner of that event. Courses can also be created and I am in the process of setting up a live leaderboard, which will use the individual holes on a course so that emmbers can enter the scores as they go through their round.

The frontend is hosted on netlify with the backend hosted on heroku. The site can be found at https://syftgolf.netlify.app, if you would like to try out the application please log in with 'testuser' 'P4ssword' This is a test ADMIN account and not linked to active accounts, so please feel free to have a play around, the default accounts are locked and you will be unable to edit them, but please try out adding new events, members and courses.



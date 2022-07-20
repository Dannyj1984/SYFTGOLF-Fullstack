#SYFT GOLF Society Application

This repo contains the backend code for my Golf Society web application written in the Java framework Spring. 

Controllers accept the API calls from the frontend and return the required data from the postgres database. As a recent Computer science graduate, this project was used as my final year project for my computing degree. This application is constantly being updated with new features and is currently in use by a golf society.

The application holds data on the members and is used to track their current handicaps, and local society handicap adjustments, wins etc. Current and past events. Current events can be created and members can view and enter these via the app. Once an event is full, then the app has the capability of creating a random draw and creating a tee sheet for the members in that event with the results of the random draw. Each event shows a list of entrants and also a leaderboard with members inputting their scores during or after a round. Inputting during a round will allow there to be a live laederboard. Once an event is complete, the admin team will complete the event. There is an ongoing yearlong sub competition with each event counting toward this. When completing an event, this subevent is automatically updated. Courses can also be created which are used for the scores and also link to a weather API to give a real time weather forecast for each course.

The frontend is hosted on netlify with the backend hosted on heroku. The site can be found at https://syftgolf.netlify.app, if you would like to try out the application please log in with 'testuser' 'P4ssword' This is a test ADMIN account and not linked to active accounts, so please feel free to have a play around, the default accounts are locked and you will be unable to edit them, but please try out adding new events, members and courses. I would love to hear any feedback, dannyjebb@gmail.com



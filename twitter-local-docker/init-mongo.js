
// Creating app user credentials. This file uses Java script because mongoDB's mongosh(shell) interprets this into DB commands.
db = db.getSiblingDB('Twitter-Clone');

db.createUser({
    user: "twitterUser",
    pwd: "twitterPass",
    roles: [
        { role: "readWrite", db: "Twitter-Clone"}
    ]
});
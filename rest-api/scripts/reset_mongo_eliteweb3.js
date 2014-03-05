db.team.drop()
db.team.createIndex({ name: 1, age: 1}, {unique: true})

db.player.drop()
db.player.createIndex({ firstname: 1, lastname: 1}, {unique: true})

/*
prove that unique constraint is working 
db.team.insert({ _id: "abc", name: "name 1", age: "15U", level: "Major" })
db.team.insert({ _id: "xyz", name: "name 1", age: "15U", level: "Major" })
db.player.insert({ _id: "abc", firstname: "player fname 1", lastname: "player lname 1", email: "bob@gmail.com" })
db.player.insert({ _id: "xyz", firstname: "player fname 1", lastname: "player lname 1", email: "bob2@gmail.com" })
*/

/** seed data for e2e tests */
db.team.insert({ _id: "e2e-tests-sample-team", name: "Austin Elite 15U", age: "15U", level: "Major" })
db.team.insert({ _id: "e2e-tests-sample-team2", name: "Austin Elite 14U", age: "14U", level: "Major" })

db.player.insert({ _id: "e2e-tests-sample-player", firstname: "Michael", lastname: "Kronschnabl", email: "mkron00@gmail.com", phone: "512-555-1212", jerseyNum: "32" })
db.player.insert({ _id: "e2e-tests-sample-player2", firstname: "Parker", lastname: "Dame", email: "pdaimgmail.com", phone: "512-555-1212", jerseyNum: "34" })


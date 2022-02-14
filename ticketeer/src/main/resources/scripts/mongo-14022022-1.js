use xyzticketeer

db.createUser({user: "xyzticketeer", pwd: "xyzticketeer", roles:[{role: "userAdminAnyDatabase" , db:"admin"}]})

// db.dropUser("xyzticketeer", {w: "majority", wtimeout: 5000})

// URL:
// mongodb://xyzticketeer:xyzticketeer@localhost:27017/ticketeer?maxPoolSize=500&authSource=admin




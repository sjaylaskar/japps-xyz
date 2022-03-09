use xyzticketeer

db.createUser({user: "xyzticketeer", pwd: "xyzticketeer", roles:[{role: "userAdminAnyDatabase" , db:"admin"}]})

// db.dropUser("xyzticketeer", {w: "majority", wtimeout: 5000})

// URL:
// mongodb://xyzticketeer:xyzticketeer@localhost:27017/ticketeer?maxPoolSize=500&authSource=admin



mongodb://xyzticketeer:xyzticketeer@localhost:27017/ticketeer?authSource=ticketeer&maxPoolSize=500&readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false
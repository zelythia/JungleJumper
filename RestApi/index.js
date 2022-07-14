const mysql = require('mysql'); 
const express = require('express');
const { host, user, password, database} = require('./config.json');

const app = express();
const PORT = 8080;

const db = mysql.createConnection({
    host: host,
    user: user,
    password: password,
    database: database
});
db.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
});

app.use(express.json());
app.listen(
    PORT,
    () => console.log("listening on localhost:8080")
);


app.get('/top', (req, result) => {
    
    db.query(`SELECT * FROM jumpkingish WHERE 1 ORDER BY (1000*score)/time DESC`, (err, res, fields) => {
        if(err){
            res.status(400).send();
            return;
        }

        var data = {"top": []};
        for(var d of res){
            data.top.push(d);
        }

        result.status(200).send(data);
    });
});  


app.post("/add/:name", (req, res) => {
    db.query(`INSERT INTO jumpkingish (name, score, time) VALUES ('${req.params.name.replace(":","")}', '${req.body.score}', '${req.body.time}');`, (err, r, fields) => {
        if(err){
            console.log(err);
            res.status(400).send();
        }
        else{
            res.status(200).send();
        }
    });
});

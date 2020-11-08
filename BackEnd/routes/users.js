const mysql = require('mysql')
const { use } = require('../backend_server.js')
const sql = require("./../sql_connection.js")
const con = sql.getConnection()

/**
 * Gets details of a user
 * @param {*} req Params include id
 * @param {*} res Returns the 
 * information with a status code of 200 
 * if successful, otherwise 400
 */
function getUser(req, res){
    console.log("/users/{{id}}")
    let id = req.params.id
    let sql_query = mysql.format("SELECT * FROM users WHERE id = ?", [id])
    con.query(sql_query, function(err, result){
        if (err) {
            res.status(400).send({code : err.code, errno : err.errno})
        }
        res.status(200).send(result)
    })
}

/**
 * Adds details of a user
 * @param {*} req Params include id
 * @param {*} res Returns a status code of 200 
 * if successful, otherwise 400
 */
function addUser(req, res){
    console.log("/users")
    let username = req.body.username
    let email = req.body.email
    let preferences = req.body.preferences
    let createdAt = (new Date()).getTime(); 

    let sql_query = mysql.format("INSERT INTO users (username, email, preferences, createdAt) VALUES (?, ?, ?, ?)", [username, email, preferences, createdAt])
    con.query(sql_query, function(err, result){
        if (err) {
            res.status(400).send({code : err.code, errno : err.errno})
        }
        res.status(200).send(result)
    })
}

/**
 * Request to get user preferences
 * @param {*} req Params include id
 * @param {*} res Returns preferences with status code
 * 200 if successful, otherwise 400
 */
function getUserPreferences(req, res){
    console.log("/users/preferences/{{id}}")
    let id = req.params.id
    let sql_query = mysql.format("SELECT preferences FROM users WHERE id = ?", [id])
    con.query(sql_query, function(err, result){
        if (err) {
            res.status(400).send({code : err.code, errno : err.errno})
        }
        res.status(200).send(result[0])
    })
}

/**
 * Updates user preferences
 * @param {*} req Body includes userId and preferences
 * @param {*} res Returns a status code of 200 
 * if successful, otherwise 400
 */
function updateUserPreferences(req, res){
    console.log("/users")
    let userId = req.body.userId
    let preferences = req.body.preferences

    let sql_query = mysql.format("UPDATE users SET preferences = ? WHERE id = ?", [preferences, userId])
    con.query(sql_query, function(err, result){
        if (err) {
            res.status(400).send({code : err.code, errno : err.errno})
        }
        res.status(200).send(result)
    })
}

function getUserName(userId){
    let sql_query = mysql.format("SELECT username FROM users WHERE id = ?", [userId])
    con.query(sql_query, function(err, result){
        if (err) {
            console.log("Error in user name retrieval: ", err)
        }
        else{
            return result
        }
    })
}

module.exports = {getUser, addUser, getUserPreferences, getUserName}

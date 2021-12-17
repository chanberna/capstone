package com.example.unichatapp

class User {
    var name: String? = null
    var email: String? = null
    var roll_num: String? = null
    var sid: String? = null
    var uid: String? = null

    constructor() {}

    constructor(name: String?, email: String?, roll_num: String?, sid: String?, uid: String?) {
        this.name = name
        this.email = email
        this.roll_num = roll_num
        this.sid = sid
        this.uid = uid
    }

} // End
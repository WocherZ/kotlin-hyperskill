package com.example.routes

import com.example.models.User
import com.example.models.loginPasswordStorage
import io.ktor.server.application.*
import io.ktor.server.mustache.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting() {
    route("/user") {
        get("/profile") {
            call.respond(MustacheContent("profile.hbs", null))
        }

        get("/login") {
            call.respond(MustacheContent("login.hbs", null))
        }

        post("/login") {
            val receivedData = mutableMapOf<String, String>()
            call.receiveText().split('&').forEach {
                receivedData[it.takeWhile { it != '=' }] = it.takeLastWhile { it != '=' }
            }
            val userLogin = receivedData.get("login")
            val userPassword = receivedData.get("password")
            if (userLogin != null && loginPasswordStorage.containsKey(userLogin)) {
                if (userPassword == loginPasswordStorage.get(userLogin)) {
                    call.respondRedirect("/user/profile")
                } else {
                    call.respond(MustacheContent("login.hbs", mutableMapOf("error" to "Error")))
                }
            } else {
                call.respond(MustacheContent("login.hbs", mutableMapOf("error" to "Error")))
            }
        }

        get("/register") {
            call.respond(MustacheContent("register.hbs", null))
        }

        post("/register") {
            val receivedData = mutableMapOf<String, String>()
            call.receiveText().split('&').forEach {
                receivedData[it.takeWhile { it != '=' }] = it.takeLastWhile { it != '=' }
            }
            val userLogin = receivedData.get("login")
            val userPassword = receivedData.get("password")
            if (loginPasswordStorage.containsKey(userLogin)) {
                call.respondRedirect("/user/register")
            }
            if (userLogin != null && userPassword != null) loginPasswordStorage[userLogin] = userPassword
            call.respondRedirect("/user/login")
        }
    }
}
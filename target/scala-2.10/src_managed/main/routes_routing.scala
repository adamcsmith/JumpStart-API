// @SOURCE:/Users/adamcsmith/Projects/JumpStart/JumpStart-API/conf/routes
// @HASH:f1cce62a615fda4f787d9af681bca5e4e7dbc73f
// @DATE:Mon Nov 04 10:55:09 EST 2013


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:7
private[this] lazy val controllers_Application_index1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("home"))))
        

// @LINE:8
private[this] lazy val controllers_Application_index2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("login"))))
        

// @LINE:9
private[this] lazy val controllers_Application_index3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("register"))))
        

// @LINE:12
private[this] lazy val controllers_api_Users_create4 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/users"))))
        

// @LINE:13
private[this] lazy val controllers_api_Users_get5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/users/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:14
private[this] lazy val controllers_api_Users_update6 = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/users/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:15
private[this] lazy val controllers_api_Users_delete7 = Route("DELETE", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/users/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:17
private[this] lazy val controllers_api_Sessions_create8 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/sessions"))))
        

// @LINE:18
private[this] lazy val controllers_api_Sessions_delete9 = Route("DELETE", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api/sessions"))))
        

// @LINE:22
private[this] lazy val controllers_Assets_at10 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """home""","""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """login""","""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """register""","""controllers.Application.index()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/users""","""controllers.api.Users.create()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/users/$id<[^/]+>""","""controllers.api.Users.get(id:Long)"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/users/$id<[^/]+>""","""controllers.api.Users.update(id:Long)"""),("""DELETE""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/users/$id<[^/]+>""","""controllers.api.Users.delete(id:Long)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/sessions""","""controllers.api.Sessions.create()"""),("""DELETE""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api/sessions""","""controllers.api.Sessions.delete()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Top Level routes""", Routes.prefix + """"""))
   }
}
        

// @LINE:7
case controllers_Application_index1(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """""", Routes.prefix + """home"""))
   }
}
        

// @LINE:8
case controllers_Application_index2(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """""", Routes.prefix + """login"""))
   }
}
        

// @LINE:9
case controllers_Application_index3(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """""", Routes.prefix + """register"""))
   }
}
        

// @LINE:12
case controllers_api_Users_create4(params) => {
   call { 
        invokeHandler(controllers.api.Users.create(), HandlerDef(this, "controllers.api.Users", "create", Nil,"POST", """ API""", Routes.prefix + """api/users"""))
   }
}
        

// @LINE:13
case controllers_api_Users_get5(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.api.Users.get(id), HandlerDef(this, "controllers.api.Users", "get", Seq(classOf[Long]),"GET", """""", Routes.prefix + """api/users/$id<[^/]+>"""))
   }
}
        

// @LINE:14
case controllers_api_Users_update6(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.api.Users.update(id), HandlerDef(this, "controllers.api.Users", "update", Seq(classOf[Long]),"PUT", """""", Routes.prefix + """api/users/$id<[^/]+>"""))
   }
}
        

// @LINE:15
case controllers_api_Users_delete7(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.api.Users.delete(id), HandlerDef(this, "controllers.api.Users", "delete", Seq(classOf[Long]),"DELETE", """""", Routes.prefix + """api/users/$id<[^/]+>"""))
   }
}
        

// @LINE:17
case controllers_api_Sessions_create8(params) => {
   call { 
        invokeHandler(controllers.api.Sessions.create(), HandlerDef(this, "controllers.api.Sessions", "create", Nil,"POST", """""", Routes.prefix + """api/sessions"""))
   }
}
        

// @LINE:18
case controllers_api_Sessions_delete9(params) => {
   call { 
        invokeHandler(controllers.api.Sessions.delete(), HandlerDef(this, "controllers.api.Sessions", "delete", Nil,"DELETE", """""", Routes.prefix + """api/sessions"""))
   }
}
        

// @LINE:22
case controllers_Assets_at10(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}

}
     
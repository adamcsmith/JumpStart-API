// @SOURCE:/Users/adamcsmith/Projects/JumpStart/JumpStart-API/conf/routes
// @HASH:f1cce62a615fda4f787d9af681bca5e4e7dbc73f
// @DATE:Mon Nov 04 10:55:09 EST 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:22
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:22
class ReverseAssets {
    

// @LINE:22
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
def index(): Call = {
   () match {
// @LINE:6
case () if true => Call("GET", _prefix)
                                                        
// @LINE:7
case () if true => Call("GET", _prefix + { _defaultPrefix } + "home")
                                                        
// @LINE:8
case () if true => Call("GET", _prefix + { _defaultPrefix } + "login")
                                                        
// @LINE:9
case () if true => Call("GET", _prefix + { _defaultPrefix } + "register")
                                                        
   }
}
                                                
    
}
                          
}
                  

// @LINE:18
// @LINE:17
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
package controllers.api {

// @LINE:18
// @LINE:17
class ReverseSessions {
    

// @LINE:17
def create(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "api/sessions")
}
                                                

// @LINE:18
def delete(): Call = {
   Call("DELETE", _prefix + { _defaultPrefix } + "api/sessions")
}
                                                
    
}
                          

// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
class ReverseUsers {
    

// @LINE:13
def get(id:Long): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "api/users/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                

// @LINE:12
def create(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "api/users")
}
                                                

// @LINE:14
def update(id:Long): Call = {
   Call("PUT", _prefix + { _defaultPrefix } + "api/users/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                

// @LINE:15
def delete(id:Long): Call = {
   Call("DELETE", _prefix + { _defaultPrefix } + "api/users/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                
    
}
                          
}
                  


// @LINE:22
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {

// @LINE:22
class ReverseAssets {
    

// @LINE:22
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "home"})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "login"})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "register"})
      }
      }
   """
)
                        
    
}
              
}
        

// @LINE:18
// @LINE:17
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
package controllers.api.javascript {

// @LINE:18
// @LINE:17
class ReverseSessions {
    

// @LINE:17
def create : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.Sessions.create",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "api/sessions"})
      }
   """
)
                        

// @LINE:18
def delete : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.Sessions.delete",
   """
      function() {
      return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "api/sessions"})
      }
   """
)
                        
    
}
              

// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
class ReverseUsers {
    

// @LINE:13
def get : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.Users.get",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/users/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                        

// @LINE:12
def create : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.Users.create",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "api/users"})
      }
   """
)
                        

// @LINE:14
def update : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.Users.update",
   """
      function(id) {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "api/users/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                        

// @LINE:15
def delete : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.api.Users.delete",
   """
      function(id) {
      return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "api/users/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                        
    
}
              
}
        


// @LINE:22
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {


// @LINE:22
class ReverseAssets {
    

// @LINE:22
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Top Level routes""", _prefix + """""")
)
                      
    
}
                          
}
        

// @LINE:18
// @LINE:17
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
package controllers.api.ref {


// @LINE:18
// @LINE:17
class ReverseSessions {
    

// @LINE:17
def create(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.Sessions.create(), HandlerDef(this, "controllers.api.Sessions", "create", Seq(), "POST", """""", _prefix + """api/sessions""")
)
                      

// @LINE:18
def delete(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.Sessions.delete(), HandlerDef(this, "controllers.api.Sessions", "delete", Seq(), "DELETE", """""", _prefix + """api/sessions""")
)
                      
    
}
                          

// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
class ReverseUsers {
    

// @LINE:13
def get(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.Users.get(id), HandlerDef(this, "controllers.api.Users", "get", Seq(classOf[Long]), "GET", """""", _prefix + """api/users/$id<[^/]+>""")
)
                      

// @LINE:12
def create(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.Users.create(), HandlerDef(this, "controllers.api.Users", "create", Seq(), "POST", """ API""", _prefix + """api/users""")
)
                      

// @LINE:14
def update(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.Users.update(id), HandlerDef(this, "controllers.api.Users", "update", Seq(classOf[Long]), "PUT", """""", _prefix + """api/users/$id<[^/]+>""")
)
                      

// @LINE:15
def delete(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.api.Users.delete(id), HandlerDef(this, "controllers.api.Users", "delete", Seq(classOf[Long]), "DELETE", """""", _prefix + """api/users/$id<[^/]+>""")
)
                      
    
}
                          
}
        
    
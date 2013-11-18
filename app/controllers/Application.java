package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import utils.SecurityUtil;
import views.html.index;

/**
 * Main controller for the Application since this is a "one page app" that is using AngularJS for the client side MVC
 * architecture there really shouldn't be much in here.
 */
public class Application extends Controller {

    public static Result index() {
        User user = SecurityUtil.connectedUser();
        return ok(index.render(user));
    }

}

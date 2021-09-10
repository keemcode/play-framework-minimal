package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.bval.jsr.ApacheValidationProvider;
import org.hibernate.validator.HibernateValidator;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * This controller contains an action to handle HTTP requests to the
 * application's home page.
 */
@Transactional
public class HomeController extends Controller {

    @Inject
    ObjectMapper objectMapper;

    public Result index() {
        return ok(Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator()
                .getClass()
                .getCanonicalName());
    }

    public Result preflight(String all) {

        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Credentials", "true");
        response().setHeader("Allow", "*");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent, Authorization");
        return noContent();
    }

    public Result swagger() {
        return ok(views.html.swagger.render());
    }

    public Result error() {
        return ok(views.html.error.render());
    }

    public Result api() {
        return ok(views.html.api.render());
    }

}

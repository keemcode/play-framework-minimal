
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author Akinniranye James Ayodele <kaimedavies@sycliff.com>
 */
@Singleton
public class ErrorHandler extends DefaultHttpErrorHandler {


    ObjectMapper mapper;

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment,
                        OptionalSourceMapper sourceMapper, Provider<Router> routes, ObjectMapper mapper) {
        super(configuration, environment, sourceMapper, routes);
        this.mapper = mapper;
    }

    private String covertToErrorMessage(String message) {
        message = message == null ? "" : message;
        try {
            return mapper.writeValueAsString(ImmutableMap.of("error", message));
        } catch (Exception e) {
        }
        return message;
    }

    @Override
    protected CompletionStage<Result> onForbidden(RequestHeader request, String message) {
        return CompletableFuture.completedFuture(
                Results.forbidden(covertToErrorMessage(message))
        );
    }

    protected CompletionStage<Result> onConflict(RequestHeader request, String message) {
        return CompletableFuture.completedFuture(
                Results.status(409, covertToErrorMessage(message))
        );
    }

    protected CompletionStage<Result> onBadRequest(RequestHeader rh, String message) {
        return CompletableFuture.completedFuture(
                Results.badRequest(covertToErrorMessage(message))
        );
    }

    protected CompletionStage<Result> onNotFound(RequestHeader rh, String message) {
        return CompletableFuture.completedFuture(
                Results.notFound("Page not found for " + rh.path())
        );
    }

    public CompletionStage<Result> onServerError(RequestHeader rh, Throwable thrwbl) {
        if (thrwbl instanceof NoSuchElementException) {
            return onNotFound(rh, Objects.toString(thrwbl.getMessage(), "oops"));
        }
        if (thrwbl instanceof IllegalStateException
                || thrwbl instanceof IllegalArgumentException
                || thrwbl instanceof IOException
                || thrwbl instanceof JsonMappingException) {
            return onBadRequest(rh, Objects.toString(thrwbl.getMessage(), "oops"));
        }
//        final var id = randomUUID().toString();
//        play.Logger.error("Server failed id {} ", id, thrwbl);
//        return CompletableFuture.completedFuture(
//                Results.internalServerError(covertToErrorMessage(String.format("Server Failed, please check log with id %s", id)))
//        );
        play.Logger.error("Server failed", thrwbl);
        return CompletableFuture.completedFuture(
                Results.internalServerError(covertToErrorMessage(thrwbl.getMessage()))
        );
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encentral.scaffold.binder;

import akka.actor.ActorSystem;
import org.hibernate.validator.HibernateValidator;

import javax.inject.Inject;
import javax.validation.Validation;

/**
 * @author poseidon
 */
public class BigBang {

    @Inject
    public BigBang(ActorSystem actorSystem) {
        //bootstrap
        Validation.byProvider(HibernateValidator.class)
                .configure().buildValidatorFactory().getValidator();
    }

}

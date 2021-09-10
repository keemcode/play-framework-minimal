/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encentral.scaffold.commons;

import com.google.inject.Singleton;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.sql.Connection;

/**
 * @author James Akinniranye
 */
@Singleton
public class PostgresSqlQueryFactory {

    public static final Configuration CONFIGURATION = new Configuration(new PostgreSQLTemplates());
    @Inject
    JPAApi jPAApi;

    public SQLQueryFactory getQueryFactory(EntityManager em) {
        return new SQLQueryFactory(CONFIGURATION, new MyConnectionWrapper(em.unwrap(Connection.class)));
    }

    public <A> A withTransaction(SqlQueryFactoryCallable<A> factoryCallable) {
        return jPAApi.withTransaction(em -> factoryCallable.call(getQueryFactory(em)));
    }

    private static class MyConnectionWrapper implements Provider<Connection> {

        private final Connection connection;

        public MyConnectionWrapper(Connection connection) {
            this.connection = connection;
        }

        @Override
        public Connection get() {
            return connection;
        }

    }
}

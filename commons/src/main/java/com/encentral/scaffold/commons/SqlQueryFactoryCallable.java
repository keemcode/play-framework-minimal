/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encentral.scaffold.commons;

import com.querydsl.sql.SQLQueryFactory;

/**
 * @author James Akinniranye
 */
@FunctionalInterface
public interface SqlQueryFactoryCallable<A> {

    public A call(SQLQueryFactory factory);
}

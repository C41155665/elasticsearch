/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.common;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.common.util.concurrent.ThreadContext;

/**
 * Restores the given {@link org.elasticsearch.common.util.concurrent.ThreadContext.StoredContext}
 * once the listener is invoked
 */
public final class ContextPreservingActionListener<R> implements ActionListener<R> {

    private final ActionListener<R> delegate;
    private final ThreadContext.StoredContext context;

    public ContextPreservingActionListener(ThreadContext.StoredContext context, ActionListener<R> delegate) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    public void onResponse(R r) {
        context.restore();
        delegate.onResponse(r);
    }

    @Override
    public void onFailure(Exception e) {
        context.restore();
        delegate.onFailure(e);
    }
}

package com.ft.framework.base;

public interface DecoratorHandler<S, T> {
	public T invoke(S data);
}

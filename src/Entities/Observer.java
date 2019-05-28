package com.stinja.birg.Entities;

public interface Observer<T> {
	public void register(T o);
	public void unregister(T o);
}
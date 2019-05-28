package com.stinja.birg.Entities;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class DataModel {
	private Set<Background> bgs;
	private Set<Descriptor> des;
	private Set<Expectation> xps;

	private Set<Observer<Background>> bgObservers;
	private Set<Observer<Descriptor>> deObservers;
	private Set<Observer<Expectation>> xpObservers;

	public DataModel(
		Set<Background> bgs,
		Set<Descriptor> des,
		Set<Expectation> xps) {
		
		this.bgs = (bgs != null) ? bgs : new HashSet<Background>();
		this.des = (des != null) ? des : new HashSet<Descriptor>();

		if (xps != null) {
			for (Expectation x : xps) {
				registerBackground(x.getBackground());
				registerDescriptor(x.getDescriptor());
			}
		}

		this.xps = (xps != null) ? xps : new HashSet<Expectation>();

		this.bgObservers = new HashSet<Observer<Background>>();
		this.deObservers = new HashSet<Observer<Descriptor>>();
		this.xpObservers = new HashSet<Observer<Expectation>>();
	}

	public DataModel() {
		this(null, null, null);
	}

	/************ ADDING AND REMOVING OBSERVERS **************/

	public void addBackgroundObserver(Observer<Background> o) {
		this.bgObservers.add(o);
	}

	public void removeBackgroundObserver(Observer<Background> o) {
		this.bgObservers.remove(o);
	}

	public void addDescriptorObserver(Observer<Descriptor> o) {
		this.deObservers.add(o);
	}

	public void removeDescriptorObserver(Observer<Descriptor> o) {
		this.deObservers.remove(o);
	}

	public void addExpectationObserver(Observer<Expectation> o) {
		this.xpObservers.add(o);
	}

	public void removeExpectationObserver(Observer<Expectation> o) {
		this.xpObservers.remove(o);
	}

	/*************** BASIC GETTERS AND SETTERS **************/

	public Set<Background> getBackgrounds() {
		return bgs;
	}	

	public Set<Descriptor> getDescriptors() {
		return des;
	}

	public Set<Expectation> getExpectations() {
		return xps;
	}

	/*************** PURE OPERATIONS ******************/

	public Background lookupBackground(String name) {
		for (Background b : bgs)
			if (name.equals(b.getName()))
				return b;
		return null;
	}

	public Descriptor lookupDescriptor(String name) {
		for (Descriptor d : des)
			if (name.equals(d.getName()))
				return d;
		return null;
	}

	/**************** STATEFUL OPERATIONS *****************/

	/**
	 * In general, the registration methods create or update an Entity
	 * of that type.
	 */
	public void registerBackground(Background b) {
		Background existing = lookupBackground(b.getName());
		if (existing == null) {
			bgs.add(b);
		}

		for (Observer<Background> o : bgObservers)
			o.register(b);
	}

	public void registerDescriptor(Descriptor d) {
		Descriptor existing = lookupDescriptor(d.getName());
		if (existing == null) {
			des.add(d);
		} else {
			existing.update(d);
		}

		for (Observer<Descriptor> o : deObservers)
			o.register(d);
	}

	public void registerExpectation(Expectation x) {
		for (Expectation x2 : xps) {
			if (x2.matches(x)) {
				x2.update(x);
				return;
			}
		}
	}
}
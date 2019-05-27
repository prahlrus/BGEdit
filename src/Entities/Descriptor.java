package com.stinja.birg.Entities;

import java.util.List;
import java.util.LinkedList;

/**
 * DTO for Descriptors
 */
public class Descriptor implements Comparable<Descriptor>
{
  private String name;
  private String base;

  public Descriptor(String name, String base) {
    this.name = name;
    this.base = base;
  }

  public Descriptor(String name) {
    this(name, null);
  }
  
  public String getName() {
    return name;
  }

  public int compareTo(Descriptor d) {
    return ((base == null) ? name : base + "!" + name)
      .compareTo((d.base == null) ? d.name : d.base + "!" + d.name);
  }
}
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
  
  public void update(Descriptor o) {
    if (base == null) base = o.base;
  }

  @Override
  public int compareTo(Descriptor d) {
    return ((base == null) ? name : base + "!" + name)
      .compareTo((d.base == null) ? d.name : d.base + "!" + d.name);
  }

  @Override
  public boolean equals(Object o) {
    if (! (o instanceof Descriptor))
      return false;
    return name.equals(((Descriptor) o).name);
  }
}
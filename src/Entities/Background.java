package com.stinja.birg.Entities;

import java.util.Set;

/**
 * DTO for Backgrounds
 */
public class Background implements Comparable<Background>
{
  private String name;
  
  public Background(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public int compareTo(Background b) {
    return name.compareTo(b.name);
  }
}

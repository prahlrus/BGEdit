package com.stinja.birg.Entities;

/**
 * Linking entity for Backgrounds and Descriptors
 */
public class Expectation
{
  private Background background;
  private ExpectationType expectationType;
  private Descriptor descriptor;

  public Expectation( 
    Background background, 
    ExpectationType expectationType, 
    Descriptor descriptor
    ) {
    this.background = background;
    this.expectationType = expectationType;
    this.descriptor = descriptor;
  }

  public Background getBackground() {
    return background;
  }

  public void setBackground(Background background) {
    this.background = background;
  }

  public ExpectationType getExpectationType() {
    return expectationType;
  }

  public void setExpectationType(ExpectationType expectationType) {
    this.expectationType = expectationType;
  }

  public Descriptor getDescriptor() {
    return descriptor;
  }

  public void setDescriptor(Descriptor descriptor) {
    this.descriptor = descriptor;
  }

  public boolean matches(Expectation x) {
    return background.equals(x.background) && descriptor.equals(x.descriptor);
  }

  public void update(Expectation x) {
    this.expectationType = x.expectationType;
  }
}

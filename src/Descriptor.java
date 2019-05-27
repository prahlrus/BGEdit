/**
 * DTO for Descriptors
 */
public class Descriptor implements Comparable<Descriptor>
{
  private String name;
  
  public Descriptor(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public int compareTo(Descriptor d) {
    return name.compareTo(d.name);
  }
}

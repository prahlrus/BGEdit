import java.util.Set;
import java.util.HashSet;

/**
 * DTO for Backgrounds
 */
public class Background
{
    private String name;
    private Set<Descriptor> des;
    
    public Background(String name) {
        this.name = name;
        this.des = new HashSet<Descriptor>();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getDeState(Descriptor de) {
        return des.contains(de);
    }
    
    public void setDeState(Descriptor de, boolean state) {
        if (state) {
            des.add(de);
        } else {
            des.remove(de);
        }
    }
}

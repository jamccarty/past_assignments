/**
 * An alphabetically sorted list of Names
 * Created May 6, 2021
 */
import java.util.ArrayList;
public class SortedNamesList extends SortedLinkedList<Name>{

    /**
     * @param name String form of name you wish to find within the list
     * @return Name object from within list
     */
    public Name getData(String name){
        if(head == null){
            return null;
        }
        Node<Name> loc = head;
        while(loc.next != null){
            if(loc.data.compareTo(name) == 0){
                return loc.data;
            }
            loc = loc.next;
        }
        if(loc.data.compareTo(name) == 0){
            return loc.data;
        }
        return null;
    }

    @Override
    /**
     * Prints list of names to terminal, along with additional data points:
     *      number of babies born per name for all inputted years
     *      name location within list
     */
    public void print(){
        Node<Name> loc = head;
        int count = 0;
        while(loc != null){
            System.out.println(loc.data.name() + " - " + loc.data.totalBabies() + " - " + count);
            loc = loc.next;
            count++;
        }
    }

    /**
     * @param name String form of name you wish to find
     * @return true if list contains this name
     */
    public boolean contains(String name){
        return find(name) != null;
    }

    /**
     * @param name the name you wish to find within list
     * @return Name object
     */
    public Name find(String name){
        //if list is empty return null
        if(head == null){
            return null;
        }
        Node<Name> loc = head; //Node pointer to move through list
        while(loc != null){
            //if data == loc.data, return current Node
            if(loc.data.compareTo(name) == 0){
                return loc.data;
            }
            loc = loc.next;
        }
        //if get to end of list with no matching data, return null
        return null;
    }

    public static void main(String[] args){
        SortedNamesList s = new SortedNamesList();
        ArrayList<String> skipped = new ArrayList<>();
        for(int year = 2000; year < 2003; year++){
            System.out.println(year);
            for(char first = 'a'; first <= 'z'; first++){
                for(char second = 'a'; second <= 'z'; second++){
                    String n = Character.toString(first) + Character.toString(second);
                    int skip = (int)(Math.random() * 5);
                    if(skip == 1){
                        skipped.add(n);
                    }else{
                        if(s.contains(n)){
                            s.getData(n).newOccurrence(year, year - 2000);
                        }else{
                            s.add(new Name(n, year - 2000, year));
                        }
                    }
                }
            }
            s.print();
            System.out.println("Skipped: ");
            for(int i = 0; i < skipped.size(); i++){
                System.out.print(" " + skipped.get(i));
                skipped.remove(i);
            }
        }
    }
}

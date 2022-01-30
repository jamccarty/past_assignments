/**
 * A perpetually sorted list of Comparable objects.
 * Sorted from least to greatest.
 * 
 * Created: May 2, 2021
 * Modified: May 7, 2021
 * @author jmccarty
 */

public class SortedLinkedList<J extends Comparable<J>> {
    protected int size;//size of list
    protected Node<J> head;//'starting node' of list

    protected class Node<T>{
        public Node<T> next; //next node in sequence
        public Node<T> prev;//previous node in sequence
        public T data; //data held within node

        /**
         * @param data the data you wish to store w/in Node
         */
        public Node(T data){
            this.data = data;
            next = null;
            prev = null;
        }
    }

    public SortedLinkedList(){
        head = null;
        size = 0;
    }

    /**
     * @param location location you wish to get from
     * @return data from this location
     */
    public J get(int location){
        if(head == null || location >= size){
            return null;
        }
        Node<J> loc = head;
        for(int i = 0; i < location; i++){
            loc = loc.next;
        }
        return loc.data;
    }

    /**
     * prints list in order
     */
    public void print(){
        //current Node that will move through list
        Node<J> loc = head;

        //while not at end of list, print current node, then move on to next node
        while(loc != null){
            System.out.println(loc.data);
            loc = loc.next;
        }
    }

    /**
     * Adds data to list while maintaining sorted order
     * @param data data you wish to add to list
     */
    public void add(J data){
        //new node that holds data
        Node<J> newNode = new Node<>(data);

        if(size == 0){
            //if nothing is in list, add node and make it the head
            head = newNode;
            size++;
        }else{
            Node<J> loc = head; //Node that will move through list
            while(true){
                if(newNode.data.compareTo(loc.data) < 0){//if newNode.data < loc.data
                    //if at end of list insert Node between loc and loc.prev
                    if(loc.next == null){ 
                        //if current loc is head, make newNode the head
                        if(loc == head){
                            head = newNode;
                        }
                        //make loc newNode.next (put in sequence)
                        newNode.next = loc;

                        //if loc.prev exists, link newNode and loc.prev together
                        if(loc.prev != null){
                            Node<J> previous = loc.prev;
                            previous.next = newNode;
                            newNode.prev = previous;
                        }

                        //make newNode loc.prev
                        loc.prev = newNode;

                        //the sequence now reads: previous <-> newNode <-> loc -> null
                        //or: null <- newNode(head) <-> loc -> null
                        size++;
                        break;
                    }else{
                        //if only one item in list, and newNode is < than it
                        // make newNode the head and link it to loc
                        //the sequence now reads: null <- newNode(head) <-> loc -> null
                        if(loc == head){
                            head = newNode;
                            newNode.next = loc;
                            loc.prev = newNode;
                            break;
                        }else{
                            //same method as before
                            Node<J>previous = loc.prev;
                            previous.next = newNode;
                            newNode.prev = previous;
                            newNode.next = loc;
                            loc.prev = newNode;
                            //sequence segment now reads:
                            // ... <-> previous <-> newNode <-> loc <-> ...
                            break;
                        }
                    }
                }else if(newNode.data.compareTo(loc.data) > 0){// if newNode.data > loc.data
                    //if reached end of list, add newNode to end
                    if(loc.next == null){
                        loc.next = newNode;
                        newNode.prev = loc;
                        //sequence now reads:
                        // ... <-> loc <-> newNode -> null
                        break;
                    }
                    //otherwise, move on to next location in list
                    loc = loc.next;
                }
            }
        }
    }

    /**
     * Removes data from list while maintaining sorted order
     * @param data the data you wish to remove
     * @return the data you removed, or null if data does not exist within list
     */
    public J remove(J data){
        Node<J> removed = find(data);

        if(removed == null){
            return null;
        }

        if(removed.prev != null && removed.next != null){
            //if both previous and next nodes exist
            //initial sequence: ... <-> previous <-> removed <-> nextNode <-> ...
            Node<J> nextNode = removed.next;
            Node<J> previous = removed.prev;
            previous.next = nextNode;
            nextNode.prev = previous;
            //sequence after removal: ... <-> previous <-> nextNode <-> ...
        }else if(removed.prev == null && removed.next == null){
            removed = null;
        }else if(removed.prev == null){
            //if previous node doesn't exist
            //initial sequence: null <- removed <-> nextNode <-> ...
            Node<J> nextNode = removed.next;
            nextNode.prev = null;
            //sequence after removal: null <- nextNode <-> ...
        }else if(removed.next == null){
            //if next node doesn't exist
            //initial sequence: ... <-> previous <-> removed -> null
            Node<J> previous = removed.prev;
            previous.next = null;
            //sequence after removal: ... <-> previous -> null
        }

        size--;
        return removed.data;
    }

    /**
     * location of data within list
     * @param data the data
     * @return location [0, list.size() - 1]
     */
    public int location(J data){
        Node<J> loc = head;
        int location = 0;
        while(loc != null){
            if(loc.data.equals(data)){
                return location;
            }
            loc = loc.next;
            location++;
        }
        return -1;
    }

    /**
     * Finds the data within the list and returns the Node it's encapsulated within
     * @return Node data is encapsulated within, null if not found within list
     */
    protected Node<J> find(J data){
        //if list is empty, return null
        if(head == null){
            return null;
        }
        Node<J> loc = head; //Node pointer to move through list
        while(loc != null){
            //if data == loc.data, return current nod
            if(loc.data.compareTo(data) == 0){
                return loc;
            }
            loc = loc.next;
        }
        //if get to end of list with no matching data, return null
        return null;
    }

    /**
     * @param iD piece of data you're searching for
     * @return true if list contains iD
     */
    public boolean contains(J iD){
        return find(iD) != null;
    }

    /**
     * @return size of list
     */
    public int size(){
        return size;
    }

    public static void main(String[] args){
        String a = "a";
        String c = "c";
        System.out.println(c.compareTo(a));
        SortedLinkedList<String> s = new SortedLinkedList<>();
        s.add("c");
        System.out.println(s.size());
        s.add("a");
        s.add("d");
        s.add("b");
    }
}

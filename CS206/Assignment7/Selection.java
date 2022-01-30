/**
 * Selection sort implementation
 * @author gtowell
 * created: April 10, 2020
 * Modified: Nov 12, 2020
 */
public class Selection extends SortBase{
    /**
    * Do Selection sort, in place, on provided array.  Hence, this changes
    * the order of items in the provided array.
    * @param rdm the array to be sorted
    */
    private void selectionSortIP(int[] rdm, int startLoc, int endLoc) {
        for (int i=startLoc; i< (endLoc-2); i++) {
            int lo = rdm[i];
            int loi = i;
            for (int j=i+1; j<endLoc; j++) {
                if (rdm[j]<lo) {
                    lo=rdm[j];
                    loi=j;
                }
            }
            int tmp = rdm[i];
            rdm[i]=rdm[loi];
            rdm[loi]=tmp;
        }
    }
    

    @Override
    public void sortInPlace(int[] rdm) {
        selectionSortIP(rdm, 0, rdm.length);
    }

    public static void main(String[] args) {
        Selection ins = new Selection();
        int[] arr = ins.fyShuffle(20);
        System.out.println(ins.stringArray(arr));
        long nntime = System.nanoTime();
        ins.sortInPlace(arr);
        long eetime = System.nanoTime();
        System.out.println(ins.stringArray(arr));
        System.out.println((eetime-nntime)/100000.0);
    }

}
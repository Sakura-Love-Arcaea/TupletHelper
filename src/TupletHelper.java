import java.util.LinkedList;
import java.util.Queue;

public class TupletHelper {
    final private String PITCH = "ees";
    public Queue<String> notes;
    public int nth;
    public int count;
    public int base;
    public int INCLUDE;
    public int take_to_merge;
    public int each;
    public TupletHelper() {
        this.notes = new LinkedList<>();
        this.count = 0;
        setNth(0);
    }
    public TupletHelper(int nth) {
        this.notes = new LinkedList<>();
        this.count = 0;
        setNth(nth);
    }
    public void setNth(int nth) {
//        if (this.nth != 0 && this.nth == nth) { //剛開始(0 -> n) && nth不變
//            return;
//        } else {  //nth變化 -> 輸出先前的， 變回去0也算
//            this.show();
//            System.out.println();
//        }
        this.nth = nth;
        this.notes.clear(); //其實不用？ 因為輸出完都沒有東西了
        this.count = 0;
        switch (nth) {
            case 0:
                break;
            case 3:
                this.INCLUDE = 3;
                this.each = 2;
                this.take_to_merge = 2; // 2nd
                this.base = 3;
                break;
            case 6:
                this.INCLUDE = 3;
                this.each = 4;
                this.take_to_merge = 2; // 2nd
                this.base = 3;
                break;

            case 12:
                this.INCLUDE = 3;
                this.each = 8;
                this.take_to_merge = 2; // 4th
                this.base = 3;
                break;

            case 20:
                this.INCLUDE = 5;
                this.each = 16;
                this.take_to_merge = 4;
                this.base = 5;
                break;
            case 24:
                this.INCLUDE = 6;
                this.each = 16;
                this.take_to_merge = 4; // 4th
                this.base = 3;
                break;
            case 28:
                this.INCLUDE = 7;
                this.each = 16;
                this.take_to_merge = 4;
                this.base = 7;
                break;

            case 48:
                this.INCLUDE = 12;
                this.each = 32;
                this.take_to_merge = 8; // 4th
                this.base = 3;
            default:
//                System.out.println("not support");
                return;
        }
    }
    private void setCount(int count, String notes) {
        this.count = count;
        test_join(notes);
    }
    private void setCount(int count) {
        this.count = count;
        test_join(count);
    }
    public int join(boolean r) {
        if (r) {
            this.notes.offer("r");
        } else {
            this.notes.offer(PITCH);
        }
        return count++;
    }
    private void test_join(String notes) {
        for (int i = 0, x = 0; x < count; ++i) {
            if (notes.charAt(i) != ' ') {
                if (notes.charAt(i) == 'e') {
                    this.notes.offer("ees");
                } else {
                    this.notes.offer("r");
                }
                x++;
            }
        }
    }
    private void test_join(int n) {
        for (int i = 0; i < n; ++i) {
                this.notes.offer("ees");
        }
    }
    public void show() { //公因數是 >= INCLUDE //nth % count == 0 ori
        if (this.notes.isEmpty()) return;
        if (base == 0) return;
//        if (count % base != 0) {
//            System.out.print("ERROR");
//            return;
//        }
        if ((count % base == 0) && (gcd(nth, count) >= INCLUDE) && !(INCLUDE > base && count == base)) { //三個為一組的可以無視 //case 3/24  //澄清條件 1.每一bar都一樣（整除）2. 3是基數，不會除不了 3.他是3以上，但是只有三個
            for (int i = 0; i < count; ++i) { // for test
                if (this.notes.isEmpty()) {
                    System.out.print("裡面是空的你要我印出什麼");
                    return;
                }
                if (i % INCLUDE == 0) {
                    System.out.printf("\\times %d/%d { ", take_to_merge, INCLUDE);
                    System.out.printf("%s%d ", this.notes.poll(), each);
                } else if (i % INCLUDE == INCLUDE - 1) {
                    System.out.printf("%s%d ", this.notes.poll(), each);
                    System.out.print("} ");
                } else {
                    System.out.printf("%s%d ", this.notes.poll(), each);
                }

            }

        } else if (count % base == 0){ //除不盡 會有剩下 24 9
            //降階 //除非有很好的算法
            int INCLUDE_REDUCE = gcd(count, INCLUDE);
            int merge_reduce = INCLUDE_REDUCE / 3 * 2;
            for (int i = 0; i < count; ++i) {
                if (this.notes.isEmpty()) {
                    System.out.print("裡面是空的你要我印出什麼2");
                    return;
                }
                if (i % INCLUDE_REDUCE == 0) {
                    System.out.printf("\\times %d/%d { ", merge_reduce, INCLUDE_REDUCE);
                    System.out.printf("%s%d ", this.notes.poll(), each);
                } else if (i % INCLUDE_REDUCE == INCLUDE_REDUCE - 1) {
                    System.out.printf("%s%d ", this.notes.poll(), each);
                    System.out.print("} ");
                } else {
                    System.out.printf("%s%d ", this.notes.poll(), each);
                }

            }
        } else {
            System.out.printf("\\times %d/%d { ", take_to_merge, INCLUDE);
            for (int i = 0; i < count; ++i) {
                System.out.printf("%s%d ", this.notes.poll(), each);
            }
            System.out.print("} ");

        }
        this.count = 0;
        System.out.println();
    }
    private static int gcd (int a, int b) {
        int r;
        while(b != 0){
            r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
    public static void main(String[] args) {
        TupletHelper T = new TupletHelper(0);
//        int nth = 20;
//
//        T.setNth(nth);
//        T.setCount(20, "eeeee eeeee eeeee eeeee");
//        T.show();
//        System.out.println();
//
//
//        nth = 24;
//        T.setNth(nth);
//        T.setCount(24);
//        T.show();
//        System.out.println();
//
//
//        nth = 24;
//        T.setNth(nth);
//        T.setCount(75);
//        T.show();
//        System.out.println();

        //  總結 只有nth的改變才會出發列印（外部控制
        // 提議： 印出後變成0？


//        T.setNth(6);
//
//        T.setCount(9, "eee eee eee");
//        T.show();
//        T.setNth(0);
//        T.show();
//        T.setNth(12);
//        System.out.println();
//
//        T.setCount(6, "eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(3, "eee eee");
//        T.show();
//        System.out.println();
//
//        T.setNth(12);
//        T.setCount(12, "eereer eereer");
//        T.show();
//        System.out.println();
//
//        T.setCount(6, "eer eer");
//        T.show();
//        System.out.println();
//
//        T.setCount(3, "eer eer");
//        T.show();
//        System.out.println();
//
//
//
//
//        T.setNth(24);
//
//        T.setCount(24, "eeeeer eeeeee eeeeee eeeeee");
//        T.show();
//        System.out.println();
//
//        T.setCount(18, "eee eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(12, "eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(6, "eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(21, "eee eee eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(15, "eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(9, "eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(3, "eee");
//        T.show();
//        System.out.println();
//
////
//
//        T.setNth(48);
//
//        T.setCount(48, "eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(36, "eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(24, "eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(12, "eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(6, "eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee");
//        T.show();
//        System.out.println();
//
//        T.setCount(3, "eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee eee");
//        T.show();
//        System.out.println();

        T.setNth(20);

        T.setCount(5, "eeeee");
        T.show();
        System.out.println();

        T.setCount(10, "eeeee eeeee");
        T.show();
        System.out.println();

        T.setCount(15, "eeeee eeeee eeeee");
        T.show();
        System.out.println();

        T.setCount(20, "eeeee eeeee eeeee eeeee");
        T.show();
        System.out.println();

        T.setCount(25, "eeeee eeeee eeeee eeeee eeeee");
        T.show();
        System.out.println();

        T.setCount(30, "eeeee eeeee eeeee eeeee eeeee eeeee");
        T.show();
        System.out.println();

        T.setCount(35, "eeeee eeeee eeeee eeeee eeeee eeeee eeeee");
        T.show();
        System.out.println();

        T.setCount(40, "eeeee eeeee eeeee eeeee eeeee eeeee eeeee eeeee");
        T.show();
        System.out.println();


        T.setNth(28);
        T.setCount(7, "eeeeeee");
        T.show();
        System.out.println();

        T.setCount(14, "eeeeeee eeeeeee");
        T.show();
        System.out.println();

        T.setCount(21, "eeeeeee eeeeeee eeeeeee ");
        T.show();
        System.out.println();

        T.setCount(28, "eeeeeee eeeeeee eeeeeee eeeeeee ");
        T.show();
        System.out.println();

        T.setCount(35, "eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee ");
        T.show();
        System.out.println();

        T.setCount(42, "eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee ");
        T.show();
        System.out.println();

        T.setCount(49, "eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee ");
        T.show();
        System.out.println();

        T.setCount(56, "eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee eeeeeee ");
        T.show();
        System.out.println();

    }

}
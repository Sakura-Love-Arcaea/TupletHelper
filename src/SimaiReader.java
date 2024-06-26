import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimaiReader{
    public String pitch;
    public int nth;
    public float bpm;
    TupletHelper T;
    public SimaiReader(String pitch) {
        this.T = new TupletHelper(pitch);
        this.nth = 0;
        this.bpm = 0;
        this.pitch = pitch;
    }
    public boolean isTuplet(int num) {
        return num % 7 == 0 || num % 3 == 0 || num % 5 == 0;
    }

    public void changeNth(int _nth) { // if is nth, do tuple.show (change) change -> show(): from 3/5/7base change to 2 (do) : or 有東西，但轉換就(do)
        this.nth = _nth;
        T.show();
        T.setNth(_nth); //預期印出上次的東西
    }

    public void changeBPM(float _bpm) {
        T.show();
        System.out.printf("\\tempo 4 = %d\n", (int) _bpm);
    }
    public void process(String token) {
        if (token.equals("E")) {
//            if (T.count != 0) T.show();
            return;
        }

        if (!token.isEmpty() && token.charAt(0) == '(') {
            changeBPM(Float.parseFloat(token.substring(1)));
            return;
        }

        if (!token.isEmpty() && token.charAt(0) == '{') {
            changeNth(Integer.parseInt(token.substring(1)));
            return;
        }

        if(isTuplet(nth)) {
            T.join(token.isEmpty());
        } else {
            System.out.print(token.isEmpty() ? "r" + nth + " " : pitch + nth + " ");
        }

    }


    public static void main(String[] args) {
        SimaiReader S = new SimaiReader("e");

        String token;
        Scanner reader = null;
//        File fp = new File("/Users/lvjiayao/about_programs/simai_to_lilypond/simai_to_lilipond/src/input.txt");
//        File fp = new File("./input.txt");
        File fp = new File(args[0]);
        try {
            reader = new Scanner(fp);
            reader.useDelimiter("[)},]");
        } catch (FileNotFoundException e) {
            System.out.println("FILE ERROR");
        }

        System.out.printf("\\header {\n" +
                "\ttitle = \"%s\"\n" +
                "\tcomposer = \"SakuraLoveArcaea\"\n" +
                "}\n" +
                "\\score {\n" +
                "\\new StaffGroup <<\n" +
                "\\new RhythmicStaff \\relative c'' { \n" +
                "\\set Staff.midiInstrument = \"standard kit\"\n" +
                "\\numericTimeSignature\n" +
                "\\time 4/4\n", fp.getName().replaceFirst("[.][^.]+$", "").replace("_", " "));

        if (reader == null) return;
        while (reader.hasNext()) {
            token = reader.next().strip();
            S.process(token);
        }

        System.out.println("}\n" +
                ">>\n" +
                "\\layout {}\n" +
                "\\midi {}\n" +
                "}");
    }
}

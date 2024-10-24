import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numLines = sc.nextInt();
        Roster rst = new Roster("AY24/25");

        for (int i = 0; i < numLines; i++) {
            rst = rst.add(sc.next(), sc.next(), sc.next(), sc.next());
        }
        //System.out.println(rst);

        while (sc.hasNext()) {
            System.out.println(rst.getGrade(sc.next(), sc.next(), sc.next()));
        }
    }
}

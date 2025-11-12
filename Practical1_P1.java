import java.io.*;

class P1 {
    public static void main(String ar[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int i, j, k, l;
        String a[][] = {
            {"", "START", "101", ""},
            {"", "MOVER", "BREG", "ONE"},
            {"AGAIN", "MULT", "BREG", "TERM"},
            {"", "MOVER", "CREG", "TERM"},
            {"", "ADD", "CREG", "N"},
            {"", "MOVEM", "CREG", "TERM"},
            {"N", "DS", "2", ""},
            {"RESULT", "DS", "2", ""},
            {"ONE", "DC", "1", ""},
            {"TERM", "DS", "1", ""},
            {"", "END", "", ""}
        };

        int lc = Integer.parseInt(a[0][2]);
        String st[][] = new String[5][2];
        int cnt = 0;

        // Build symbol table
        for (i = 1; i < 11; i++) {
            if (!a[i][0].equals("")) {  // String comparison
                st[cnt][0] = a[i][0];
                st[cnt][1] = Integer.toString(lc);
                cnt++;
                if (a[i][1].equals("DS")) {
                    int d = Integer.parseInt(a[i][2]);
                    lc = lc + d;
                } else {
                    lc++;
                }
            } else {
                lc++;
            }
        }

        // Print symbol table
        System.out.println("***SYMBOL TABLE****");
        System.out.println("_____________________");
        for (i = 0; i < cnt; i++) {
            System.out.println(st[i][0] + "\t" + st[i][1]);
        }

        String inst[] = {"STOP", "ADD", "SUB", "MULT", "MOVER", "MOVEM", "COMP", "BC", "DIV", "READ", "PRINT"};
        String reg[] = {"NULL", "AREG", "BREG", "CREG", "DREG"};
        int op[][] = new int[12][3];
        int p = 1, cnt1 = 0;

        // Build operation table
        for (i = 1; i < 11; i++) {
            for (j = 0; j < 11; j++) {
                if (a[i][1].equalsIgnoreCase(inst[j])) {
                    op[cnt1][0] = j;
                } else if (a[i][1].equalsIgnoreCase("DS")) {
                    p = Integer.parseInt(a[i][2]);
                } else if (a[i][1].equalsIgnoreCase("DC")) {
                    op[cnt1][2] = Integer.parseInt(a[i][2]);
                }
            }

            for (k = 0; k < 5; k++) {
                if (a[i][2].equalsIgnoreCase(reg[k])) {
                    op[cnt1][1] = k;
                }
            }

            for (l = 0; l < cnt; l++) { // use cnt here because st size is cnt
                if (a[i][3].equalsIgnoreCase(st[l][0])) {
                    int mn = Integer.parseInt(st[l][1]);
                    op[cnt1][2] = mn;
                }
            }
            cnt1 = cnt1 + p;
            p = 1;  // reset p for next iteration
        }

        System.out.println("\n*****OUTPUT*****\n");
        System.out.println("**********MOT TABLE**********");
        int dlc = Integer.parseInt(a[0][2]);

        for (i = 0; i < cnt1; i++) {
            System.out.print(dlc++ + "\t");
            for (j = 0; j < 3; j++) {
                System.out.print(" " + op[i][j] + " ");
            }
            System.out.println();
        }
    }
}
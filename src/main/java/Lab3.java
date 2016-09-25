import java.lang.Math;
import java.util.*;

public class Lab3 {
    private static Scanner sc = new Scanner(System.in);
    private static double[] size = {0, 0, 0, 0};
    private static double[] studentSize = new double[4];
    private static Map <String, Integer> diff = new TreeMap <> ();
    private static String fioFix;
    private static int binaryMinLen;
    String[] treeShenFano = new String[100];
    boolean[] emptyVrtxFano = new boolean[100];
    public static void main (String[] args) {
        readFIO();
        prepareCoding();
        //binaryCode();
        codeShennonFano();
    }
    private static void readFIO() {
        System.out.println("Введите фамилию имя и отчество:");
        String fio = sc.nextLine().toUpperCase().replaceAll("\\s+", " ").trim();
        String[] checkFio = fio.split(" ");
        if(checkFio.length != 3) {
            System.out.println("ФИО должно сотоять из 3х слов");
            readFIO();
        } else {
            fioFix = checkFio[0].substring(0, Math.min(9, checkFio[0].length()));
            fioFix += checkFio[1].substring(0, Math.min(9, checkFio[1].length()));
            fioFix += checkFio[2].substring(0, Math.min(9, checkFio[2].length()));
            char[] adjust_sym = {'А', 'О', 'П', 'Р'};
            int id = 0;
            while (fioFix.length() < 27) {  //  + А О П Р
                fioFix += (adjust_sym[id % 4]);
                ++id;
            }
        }
    }
    private static void prepareCoding() {
        System.out.println ("Введите строку для кодирования: ");
        String studentFioFix = sc.nextLine().toUpperCase().trim();
        while (!fioFix.equals(studentFioFix)) {
            System.out.println("Введена некорректная строка, введите заново: ");
            studentFioFix = sc.nextLine().toUpperCase().trim();
        }
        size[0] = fioFix.length() * 2; // стартовый объем
        System.out.println ("Введите числом объем строки в байтах: ");
        try {
            studentSize[0] = Double.parseDouble(sc.nextLine());
        } catch (Exception e) { studentSize[0] = -1; }
        while (Double.compare(size[0], studentSize[0]) != 0) {
            System.out.println("Введен неверный объем строки, введите заново: ");
            try {
                studentSize[0] = Double.parseDouble(sc.nextLine());
            } catch (Exception e) { studentSize[0] = -1; }
        }
        System.out.println("Кодируемая строка: " + fioFix + "   Длина строки: " + fioFix.length());
        System.out.println("Объем в байтах: " + size[0] + "   Объем в битах: " + size[0] * 8);
        for (int i = 0; i < fioFix.length(); ++i) { // кол-во различных букв
            String char_i = fioFix.charAt(i) + "";
            int before = diff.get(char_i) == null ? 0 : diff.get(char_i);
            diff.put(char_i, before + 1);
        }
    }
    private static void binaryCode () {
        System.out.println("\nИспользуя двоичный код постоянной длины, закодируйте строку: " + fioFix);
        // Мин разрядов
        binaryMinLen = (int) (Math.log (diff.size()) / Math.log (2));
        if((int) Math.pow (2.0, binaryMinLen) != diff.size())
            ++binaryMinLen;
        System.out.println("Введите минимальное количество разрядов необходимых для кодирования одного символа: ");
        int studentLen01;
        try {
            studentLen01 = Integer.parseInt(sc.nextLine());
        } catch (Exception e) { studentLen01 = -1; }
        while (binaryMinLen != studentLen01) {
            System.out.println("Введено неверное кол-во разрядов, введите заново: ");
            try {
                studentLen01 = Integer.parseInt(sc.nextLine());
            } catch (Exception e) { studentLen01 = -1; }
        }
        // Таблица
        Map <String, String> studentDiff = new TreeMap <> ();
        inputTable(studentDiff, true);
        for(Map.Entry<String, String> entry : studentDiff.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        // Последовательность
        String finalStr = "";
        for(int i = 0; i < fioFix.length(); i++)
            finalStr += studentDiff.get(fioFix.substring(i, i + 1));
        System.out.println("Введите последовательность бит, которой кодируется строка: ");
        String studentFinalString = sc.nextLine().trim();
        while (!finalStr.equals(studentFinalString)) {
            System.out.println("Получена неверная последовательность! Введи заново");
            studentFinalString = sc.nextLine().trim();
        }
        // Объем
        size[1] = fioFix.length() * binaryMinLen; // 1 способ
        System.out.println("Введите объем строки в БИТАХ: ");
        try {
            studentSize[1] = Double.parseDouble(sc.nextLine());
        } catch (Exception e) { studentSize[1] = -1; }
        while (Double.compare(size[1], studentSize[1]) != 0) {
            System.out.println("Введен неверный объем строки, введите заново: ");
            try {
                studentSize[1] = Double.parseDouble(sc.nextLine());
            } catch (Exception e) { studentSize[1] = -1; }
        }
        System.out.println("Введите коэфф сжатия с точностью 2 знака: ");
        double studentCoeff;
        try {
            studentCoeff = Double.parseDouble(sc.nextLine().replaceAll(",", "."));
        } catch (Exception e) { studentCoeff = -1.0; }
        while(Math.abs(studentCoeff - size[0] * 8 / size[1]) >= 0.01) {
            System.out.println("Введен неверный коэфф сжатия, введите заново");
            try {
                studentCoeff = Double.parseDouble(sc.nextLine().replaceAll(",", "."));
            } catch (Exception e) { studentCoeff = -1.0; }
        }
        System.out.println("Успех! Строка успешно закодирована двоичным кодом постоянной длины");
    }
    private static boolean isLastLevel(String lvl) {
        String[] tmp = lvl.trim().replaceAll("\\s+", " ").split(" ");
        for(int i = 0; i < tmp.length; i++)
            if(tmp[i].length() > 1) return false;
        return true;
    }
    private static boolean checkShennonFanoLevel(String lvl) {

        return true;
    }
    private static void codeShennonFano() {
        System.out.println("\nИспользуя метод кодирования Шеннона-Фано, закодируйте строку: " + fioFix);
        Map <String, String> studentDiff = new TreeMap <> ();
        System.out.println("Введите дерево по которому стротся код, просто перечисляя вершины " +
                "на уровне слева направо через пробел(Количество пробелов может быть произвольным)");

        System.out.println("Например: ");
        System.out.println("абвгд");
        System.out.println("аб вгд");
        System.out.println("а б в гд");
        System.out.println("г д");
        String thisLvl;
        int id = 0;
        do {
            thisLvl = sc.nextLine();
            while(!checkShennonFanoLevel(thisLvl)) {
                System.out.println("В построении дерева ошибка! Или строка некорректна! Введите заново этот уровень дерева");
                thisLvl = sc.nextLine();
            }

        } while(!isLastLevel(thisLvl));


        inputTable(studentDiff, false);


        for(Map.Entry<String, Integer> entry : diff.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        for(Map.Entry<String, String> entry : codeToSym.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " = " + value);
        }


    }
    private static void inputTable (Map <String, String> studentDiff, boolean isBinaryCode) {
        System.out.println("Введите таблицу соответствия исходных слов и результирующих в виде Символ = Код");
        for (int i = 0; i < diff.size(); ++i) {
            System.out.println("Введите символ и соответсвующий ему код");
            String inTable = sc.nextLine();
            String sym = inTable.split("=", 2)[0].trim().toUpperCase();
            String code = inTable.split("=", 2).length == 2 ? inTable.split("=", 2)[1].trim() : "2222";
            boolean flag = (diff.containsKey(sym) && (code.length() == binaryMinLen || !isBinaryCode)
                            && code.replaceAll("1", "").replaceAll("0", "").length() == 0);
            while(studentDiff.containsValue(code) || studentDiff.containsKey(sym) || !flag) {
                System.out.println("Данный код или символ введен повторно, или строка некорректна");
                inTable = sc.nextLine();
                sym = inTable.split("=", 2)[0].trim().toUpperCase();
                code = inTable.split("=", 2).length == 2 ? inTable.split("=", 2)[1].trim() : "2222";
                flag = (diff.containsKey(sym) && (code.length() == binaryMinLen || !isBinaryCode)
                        && code.replaceAll("1", "").replaceAll("0", "").length() == 0);
            }
            studentDiff.put(sym, code);
        }
    }


}
/*
аопраопра
000110110001101100000110110001101100000110110001101100


 */

package base;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

public class Help {

    private static final String ROOT = "database";

    public static final String DIVDER = "    ";

    public static final String EXAMINER_PATH = Help.ROOT + "/examiners.txt";
    public static final String STUDENT_PATH = Help.ROOT + "/students.txt";
    public static final String QUESTION_PATH = Help.ROOT + "/questions.txt";

    public static final int EXAMINER_SIZE = 4;
    public static final int STUDENT_SIZE = 10;
    public static final int QUESTION_SIZE = 8;

    public static final double MARK_PER_QUESTION = 1.25;

    public static void echo(String str) {
        System.out.print(str);
    }

    public static void echo(int str) {
        System.out.print(str);
    }

    public static void echo(double str) {
        System.out.print(str);
    }

    public static void echoLn(String str) {
        System.out.println(str);
    }

    public static void echoLn(int str) {
        System.out.println(str);
    }

    public static void echoLn(double str) {
        System.out.println(str);
    }

    public static void generateOptionsList(String options[]) {
        Help.echoLn("\n======================");
        Help.echoLn("| Choose your opiton |");
        Help.echoLn("======================\n");
        for (int i = 0; i < options.length; i++) {
            Help.echoLn((i + 1) + ". " + options[i]);
        }
        Help.echo("Enter your Option: ");
    }

    public static String readString() {

        try {
            String line = null;
            Scanner input = new Scanner(System.in);
            if (input.hasNextLine()) {
                line = input.nextLine();
            } else if (!input.hasNextLine()) {
                input.close();
                line = Help.readString();
            } else {
                input.close();
                throw new IOException();
            }
            return line;
        } catch (InputMismatchException err) {
            Help.echoLn("\n\n[Error] InputMismatchException\n[!]Try to input a UTF-8 format String\n");
            // err.printStackTrace();
        } catch (IOException err) {
            Help.echoLn("\n\n[Error] IOException!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        } catch (Exception err) {
            Help.echoLn("\n\n[Error] Exception!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        }

        return null;
    }

    public static int readInteger() {
        try {
            int line = -1;
            Scanner input = new Scanner(System.in);
            if (input.hasNextLine()) {
                line = input.nextInt();
                input.nextLine();
            } else if (!input.hasNextLine()) {
                input.close();
                line = Help.readInteger();
            } else {
                input.close();
                throw new IOException();
            }
            return line;
        } catch (InputMismatchException err) {
            Help.echoLn("\n\n[Error] InputMismatchException\n[!]Try to input an Integer Number!!\n");
            // err.printStackTrace();
        } catch (IOException err) {
            Help.echoLn("\n\n[Error] IOException!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        } catch (Exception err) {
            Help.echoLn("\n\n[Error] Exception!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        }

        return -1;
    }

    /*
    public static double readDouble() {
        try {
            double line = -1.0;
            Scanner input = new Scanner(System.in);
            if (input.hasNextLine()) {
                line = input.nextDouble();
                input.nextLine();
            } else if (!input.hasNextLine()) {
                input.close();
                line = Help.readDouble();
            } else {
                input.close();
                throw new IOException();
            }
            return line;
        } catch (InputMismatchException err) {
            Help.echoLn("\n\n[Error] InputMismatchException\n[!]Try to input a Float Number!!\n");
            // err.printStackTrace();
        } catch (IOException err) {
            Help.echoLn("\n\n[Error] IOException!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        } catch (Exception err) {
            Help.echoLn("\n\n[Error] Exception!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        }
        return -1.0;
    }
    */

    public static void writeFile(String path, String line, boolean mode) {
        try {
            File file = new File(path);
            FileWriter fWriter = new FileWriter(file, mode);

            if (file.exists()) {
                if (file.canWrite()) {
                    fWriter.write(line);
                } else {
                    Help.echoLn("\nCan't Write to " + file.getName() + "\n");
                }
            } else {
                Help.echoLn("\n" + file.getName() + "File Does not exists!!\n");
            }
            fWriter.close();
        } catch (FileNotFoundException err) {
            Help.echoLn("\n\n[Error] FileNotFoundException\n[!] File does not exist or the path is wrong!!");
            // err.printStackTrace();
        } catch (Exception err) {
            Help.echoLn("\n\n[Error] Exception!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        }
    }

    public static String[] readFile(String path, int length) {
        try {
            int i = 0;
            File file = new File(path);
            if (file.exists()) {
                Scanner fRead = new Scanner(file);

                String[] line = new String[length];

                while (fRead.hasNextLine()) {
                    if (!(line[i] = fRead.nextLine()).equals(null)) {
                        i++;
                    } else {
                        break;
                    }
                }
                fRead.close();
                return line;
            } else {
                return new String[0];
            }

        } catch (FileNotFoundException err) {
            Help.echoLn("\n\n[Error] FileNotFoundException\n[!] File does not exist or the path is wrong!!");
            // err.printStackTrace();
        } catch (Exception err) {
            Help.echoLn("\n\n[Error] Exception!!\n[!] Something went wrong!!");
            // err.printStackTrace();
        }

        return new String[0];
    }
}
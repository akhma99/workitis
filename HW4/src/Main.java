import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int[] array = {
                0,1,2,3,4,5,6,8
        };
        System.out.println(findMissing(array));
        System.out.println(task1("maks","smak"));
    }

    public static boolean task1(String str1, String str2){
        if (str1.length()!=str2.length()){
            return false;
        }
        if (str1==null || str2==null){
            return false;
        }
        for (int i = 0; i<str1.length(); i++){
            int cntWin = 0;
            for (int j = 0; j<str1.length();j++) {
                if (str1.charAt(j) == str2.charAt((i + j) % str1.length())) {
                    cntWin++;
                }else {
                    break;
                }
            }
            if (cntWin==str1.length()){
                return true;
            }
        }
        return false;
    }

    public static double findMissing(int[] array){
        int n = array.length;
        double sumNumbers = (1+n)/2.0*n;
        double sumArray = 0.0;
        for (int el: array){
            sumArray+=el;
        }
        return sumNumbers-sumArray;
    }
}
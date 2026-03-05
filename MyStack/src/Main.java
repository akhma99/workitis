import Stack.Stack;

public class Main {
    public static void main(String[] args) {
        postficsResult("34+5*");
    }

    public static void postficsResult(String input) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int count=0; count<input.length();count++) {
            if (input.charAt(count) != '+' && input.charAt(count) != '*' && input.charAt(count) != '-' && input.charAt(count) != ' ') {
                stack.push(Character.getNumericValue(input.charAt(count)));
            } else {
                int firstNumber = stack.pop();
                int secondNumber = stack.pop();
                if (input.charAt(count) == '+') {
                    stack.push(firstNumber + secondNumber);
                } else if (input.charAt(count) == '*') {
                    stack.push(firstNumber * secondNumber);
                } else {
                    stack.push(firstNumber - secondNumber);
                }
            }
        }
        System.out.println(stack.peek());
    }
}


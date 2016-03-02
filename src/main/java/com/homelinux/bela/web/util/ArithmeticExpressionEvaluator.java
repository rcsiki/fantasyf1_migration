package com.homelinux.bela.web.util;

/**
You can use this to evaluate a character string as an arithmetic
real expression and have it return a real value.  This
class does not verify the correctness of the expression, and
it does not recognize operator precedence or associativity.
The only structuring tool it recognizes is the parenthesis.
All operators are binary.  This class does not understand unary
plus or minus.

You can either use the primitive arithmetic parser provided (in which
case simply pass in an expression as a character string) or else give
an array of Strings to eval.  (There is a companion class called
RealParse that contains an operator precedence parser.)

The arithmetic parser is able to skip over multiple blanks because it
totally ignores blanks.  But it can't handle alphnumerics, only reals.
All other characters are translated into single character tokens.
**/

public class ArithmeticExpressionEvaluator {

     private String[] stack;
     private int stacktop;
     private int maxstack;
     private String[] tokens;   // used internally to parse expression

     //-----------------------------------------------------------------
     /**
      *   Create a RealEval object so we can evaluate real arithmetic
      *   expressions.
      *   @param
      *   @return
      *        a new RealEval object
     **/
     //-----------------------------------------------------------------

     public ArithmeticExpressionEvaluator () {
         initstack();
     }

     //-----------------------------------------------------------------
     /**
      *   Evaluate an arithmetic expression.
      *   @param s
      *        the String containing which will be tokenized and then
      *        fed into the real evaluator (which is below).
      *   @return
      *        a double, the resulting real number
     **/
     //-----------------------------------------------------------------

     public double eval (String s) {
          parse(s);
          return evalx (tokens);
     }

     //-----------------------------------------------------------------
     /**
      *   Evaluate an arithmetic expression.
      *   @param tokens
      *        an array of String (String[]) which contains the tokens
      *        that will be evaluated, such as {"1.2", "+", "6.9"}
      *   @return
      *        a double, the resulting real number
     **/
     //-----------------------------------------------------------------

     public double eval (String[] tokens) {
          String[] newtokens = new String[tokens.length+1];
          for (int i=0; i<tokens.length; i++)
               newtokens[i] = tokens[i];
          newtokens[tokens.length] = "$";
          return evalx (newtokens);
     }

     private String binaryEval (String val1, String operator, String val2) {
         if (operator.equals("+"))
              return String.valueOf(atod(val1) + atod(val2));
         if (operator.equals("-"))
              return String.valueOf(atod(val1) - atod(val2));
         if (operator.equals("*"))
              return String.valueOf(atod(val1) * atod(val2));
         if (operator.equals("/"))
              return String.valueOf(atod(val1) / atod(val2));
         if (operator.equals("%"))
              return String.valueOf(atod(val1) % atod(val2));
         if (operator.equals("^"))
              return String.valueOf(exponentiate (atod(val1), atod(val2)));
         return "0";
     }

     private void evalTop2 () {
          String operand2 = pop();
          String operator = pop();
          String operand1 = pop();
          String newvalue = binaryEval (operand1, operator, operand2);
          push (newvalue);
     }

     private double evalx (String[] tokens) {
          push ("$");
          for (int i=0; i<tokens.length; i++) {
               if (isreal(tokens[i])) {
                    if (top().equals("$") || top().equals("("))
                         push(tokens[i]);
                    else {
                         push (tokens[i]);
                         evalTop2 ();
                    }
               }
               else if (tokens[i].equals("("))
                    push("(");
               else if (tokens[i].equals(")")) {
                    String topvalue = pop();
                    if (top().equals("("))
                         pop();   // remove left parenthesis underneath
                    push (topvalue);
               }
               else if (tokens[i].equals("$")) {
                    if (top(1).equals("$"))
                         return atod(top());
                    else {
                         evalTop2 ();
                         return atod(top());
                    }
               }
               else { 
                    push (tokens[i]);   // assume it is an operator
               }
          }
          return 0;    // just to keep the Java compiler happy
     }

     //-----------------------STACK STUFF-------------------------------

     private void initstack () {
         stack = new String[10];
         maxstack = 10;
         stacktop = -1;    // signals that it is empty
     }

     private void grow () {
         String[] newstack = new String[stack.length+10];
         for (int i=0; i<stack.length; i++)
              newstack[i] = stack[i];
         stack = newstack;
     }

     private void push (String token) {
         if (stacktop == maxstack-1) grow();
         stack[++stacktop] = token;
     }

     private String pop () {
         if (stacktop < 0) return "";
         String returnValue = stack[stacktop];
         stack[stacktop] = null;
         stacktop--;
         return returnValue;
     }

     private String top (int n) {
         if (stacktop-n < 0) return "";
         return stack[stacktop-n];
     }

     private String top () {
         if (stacktop < 0) return "";
         return stack[stacktop];
     }

     //------------------------HELPER STUFF--------------------------

     private boolean isreal (String s) {
         for (int i=0; i<s.length(); i++) {
              char ch = s.charAt(i);
              if (ch != '.' && (ch < '0' || ch > '9')) return false;
         }
         return true;
     }
 
     private static double atod (String s) {
          try {
               return Double.valueOf(s).doubleValue();
          } catch (NumberFormatException nfe) {return 0.0;}
     }

     private static int atoi (String s) {
          try {
               return Integer.valueOf(s).intValue();
          } catch (NumberFormatException nfe) {return 0;}
     }

     private double exponentiate (double val1, double val2) {
          double result = 1;
          for (int i=0; i<val2; i++)
               result *= val1;
          return result;
     }

     private boolean isdigit(char ch) {
          return (ch >= '0' && ch <= '9' || ch == '.');
     }

     //------------------------A SIMPLE PARSER-------------------------

     //  This does not check for correctness of the token stream.
     //  For example,  5 3 +  will not be flagged as incorrect!
     //  Blanks do not count for anything.  They are ignored.
     //  They do not even separate token characters.

     private void parse (String s) {
          tokens = null;
          String curtoken = "";
          s = s + "$";
          for (int i=0; i<s.length(); i++) {
               char ch = s.charAt(i);
               if (isdigit(ch))
                    curtoken += ch;
               else if (curtoken.equals("")) 
                    addToken ("" + ch);
               else if (ch != ' ') {
                    addToken (curtoken);
                    curtoken = "";
                    addToken ("" + ch);
               }
          }
     }

     private void addToken (String newtoken) {
         if (tokens == null) {
              tokens = new String[1];
              tokens[0] = newtoken;
              return;
         }
         String[] newtokens = new String[tokens.length+1];
         for (int i=0; i<tokens.length; i++)
              newtokens[i] = tokens[i];
         tokens = newtokens;
         tokens[tokens.length-1] = newtoken;
     }
     
     public static void main (String[] args)
     {
     	ArithmeticExpressionEvaluator re = new ArithmeticExpressionEvaluator();
     	String expression = "(25-29)*10";
     	System.out.println("re.eval(expression) = "+re.eval(expression));
     	expression = "21-17";
		System.out.println("re.eval(expression) = "+re.eval(expression));
		expression = "(-20)";
		System.out.println("re.eval(expression) = "+re.eval(expression));
		expression = "50";
		System.out.println("re.eval(expression) = "+re.eval(expression));
     }

}


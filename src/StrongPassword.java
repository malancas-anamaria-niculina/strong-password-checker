import java.util.Scanner;

public class StrongPassword {

    private int strongPassword(String password,int numberOfDeletions) {
        int needToReplaceCharacters = 0;
        int oneDeletion = 0, twoDeletion = 0;
        int lowercase = 1, uppercase = 1, digits = 1; //first consideration is that there are no existing digits,
        //uppercase or lowercase characters in the password

        for (int i = 0; i < password.length(); ) {
            //check if the current character is a digit, if it is a digit, means that,
            // it does not need to be inserted into the string
            if (password.charAt(i) > 47 && password.charAt(i) < 58) {
                digits = 0;
            }//check if the current character is uppercase, if it is an uppercase, means that,
            // it does not need to be inserted into the string
            else if (password.charAt(i) > 64 && password.charAt(i) < 91) {
                uppercase = 0;
            }//check if the current character is lowercase
            // if it is lowercase, means that it does not need to be inserted into the string
            else if (password.charAt(i) > 96 && password.charAt(i) < 123) {
                lowercase = 0;
            }

            int length = 1;

            /**
                When the two equals characters occur, the program will start to count the length of the current
                repeating sequence.
             **/
            while (i + length < password.length() && password.charAt(i + length) == password.charAt(i + length - 1)) {
                length++;
            }

            /**
                If is find a repeating sequence that had the length greater or equal to 3, it means that we need to
                reduce the number of reducing characters to be validated in the third condition of the problem.
             **/
            if (length >= 3) {
                //Finding the number of possible replaces in all the sequences that do not validate the third condition
                needToReplaceCharacters += length / 3;
                //Check if the actual sequence is the type of 3*n and mark the possible number of deletions
                if (length % 3 == 0) {
                    oneDeletion++;
                }
                //Check if the actual sequence is the type of 3*n+1 and mark the possible number of deletions
                if (length % 3 == 1) {
                    twoDeletion += 2;
                }
            }

            i += length ; //continue from the next different character after the repeating sequence (if it is the case)
        }

        if (password.length() < 6) {
            //if the password length is less than 6, we check if is need to do replacements
            // (if it is not possible to insert another character in the string due to its length and the second
            // condition for being a strong password is not validate), or only insertions
            return Math.max(lowercase + uppercase + digits - numberOfDeletions,0);
        }
        /**
            If the password length is greater or equal to 6, depending of number of deletions the password have left,
            the characters will be deleted or replace in order to make the password, an strong password
         **/

        //If the password length is less or equal to 20, there is no need for deletion, only replacements, and in this
        //case, the number of characters needed to replace will decrease by the smallest number of deletions
        // (this is the case when we consider the 3*n sequences when one deletion change a replacement)
        //(this value includes only the additional changes, it does not count the number of needed to insert characters)
        needToReplaceCharacters -= Math.min(numberOfDeletions, oneDeletion);

        //This is the case when sequences are in the form of 3*n+1, there, the program will eliminate the minimum number
        //or replaces, based on the possibility to delete one character in a sequence and to obtain a value less or greater
        // than deletion of two characters in a sequence. Because for the sequences in form  3*n+1, two deletions,
        //eliminate a replacement, the number of needed tp replace characters will decrease by the minimum deletions / 2
        needToReplaceCharacters -= Math.min(Math.max(numberOfDeletions - oneDeletion, 0), twoDeletion)/2;

        //For the case when sequences are in the form of 3*n+2, the number of replaces remaining to made are the number
        //of needed deletions / 3 (because 3 deletions means 1 replacement)
        needToReplaceCharacters -= Math.max((numberOfDeletions - oneDeletion - twoDeletion), 0)/3;

        //In the final, we return the minimum number of steps need to modify the password to become a strong password
        //(this value includes only the additional changes, it does not count the number of needed to delete characters)
        return Math.max(lowercase + uppercase + digits, needToReplaceCharacters);
    }

    public int minimumChangesForStrongPassword(String password) {
        int steps = (password.length() < 6) ? (6 - password.length()):((password.length() >= 20) ? (password.length() - 20) : 0);

        //Number of minimum steps to make the given password a strong password
        steps += strongPassword(password, steps);

        return steps;
    }

    public static void main(String[] args) {
        StrongPassword strongPassword = new StrongPassword();

        String checkAnotherPassword = "yes";
        Scanner scanner = new Scanner(System.in);

        while (checkAnotherPassword.equals("yes")){
            System.out.println("Insert a password: ");

            int steps = strongPassword.minimumChangesForStrongPassword(scanner.nextLine());

            if (steps == 0){
                //steps = 0 means password is strong
                System.out.println("Password is strong.");
            }else{
                System.out.println("Minimum number of steps required to make the password strong is: " + steps);
            }

            System.out.println("Do you want to check if another password is strong?");
            checkAnotherPassword = scanner.nextLine();
        }
    }
}

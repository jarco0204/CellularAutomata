
/**
 * @author Johan a.k.a Batman
 * @version 1.24
 *
 * This class will create an elementary automaton based on the arguments provided by the user; number of iterations and rule. The main algorithm is based on the CA.java
 * that professor Mark Hatcher provided us.
 */


public class CA {

    //Main method to execute and run from console
    public static void main(String args[])
    {
        //Handling user-input
        int numIterations= Integer.parseInt(args[0]);
        int ruleNumber= Integer.parseInt(args[1]);
        int widthGrid= numIterations * 2;

        //Converting into binary digit of 8 bits
        String binaryString = "";
        while(ruleNumber > 0)

        {

            int a = ruleNumber % 2;

            binaryString = a + binaryString;

            ruleNumber= ruleNumber / 2;

        }
        switch(binaryString.length())
        {
            case 1:
                binaryString="0000000" + binaryString;
                break;

            case 2:
                binaryString="000000" + binaryString;
                break;

            case 3:
                binaryString= "00000" + binaryString;
                break;
            case 4:
                binaryString = "0000" + binaryString;
                break;
            case 5:
                binaryString= "000" + binaryString;
                break;
            case 6:
                binaryString= "00" + binaryString;
                break;
            case 7:
                binaryString= "0"+ binaryString;
                break;
            default: //handling the case, I get 8 bits j
                break;


        }

        //  System.out.println(binaryString); //For debugging purposes


        // Creating two arrays of boolean values
        boolean[] currentCA= new boolean[widthGrid]; // at time t where t is an iteration
        boolean[] oldCA= new boolean[widthGrid]; // at time t-1
        currentCA[widthGrid/2]= true; // following specifications

        //Main logic of the program
        for(int t=1; t< numIterations; t++)
        {
            //Draw current row
            for(int i=0; i< widthGrid; i++)
            {
                if(currentCA[i])
                {
                    System.out.print("*");
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            //Transfer the contents of current CA to old CA
            for(int j=0; j< widthGrid; j++)
            {
                oldCA[j]= currentCA[j];
            }

            //Calculating the next state based on the rule user wanted
            for( int k=0; k< widthGrid; k++)
            {
                //Handling the special cases; index = 0 and widthGrid -1
                if(k==0)
                {
                    ;
                }
                else if (k==widthGrid-1)
                {
                    ;
                }
                else
                {
                    String neighborhood= "";
                    boolean leftNeighbor= oldCA[k-1];
                    if (leftNeighbor)
                    {
                        neighborhood= 1+ neighborhood;
                    }
                    else
                    {
                        neighborhood= 0+ neighborhood;
                    }
                    boolean cellOperate = oldCA[k];
                    if (cellOperate)
                    {
                        neighborhood= neighborhood+"1";
                    }
                    else
                    {
                        neighborhood= neighborhood+"0";
                    }
                    boolean rightNeighbor= oldCA[k+1];
                    if (rightNeighbor)
                    {
                        neighborhood=neighborhood+"1";
                    }
                    else
                    {
                        neighborhood= neighborhood+"0";
                    }
                   // System.out.println(neighborhood);
                    //Neighborhood should be computed by this step, leap of faith

                    int numForRule=0;
                    switch (neighborhood){
                        case "000":
                            numForRule=7;
                            break;
                        case "001":
                            numForRule=6;
                            break;
                        case "010":
                            numForRule=5;
                            break;
                        case "011":
                            numForRule=4;
                            break;
                        case "100":
                            numForRule=3;
                            break;
                        case "101":
                            numForRule=2;
                            break;
                        case "110":
                            numForRule=1;
                            break;
                        case "111":
                            numForRule=0;
                            break;
                        default:
                            System.out.println("Error you fucked up somewhere");


                    }
                    //After this block of code, I should have the index of the string to access
                    String indexOfRule=  Character.toString(binaryString.charAt(numForRule));
                    //System.out.println(indexOfRule);
                    if(indexOfRule.equals("1"))
                    {
                        currentCA[k]=true;
                    }
                    else
                    {
                        currentCA[k]=false;
                    }



                }
            }
        }
    }
}

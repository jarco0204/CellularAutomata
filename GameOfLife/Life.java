import java.awt.*;

public class Life  {

    /**
        Main function to run the game. Takes three arguments
        size, iterations, configurations
     */
    public static void main(String[] args)
    {
        if (args[2].toUpperCase().equals("R") || args[2].toUpperCase().equals("G") )
        {
            Life game= new Life(args[0],args[1],args[2]);
        }
        else
        {
            throw new IllegalArgumentException("Illegal configuration. R or G");
        }

    }

    //Fields
    private int[][] current; // current state of cells (time t)
    private int[][] previous; // previous state of cell (time t-1)
    private int size; // width and height of square grid
    private int iterations; // maximum number of iterations
    private String configuration;
    private final int magnification=15; // Idea from the PictureDemo class

    //Fields for display
    private Picture graphUI;

    /**
     * Constructor of the class. It takes three parameters that are passed
     * when compiling the program
     *
     */
    public Life(String size, String iter, String config)
    {
        this.size=Integer.parseInt(size);
        current= new int[this.size][this.size];
        previous= new int[this.size][this.size];
        iterations= Integer.parseInt(iter);
        configuration= config;


        graphUI= new Picture(this.size*magnification,this.size*magnification);
        setUpGrid();
        setUpConfiguration(configuration);


        //Main Logic
        for(int i=0; i<iterations; i++)
        {
            System.out.println(i);
            //Draw current iter
            for(int j=0; j<this.size;j++)
            {
                for(int k=0; k<this.size;k++)
                {
                    previous[j][k]= current[j][k]; // transferring the contents in the same loop
                    if(current[j][k]==1)
                    {
                        drawCell(j,k,Color.black);
                    }
                    else
                    {
                        drawCell(j,k,Color.LIGHT_GRAY);
                    }

                }
            }
            graphUI.show();
            //Transfer current to previous

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Calculate next state
            //Implemented using Moore's idea of neighborhood
            for(int row=0;row<this.size;row++)
            {
                for(int column=0;column<this.size; column++ )
                {
                    //All cases depend on checking the amount of live cells in a neighbourhood

                    //All special cases in the first row; following the principle of torus
                    if(row==0 && column==0) // Special case
                    {
                        int liveCells=checkNumberNeighbours(0,0,0);
                        current[0][0]=makeDecisionState(liveCells,0,0);
                        //System.out.println("cell 0,0"+"numN: "+liveCells);
                    }
                    else if(row==0 && column==this.size-1)
                    {
                        int liveCells=checkNumberNeighbours(1,0,this.size-1);
                        current[0][this.size-1]=makeDecisionState(liveCells,0,this.size-1);
                        //System.out.println("cell 0,"+this.size+"numN: "+liveCells);
                    }
                    else if(row==this.size-1 && column==this.size-1)
                    {
                        int liveCells=checkNumberNeighbours(3,0,this.size-1);
                        current[this.size-1][this.size-1]=makeDecisionState(liveCells,this.size-1,this.size-1);
                        //System.out.println("cell"+this.size+","+this.size+" numN: "+liveCells);
                    }
                    else if(row==this.size-1 && column==0)
                    {
                        int liveCells=checkNumberNeighbours(2,this.size-1,0);
                        current[this.size-1][0]=makeDecisionState(liveCells,this.size-1,0);
                        //System.out.println("cell"+this.size+",0"+" numN: "+liveCells);
                    }
                    else if(row==0)
                    {
                        int liveCells=checkNumberNeighbours(4,0,column); // Quantifying first row
                        current[0][column]=makeDecisionState(liveCells,0,column);
                        //System.out.println("cell0"+","+column+ "numN "+liveCells);
                    }

                    else if(column== 0)
                    {
                        int liveCells=checkNumberNeighbours(7,row,0);
                        current[row][0]=makeDecisionState(liveCells,row,0);
                        //System.out.println("cell"+row+","+column+"numN: "+liveCells);

                    }
                    else if(column==this.size-1)
                    {
                        int liveCells=checkNumberNeighbours(5,row,this.size-1); // Quantifying last column
                        current[row][this.size-1]=makeDecisionState(liveCells,row,this.size-1);
                        //System.out.println("cell"+row+" "+this.size+"numN: "+liveCells);
                    }
                    //Possibly an error

                    else if( row==this.size-1)
                    {
                        int liveCells=checkNumberNeighbours(6,this.size-1,column);
                        current[this.size-1][column]=makeDecisionState(liveCells,this.size-1,column);
                        //System.out.println("cell"+this.size+","+column+"numN: "+liveCells);
                    }

                    else // normal cells that follow same pattern
                    {
                        int liveCells=checkNumberNeighbours(8,row,column);
                        //System.out.println("cell"+row+" "+ column+"numN: "+liveCells);
                        current[row][column]=makeDecisionState(liveCells,row,column);
                    }

                }
            }

        }




    }
    /**
     * Function to set all the cells of the array as false, in order to facilitate the process of establishing the configurations
     */
    private void setUpGrid()
    {
        for(int i=0; i<size;i++)
        {
            for(int j=0; j<size; j++)
            {
                current[i][j]=0; // ALl cells would be dead
                drawCell(i,j,Color.LIGHT_GRAY);
            }
        }

    }
    /**
     * This function was taken from the PictureDemo class
     */
    private void drawCell(int i, int j,Color col)
    {

        for (int offsetX = 0; offsetX < magnification; offsetX++)
        {
            for (int offsetY = 0; offsetY < magnification; offsetY++)
            {
                // set() colours an individual pixel
                graphUI.set((j*magnification)+offsetY,
                        (i*magnification)+offsetX, col);
            }
        }
    }
    /**
     * Function to set the initial configuration of the board
     */
    private void setUpConfiguration(String configuration1)
    {
        if(configuration1.equals("R"))
        {
            //Create a random number generator
            current[2][2]= 1;
            current[2][3]=1;
            current[3][2]=1;
            current[3][3]=1;
            //Beehive
            current[6][4]=1;
            current[6][5]=1;
            current[7][3]=1;
            current[7][6]=1;
            current[8][4]=1;
            current[8][5]=1;
            //Boat
            current[10][8]=1;
            current[10][9]=1;
            current[11][8]=1;
            current[11][10]=1;
            current[12][9]=1;
            //Loaf
            current[15][20]=1;
            current[15][21]=1;
            current[16][19]=1;
            current[16][22]=1;
            current[17][20]=1;
            current[17][22]=1;
            current[18][21]=1;
            //OSCILLATORS
            //blinker
            current[2][40]=1;
            current[3][40]=1;
            current[4][40]=1;
            //toad
            current[20][4]=1;
            current[20][5]=1;
            current[20][6]=1;
            current[21][3]=1;
            current[21][4]=1;
            current[21][5]=1;
            //beacon
            current[20][30]=1;
            current[20][31]=1;
            current[21][30]=1;
            current[22][33]=1;
            current[23][32]=1;
            current[23][33]=1;

            //Spaceships
            //glider
            current[30][4]=1;
            current[30][6]=1;
            current[29][6]=1;
            current[31][5]=1;
            current[31][6]=1;

            //lightweight
            current[30][20]=1;
            current[30][23]=1;
            current[31][24]=1;
            current[32][20]=1;
            current[32][24]=1;
            current[33][21]=1;
            current[33][22]=1;
            current[33][23]=1;
            current[33][24]=1;
            //block for spaceship to crash
            current[30][28]=1;
            current[30][29]=1;
            current[31][28]=1;
            //current[31][29]=1;


        }
        else // It will want a glider gun
        {
            current[2][26] = 1;
            current[3][24] = 1;
            current[3][26] = 1;
            current[4][14] = 1;
            current[4][15] = 1;
            current[4][22] = 1;
            current[4][23] = 1;
            current[4][36] = 1;
            current[4][37] = 1;
            current[5][13] = 1;
            current[5][17] = 1;
            current[5][22] = 1;
            current[5][23] = 1;
            current[5][36] = 1;
            current[5][37] = 1;
            current[6][2] = 1;
            current[6][3] = 1;
            current[6][12] = 1;
            current[6][18] = 1;
            current[6][22] = 1;
            current[6][23] = 1;
            current[7][2] = 1;
            current[7][3] = 1;
            current[7][12] = 1;
            current[7][16] = 1;
            current[7][18] = 1;
            current[7][19] = 1;
            current[7][24] = 1;
            current[7][26] = 1;
            current[8][12] = 1;
            current[8][18] = 1;
            current[8][26] = 1;
            current[9][13] = 1;
            current[9][17] = 1;
            current[10][14] = 1;
            current[10][15] = 1;

        }
    }
    /**
     * Function to check how many live neighbors any cell has
     * @return int representing the number of neighboors
     * @parameters int representing the special case
     */
    private int checkNumberNeighbours(int caso, int x, int y)
    {
        int liveCells=0;
        switch(caso)
        {
            //Cell 0,0
            case 0:
            {
                //All cases correct
                if(previous[size-1][0]==1) liveCells++; //North
                if(previous[size-1][1]==1) liveCells++; //NorthEast
                if(previous[0][1]==1) liveCells++; //East
                if(previous[1][1]==1) liveCells++; //SouthEast
                if(previous[1][0]==1) liveCells++; //South
                if(previous[1][size-1]==1) liveCells++; //SouthWest
                if(previous[0][size-1]==1) liveCells++; //West
                if(previous[size-1][size-1]==1) liveCells++; //NorthWest
                break;

            }
            //cell 0,n-1
            case 1:
            {
                //All cases correct
                if(previous[size-1][size-1]==1) liveCells++; //North
                if(previous[size-1][0]==1) liveCells++; //NorthEast
                if(previous[0][0]==1) liveCells++; //East
                if(previous[1][0]==1) liveCells++; //SouthEast
                if(previous[1][size-1]==1) liveCells++; //South
                if(previous[1][size-2]==1) liveCells++; //SouthWest
                if(previous[0][size-2]==1) liveCells++; //West
                if(previous[size-1][size-2]==1) liveCells++; //NorthWest
                break;
            }
            //Cell in n-1,0
            case 2:
            {
                //All cases correct
                if(previous[size-2][0]==1) liveCells++; //North
                if(previous[size-2][1]==1) liveCells++; //NorthEast
                if(previous[size-1][1]==1) liveCells++; //East
                if(previous[0][1]==1) liveCells++; //SouthEast
                if(previous[0][0]==1) liveCells++; //South
                if(previous[0][size-1]==1) liveCells++; //SouthWest
                if(previous[size-1][size-1]==1) liveCells++; //West
                if(previous[size-2][size-1]==1) liveCells++; //NorthWest
                break;
            }
            //cell n-1,n-1
            case 3: {
                //All cases correct
                if(previous[size-2][size-1]==1) liveCells++; //North
                if(previous[size-2][0]==1) liveCells++; //NorthEast
                if(previous[size-1][0]==1) liveCells++; //East
                if(previous[0][0]==1) liveCells++; //SouthEast
                if(previous[0][size-1]==1) liveCells++; //South
                if(previous[0][size-2]==1) liveCells++; //SouthWest
                if(previous[size-1][size-2]==1) liveCells++; //West
                if(previous[size-2][size-2]==1) liveCells++; //NorthWest
                break;
            }
            //cells 0,y
            case 4:
            {
                //All cases correct
                if(previous[size-1][y]==1) liveCells++; //North
                if(previous[size-1][y+1]==1) liveCells++; //NorthEast
                if(previous[0][y+1]==1) liveCells++; //East
                if(previous[1][y+1]==1) liveCells++; //SouthEast
                if(previous[1][y]==1) liveCells++; //South
                if(previous[1][y-1]==1) liveCells++; //SouthWest
                if(previous[0][y-1]==1) liveCells++; //West
                if(previous[size-1][y-1]==1) liveCells++; //NorthWest
                break;

            }
            //cells x,size-1
            case 5:
            {
                //All cases correct
                if(previous[x-1][size-1]==1) liveCells++; //North
                if(previous[x-1][0]==1) liveCells++; //NorthEast
                if(previous[x][0]==1) liveCells++; //East
                if(previous[x+1][0]==1) liveCells++; //SouthEast
                if(previous[x+1][size-1]==1) liveCells++; //South
                if(previous[x+1][size-2]==1) liveCells++; //SouthWest
                if(previous[x][size-2]==1) liveCells++; //West
                if(previous[x-1][size-2]==1) liveCells++; //NorthWest
                break;
            }
            //cells size-1,y
            case 6:
            {
                //All cases correct
                if(previous[size-2][y]==1) liveCells++; //North
                if(previous[size-2][y+1]==1) liveCells++; //NorthEast
                if(previous[size-1][y+1]==1) liveCells++; //East
                if(previous[0][y+1]==1) liveCells++; //SouthEast
                if(previous[0][y]==1) liveCells++; //South
                if(previous[0][y-1]==1) liveCells++; //SouthWest
                if(previous[size-1][y-1]==1) liveCells++; //West
                if(previous[size-2][y-1]==1) liveCells++; //NorthWest
                break;
            }
            //cells x,0
            case 7:
            {
                if(previous[x-1][0]==1) liveCells++; //North
                if(previous[x-1][1]==1) liveCells++; //NorthEast
                if(previous[x][1]==1) liveCells++; //East
                if(previous[x+1][1]==1) liveCells++; //SouthEast
                if(previous[x+1][0]==1) liveCells++; //South
                if(previous[x+1][size-1]==1) liveCells++; //SouthWest
                if(previous[x][size-1]==1) liveCells++; //West
                if(previous[x-1][size-1]==1) liveCells++; //NorthWest
                break;
            }
            //Rest of cells
            case 8://problem
            {
                if(previous[x-1][y]==1) liveCells++; //North
                if(previous[x-1][y+1]==1) liveCells++; //NorthEast
                if(previous[x][y+1]==1) liveCells++; //East
                if(previous[x+1][y+1]==1) liveCells++; //SouthEast
                if(previous[x+1][y]==1) liveCells++; //South
                if(previous[x+1][y-1]==1) liveCells++; //SouthWest
                if(previous[x][y-1]==1) liveCells++; //West
                if(previous[x-1][y-1]==1) liveCells++; //NorthWest
                break;
            }
            default:
            {
                System.out.println("error");
            }
        }
        return liveCells;
    }
    /**
     * Function to decide what is the outcome of an individual cell based on the number of cells and its current state
     */
    private int makeDecisionState(int numCells,int row,int column)
    {
        //Lives and Reproduction
         if( (numCells==2 && previous[row][column]==1) || numCells==3 ) //Key statement
            {
            return 1; // mark cell as alive
            }
         //Underpopulation

        else if(numCells<=2)
            {
            return 0; // mark cell as dead
            }

        //Overpopulation
        else if(numCells> 3)
        {
            return 0; // mark cell as dead
        }

        else
        {

            System.out.println("major error");
            return 0;
        }
    }
}

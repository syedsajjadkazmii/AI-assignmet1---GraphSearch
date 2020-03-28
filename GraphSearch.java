
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Syed Sajjad Kazmii
 */
public class GraphSearch {

    //declaring public variables

    public static int m = 0, n = 0, t = 0;
    public static String[] stateDescriptions;
    public static String[] ruleDescriptions;
    public static int[][] transitionTable;
    public static String[] testCases;

    public static Queue<Node> frontier= new LinkedList<Node>();
    public static Set<Integer> exploredSet = new HashSet<Integer>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //initializing
        System.out.println("input----");
        m = sc.nextInt();
        n = sc.nextInt();
        t = sc.nextInt();

        stateDescriptions = new String[m];
        ruleDescriptions = new String[n];
        transitionTable = new int[m][n];
        testCases = new String[t];

        sc.nextLine();
        sc.nextLine();
        for (int i = 0; i < m; i++) {
            stateDescriptions[i] = sc.nextLine();
        }
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            ruleDescriptions[i] = sc.nextLine();
        }
        sc.nextLine();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transitionTable[i][j] = sc.nextInt();
            }
        }
        sc.nextLine();
        sc.nextLine();
        for (int i = 0; i < t; i++) {
            testCases[i] = sc.nextLine();
        }

        String[] temp;
        Node result;
        for(int i=0;i<t;i++){
            temp=testCases[i].split("\t");
            result=graphSearch(temp[0], temp[1]);
            System.out.println("");
            printResult(result);            
        }
        System.out.println("");
    }
    public static Node graphSearch(String initialState, String finalState){
        int initStateNum=getStateNumber(initialState);
        int goalStateNum=getStateNumber(finalState);
        Node initialNode=new Node(initStateNum, -1, 0, null);
        frontier.add(initialNode);
        exploredSet.clear();
        do{
            if(frontier.isEmpty())
                return new Node(-1, -1, -1, null);
            
            Node leafNode=frontier.remove();
            if(leafNode.getState()==goalStateNum)
                return leafNode;
            
            exploredSet.add(leafNode.getState());
            Node childs[] = getChildNodes(leafNode);
            int i=0;
            while(i<childs.length){
                if(!isNodeVisited(childs[i]))
                    frontier.add(childs[i]);
                i++;
            }
        }while(true);
    }
    public static int getStateNumber(String state){
        int i=0;
        while(i<m && stateDescriptions[i].compareTo(state)!=0){
            i++;
        }
        return i;
    }
    public static Node[] getChildNodes(Node parent){
        Node[] childs=new Node[n];
        for(int i=0;i<n;i++){
            childs[i]=new Node(transitionTable[parent.getState()][i], i, parent.getCost()+1, parent);
        }
        return childs;
    }
    public static boolean isNodeVisited(Node node){
        if(isNodeExistInExploredSet(node) || isNodeExistInFrontier(node))
            return true;
        else
            return false;
    }
    public static boolean isNodeExistInFrontier(Node node){
        int i=0;
        Node temp;
        boolean flag=false;
        while(i<frontier.size()){
            temp=frontier.remove();
            if(node.getState()==temp.getState())
                flag = true;
            frontier.add(temp);
            i++;
        }
        return flag;    
    }
    public static boolean isNodeExistInExploredSet(Node node){
        int i=0;
        if(exploredSet.contains(node.getState()))
            return true;
        else
            return false;
    }        
    public static void printResult(Node node){
        if(node.getState()==-1)
            System.out.println("No Solution Exist");
        else{
            Stack<String> s=new Stack<String>();
            while(node.getParent()!=null){
                s.push(ruleDescriptions[node.getAction()]);
                node=node.getParent();
            }
            
            for(int i=s.size()-1;i>=0;i--){
                System.out.print(s.pop());
                if(i!=0)
                    System.out.print(" => ");
            }
        }
    }
    
}

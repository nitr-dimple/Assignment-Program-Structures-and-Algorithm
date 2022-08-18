package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Dimpleben Kanjibhai Patel
 */
public class UF_HWQUPC_client {
    public static int count(int n){
        int connections = 0, pairs = 0;
        UF_HWQUPC uf = new UF_HWQUPC(n, true);
        Random rand = new Random();
        while(uf.components() != 1){
            int p = rand.nextInt(n);
            int q = rand.nextInt(n);
            if(!uf.connected(p,q)) {
                uf.union(p, q);
                connections++;
            }
            pairs++;
        }
        System.out.println("Pairs generated : " + pairs);
        System.out.println("Connections generated : " + connections);
        return pairs;
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter Number of sites: ");
        int n = sc.nextInt();
        System.out.println("Number of sites: " + n );
        int pairs = count(n);
    }
}

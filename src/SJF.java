import java.util.*;

class SJF {
    private static int job;
    private static int[][] process;
    private static int[] flag;
    private static Scanner sc = new Scanner(System.in);

    //driver code
    public static void main(String[] args) {
        job_detail();
        job_before();
        job_after();
    }

    //input number of job
    public static void getJob() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total of jobs waiting in queue: ");
        job = sc.nextInt();
    }

    //input arrival time and cpu time of each job
    public static void job_detail() {
        getJob();
        process = new int[job][6];
        flag = new int[job];
        for (int i = 0; i < job; i++) {
            System.out.println("****** Job " + (i + 1) + " ******");
            process[i][0] = i;
            System.out.print("Enter Arrival Time: ");
            process[i][1] = sc.nextInt();
            System.out.print("Enter CPU Time required to execute: ");
            process[i][2] = sc.nextInt();
            flag[i] = 0;
        }
    }

    //display the initial job list
    public static void job_before() {
        System.out.println("Before Arrange...");
        System.out.printf("%10s %20s %20s\n", "Job ID", "Arrival Time", "CPU Time");
        for (int i = 0; i < job; i++) {
            System.out.printf("%10d %20d %20d \n", ((process[i][0]) + 1), process[i][1], process[i][2]);
        }
    }

    //after scheduling
    public static void job_after() {
        compT();
        System.out.print("\nFinal Result...\n");
        System.out.print("Wait few seconds...\n");
        System.out.printf("%10s %20s %20s %20s %20s\n", "Job ID", "Arrival Time", "CPU Time", "Waiting Time", "Turnaround Time");
        double ttlTurnaround = 0.0, ttlWaiting = 0.0;
        for (int i = 0; i < job; i++) {
            System.out.printf("%10d %20d %20d %20d %20d\n", ((process[i][0]) +
                    1), process[i][1], process[i][2], process[i][4], process[i][5]);
            ttlWaiting += process[i][4];
            ttlTurnaround += process[i][5];
        }
        System.out.println("Average waiting time : \t" + ttlWaiting / job);
        System.out.print("Average turnaround time : \t" + ttlTurnaround / job);
    }

    //completion time
    public static void compT() {
        int tot = 0, st = 0;
        while (true) {
            int c = job, min = 999;
            if (tot == job)
                break;
            for (int i = 0; i < job; i++) {
                if ((process[i][1] <= st) && (flag[i] == 0) && (process[i][2] < min)) {
                    min = process[i][2];
                    c = i;
                }
            }
            if (c == job)
                st++;
            else {
                process[c][3] = st + process[c][2];
                st += process[c][2];
                process[c][5] = process[c][3] - process[c][1];
                process[c][4] = process[c][5] - process[c][2];
                flag[c] = 1;
                tot++;
            }
        }
        sortCompT();
    }

    //sort the queue by its completion time
    public static void sortCompT() {
        for (int i = 0; i < job; i++) {
            for (int j = 0; j < job - i - 1; j++) {
                if (process[j][3] > process[j + 1][3]) {
                    for (int k = 0; k < 5; k++) {
                        int temp = process[j][k];
                        process[j][k] = process[j + 1][k];
                        process[j + 1][k] = temp;
                    }
                }
            }
        }
    }
}


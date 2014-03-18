package my.example.code;

/**
 * Implementation of a data center capacity exercise for the Apple Search Engineering team interview process.
 * <p/>
 * <b>You need to write a program that:</b>
 * <ul>
 *     <li>Reads M and N from standard input.</li>
 *     <li>Reads versions of subsets hosted by all of M x N machines. Each line represents a group of N machines; i-th
 *     machine hosts i-th subset, and i-th number on the line is the version of the subset on that machine.</li>
 *     <li>Computes the overall capacity of the service and writes it to the standard output. You may write the program
 *     in either C, C++, or Java and use the standard libraries for collections and algorithms (e.g. STL). Please
 *     optimize for computational complexity in the worst case, and provide an upper boundary for it in terms of the
 *     basic operations - comparisons, copies, loop iterations, memory allocations (so if you use a standard
 *     container, you need to mention associated computational costs).</li>
 * </ul>
 * <p/>
 * <b>Example input:</b>
 * <table border=0>
 *     <tr><td>4</td><td>5</td><td/><td/><td/></tr>
 *     <tr><td>103</td><td>107</td><td>103</td><td>107</td><td>107</td></tr>
 *     <tr><td>107</td><td>103</td><td>107</td><td>107</td><td>103</td></tr>
 *     <tr><td>103</td><td>107</td><td>107</td><td>103</td><td>103</td></tr>
 *     <tr><td>103</td><td>103</td><td>107</td><td>107</td><td>107</td></tr>
 * </table>
 * <p/>
 * <b>Expected output:</b> 200
 * <p/>
 * <b>Author assumptions:</b>
 * <ul>
 *     <li>In practical terms, M and N would not in multiple exceed a value of 100,000 (i.e. a server
 *     farm of 100,000 machines dedicated to a single task is extremely rare). As such,
 *     I would not normally not be concerned in excess about the performance characteristics of this code now or in
 *     the near term. However, due to the last bullet above, I am coding as if the data sets being operated on could
 *     be huge.</li>
 * </ul>
 *
 * @author David Williams (davidkwilliams@yahoo.com)
 * @since 6/11/2013
 */
public class DataCenterCapacity {
    private static final int REQUESTS_PER_SECOND_PER_MACHINE_GROUP = 100;

    public static void main(String[] args) {
        if (args.length > 0) {
            final DataCenterInstancesReader<MultisetsDataCenterInstances> dataCenterInstancesReader
                    = new FileDataCenterInstancesReader<MultisetsDataCenterInstances>(args[args.length - 1]);
            final DataCenterInstances dataCenterInstances = dataCenterInstancesReader.read(
                    MultisetsDataCenterInstances.class);
            dataCenterInstances.calculateTotalUsableMachineGroups();
            System.out.println();
            System.out.println("----------------");
            System.out.println("Result: The capacity is " + dataCenterInstances.calculateTotalUsableMachineGroups()
                    * REQUESTS_PER_SECOND_PER_MACHINE_GROUP);
            System.out.println("----------------");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Invalid input. Expected command line is:");
            System.out.println();
            System.out.println("  java -jar data-center-capacity-v2.jar INPUTFILE");
            System.out.println();
        }
    }
}

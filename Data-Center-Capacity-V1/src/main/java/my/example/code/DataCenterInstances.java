package my.example.code;

/**
 * Describes a set of behaviors used for storing data center capacity data and calculating the usable machine groups
 * (M) the data describes. It is intended that each implementation of this interface provide a new strategy for
 * how the data is stored and calculated in a performant way.
 * <p/>
 * @author David Williams (david.k.williams@gmail.com)
 * @since 6/11/2013
 */
public interface DataCenterInstances {
    /**
     * <p>Returns the total number of machine groups to be held by this instance.</p>
     *
     * @return the total number of machine groups to be held by this instance
     */
    int getTotalMachineGroups();

    /**
     * <p>Sets the total number of machine groups to be held by this instance.</p>
     *
     * @param totalMachineGroups the total number of machine groups to be held by this instance
     */
    void setTotalMachineGroups(final int totalMachineGroups);

    /**
     * <p>Returns the total number of data subsets to be held by this instance.</p>
     *
     * @return the total number of data subsets to be held by this instance
     */
    int getTotalDataSubsets();

    /**
     * <p>Sets the total number of data subsets to be held by this instance.</p>
     *
     * @param totalDataSubsets the total number of data subsets to be held by this instance
     */
    void setTotalDataSubsets(final int totalDataSubsets);

    /**
     * <p>Adds for management a data subset machine instance with the given version.</p>
     *
     * @param machineGroupIndex the machine group index (0 based) for this data subset machine instance
     * @param dataSubsetIndex the data subset index (0 based) for this data subset machine instance
     * @param version the version for this data subset
     */
    void addInstance(final int machineGroupIndex, final int dataSubsetIndex, final int version);

    /**
     * <p>Calculates the number of machine groups containing a set of matching data subset versions using the managed
     * data. These represent a complete set of data subsets which together can server 100 requests per second.</p>
     *
     * @return the number of machine groups containing a set of matching data subset versions
     */
    int calculateTotalUsableMachineGroups();
}

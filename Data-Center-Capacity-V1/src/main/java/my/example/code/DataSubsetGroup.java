package my.example.code;

/**
 * Describes a set of behaviors used for storing and manipulating the set of all instances holding a single data
 * subset (spans machine groups). It is intended that each implementation of this interface provide a new strategy for
 * how the subset data is stored and manipulated in a performant way.
 * <p/>
 * @author David Williams (davidkwilliams@yahoo.com)
 * @since 6/11/2013
 */
public interface DataSubsetGroup {
    /**
     * <p>Return the total number of machine instances for this data subset. This is not version-specific.</p>
     *
     * @return the total number of machine instances for this data subset
     */
    int getTotalMachineInstances();

    /**
     * <p>Adds an instance with the given version to this data subset.</p>
     *
     * @param version the version for this new instance
     */
    void addInstance(final int version);

    /*
     * <p>Updates this {@link DataSubsetGroup} by retaining only those subset versions which can be paired
     * one-to-one with the subset versions contained in the {@link DataSubsetGroup} passed in.</p>
     *
     * @param thatDataSubsetGroup the {@link DataSubsetGroup} used in this operation
     */
    void retainCommonVersionsAndQuantities(final DataSubsetGroup thatDataSubsetGroup);
}

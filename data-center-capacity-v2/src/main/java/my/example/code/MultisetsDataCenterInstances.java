package my.example.code;

import com.google.common.collect.Multiset;
import com.google.common.collect.HashMultiset;

import java.util.*;

/**
 * Implements a set of behaviors used for storing data center capacity data and calculating the usable machine groups
 * (M) the data describes. The strategy used by this implementation is to use
 * {@link my.example.code.MultisetsDataCenterInstances} instances for the {@link my.example.code.DataSubsetGroup}
 * behaviors.
 * <p/>
 * @author David Williams (davidkwilliams@yahoo.com)
 * @since 3/17/2014
 */
public class MultisetsDataCenterInstances implements DataCenterInstances {
    private int totalMachineGroups = 0;
    private int totalDataSubsets = 0;
    private List<Multiset<Integer>> dataSubsetMultisets = null;

    public MultisetsDataCenterInstances() {
        // Do nothing...
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalMachineGroups() {
        return totalMachineGroups;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTotalMachineGroups(final int totalMachineGroups) {
        this.totalMachineGroups = totalMachineGroups;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalDataSubsets() {
        return totalDataSubsets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTotalDataSubsets(final int totalDataSubsets) {
        this.totalDataSubsets = totalDataSubsets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInstance(final int machineGroupIndex, final int dataSubsetIndex, final int version) {
        if (machineGroupIndex < 0 || !(machineGroupIndex < totalMachineGroups)) {
            throw new IllegalArgumentException("Invalid machineGroupIndex value of " + machineGroupIndex);
        }
        if (dataSubsetIndex < 0 || !(dataSubsetIndex < totalDataSubsets)) {
            throw new IllegalArgumentException("Invalid dataSubsetIndex value of " + dataSubsetIndex);
        }
        final Multiset<Integer> dataSubsetMultiset;
        if (0 == machineGroupIndex) {
            if (0 == dataSubsetIndex) {
                dataSubsetMultisets = new ArrayList<Multiset<Integer>>(totalDataSubsets);
            }
            dataSubsetMultiset = HashMultiset.<Integer>create();
            dataSubsetMultisets.add(dataSubsetMultiset);
        } else {
            dataSubsetMultiset = dataSubsetMultisets.get(dataSubsetIndex);
        }
        dataSubsetMultiset.add(version); // TODO: Check that autoboxing uses Integer.valueof() - for performance
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateTotalUsableMachineGroups() {
        if (null == dataSubsetMultisets) {
            return 0;
        }
        final Multiset<Integer> dataSubsetMultisetsIntersection = HashMultiset.<Integer>create();
        for (final Multiset<Integer> dataSubsetMultiset : dataSubsetMultisets) {
            if (dataSubsetMultisetsIntersection.isEmpty()) {
                dataSubsetMultisetsIntersection.addAll(dataSubsetMultiset);
            } else {
                dataSubsetMultisetsIntersection.retainAll(dataSubsetMultiset);
            }
        }
        return dataSubsetMultisetsIntersection.size();
    }
}

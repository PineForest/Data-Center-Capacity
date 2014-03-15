package my.example.code;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a set of behaviors used for storing data center capacity data and calculating the usable machine groups
 * (M) the data describes. The strategy used by this implementation is to use {@link UniqueVersionsDataSubsetGroup}
 * instances for the {@link DataSubsetGroup} behaviors.
 * <p/>
 * @author David Williams (david.k.williams@gmail.com)
 * @since 6/11/2013
 */
public class UniqueVersionsDataCenterInstances implements DataCenterInstances {
    private int totalMachineGroups = 0;
    private int totalDataSubsets = 0;
    private List<DataSubsetGroup> dataSubsetGroups = null;

    public UniqueVersionsDataCenterInstances() {
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
        DataSubsetGroup dataSubsetGroup;
        if (0 == machineGroupIndex) {
            if (0 == dataSubsetIndex) {
                dataSubsetGroups = new ArrayList<>(totalDataSubsets);
            }
            dataSubsetGroup = new UniqueVersionsDataSubsetGroup(totalMachineGroups);
            dataSubsetGroups.add(dataSubsetGroup);
        } else {
            dataSubsetGroup = dataSubsetGroups.get(dataSubsetIndex);
        }
        dataSubsetGroup.addInstance(version);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateTotalUsableMachineGroups() {
        if (null == dataSubsetGroups) {
            return 0;
        }
        DataSubsetGroup retainedDataSubsetGroup = null;
        for (final DataSubsetGroup dataSubsetGroup : dataSubsetGroups) {
            if (null == retainedDataSubsetGroup) {
                retainedDataSubsetGroup = dataSubsetGroup;
            } else {
                retainedDataSubsetGroup.retainCommonVersionsAndQuantities(dataSubsetGroup);
            }
        }
        return retainedDataSubsetGroup.getTotalMachineInstances();
    }
}

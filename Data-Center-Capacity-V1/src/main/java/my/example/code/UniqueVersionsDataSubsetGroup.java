package my.example.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link DataSubsetGroup} that maintains the set of unique versions found in this data subset and
 * the count of machine instances that are using each of these versions.
 * <p/>
 * @author David Williams (davidkwilliams@yahoo.com)
 * @since 6/11/2013
 */
public class UniqueVersionsDataSubsetGroup implements DataSubsetGroup {
    /**
     * A wrapper class for an <code>int</code> quantity.
     */
    private static class QuantityHolder {
        private int value;

        /**
         * Creates a new instance with a value set to 1.
         */
        public QuantityHolder() {
            value = 1;
        }

        public int getQuantity() {
            return value;
        }

        public void increment() {
            ++value;
        }

        public void retainLesser(final QuantityHolder that) {
            value = value < that.value ? value : that.value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final QuantityHolder that = (QuantityHolder) o;

            return (value == that.value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return value;
        }
    }

    private int totalManagedGroups;
    private Map<DataSubsetVersion, QuantityHolder> versionedQuantities;

    public UniqueVersionsDataSubsetGroup(final int totalManagedGroups) {
        // Simple formula to pre-allocate space to minimize resizing of map. Should be tuned.
        versionedQuantities = new HashMap<>(totalManagedGroups / 4);
        this.totalManagedGroups = totalManagedGroups;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalMachineInstances() {
        return totalManagedGroups;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInstance(final int version) {
        final DataSubsetVersion dataSubsetVersion = DataSubsetVersion.getInstance(version);
        final QuantityHolder dataSubsetQuantity = versionedQuantities.get(dataSubsetVersion);
        if (null == dataSubsetQuantity) {
            versionedQuantities.put(dataSubsetVersion, new QuantityHolder());
        } else {
            dataSubsetQuantity.increment();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void retainCommonVersionsAndQuantities(final DataSubsetGroup thatDataSubsetGroup) {
        final UniqueVersionsDataSubsetGroup thatUniqueVersionsDataSubsetGroup =
                (UniqueVersionsDataSubsetGroup) thatDataSubsetGroup;
        final Set<DataSubsetVersion> dataSubsetVersionsIntersection = versionedQuantities.keySet();
        final Set<DataSubsetVersion> thatDataSubsetVersions = thatUniqueVersionsDataSubsetGroup.versionedQuantities
                .keySet();
        dataSubsetVersionsIntersection.retainAll(thatDataSubsetVersions);
        final Map<DataSubsetVersion, QuantityHolder> versionedQuantitiesIntersection = new HashMap<>(
                dataSubsetVersionsIntersection.size());
        int revisedTotalManagedGroups = 0;
        for (final DataSubsetVersion dataSubsetVersion : dataSubsetVersionsIntersection) {
            final QuantityHolder thisDataSubsetVersionQuantity = versionedQuantities.get(dataSubsetVersion);
            final QuantityHolder thatDataSubsetVersionQuantity = thatUniqueVersionsDataSubsetGroup.versionedQuantities
                    .get(dataSubsetVersion);
            if (null == thisDataSubsetVersionQuantity || null == thatDataSubsetVersionQuantity) {
                throw new IllegalStateException("Expected element missing from map. Likely a concurrent modification");
            }
            thisDataSubsetVersionQuantity.retainLesser(thatDataSubsetVersionQuantity);
            versionedQuantitiesIntersection.put(dataSubsetVersion, thisDataSubsetVersionQuantity);
            revisedTotalManagedGroups += thisDataSubsetVersionQuantity.getQuantity();
        }
        this.versionedQuantities = versionedQuantitiesIntersection;
        this.totalManagedGroups = revisedTotalManagedGroups;
    }
}

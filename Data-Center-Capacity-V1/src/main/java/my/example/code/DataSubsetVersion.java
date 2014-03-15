package my.example.code;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides a factory for wrappers of <code>int</code> versions. The factory method looks for a {@link
 * DataSubsetVersion} instance previously created with the same version and will return it if found. To avoid
 * creation of {@link DataSubsetVersion} instances as keys (which would defeat the purpose of the cache!),
 * a keys pool is maintained and keys within it reused by updating their version when checked out of the pool. If a
 * key from the pool is not available OR if the cache does not hold a {@link DataSubsetVersion} for the version
 * requested, a new {@link DataSubsetVersion} is created (and in the latter case, added to the cache).
 * <p/>
 * Note: this is designed to be used in a concurrent environment. Additionally, it is designed to be performant by
 * reducing the creation and release of numerous duplicate instances for the same version. This will reduce memory
 * usage as well as garbage in the heap.
 * <p/>
 * @author David Williams (david.k.williams@gmail.com)
 * @since 6/11/2013
 */
public class DataSubsetVersion implements Comparable<DataSubsetVersion> {
    private final static int DATA_SUBSET_VERSION_KEYS_POOL_SIZE = 64;
    private final static Queue<DataSubsetVersion> DATA_SUBSET_VERSION_KEYS_POOL =
            new ArrayBlockingQueue<>(DATA_SUBSET_VERSION_KEYS_POOL_SIZE);
    private final static Map<DataSubsetVersion, DataSubsetVersion> DATA_SUBSET_VERSIONS_CACHE =
            new ConcurrentHashMap<>();

    static {
        for (int i = 0; i < DATA_SUBSET_VERSION_KEYS_POOL_SIZE; ++i) {
            DATA_SUBSET_VERSION_KEYS_POOL.offer(new DataSubsetVersion(0));
        }
    }

    private int version;

    private DataSubsetVersion(final int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    /**
     * <p>Returns <code>true</code> only if this instance and the instance passed in have the same version.</p>
     *
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

        DataSubsetVersion that = (DataSubsetVersion) o;

        return (version == that.version);
    }

    @Override
    public int hashCode() {
        return version;
    }

    /**
     * <p>Implementation of the interface behavior that compares only this instance's version to the version held
     * by the instance passed in.</p>
     *
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final DataSubsetVersion that) {
        return version < that.version ? -1 : version > that.version ? 1 : 0;
    }

    public static DataSubsetVersion getInstance(final int version) {
        DataSubsetVersion dataSubsetVersionKey = null;
        try {
            dataSubsetVersionKey = DATA_SUBSET_VERSION_KEYS_POOL.poll();
            if (null == dataSubsetVersionKey) {
                // Because there is no key to reuse there is no caching benefit and so return a new instance
                return new DataSubsetVersion(version);
            }
            // Set the version on the key to the one we are looking for
            dataSubsetVersionKey.version = version;
            DataSubsetVersion dataSubsetVersion = DATA_SUBSET_VERSIONS_CACHE.get(dataSubsetVersionKey);
            if (null == dataSubsetVersion) {
                // Instance with this version not cached, so create a new instance for this version and cache it
                dataSubsetVersion = new DataSubsetVersion(version);
                DATA_SUBSET_VERSIONS_CACHE.put(dataSubsetVersion, dataSubsetVersion);
            }
            return dataSubsetVersion;
        } finally {
            if (null != dataSubsetVersionKey) {
                // Return the key to the keys cache for reuse
                DATA_SUBSET_VERSION_KEYS_POOL.offer(dataSubsetVersionKey);
            }
        }
    }
}

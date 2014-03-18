package my.example.code;

/**
 * Describes a set of behaviors used for reading in data center capacity data. It is intended that each implementation of this interface provide a new strategy for
 * how the data is stored and calculated while providing the best performance.
 * <p/>
 * @author David Williams (davidkwilliams@yahoo.com)
 * @since 6/11/2013
 */
public interface DataCenterInstancesReader<D extends DataCenterInstances> {
    /**
     * <p>Reads data center capacity data from an input source.</p>
     *
     * @throws RuntimeException likely caused by an i/o error of some type. Note to reader: this is present for API
     * clarity as it is not required for this class of exceptions
     */
     D read(Class<D> dataCenterInstancesClass) throws RuntimeException;
}

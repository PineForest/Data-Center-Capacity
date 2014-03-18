package my.example.code;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Implementation of a set of behaviors used for reading in data center capacity data from a file.
 * <p/>
 * @author David Williams (davidkwilliams@yahoo.com)
 * @since 3/15/2014
 */
public class FileDataCenterInstancesReader<D extends DataCenterInstances> implements
        DataCenterInstancesReader<D> {
    private String filename;

    public FileDataCenterInstancesReader(final String filename) {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public D read(Class<D> dataCenterInstancesClass) throws RuntimeException {
        final Path filenamePath = FileSystems.getDefault().getPath(filename);
        final Scanner dataScanner;
        try {
            dataScanner = new Scanner(filenamePath);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        String inputLine = dataScanner.nextLine();
        Scanner inputLineScanner = new Scanner(inputLine);
        final D dataCenterInstances = createInstance(dataCenterInstancesClass);
        final int countMachineGroups = inputLineScanner.nextInt();
        dataCenterInstances.setTotalMachineGroups(countMachineGroups);
        final int countDataSubsets = inputLineScanner.nextInt();
        dataCenterInstances.setTotalDataSubsets(countDataSubsets);
        int machineGroupIndex = 0;
        int dataSubsetIndex = 0;
        for (; dataScanner.hasNextLine(); ++machineGroupIndex) {
            inputLine = dataScanner.nextLine();
            inputLineScanner = new Scanner(inputLine);
            dataSubsetIndex = 0;
            for (; inputLineScanner.hasNextInt(); ++dataSubsetIndex) {
                final int version = inputLineScanner.nextInt();
                dataCenterInstances.addInstance(machineGroupIndex, dataSubsetIndex, version);
            }
            if (countDataSubsets != dataSubsetIndex) {
                break;
            }
        }
        if (countMachineGroups != machineGroupIndex || countDataSubsets != dataSubsetIndex) {
            throw new RuntimeException(String.format("Incorrect number of input versions encountered at machine " +
                    "group %d and data subset %d", countMachineGroups + 1, countDataSubsets + 1));
        }
        return dataCenterInstances;
    }

    /**
     * <p>Creates an instance of a class that implements the {@link my.example.code.DataCenterInstances} interface.
     * It assumes the class instance has a default constructor.</p>
     *
     * @param dataCenterInstancesClass a class that implements the {@link my.example.code.DataCenterInstances} interface
     * @return an instance of the class passed in
     * @throws RuntimeException if an exceptional condition results from use of reflection to invoke the default
     * constructor
     */
    protected D createInstance(Class<D> dataCenterInstancesClass) throws RuntimeException {
        Exception exception = null;
        try {
            final Constructor<D> dataCenterInstancesConstructor = dataCenterInstancesClass.getConstructor();
            return dataCenterInstancesConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            exception = e;
        } catch (InvocationTargetException e) {
            exception = e;
        } catch (InstantiationException e) {
            exception = e;
        } catch (IllegalAccessException e) {
            exception = e;
        }
        throw new RuntimeException("Unable to create a class of type " + dataCenterInstancesClass.getName(),
                exception);
    }
}

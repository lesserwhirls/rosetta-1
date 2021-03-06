package edu.ucar.unidata.rosetta.service.exceptions;

/**
 * Rosetta Data object exception.
 *
 * @author oxelson@ucar.edu
 */
public class RosettaDataException extends Exception{

    public RosettaDataException() {}

    public RosettaDataException(String message) {
        super(message);
    }
}

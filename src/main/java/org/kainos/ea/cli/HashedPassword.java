package org.kainos.ea.cli;

/**
 * Represents a hashed password from the database.
 */
public class HashedPassword {
    private final byte[] passwordHash;
    private final int iterations;
    private final byte[] passwordSalt;

    /**
     * Creates a new hashed password
     * @param passwordHash the hashed password
     * @param iterations the number of iterations used
     * @param passwordSalt the salt added to the hash
     */
    public HashedPassword(byte[] passwordHash, int iterations, byte[] passwordSalt) {
        this.passwordHash = passwordHash;
        this.iterations = iterations;
        this.passwordSalt = passwordSalt;
    }

    /**
     * Gets the password hash.
     * @return byte[] representing the password hash
     */
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    /**
     * Gets the number of iterations used.
     * @return the number of iterations used
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Gets the salt used for the hash.
     * @return byte[] representing the salt
     */
    public byte[] getPasswordSalt() {
        return passwordSalt;
    }
}

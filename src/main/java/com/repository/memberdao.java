package com.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.entity.memberdata;

/**
 * An interface for managing {@link memberdata} instances.
 *
 * @author Rob Winch
 *
 */
public interface memberdao {

    /**
     * Gets a {@link memberdata} for a specific {@link memberdata#getId()}.
     *
     * @param id
     *            the {@link memberdata#getId()} of the {@link memberdata} to find.
     * @return a {@link memberdata} for the given id. Cannot be null.
     * @throws EmptyResultDataAccessException
     *             if the {@link memberdata} cannot be found
     */
    memberdata getUser(int id);

    /**
     * Finds a given {@link memberdata} by email address.
     *
     * @param email
     *            the email address to use to find a {@link memberdata}. Cannot be null.
     * @return a {@link memberdata} for the given email or null if one could not be found.
     * @throws IllegalArgumentException
     *             if email is null.
     */
    memberdata findUserByEmail(String email);


    /**
     * Finds any {@link memberdata} that has an email that starts with {@code partialEmail}.
     *
     * @param partialEmail
     *            the email address to use to find {@link memberdata}s. Cannot be null or empty String.
     * @return a List of {@link memberdata}s that have an email that starts with given partialEmail. The returned value
     *         will never be null. If no results are found an empty List will be returned.
     * @throws IllegalArgumentException
     *             if email is null or empty String.
     */
    List<memberdata> findUsersByEmail(String partialEmail);

    /**
     * Creates a new {@link memberdata}.
     *
     * @param user
     *            the new {@link memberdata} to create. The {@link memberdata#getId()} must be null.
     * @return the new {@link memberdata#getId()}.
     * @throws IllegalArgumentException
     *             if {@link memberdata#getId()} is non-null.
     */
    int createUser(memberdata user);
}

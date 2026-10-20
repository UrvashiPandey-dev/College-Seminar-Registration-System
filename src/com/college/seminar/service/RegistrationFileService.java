
package com.college.seminar.service;

import com.college.seminar.model.Registration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationFileService {

    public void exportRegistrations(
            List<Registration> registrations, String filePath)
            throws IOException {

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            out.writeObject(new ArrayList<>(registrations));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Registration> importRegistrations(String filePath)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filePath))) {

            Object data = in.readObject();

            if (!(data instanceof List<?> list)
                    || list.stream().anyMatch(
                    item -> !(item instanceof Registration))) {
                throw new IOException("Invalid registration backup file");
            }

            return (List<Registration>) data;
        }
    }
}

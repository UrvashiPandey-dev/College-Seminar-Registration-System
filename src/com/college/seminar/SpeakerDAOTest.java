
package com.college.seminar;

import com.college.seminar.dao.SpeakerDAO;
import com.college.seminar.model.Speaker;

public class SpeakerDAOTest {

    public static void main(String[] args) {
        SpeakerDAO dao = new SpeakerDAO();

        try {
            Speaker speaker = new Speaker(
                "TESTSP001",
                "Test Speaker",
                "test.speaker@example.com",
                "Test Organization",
                "Java"
            );

            System.out.println("Add : " + dao.addSpeaker(speaker));

            speaker.setName("Updated Test Speaker");
            System.out.println("Update : " + dao.updateSpeaker(speaker));

            System.out.println("\nAll Speakers:");

            for (Speaker s : dao.getAllSpeakers()) {
                System.out.println(
                    s.getSpeakerId() + " | " +
                    s.getName() + " | " +
                    s.getEmail() + " | " +
                    s.getOrganization() + " | " +
                    s.getExpertise()
                );
            }

            System.out.println(
                "\nDelete : " + dao.deleteSpeaker("TESTSP001")
            );
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
